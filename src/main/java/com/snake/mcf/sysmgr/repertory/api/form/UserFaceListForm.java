package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserFaceListForm implements Serializable{
	private static final long serialVersionUID = 1L;

	@Min(value = 1, message = "最小1")
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String page = "1"; // 页数
	
	@Min(value = 1, message = "最小1")
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String count = "1"; // 数量
	
}
