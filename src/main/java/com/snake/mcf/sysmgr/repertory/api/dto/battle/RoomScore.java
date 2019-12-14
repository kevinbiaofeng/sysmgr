package com.snake.mcf.sysmgr.repertory.api.dto.battle;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 返回战绩
 */
@Data
public class RoomScore implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;

	private String personalRoomGuid;
	
	private Integer kindId;
	
	private Integer roomId;
	
	private Integer score;
	
	private Integer cellScore;
	
	private Date startTime;
	
	private Long roomHostId;
	
	private Integer playBackCode;
	
	private Integer chairId;
}
