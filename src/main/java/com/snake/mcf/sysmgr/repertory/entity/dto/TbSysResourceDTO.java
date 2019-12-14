package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysResource;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbSysResourceDTO
 * @Author 大帅
 * @Date 2019/6/21 11:46
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysResourceDTO extends TbSysResource {

    private static final long serialVersionUID = 1L;

    private String createdByName;

    private String updatedByName;

    private String state;

    private boolean leaf;

}
