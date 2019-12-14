package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;

import lombok.Data;
/**
 * 游戏信息
 */
@Data
public class RecordDrawInfo implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private Long drawId;

	private Integer userCount;

	private Integer androidCount;
}
