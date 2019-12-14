package com.snake.mcf.sysmgr.repertory.api.vo.loginlater;

import java.io.Serializable;
import java.util.List;

import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.RankingConfig;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.SpreadConfig;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.WealthRank;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.WinCountRank;
import com.snake.mcf.sysmgr.repertory.api.vo.BaseAPIVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserLoginDataLaterVO extends BaseAPIVO implements Serializable{

	private static final long serialVersionUID = 4061239869302071136L;
	
	private String shareLink;
	
	private String u3dShareLink;
	
	private boolean hasGrant;
	
	private Integer grantDiamond = 0;
	
	private Integer grantGold = 0;
	
	private Integer friendCount;
	
	private List<SpreadConfig> spreadList;
	
	private List<WealthRank> wealthRank;
	
	private List<WinCountRank> winCountRank;
	
	private List<RankingConfig> rankConfig;
}
