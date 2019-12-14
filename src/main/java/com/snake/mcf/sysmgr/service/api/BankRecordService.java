package com.snake.mcf.sysmgr.service.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.snake.mcf.sysmgr.repertory.api.form.BankRecordForm;

public interface BankRecordService {
	/**
	 * 根据用户Id获取银行记录
	 * @return
	 */
	public Map<String, Object> getBankRecordByUserId(BankRecordForm bankRecordForm, Integer apiVersion) throws IllegalAccessException, InvocationTargetException;
}
