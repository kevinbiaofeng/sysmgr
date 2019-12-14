package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordtreasureserial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordtreasureserialDTO
 * @Author 大帅
 * @Date 2019/6/25 18:23
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordtreasureserialDTO extends Recordtreasureserial {

    private static final long serialVersionUID = 1L;

    private String collectdateStartStr;

    private String collectdateEndStr;

    private String typeidDesc;

    private String accounts;

    private Integer gameId;

    private String merchant;

    private String curScoreDesc;

    private String curinSureScoreDesc;

    private String changeScoreDesc;

    private String masterName;

}
