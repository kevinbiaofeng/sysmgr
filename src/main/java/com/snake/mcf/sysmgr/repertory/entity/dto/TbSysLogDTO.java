package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbSysLog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName TbSysLogDTO
 * @Author 大帅
 * @Date 2019/6/21 9:48
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TbSysLogDTO extends TbSysLog {

    private static final long serialVersionUID = 1L;

    private String createdDateEndStr;

    private String createdDateStartStr;

}
