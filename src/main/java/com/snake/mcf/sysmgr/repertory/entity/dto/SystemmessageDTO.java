package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Systemmessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName SystemmessageDTO
 * @Author 大帅
 * @Date 2019/7/1 11:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SystemmessageDTO extends Systemmessage {

    private static final long serialVersionUID = 1L;

    private String messagetypeDesc;

    private String nullityDesc;

    private String starttimeStr;

    private String concludetimeStr;

    private String ids;


}
