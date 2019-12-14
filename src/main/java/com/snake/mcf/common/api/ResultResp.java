package com.snake.mcf.common.api;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller 统一定义返回类
 */
@Slf4j
public class ResultResp {
	public static final ResultResp SUCCESS = new ResultResp(ResultCode.SUCCESS);
	public static final ResultResp FAIL = new ResultResp(ResultCode.FAIL);
	public static final ResultResp FORBIDDEN = new ResultResp(ResultCode.FORBIDDEN);
	public static final ResultResp NULL = new ResultResp(ResultCode.NULL);
	public static final ResultResp EXCEPTION = new ResultResp(ResultCode.EXCEPTION);
	public static final ResultResp PARAM_INVALID = new ResultResp(ResultCode.PARAM_INVALID);
	
	/**
	 * 返回代码
	 */
	private String code;

	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private Object data;
	
	public Object getData() {
		if(data == null) {
			data = "";
		}
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 默认构造，返回操作正确的返回代码和信息
	 */
	public ResultResp() {
		this.setCode(ResultCode.SUCCESS.code());
		this.setMessage(ResultCode.SUCCESS.msg());
	}

	/**
	 * 构造一个返回特定代码的ResultResp对象
	 * 
	 * @param code
	 */
	public ResultResp(ResultCode code) {
		this.setCode(code.code());
		this.setMessage(code.msg());
	}

	public ResultResp(String code, String message) {
		super();
		this.setCode(code);
		this.setMessage(message);
	}

	/**
	 * 默认值返回，默认返回正确的code和message
	 * 
	 * @param data
	 */
	public ResultResp(Object data) {
		ResultCode rc = data == null ? ResultCode.NULL : ResultCode.SUCCESS;
		this.setCode(rc.code());
		this.setMessage(rc.msg());
		this.setData(data);
	}

	/**
	 * 构造返回代码，以及自定义的错误信息
	 * 
	 * @param code
	 * @param message
	 */
	public ResultResp(ResultCode code, String message) {
		this.setCode(code.code());
		this.setMessage(message);
	}

	/**
	 * 构造自定义的code，message，以及data
	 * 
	 * @param code
	 * @param message
	 * @param data
	 */
	public ResultResp(ResultCode code, String message, Object data) {
		this.setCode(code.code());
		this.setMessage(message);
		this.setData(data);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		// request请求响应的时候，一定会走到这里，判断如果code不是成功状态，就输出日志
		if (!ResultCode.SUCCESS.code().equals(code))
			log.info("ResultResp={}", new Gson().toJson(this));
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
