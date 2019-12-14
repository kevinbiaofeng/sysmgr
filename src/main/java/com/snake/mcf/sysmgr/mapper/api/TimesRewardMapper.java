package com.snake.mcf.sysmgr.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.api.dto.share.TimesReward;

@Repository
@Mapper
public interface TimesRewardMapper {
	public List<TimesReward> callTimesReward(String userId, String strClientIp);
}
