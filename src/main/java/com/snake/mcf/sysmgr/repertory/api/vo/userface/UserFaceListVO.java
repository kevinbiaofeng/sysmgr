package com.snake.mcf.sysmgr.repertory.api.vo.userface;

import java.io.Serializable;
import java.util.List;

import com.snake.mcf.sysmgr.repertory.api.vo.BaseAPIVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserFaceListVO extends BaseAPIVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer total;
	
	private Integer page;
	
	private List<String> lists;
}
