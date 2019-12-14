package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysUserRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbSysUserRoleDTO
 * @Author 大帅
 * @Date 2019/6/21 16:42
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysUserRoleDTO extends TbSysUserRole {

    private static final long serialVersionUID = 1L;

    private String roleIds;




}
