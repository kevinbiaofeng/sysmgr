package com.snake.mcf.common.web.advice;

import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.web.result.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 */

@Slf4j
@RestControllerAdvice
public class BusinessHandlerExceptionAdvice {

    /**
     * 400 - Bad Request
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value= BusinessException.class)
    public ResponseEntity<Result> globalHandlerException(BusinessException e){
        log.error("未知业务异常,请联系管理员!---{}",e.getMessage());
        Result result = Result.fail(e.getMessage());
        // 处理业务异常
        AdviceMessage adviceMessage = new AdviceMessage();
        adviceMessage.setMsg(e.getMessage());
        adviceMessage.setHttpStatus(e.getHttpStatus());
        adviceMessage.setHttpStatusCode(e.getHttpStatusCode());
        result.setData(adviceMessage);

        //返回值定义
        ResponseEntity<Result> entity = null;
        if(adviceMessage.getHttpStatusCode() != null){
            entity = ResponseEntity.status(e.getHttpStatusCode()).body(result);
        }else if(adviceMessage.getHttpStatus() != null){
            entity = new ResponseEntity<>(result, adviceMessage.getHttpStatus());
        }else{
            entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(value= HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result> globalHandlerException(HttpRequestMethodNotSupportedException e){
        log.error("不支持当前请求方法,请联系管理员!---{}",e.getMessage());
        Result result = Result.fail(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(value= HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Result> globalHandlerException(HttpMediaTypeNotSupportedException e){
        log.error("不支持当前媒体类型,请联系管理员!---{}",e.getMessage());
        Result result = Result.fail(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 500 - Internal Server Error
     */
    @ExceptionHandler(value= Exception.class)
    public ResponseEntity<Result> globalHandlerException(Exception e){
        if(e instanceof BusinessException){
            return this.globalHandlerException(e);
        }
        e.printStackTrace();
        log.error("服务运行异常,请联系管理员!---{}",e.getMessage());
        Result result = Result.fail(e.getMessage());
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value= MultipartException.class)
    public ResponseEntity<Result> globalHandlerException(MultipartException e){
        log.error("单次上传大小超出限制!---{}", e.getMessage());
        Result result = Result.fail("单次上传大小不能超过2MB");
        return new ResponseEntity<>(result, HttpStatus.REQUEST_ENTITY_TOO_LARGE);
    }

    @ExceptionHandler(value= MaxUploadSizeExceededException.class)
    public ResponseEntity<Result> globalHandlerException(MaxUploadSizeExceededException e){
        log.error("MaxUploadSizeExceededException_单次上传大小超出限制!---{}", e.getMessage());
        Result result = Result.fail("单次上传大小不能超过2MB");
//        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.ok(result);
    }


    @ExceptionHandler(value= FileUploadBase.FileSizeLimitExceededException.class)
    public ResponseEntity<Result> globalHandlerException(FileUploadBase.FileSizeLimitExceededException e){
        log.error("FileUploadBase_单次上传大小超出限制!---{}", e.getMessage());
        Result result = Result.fail("单次上传大小不能超过2MB");
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
