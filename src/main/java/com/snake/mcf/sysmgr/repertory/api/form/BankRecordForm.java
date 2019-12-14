package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class BankRecordForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private Integer number = 20; //每页显示多少条
	
	private Integer page = 1; //当前页
}
