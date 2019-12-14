package com.snake.mcf.sysmgr.repertory.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.snake.mcf.sysmgr.repertory.entity.Recordinsure;

@Repository
@Mapper
public interface RecordinsureMapper {
	List<Recordinsure> queryRecordInsureList(Map<String, Object> paramMap);
}