package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @ClassName TbSysUserDTO
 * @Author 大帅
 * @Date 2019/6/20 14:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysUserDTO extends TbSysUser {

    private static final long serialVersionUID = 1L;

    private String createdByName;

    private String updatedByName;

    private String confirmPassword;

    private Integer checkedType;

    private boolean checked;

    private Integer commissionratio;

    private List<TbSysRoleDTO> roleList;

    private Map<String,Object> menuMap;

    private Integer permission;
    
    private String phone;
    
    private String mail;
    
    private String qqAccount;
    
    private String wechatAccount;
    
    private String safePassword;
    
    private String merchant;

}
