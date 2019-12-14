package com.snake.mcf.sysmgr.repertory.api.dto.rank;

import java.io.Serializable;

import lombok.Data;

@Data
public class GameRankData implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long rankNum;
	private Long userId;
	private Long gameId;
	private String nickName;
	private String faceUrl;
	private Integer faceId;
	private String underWrite;
	private Long WinCount;
	private Integer gender;
}
