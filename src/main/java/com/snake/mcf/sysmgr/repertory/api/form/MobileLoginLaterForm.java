package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MobileLoginLaterForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String userId;
}
