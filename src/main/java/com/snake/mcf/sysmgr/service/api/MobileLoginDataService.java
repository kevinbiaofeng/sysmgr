package com.snake.mcf.sysmgr.service.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.snake.mcf.sysmgr.repertory.api.vo.logindata.UserLoginDataVO;

public interface MobileLoginDataService {
	/**
	  *  用户登陆获取信息
	 * @return
	 */
	public UserLoginDataVO getMobileLoginData(String platformType, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
	/**
	  *  获取玩家分享奖励
	 * @return
	 */
	public Map<String, Object> getTimesReward(String userId, String _strClientIp, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 获取分享奖励
	 * @param userId
	 * @param apiVersion
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Map<String, Object> getShareReward(String userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
}
