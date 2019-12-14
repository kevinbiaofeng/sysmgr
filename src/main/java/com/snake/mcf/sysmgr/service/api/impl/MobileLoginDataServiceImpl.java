package com.snake.mcf.sysmgr.service.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.sysmgr.mapper.api.MobileLoginDataMapper;
import com.snake.mcf.sysmgr.mapper.api.TimesRewardMapper;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.ActivityList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.AdsAlertList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.AdsList;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.ConfigInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.IMGroupOption;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.SystemNotice;
import com.snake.mcf.sysmgr.repertory.api.dto.logindata.SystemStatusInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.share.TimesReward;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.CustomerService;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.GroupConfig;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.MobileConfig;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.SystemConfig;
import com.snake.mcf.sysmgr.repertory.api.vo.logindata.UserLoginDataVO;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.entity.Shareconfig;
import com.snake.mcf.sysmgr.repertory.mapper.ConfiginfoMapper;
import com.snake.mcf.sysmgr.service.api.MobileLoginDataService;
import com.snake.mcf.sysmgr.service.goldmgr.ShareConfigService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
@SuppressWarnings("all")
public class MobileLoginDataServiceImpl implements MobileLoginDataService {
	@Autowired
    private MobileLoginDataMapper mobileLoginDataMapper;
	@Autowired
    private TimesRewardMapper timesRewardMapper;
	@Autowired
    private ShareConfigService shareConfigService;
	
	@Autowired
    private ConfiginfoMapper configinfoMapper;
	
	public UserLoginDataVO getMobileLoginData(String platformType, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
		List<List<?>> result = mobileLoginDataMapper.callGetMobileLoginData(Integer.valueOf(platformType));
		
		if(result != null && result.size() > 0) {
			UserLoginDataVO dto = new UserLoginDataVO();
			dto.setApiVersion(apiVersion);
			dto.setValid(true);
			dto.setDateTime(System.currentTimeMillis());
//			dto.setSign(sign);
			
			/** 装配 systemConfig 网站站点配置 **/
			List<SystemStatusInfo> resultSystemConfigList = (List<SystemStatusInfo>)result.get(0);
			dto.setSystemConfig(this.setSystemConfig(resultSystemConfigList));
			
			/** 配置 图片域名地址 imageServerHost **/
			Map<String, Object> resultImageServerHost = (Map<String, Object>)result.get(10).get(0);
			String domainUrl = String.valueOf(resultImageServerHost.get("picUrlPrefix"));
			dto.setImageServerHost(domainUrl);
			
			/** 装配 groupconfig 亲友圈配置 **/
			List<IMGroupOption> resultIMGroupOptionList = (List<IMGroupOption>)result.get(9);
			dto.setGroupConfig(this.setGroupConfig(resultIMGroupOptionList));
			
			/** 配置客户服务系统 **/
			ConfigInfo resultConfigInfo = (ConfigInfo)result.get(1).get(0);
			dto.setCustomerService(this.setCustomerService(resultConfigInfo, domainUrl));
			
			/** 装配系统公告列表 NoticeMobile **/
			List<SystemNotice> resultSystemNotice = (List<SystemNotice>)result.get(2);
			dto.setSystemNotice(resultSystemNotice);
			
			/** 装配手机固定位广告图 **/
			List<AdsList> resultAdsList = (List<AdsList>)result.get(3);
			for (AdsList adsList : resultAdsList) {
				adsList.setResourceURL(domainUrl + adsList.getResourceURL());
			}
			dto.setAdsList(resultAdsList);
			
			/** 装配手机弹出广告图  **/
			List<AdsAlertList> resultAdsAlertList = (List<AdsAlertList>)result.get(4);
			for (AdsAlertList adsAlertList : resultAdsAlertList) {
				adsAlertList.setResourceURL(domainUrl + adsAlertList.getResourceURL());
			}
			dto.setAdsAlertList(resultAdsAlertList);
			
			/** 装配手机活动广告  **/
			List<ActivityList> resultActivityList = (List<ActivityList>)result.get(7);
			for (ActivityList activityList : resultActivityList) {
				activityList.setResourceURL(domainUrl + activityList.getResourceURL());
			}
			dto.setActivityList(resultActivityList);
			
			Example example = new Example(Configinfo.class);
	        Example.Criteria criteria = example.createCriteria();
	        criteria.andEqualTo("configKey", "MobilePlatformVersion");
			Configinfo configinfo = configinfoMapper.selectOneByExample(example);
			MobileConfig mobileConfig = null;
			if(configinfo != null){
				mobileConfig = new MobileConfig();
				BeanUtils.copyProperties(mobileConfig, configinfo);
			}
			dto.setMobileConfig(mobileConfig);
			return dto;
		}else{
			return null;
		}
	}
	
