package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserFaceSetForm implements Serializable{
	private static final long serialVersionUID = 1L;

	@Min(value = 1, message = "最小1")
	private String userId;
	
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	@Min(value = 1, message = "最小1")
	private String customId;
	
	@NotNull(message = "不能为空")
	@NotEmpty(message = "不能为空")
	private String faceUrl; //设置头像图片路径
	
}
