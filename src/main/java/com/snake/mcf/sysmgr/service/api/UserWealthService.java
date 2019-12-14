package com.snake.mcf.sysmgr.service.api;

import java.lang.reflect.InvocationTargetException;

import com.snake.mcf.sysmgr.repertory.api.vo.wealth.UserWealthVO;

public interface UserWealthService {
	
	/**
	 * 获取用户财务信息
	 * @param customId
	 * @return
	 */
	public UserWealthVO getUserWealth(Long userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
}
