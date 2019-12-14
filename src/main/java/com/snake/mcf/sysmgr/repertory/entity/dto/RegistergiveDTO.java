package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Registergive;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RegistergiveDTO
 * @Author 大帅
 * @Date 2019/6/27 9:54
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RegistergiveDTO extends Registergive {

    private static final long serialVersionUID = 1L;

    private String platformtypeDesc;

    private String configids;

    /**
     * 赠送金币数
     */
    private Double scorecountDouble;

    private Double upgradescorecountDouble;

    private Double visitorscorecountDouble;


}
