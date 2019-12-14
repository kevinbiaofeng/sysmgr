package com.snake.mcf.sysmgr.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GameListMapper {
	public List<List<?>> callGetMobileGameAndVersion();
}
