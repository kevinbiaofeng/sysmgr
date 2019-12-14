package com.snake.mcf.sysmgr.mapper.api;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface HallBattleRecordMapper {
	/**
	 * 获取游戏大厅约战记录
	 * @return
	 */
	public List<List<?>> callGetHallBattleRecordTypeOne(Long userId);
	
	/**
	 * 获取游戏大厅金币场记录
	 * @return
	 */
	public List<List<?>> callGetHallBattleRecordTypeTwo(Long userId);
	
	/**
	 * 获取俱乐部战绩记录
	 * @return
	 */
	public List<List<?>> callGetClubBattleRecord(Integer groupId, Long userId, Date startTime, Date endTime);
}
