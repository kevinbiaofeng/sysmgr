package com.snake.mcf.sysmgr.repertory.mapper;

import com.snake.mcf.sysmgr.repertory.entity.Configinfo;
import com.snake.mcf.sysmgr.repertory.tk.TkComponentMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ConfiginfoMapper extends TkComponentMapper<Configinfo> {

}