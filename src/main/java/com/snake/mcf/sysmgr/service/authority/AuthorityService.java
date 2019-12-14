package com.snake.mcf.sysmgr.service.authority;

import com.snake.mcf.common.ui.menu.filter.LayMenuFilter;
import com.snake.mcf.sysmgr.authority.token.AuthorityUsernamePasswordToken;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;

import java.util.List;
import java.util.Map;

public interface AuthorityService extends BaseService {

    TbSysUserDTO querySysUserByLoginName(String username);

    List<TbSysRoleDTO> queryUserRoleByUserId(String id);

    Map<String, Object> queryUserMenuByUserId(String id);

    /**
     * 用户角色所属权限
     *
     * @param id
     * @return
     */
    int getPermissionByUserId(String id);

    void recordLog (AuthorityUsernamePasswordToken token, TbSysUserDTO sysUserDto, List<TbSysRoleDTO> roleList);

    List<LayMenuFilter> queryUserPermissionByUserId(String userId);
}
