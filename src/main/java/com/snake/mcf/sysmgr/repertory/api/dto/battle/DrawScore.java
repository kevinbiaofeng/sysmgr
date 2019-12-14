package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 游戏成绩
 */
@Data
public class DrawScore implements Serializable {
	private static final long serialVersionUID = 4061239869302071136L;

	private Long drawId;

	private Integer kindId;

	private Integer score;

	private Integer grade;

	private Integer revenue;

	private Date insertTime;
}
