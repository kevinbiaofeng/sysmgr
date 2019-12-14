package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BaseRequestForm implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 签名
	 */
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String sign;
	
	/**
	 * 时间戳
	 */
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String dateTime;
	
	/**
	 * 数据
	 */
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String data;
}
