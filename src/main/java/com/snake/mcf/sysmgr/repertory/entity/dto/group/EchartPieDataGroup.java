package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName EchartPieDataGroup
 * @Author 大帅
 * @Date 2019/7/20 12:21
 */
@Data
public class EchartPieDataGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 数值
     */
    private Double value;
}
