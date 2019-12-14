package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class MobileLoginDataForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String platformType = "0";
}
