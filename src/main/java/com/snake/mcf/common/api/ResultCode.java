package com.snake.mcf.common.api;

public enum ResultCode {
	/** 操作成功 */
	SUCCESS("1", "成功"),

	/** 操作失败 */
	FAIL("0", "失败"),
	
	/** 操作失败 */
	NULL("1001", "数据不存在"),

	/** 系统发生异常 */
	EXCEPTION("999", "服务器未知错误"),
	
	/** 没有权限 */
	FORBIDDEN("1000", "没有权限"),
	
	/** 参数错误 */
	PARAM_INVALID("1002", "参数错误");
	
	private String code;
	private String msg;
	
	private ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String code() {
		return code;
	}

	public String msg() {
		return msg;
	}
}
