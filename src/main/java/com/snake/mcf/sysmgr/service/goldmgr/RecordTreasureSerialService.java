package com.snake.mcf.sysmgr.service.goldmgr;

import java.util.Map;

import com.snake.mcf.sysmgr.base.BaseService;

public interface RecordTreasureSerialService extends BaseService {
	public Map<String, Object> queryRecordTreasureSerialListByCondition(Integer page, Integer rows, String _userId, Integer apiVersion);
}
