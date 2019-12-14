package com.snake.mcf.sysmgr.repertory.api.vo.bankrecord;

import java.io.Serializable;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class BankRecordVO implements Serializable {
	private Integer recordId;
	private Integer kindId;
	private Integer serverId;
	private Integer sourceUserId;
	private Long sourceGold;
	private Long sourceBank;
	private Integer targetUserId;
	private Long targetGold;
	private Long targetBank;
	private Long swapScore;
	private Long revenue;
	private Integer isGamePlaza;
	private Integer tradeType;
	private String clientIP = "";
	private Long collectDate;
	private String collectNote = "";
	private String transferAccounts = "";
	private String tradeTypeDescription = "";
	private String transferNickName = "";
}
