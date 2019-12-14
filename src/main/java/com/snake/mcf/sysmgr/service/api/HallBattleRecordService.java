package com.snake.mcf.sysmgr.service.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface HallBattleRecordService {
	
	/**
	 * 获取用户对战、金币场战绩信息
	 * @param customId
	 * @return
	 */
	public Map<String, Object> getHallBattleRecord(Long userId, Integer type, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
	
	/**
	 * 获取亲友圈战绩信息
	 * @param customId
	 * @return
	 */
	public Map<String, Object> getGourpBattleRecord(Long userId, Integer groupId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
}
