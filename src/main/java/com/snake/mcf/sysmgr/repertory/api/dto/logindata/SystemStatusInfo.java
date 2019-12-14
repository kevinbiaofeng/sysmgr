package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;

import lombok.Data;

@Data
public class SystemStatusInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String statusName;
	private Integer statusValue;
}
