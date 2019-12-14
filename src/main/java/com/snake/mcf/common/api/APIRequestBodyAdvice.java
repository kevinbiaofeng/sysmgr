package com.snake.mcf.common.api;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.sysmgr.repertory.api.form.BaseRequestForm;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ControllerAdvice(basePackages = "com.snake.mcf.sysmgr.controller.api")
public class APIRequestBodyAdvice implements RequestBodyAdvice {
	
	@Autowired
    private ConfigResource configResource;
	
	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		BaseRequestForm form = (BaseRequestForm) body;
		try {
			String data = AESCBCUtils.decrypt(form.getData(), configResource.getApiAesKey(), true);
			
			StringBuffer sb = new StringBuffer();
			sb.append(data).append(configResource.getApiRequestMD5Key()).append(data).append(form.getDateTime());
			boolean md5Bool = MD5Utils.checkSign(String.valueOf(sb), form.getSign());
			if(!md5Bool) {
				String fromStr = GsonUtils.toString(form);
				log.error("验签失败，form：{} ", fromStr);
				return new BusinessException(fromStr , HttpStatus.BAD_GATEWAY);
			}
			form.setData(data);
		} catch (Exception e) {
			log.error("解密失败，解密串：{} ，错误信息： {}", form.getData(), e.getMessage());
			return e;
		}
		return form;
	}

	@Override
	public Object handleEmptyBody(@Nullable Object obj, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
		return obj;
	}
	
    @ExceptionHandler(value= Exception.class)
    public ResponseEntity<Object> globalHandlerException(Exception e){
        if(e instanceof IllegalStateException) {
        	//解密失败
        	return new ResponseEntity<Object>(new ResultResp(ResultCode.FORBIDDEN, ResultCode.FORBIDDEN.msg()), HttpStatus.OK);
        }
        log.error("服务运行异常,请联系管理员!---{}",e.getMessage());
        return new ResponseEntity<Object>(new ResultResp(ResultCode.EXCEPTION, ResultCode.EXCEPTION.msg()), HttpStatus.OK);
    }
    
	
	/**
	 * Validator 参数校验异常处理
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = BindException.class)
	public ResponseEntity<Object> handleBindException(BindException ex) {
		FieldError err = ex.getFieldError();
		String message = "参数{".concat(err.getField()).concat("}").concat(err.getDefaultMessage());
		log.error("{} -> {}", err.getObjectName(), message);
		return new ResponseEntity<Object>(new ResultResp(ResultCode.PARAM_INVALID, message), HttpStatus.OK);
	}
	
	/**
	 * Validator 参数校验异常处理
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleBMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult err = ex.getBindingResult();
		String message = "参数{".concat(err.getFieldError().getField()).concat("}").concat(err.getFieldError().getDefaultMessage());
		log.error("{} -> {}", err.getObjectName(), message);
		return new ResponseEntity<Object>(new ResultResp(ResultCode.PARAM_INVALID, message), HttpStatus.OK);
	}
	
	/**
	 * Content-Type 不支持
	 * @return
	 */
	@ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
		String message = "Content-Type不支持{".concat(ex.getLocalizedMessage()).concat("}");
		log.error("{} -> {} -> {}", "Content-Type", "不支持", ex.getLocalizedMessage());
		return new ResponseEntity<Object>(new ResultResp(ResultCode.PARAM_INVALID, message), HttpStatus.OK);
	}
}
