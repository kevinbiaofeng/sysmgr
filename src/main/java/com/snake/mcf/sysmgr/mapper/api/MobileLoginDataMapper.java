package com.snake.mcf.sysmgr.mapper.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AccountGivegoldMapper
 * @Author 大帅
 * @Date 2019/6/26 19:05
 */
@Repository
@Mapper
public interface MobileLoginDataMapper {
	public List<List<?>> callGetMobileLoginData(Integer platformType);
}
