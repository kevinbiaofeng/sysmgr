package com.snake.mcf.sysmgr.repertory.api.dto.loginlater;

import java.io.Serializable;

import lombok.Data;

@Data
public class RecordRegisterGrant implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer grantDiamond;
	private Integer grantGold;
}
