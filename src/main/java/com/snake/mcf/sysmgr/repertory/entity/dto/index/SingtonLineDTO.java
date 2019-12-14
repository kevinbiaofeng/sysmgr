package com.snake.mcf.sysmgr.repertory.entity.dto.index;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 单条折线图查询返回数据
 *
 * @ClassName SingtonLineDTO
 * @Author 大帅
 * @Date 2019/7/18 16:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class SingtonLineDTO extends LineDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 数据
     */
    private Double seriesData;


}
