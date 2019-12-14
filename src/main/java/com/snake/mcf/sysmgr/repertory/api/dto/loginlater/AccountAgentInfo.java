package com.snake.mcf.sysmgr.repertory.api.dto.loginlater;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountAgentInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long agentId;
	private Long GameId;
	private String agentDomain;
}
