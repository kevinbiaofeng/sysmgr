package com.snake.mcf.sysmgr.service.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.snake.mcf.sysmgr.repertory.api.form.MobileLoginLaterForm;
import com.snake.mcf.sysmgr.repertory.api.vo.loginlater.UserLoginDataLaterVO;

public interface MobileLoginLaterService {
	/**
	  * 用户登陆之后
	 * @return
	 */
	public UserLoginDataLaterVO getMobileLoginLater(MobileLoginLaterForm mobileLoginLaterForm, String localURL, Integer apiVersion);

	/**
	 * 获取排行榜
	 * @param _userId 用户Id 非必填
	 * @param type 1:财富排行榜    2：胜局排行榜
	 * @return
	 */
	public Map<String, Object> getDayRankData(String _userId, String typeId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 排行榜奖励
	 */
	public Map<String, Object> receiveRankingAward(String _userId, String dateId, String typeId, String ip, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
}
