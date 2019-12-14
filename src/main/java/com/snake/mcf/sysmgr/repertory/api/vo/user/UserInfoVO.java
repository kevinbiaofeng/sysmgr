package com.snake.mcf.sysmgr.repertory.api.vo.user;

import java.io.Serializable;

import com.snake.mcf.sysmgr.repertory.api.vo.BaseAPIVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserInfoVO extends BaseAPIVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer userId;
	
	private Integer gameId;
	
	private Integer customId;
	
	private String nickName;
	
	private String underWrite;
	
	private String lastLogonIp;
	
	private String placeName;
}