	/**
	   *    根据结果集设置客户服务系统VO对象  customerService
	 * @param resultSystemConfigList
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private CustomerService setCustomerService(ConfigInfo resultConfigInfo, String domainUrl) throws IllegalAccessException, InvocationTargetException {
		CustomerService cs = new CustomerService();
		BeanUtils.copyProperties(cs, resultConfigInfo);
		cs.setLink(domainUrl + "/site/qrcustomer.png");
		return cs;
	}
	
	/**
	  *    根据亲友圈结果集设置VO对象  Groupconfig
	 * @param resultSystemConfigList
	 * @return
	 */
	private GroupConfig setGroupConfig(List<IMGroupOption> resultIMGroupOptionList) {
		GroupConfig sysconfig = new GroupConfig();
		for (IMGroupOption imGroupOption : resultIMGroupOptionList) {
			switch (imGroupOption.getOptionName()) {
			case "MaxMemberCount":
				sysconfig.setMaxMemberCount(imGroupOption.getOptionValue());
                break;
            case "MaxCreateGroupCount":
            	sysconfig.setMaxCreateGroupCount(imGroupOption.getOptionValue());
                break;
            case "CreateGroupTakeIngot":
            	sysconfig.setCreateGroupTakeIngot(imGroupOption.getOptionValue());
                break;
            case "CreateGroupDeductIngot":
            	sysconfig.setCreateGroupDeductIngot(imGroupOption.getOptionValue());
                break;
            case "MaxJoinGroupCount":
            	sysconfig.setMaxJoinGroupCount(imGroupOption.getOptionValue());
                break;
            case "GroupPayType":
            	sysconfig.setGroupPayType(imGroupOption.getOptionValue());
                break;
            case "GroupPayTypeChange":
            	sysconfig.setGroupPayTypeChange(imGroupOption.getOptionValue());
                break;
            case "GroupRoomType":
            	sysconfig.setGroupRoomType(imGroupOption.getOptionValue());
                break;
			}
			
		}
		return sysconfig;
	}
	
	/**
	   *    根据网站站点结果集设置VO对象  systemConfig
	 * @param resultSystemConfigList
	 * @return
	 */
	private SystemConfig setSystemConfig(List<SystemStatusInfo> resultSystemConfigList) {
		SystemConfig sysconfig = new SystemConfig();
		for (SystemStatusInfo systemStatusInfo : resultSystemConfigList) {
			switch (systemStatusInfo.getStatusName()) {
				case "JJOpenMobileMall":
					sysconfig.setIsOpenMall(systemStatusInfo.getStatusValue());
					break;
				case "JJPayBindSpread":
					sysconfig.setIsPayBindSpread(systemStatusInfo.getStatusValue());
					break;
				case "JJBindSpreadPresent":
					sysconfig.setBindSpreadPresent(systemStatusInfo.getStatusValue());
					break;
				case "JJRankingListType":
					sysconfig.setRankingListType(systemStatusInfo.getStatusValue());
					break;
				case "JJPayChannel":
					sysconfig.setPayChannel(systemStatusInfo.getStatusValue());
					break;
				case "JJDiamondBuyProp":
					sysconfig.setDiamondBuyPropCount(systemStatusInfo.getStatusValue());
					break;
				case "JJRealNameAuthentPresent":
					sysconfig.setRealNameAuthentPresent(systemStatusInfo.getStatusValue());
					break;
				case "JJEffectiveFriendGame":
					sysconfig.setEffectiveFriendGame(systemStatusInfo.getStatusValue());
					break;
				case "IOSNotStorePaySwitch":
					sysconfig.setIOSNotStorePaySwitch(systemStatusInfo.getStatusValue());
					break;
				case "JJGoldBuyProp":
					sysconfig.setGoldBuyPropCount(systemStatusInfo.getStatusValue());
					break;
				case "EnjoinInsure":
					sysconfig.setEnjoinInsure(systemStatusInfo.getStatusValue());
					break;
				case "TransferStauts":
					sysconfig.setTransferStauts(systemStatusInfo.getStatusValue());
					break;
				case "MobileBattleRecordMask":
					sysconfig.setMobileBattleRecordMask(systemStatusInfo.getStatusValue());
					break;
				case "OpenGameSignIn":
					sysconfig.setIsOpenGameSignIn(systemStatusInfo.getStatusValue());
					break;
				case "IsRedemptionCenter":
					sysconfig.setIsOpenRedemptionCenter(systemStatusInfo.getStatusValue());
					break;
			}
			
		}
		return sysconfig;
	}
	
	
	public Map<String, Object> getTimesReward(String userId, String _strClientIp, Integer apiVersion) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		List<TimesReward> list = timesRewardMapper.callTimesReward(userId, _strClientIp);
		if(list != null && list.size() > 0) {
			TimesReward timesReward = list.get(0);
			data.put("rst", timesReward.getRst());
			data.put("timeShareDiamond", timesReward.getTimeShareDiamond());
			data.put("timeShareGold", timesReward.getTimeShareGold());
		}else {
			data.put("rst", -1);
			data.put("timeShareDiamond", 0);
			data.put("timeShareGold", 0);
		}
		return data;
	}
	
	public Map<String, Object> getShareReward(String userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		List<Shareconfig> list = shareConfigService.getShareRewardList();
		List<Map<String, Object>> dataList = null;
		if(list != null && list.size() > 0) {
			dataList = new ArrayList<Map<String,Object>>();
			Map<String, Object> mapData = null;
			for (Shareconfig shareConfig : list) {
				mapData = new HashMap<String, Object>();
				mapData.put("id", shareConfig.getId());
				mapData.put("dayShareLmt", shareConfig.getDaysharelmt());
				mapData.put("timeShareGold", shareConfig.getTimesharegold());
				mapData.put("timeShareDiamond", shareConfig.getTimesharediamond());
				mapData.put("nullity", shareConfig.getNullity());
				dataList.add(mapData);
			}
			data.put("list", dataList);
		}
		return data;
	}
}
