package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recorduseproperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordusepropertyDTO
 * @Author 大帅
 * @Date 2019/7/3 16:39
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordusepropertyDTO extends Recorduseproperty {

    private static final long serialVersionUID = 1L;

    private String usedateEndStr;

    private String usedateStartStr;

    private Integer gameid;

    private String nickname;

    private Double useresultsgoldDouble;


}
