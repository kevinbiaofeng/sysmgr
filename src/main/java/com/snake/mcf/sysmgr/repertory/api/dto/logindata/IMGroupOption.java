package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;

import lombok.Data;

@Data
public class IMGroupOption implements Serializable{
	private static final long serialVersionUID = 1L;
	private String OptionName;
	private Integer OptionValue;
}
