package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReceiveRankingAwardForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userId;   //用户标识
	
	private String dateId;   //时间标识
	
	private String typeId;   //排行榜类型
	
	private String ip;   //领取地址
}
