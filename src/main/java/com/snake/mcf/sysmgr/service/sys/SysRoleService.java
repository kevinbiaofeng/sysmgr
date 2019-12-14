package com.snake.mcf.sysmgr.service.sys;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.RoleResourceGroup;

public interface SysRoleService extends BaseService {

    boolean saveSysRole(TbSysRoleDTO roleDto);

    boolean checkRoleCode(String code);

    TbSysRoleDTO querySysRoleByCode(String code);

    PageResult<TbSysRoleDTO> querySysRoleWithPage(EasyPageFilter pageFilter, TbSysRoleDTO roleDto);

    boolean checkedStatus(TbSysRoleDTO roleDto);

    TbSysRoleDTO querySysRoleById(String id);

    boolean updateSysRole(TbSysRoleDTO roleDto);

    List<TbSysRoleDTO> queryRoleDataList(TbSysRoleDTO roleDto);

    List<RoleResourceGroup> querySysRoleResource(String id);

    boolean saveSysRoleResource(TbSysRoleResourceDTO roleResourceDTO);
}
