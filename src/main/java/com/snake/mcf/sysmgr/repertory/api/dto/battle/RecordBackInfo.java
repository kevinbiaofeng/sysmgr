package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;

import lombok.Data;
/**
 * 战绩详情
 */
@Data
public class RecordBackInfo implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private Integer gamesNum;

	private Integer roomId;

	private Long userId;

	private Integer score;

	private String id;
}
