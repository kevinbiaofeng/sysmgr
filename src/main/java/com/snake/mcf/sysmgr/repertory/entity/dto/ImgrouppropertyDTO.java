package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Imgroupproperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName ImgrouppropertyDTO
 * @Author 大帅
 * @Date 2019/7/15 12:16
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ImgrouppropertyDTO extends Imgroupproperty {

    private static final long serialVersionUID = 1L;

    /**
     * 亲友圈开房次数
     */
    private Integer battlecreate;

    /**
     * 亲友圈钻石
     */
    private Long ingot;

    /**
     * 亲友圈消耗钻石
     */
    private Long consumeingot;

    private String groupstatusDesc;

    private String limitDesc;

    private String groupids;




}
