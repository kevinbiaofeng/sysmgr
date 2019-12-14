package com.snake.mcf.sysmgr.mapper.index;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.GlobalGroup;

@Repository
@Mapper
public interface GlobalStatDataMapper {


    GlobalGroup queryGlobalStatData(Map<String, Object> paramMap);
}
