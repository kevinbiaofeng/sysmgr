package com.snake.mcf.sysmgr.repertory.api.dto.gamelist;

import java.io.Serializable;

import lombok.Data;

@Data
public class GameList implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private Long kindId;

	private String kindName;

	private Integer typeId;

	private String moduleName;

	private Long clientVersion;
	
	private Integer resVersion;
	
	private Long sortId;
	
	private Integer kindMark;
	
	private Integer nullity;
}
