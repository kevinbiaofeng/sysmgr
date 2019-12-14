package com.snake.mcf.sysmgr.repertory.entity.dto.index;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName SingtonPieDTO
 * @Author 大帅
 * @Date 2019/7/20 12:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SingtonPieDTO extends PieDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 总计身上金币数
     */
    private Double totalScore;

    /**
     * 总计银行金币数
     */
    private Double totalInsureScore;


}
