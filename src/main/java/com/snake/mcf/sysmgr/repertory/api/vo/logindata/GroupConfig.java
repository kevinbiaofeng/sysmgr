package com.snake.mcf.sysmgr.repertory.api.vo.logindata;

import java.io.Serializable;
import lombok.Data;

@Data
public class GroupConfig implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;
	
	private Integer maxMemberCount;
	
	private Integer maxCreateGroupCount;
	
	private Integer createGroupTakeIngot;
	
	private Integer createGroupDeductIngot;
	
	private Integer maxJoinGroupCount;
	
	private Integer groupPayType;
	
	private Integer groupPayTypeChange;
	
	private Integer groupRoomType;
}
