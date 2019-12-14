package com.snake.mcf.sysmgr.repertory.api.dto.loginlater;

import java.io.Serializable;

import lombok.Data;

@Data
public class RankingConfig implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long configId;
	private Integer typeId;
	private Integer rankType;
	private Long gold;
	private Integer diamond;
	private Integer awardTicket;
}
