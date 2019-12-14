package com.snake.mcf.sysmgr.repertory.api.vo.logindata;

import java.io.Serializable;
import lombok.Data;

@Data
public class SystemConfig implements Serializable{

	private static final long serialVersionUID = 4061239869302071136L;
	
	private Integer isOpenMall = 0;
	
	private Integer isPayBindSpread = 0;
	
	private Integer bindSpreadPresent = 0;
	
	private Integer rankingListType = 0;
	
	private Integer payChannel = 0;
	
	private Integer diamondBuyPropCount = 0;
	
	private Integer realNameAuthentPresent = 0;
	
	private Integer effectiveFriendGame = 0;
	
	private Integer iOSNotStorePaySwitch = 0;
	
	private Integer goldBuyPropCount = 0;
	
	private Integer enjoinInsure = 0;
	
	private Integer transferStauts = 0;
	
	private Integer mobileBattleRecordMask = 0;
	
	private Integer isOpenGameSignIn = 0;
	
	private Integer isOpenRedemptionCenter = 0;
}
