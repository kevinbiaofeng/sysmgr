package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 每日统计
 *
 * @ClassName EverdayGroup
 * @Author 大帅
 * @Date 2019/7/18 10:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class EverdayGroup extends EchartGroup {

    private static final long serialVersionUID = 1L;

    /**
     * 图表类型
     *
     * 1 注册统计
     * 2 充值统计
     * 3 金币统计
     * 4 服务费统计
     * 5 损耗统计
     * 6 开房统计
     *
     */
    private Integer type;

    public EverdayGroup(){

    }

    public EverdayGroup(Integer type){
        this.type = type;
    }




}
