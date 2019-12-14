package com.snake.mcf.sysmgr.service.api;

import java.util.Map;

public interface GameListService {
	/**
	  * 游戏列表接口
	 * @return
	 */
	public Map<String, Object> getGameList(String type, Integer apiVersion);
}
