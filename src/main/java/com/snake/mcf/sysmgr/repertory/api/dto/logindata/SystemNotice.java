package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SystemNotice implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long noticeID;
	private String noticeTitle;
	private String moblieContent;
	private Date publisherTime;
	private Integer platformType;
}
