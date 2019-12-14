package com.snake.mcf.sysmgr.mapper.authority;

import com.snake.mcf.common.ui.menu.filter.LayMenuFilter;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface AuthorityMapper {


    List<TbSysRoleDTO> queryUserRoleByUserId(Map<String, Object> paramMap);

    LinkedList<LayMenuFilter> queryMenuListByUserId(Map<String, Object> paramMap);

}
