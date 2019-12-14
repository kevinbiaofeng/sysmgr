package com.snake.mcf.sysmgr.service.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;
import com.snake.mcf.sysmgr.mapper.api.CallGetDayRankingDataMapper;
import com.snake.mcf.sysmgr.mapper.api.MobileLoginLaterMapper;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.AccountAgentInfo;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.RankingConfig;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.RecordRegisterGrant;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.SpreadConfig;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.WealthRank;
import com.snake.mcf.sysmgr.repertory.api.dto.loginlater.WinCountRank;
import com.snake.mcf.sysmgr.repertory.api.dto.rank.GameRankData;
import com.snake.mcf.sysmgr.repertory.api.dto.rank.WealthRankData;
import com.snake.mcf.sysmgr.repertory.api.form.MobileLoginLaterForm;
import com.snake.mcf.sysmgr.repertory.api.vo.loginlater.UserLoginDataLaterVO;
import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.mapper.ConfiginfoMapper;
import com.snake.mcf.sysmgr.service.api.MobileLoginLaterService;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
@SuppressWarnings("all")
public class MobileLoginLaterServiceImpl implements MobileLoginLaterService {
	@Autowired
    private MobileLoginLaterMapper mobileLoginLaterMapper;
	
	@Autowired
    private CallGetDayRankingDataMapper callGetDayRankingDataMapper;
	
	@Autowired
    private ConfiginfoMapper configinfoMapper;
	
	@Autowired
    private ConfigResource configResource;
	
