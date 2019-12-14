package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName EchartGroup
 * @Author 大帅
 * @Date 2019/7/19 18:45
 */
@Data
public class EchartGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 横坐标
     */
    private List<String> xAxisDataList;

    /**
     * 单折线 图表数据
     */
    private List<Double> seriesSingtonDataList;

    /**
     * 多折线 图表数据
     */
    private Map<String,List<Double>> seriesMultipleDataMap;


}
