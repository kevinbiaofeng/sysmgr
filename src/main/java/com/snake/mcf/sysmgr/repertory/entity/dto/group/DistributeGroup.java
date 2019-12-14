package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName DistributeGroup
 * @Author 大帅
 * @Date 2019/7/20 12:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class DistributeGroup extends EchartPieGroup {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     * 1 金币统计
     *
     */
    private Integer type;

    private Double totalNumber1;

    private Double totalNumber2;

    public DistributeGroup(){

    }

    public DistributeGroup(Integer type){
        this.type = type;
    }

}
