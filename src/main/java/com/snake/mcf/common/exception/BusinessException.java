package com.snake.mcf.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 业务异常
 * 
 * @ClassName:  BusinessException   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:28:08   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class BusinessException extends FrameworkException {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus; // 请求http服务状态

    private Integer httpStatusCode; // 返回错误状态码

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message,Integer httpStatusCode) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public BusinessException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String fmt, Object... args) {
        super(fmt, args);
    }

    public BusinessException(String fmt,HttpStatus httpStatus, Object... args) {
        super(fmt, args);
        this.httpStatus = httpStatus;
    }

    public BusinessException(String fmt,Integer httpStatusCode, Object... args) {
        super(fmt, args);
        this.httpStatusCode = httpStatusCode;
    }

    public BusinessException(Throwable cause, String fmt, Object... args) {
        super(cause, fmt, args);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }



}
