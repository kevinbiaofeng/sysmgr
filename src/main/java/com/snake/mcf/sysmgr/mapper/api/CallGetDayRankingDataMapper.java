package com.snake.mcf.sysmgr.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface CallGetDayRankingDataMapper {
	public List<List<?>> callGetDayWealthRankingData(Long userId, Integer typeId);
	
	public List<List<?>> callGetDayGameRankingData(Long userId, Integer typeId);
}
