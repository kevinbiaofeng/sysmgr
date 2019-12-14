package com.snake.mcf.sysmgr.repertory.api.dto.wealth;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserWealth implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long score;
	private Long insure;
	private Long diamond;
	private Long awardTicket;
}
