package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Gameproperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName GamepropertyDTO
 * @Author 大帅
 * @Date 2019/7/2 18:01
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class GamepropertyDTO extends Gameproperty {

    private static final long serialVersionUID = 1L;

    private String nullityDesc;

    private String exchangetypeDesc;

    private String useareaDesc;

    private String serviceareaDesc;

    private String recommendDesc;

    private String dotaRatio;

    private Double buyresultsgoldDouble;

    private Double useresultsgoldDouble;


}
