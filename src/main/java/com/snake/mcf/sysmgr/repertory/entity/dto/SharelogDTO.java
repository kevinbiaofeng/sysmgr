package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Sharelog;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName SharelogDTO
 * @Author 大帅
 * @Date 2019/7/7 12:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SharelogDTO extends Sharelog {

    private static final long serialVersionUID = 1L;

    private Integer gameid;

    private String nickname;

    private String logtimeEndStr;

    private String logtimeStartStr;

    /**
     * 分享获得金币
     */
    private Double timesharegoldDouble;

}
