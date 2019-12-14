package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbSysRoleDTO
 * @Author 大帅
 * @Date 2019/6/21 10:42
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysRoleDTO extends TbSysRole {

    private static final long serialVersionUID = 1L;

    private String createdByName;

    private boolean checked;

    private String[] perm;


}
