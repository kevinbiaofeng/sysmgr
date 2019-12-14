package com.snake.mcf.sysmgr.service.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.sysmgr.mapper.api.GameListMapper;
import com.snake.mcf.sysmgr.repertory.api.dto.gamelist.GameListConfigInfo;
import com.snake.mcf.sysmgr.repertory.api.form.GameListForm;
import com.snake.mcf.sysmgr.service.api.GameListService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@SuppressWarnings("all")
public class GameListServiceImpl implements GameListService {
	@Autowired
    private GameListMapper gameListMapper;
	
	public Map<String, Object> getGameList(String type, Integer apiVersion) {
		List<List<?>> result = gameListMapper.callGetMobileGameAndVersion();
		Map<String, Object> data = new HashMap<String, Object>();
		//获取大厅版本
		if(result != null && result.size() > 0) {
			GameListConfigInfo ci = (GameListConfigInfo)result.get(0).get(0);
			data.put("apiVersion", apiVersion);
			data.put("valid", true);
			data.put("dateTime", System.currentTimeMillis());
			
			if(null != ci) {
				data.put("downloadUrl", ci.getField1());
				data.put("clientVersion", ci.getField2());
				data.put("resVersion", ci.getField3());
				data.put("iosUrl", ci.getField4());
				
				if(!"1".equals(type)) {
					data.put("downloadUrlFor818", ci.getField7());
					data.put("clientVersionFor818", ci.getField8());
					data.put("resVersionFor818", ci.getField9());
					data.put("iosUrlFor818", ci.getField10());
				}
			}
			data.put("gameList", result.get(1));
			return data;
		}
		return null;
	}
}
