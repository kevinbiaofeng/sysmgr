package com.snake.mcf.sysmgr.service.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.sysmgr.mapper.api.UserWealthMapper;
import com.snake.mcf.sysmgr.repertory.api.dto.wealth.UserWealth;
import com.snake.mcf.sysmgr.repertory.api.vo.wealth.UserWealthVO;
import com.snake.mcf.sysmgr.service.api.UserWealthService;

@Service
public class UserWealthServiceImpl implements UserWealthService {

	@Autowired
    private UserWealthMapper userWealthMapper;

	@Override
	public UserWealthVO getUserWealth(Long userId, Integer apiVersion) throws IllegalAccessException, InvocationTargetException {
		List<List<?>> result = userWealthMapper.callGetUserWealth(userId);
		UserWealth uw = null;
		UserWealthVO data = new UserWealthVO();
		if(result != null && result.size() > 0) {
			uw = (UserWealth)result.get(0);
	    	if(uw != null) {
	    		BeanUtils.copyProperties(data, uw);
	    	}
	    	data.setValid(true);
			data.setApiVersion(apiVersion);
			data.setDateTime(System.currentTimeMillis());
		}
		data.setValid(true);
		data.setApiVersion(apiVersion);
		data.setDateTime(System.currentTimeMillis());
		return data;
	}
	
	
}
