package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;

import lombok.Data;

@Data
public class ConfigInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String phone;
	private String weiXin;
	private String qq;
	private String link;
}
