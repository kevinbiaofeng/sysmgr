package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Streamcreatetablefeeinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName StreamcreatetablefeeinfoDTO
 * @Author 大帅
 * @Date 2019/7/13 18:19
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class StreamcreatetablefeeinfoDTO extends Streamcreatetablefeeinfo {

    private static final long serialVersionUID = 1L;

    /**
     * 房间状态
     */
    private String roomstatusDesc;

    /**
     * 游戏房间
     */
    private String servername;

    /**
     * 服务费
     */
    private Double taxcountDouble;

    private Integer gameid;

    private String createdateEndStr;

    private String createdateStartStr;

    private Integer userid;


}
