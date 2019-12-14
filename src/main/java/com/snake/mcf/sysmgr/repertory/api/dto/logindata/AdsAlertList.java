package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;

import lombok.Data;

@Data
public class AdsAlertList implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private String resourceURL;
	private String linkURL;
	private Integer sortID;
	private Integer platformType;
	private String fileMD5;
}
