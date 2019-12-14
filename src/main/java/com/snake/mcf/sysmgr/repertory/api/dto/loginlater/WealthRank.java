package com.snake.mcf.sysmgr.repertory.api.dto.loginlater;

import java.io.Serializable;

import lombok.Data;

@Data
public class WealthRank implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer rankNum;
	private Integer rankValue;
	private Long userId;
	private Long gameId;
	private String nickName;
	private String faceUrl;
	private Integer faceId;
	private Long gold;
	private Integer diamond;
	private Integer awardTicket;
	private Integer dateId;
	private Integer typeId;
}
