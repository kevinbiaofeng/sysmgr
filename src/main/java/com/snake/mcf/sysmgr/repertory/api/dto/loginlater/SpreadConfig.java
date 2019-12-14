package com.snake.mcf.sysmgr.repertory.api.dto.loginlater;

import java.io.Serializable;

import lombok.Data;

@Data
public class SpreadConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer configId;
	private Integer spreadNum;
	private Integer presentDiamond;
	private Integer presentPropId;
	private String presentPropName;
	private Integer presentPropNum;
	private Integer flag;
}
