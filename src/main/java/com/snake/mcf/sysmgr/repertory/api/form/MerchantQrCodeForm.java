package com.snake.mcf.sysmgr.repertory.api.form;

import java.io.Serializable;

import lombok.Data;

@Data
public class MerchantQrCodeForm implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userId;	//用户Id
	
	private String gameId;	//用户GameId
	
	private String merchant;	//商户号
}
