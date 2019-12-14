package com.snake.mcf.sysmgr.repertory.api.dto.share;

import java.io.Serializable;

import lombok.Data;

@Data
public class TimesReward implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long rst;
	private Long timeShareDiamond;
	private Long timeShareGold;
}
