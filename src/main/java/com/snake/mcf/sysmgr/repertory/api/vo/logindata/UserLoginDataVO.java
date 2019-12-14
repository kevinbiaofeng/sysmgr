package com.snake.mcf.sysmgr.repertory.api.vo.logindata;

import java.io.Serializable;
import java.util.List;

import com.snake.mcf.sysmgr.repertory.api.dto.logindata.ActivityList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.AdsAlertList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.AdsList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.SystemNotice;
import com.snake.mcf.sysmgr.repertory.api.vo.BaseAPIVO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserLoginDataVO extends BaseAPIVO implements Serializable{

	private static final long serialVersionUID = 4061239869302071136L;
	
	private SystemConfig systemConfig;
	
	private GroupConfig groupConfig;
	
	private CustomerService customerService;
	
	private List<SystemNotice> systemNotice;
	
	private List<AdsList> adsList;
	
	private List<AdsAlertList> adsAlertList;
	
	private List<ActivityList> activityList;
	
	private String imageServerHost;
	
	private MobileConfig mobileConfig;
}
