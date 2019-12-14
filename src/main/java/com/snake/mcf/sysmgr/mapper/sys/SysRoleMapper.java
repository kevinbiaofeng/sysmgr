package com.snake.mcf.sysmgr.mapper.sys;

import com.snake.mcf.sysmgr.repertory.entity.TbSysResource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface SysRoleMapper {

    public List<TbSysResource> queryResourceByUserId(Map<String,Object> paramMap);

}
