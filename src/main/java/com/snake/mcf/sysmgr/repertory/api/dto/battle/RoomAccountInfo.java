package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;

import lombok.Data;
/**
 *  房主、用户信息
 */
@Data
public class RoomAccountInfo implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private Long userId;
	
	private Long gameId;
	
	private String nickName;
}
