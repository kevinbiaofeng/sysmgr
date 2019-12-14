package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordgamesignin;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordgamesigninDTO
 * @Author 大帅
 * @Date 2019/7/6 11:25
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordgamesigninDTO extends Recordgamesignin {

    private static final long serialVersionUID = 1L;

    private Integer gameid;

    private String nickname;

    private String signtypeDesc;

    private String collectdateEndStr;

    private String collectdateStartStr;



}
