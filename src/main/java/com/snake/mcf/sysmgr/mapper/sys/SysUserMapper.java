package com.snake.mcf.sysmgr.mapper.sys;

import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysUserMapper
 * @Author 大帅
 * @Date 2019/6/21 17:41
 */
@Repository
@Mapper
public interface SysUserMapper {

    public List<TbSysRoleDTO> queryRoleByUserId(Map<String,Object> paramsMap);

}
