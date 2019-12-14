package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;

import lombok.Data;
/**
 * 游戏配置
 */
@Data
public class GameKindItem implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private Integer kindId;

	private String kindName;
}
