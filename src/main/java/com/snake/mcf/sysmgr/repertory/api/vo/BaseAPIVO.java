package com.snake.mcf.sysmgr.repertory.api.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseAPIVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * API版本，固定值
	 */
	private Integer apiVersion;
	/**
	 * API状态
	 */
	private boolean valid;
	/**
	 * MD5签名
	 */
	private String sign;
	/**
	 * 时间戳
	 */
	private Long dateTime;
}