	public UserLoginDataLaterVO getMobileLoginLater(MobileLoginLaterForm mobileLoginLaterForm, String localURL, Integer apiVersion) {
		List<List<?>> result = mobileLoginLaterMapper.callGetMobileLoginLater(Long.valueOf(mobileLoginLaterForm.getUserId()));
		/** 获取网站配置 **/
		Example example = new Example(Configinfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("configKey", "WebSiteConfig");
		Configinfo configInfo = configinfoMapper.selectOneByExample(example);
		
		if(result != null && result.size() > 0) {
			UserLoginDataLaterVO dto = new UserLoginDataLaterVO();
			dto.setApiVersion(apiVersion);
			dto.setValid(true);
			dto.setDateTime(System.currentTimeMillis());
			
			/** 设置推广链接   **/
			List<AccountAgentInfo> accountAgentInfo = (List<AccountAgentInfo>) result.get(0);
			if(accountAgentInfo != null && accountAgentInfo.size() > 0) {
				String spreadLink = this.getSpreadLink(accountAgentInfo.get(0), configInfo, localURL);
				if(StringUtil.isNotEmpty(spreadLink)) {
					dto.setShareLink(spreadLink); //获取推广链接
				}
				String u3dShareLink = this.getU3DSpreadLink(accountAgentInfo.get(0), configInfo, localURL);
				if(StringUtil.isNotEmpty(u3dShareLink)) {
					dto.setU3dShareLink(u3dShareLink); //获取 U3D 推广链接
				}
			}
			/** 获取注册奖励  **/
			List<RecordRegisterGrant> recordRegisterGrantList = (List<RecordRegisterGrant>) result.get(1);
			if(recordRegisterGrantList != null && recordRegisterGrantList.size() > 0) {
				RecordRegisterGrant recordRegisterGrant = recordRegisterGrantList.get(0);
				Integer grantDiamond = recordRegisterGrant.getGrantDiamond();
				Integer grantGold = recordRegisterGrant.getGrantGold();
				dto.setGrantDiamond(grantDiamond); //钻石
				dto.setGrantGold(grantGold); //金币
				//是否包含注册奖励
				dto.setHasGrant(grantDiamond > 0 || grantGold > 0);
			}
			/** 获取用户好友总数  **/
			Map<String, Object> friendCount = (Map<String, Object>)result.get(5).get(0);
			if(friendCount.get("total") != null) {
				dto.setFriendCount(Integer.valueOf(friendCount.get("total").toString()));
			}
			
			List<SpreadConfig> spreadConfigList = (List<SpreadConfig>)result.get(2);
			dto.setSpreadList(spreadConfigList);
			
			List<WealthRank> wealthRankList = (List<WealthRank>)result.get(3);
			dto.setWealthRank(wealthRankList);
			
			List<WinCountRank> winCountRankList = (List<WinCountRank>)result.get(4);
			dto.setWinCountRank(winCountRankList);
			
			List<RankingConfig> rankingConfigList = (List<RankingConfig>)result.get(6);
			dto.setRankConfig(rankingConfigList);
			return dto;
		}else {
			return null;
		}
	}
	
	/**
	 * 获取推广链接
	 * @return
	 */
	private String getSpreadLink(AccountAgentInfo accountAgentInfo, Configinfo configInfo, String localURL) {
		String domain = configInfo.getField4();
		domain = StringUtils.isNotEmpty(domain) ? domain : localURL; //主域名
		StringBuffer sb = new StringBuffer();
		if(accountAgentInfo.getAgentId() > 0) {
			sb.append(domain).append("/mobile/shareLink");
		}else {
			String[] domainStr = domain.split("\\.");
			if(domainStr.length != 3) {
				sb.append(domain).append("/mobile/shareLink?g=").append(accountAgentInfo.getGameId());
			}else {
				sb.append(domainStr[0].substring(0, domainStr[0].lastIndexOf("/") + 1))
				.append(accountAgentInfo.getGameId())
				.append(".")
				.append(domainStr[1])
				.append(".")
				.append(domainStr[2])
				.append("/mobile/shareLink");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取U3D推广链接
	 * @return
	 */
	private String getU3DSpreadLink(AccountAgentInfo accountAgentInfo, Configinfo configInfo, String localURL) {
		String domain = configInfo.getField4();
		domain = StringUtils.isNotEmpty(domain) ? domain : localURL; //主域名
		StringBuffer sb = new StringBuffer();
		if(accountAgentInfo.getAgentId() > 0) {
			sb.append(domain).append("/mobile/agentShareLink").append("?g=").append(accountAgentInfo.getGameId()).append("&y=u3d");
		}else {
			sb.append(domain).append("/mobile/shareLinkNew").append("?g=").append(accountAgentInfo.getGameId()).append("&y=u3d");
		}
		return sb.toString();
	}
	
	public Map<String, Object> getDayRankData(String _userId, String typeId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		List<List<?>> list = null;
		boolean isRank = false;
		if(typeId.equals("1")) {
			list = callGetDayRankingDataMapper.callGetDayWealthRankingData(Long.valueOf(_userId), Integer.valueOf(typeId));
			if(list != null && list.get(0) != null && list.get(0).size() > 0) {
				isRank = true;
				List<WealthRankData> mainList = (List<WealthRankData>)list.get(0);
				for (WealthRankData wealthRankData : mainList) {
					wealthRankData.setNickName(AESCBCUtils.decrypt(wealthRankData.getNickName(), configResource.getAesUserKey()));
				}
				
				List<WealthRankData> firstOne = (List<WealthRankData>)list.get(1);
				if(list.get(1) != null && list.get(1).size() > 0) {
					WealthRankData firstBean = firstOne.get(0);
					firstBean.setNickName(AESCBCUtils.decrypt(firstBean.getNickName(), configResource.getAesUserKey()));
					mainList.add(0, firstBean);
				}
				data.put("wealthRank", mainList);
			}
			
			
		}else if(typeId.equals("2")) {
			list = callGetDayRankingDataMapper.callGetDayGameRankingData(Long.valueOf(_userId), Integer.valueOf(typeId));
			if(list != null && list.get(0) != null && list.get(0).size() > 0) {
				isRank = true;
				List<GameRankData> mainList = (List<GameRankData>)list.get(0);
				for (GameRankData gameRankData : mainList) {
					gameRankData.setNickName(AESCBCUtils.decrypt(gameRankData.getNickName(), configResource.getAesUserKey()));
				}
				List<GameRankData> firstOne = (List<GameRankData>)list.get(1);
				if(list.get(1) != null && list.get(1).size() > 0) {
					GameRankData firstBean = firstOne.get(0);
					firstBean.setNickName(AESCBCUtils.decrypt(firstBean.getNickName(), configResource.getAesUserKey()));
					mainList.add(0, firstBean);
				}
				data.put("gameRank", mainList);
			}
		}
		data.put("isRank", isRank);
		return data;
	}
	
	public Map<String, Object> receiveRankingAward(String _userId, String dateId, String typeId, String ip, Integer apiVersion) throws IllegalAccessException, InvocationTargetException{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("apiVersion", apiVersion);
		data.put("valid", true);
		data.put("dateTime", System.currentTimeMillis());
		data.put("score", 0);
		data.put("diamond", 0);
		return data;
	}
}
