package com.snake.mcf.sysmgr.service.api;

import java.util.Map;

public interface UserFaceService {
	/**
	 * 获取用户当前设置头像
	 * @return
	 */
	public Map<String, Object> getUserFaceByCustomId(String userId, String customId, Integer apiVersion);
	
	/**
	 * 设置用户头像
	 * @param customId
	 * @return
	 */
	public Map<String, Object> setUserFaceByCustomId(String userId, String customId, String faceUrl, Integer apiVersion);
	
	/**
	 * 获取用户头像列表
	 * @param customId
	 * @return
	 */
	public Map<String, Object> getUserFaceList(Integer page, Integer count, Integer apiVersion);
}
