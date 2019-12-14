package com.snake.mcf.sysmgr.repertory.tk;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @ClassName TkComponentMapper
 * @Author 大帅
 * @Date 2019/6/19 17:03
 */
//@org.apache.ibatis.annotations.Mapper
public interface TkComponentMapper <T> extends Mapper<T>, IdsMapper<T> {

}
