package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 饼图
 *
 * @ClassName EchartPieGroup
 * @Author 大帅
 * @Date 2019/7/20 12:10
 */
@Data
public class EchartPieGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 显示描述
     */
    private List<String> nameList;

    /**
     * 数据
     */
    private List<EchartPieDataGroup> dataList;



}
