package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordbuynewproperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordbuynewpropertyDTO
 * @Author 大帅
 * @Date 2019/7/3 15:46
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordbuynewpropertyDTO extends Recordbuynewproperty {

    private static final long serialVersionUID = 1L;

    private String collectdateEndStr;

    private String collectdateStartStr;

    private Integer gameid;

    private String nickname;

    private String exchangetypeDesc;

    private Double currencyDouble;

    private Double beforecurrencyDouble;


}
