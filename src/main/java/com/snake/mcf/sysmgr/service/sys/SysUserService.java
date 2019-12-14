package com.snake.mcf.sysmgr.service.sys;

import java.util.List;
import java.util.Map;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserRoleDTO;

public interface SysUserService extends BaseService {


    PageResult<TbSysUserDTO> queryUserWithPage(EasyPageFilter pageFilter, TbSysUserDTO sysUserDTO);

    boolean checkLoginName(String loginName);

    TbSysUserDTO querySysUserByLoginName(String loginName);

    boolean saveSysUser(TbSysUserDTO userDto);

    TbSysUserDTO querySysUserById(String id);

    boolean updateSysUser(TbSysUserDTO userDto);

    boolean checkedStatus(TbSysUserDTO userDto);
    
    boolean saveSysUserRole(TbSysUserRoleDTO sysUserRoleDTO);

    List<TbSysRoleDTO> queryCurrUserRole(String userId);
    /**
     * 根据用户Id list获取用户列表
     * @param userId
     * @return
     */
    List<TbSysUser> queryUserListByIds(List<Long> userList);
    
    Map<String, Object> queryUserMapByIds(List<Long> userList);
    /**
     * 根据商户号获取用户
     * @param merchant
     * @return
     */
    TbSysUser queryUserByMerchant(String merchant);
}
