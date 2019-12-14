package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysRoleResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbSysRoleResourceDTO
 * @Author 大帅
 * @Date 2019/6/22 18:10
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysRoleResourceDTO extends TbSysRoleResource {

    private static final long serialVersionUID = 1L;

    private String resourceIds;

}
