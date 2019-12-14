package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName OnlineGroup
 * @Author 大帅
 * @Date 2019/7/19 18:48
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OnlineGroup extends EchartGroup {

    private static final long serialVersionUID = 1L;

    /**
     * 设置开始时间
     */
    private String startValue;

}
