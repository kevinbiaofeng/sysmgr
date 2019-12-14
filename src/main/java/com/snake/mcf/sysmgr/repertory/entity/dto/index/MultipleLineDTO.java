package com.snake.mcf.sysmgr.repertory.entity.dto.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @ClassName MultipleLineDTO
 * @Author 大帅
 * @Date 2019/7/19 10:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class MultipleLineDTO extends LineDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 数据1
     */
    private Double seriesData1;

    /**
     * 数据2
     */
    private Double seriesData2;

    /**
     * 数据3
     */
    private Double seriesData3;



}
