package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recorddrawinfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecorddrawinfoDTO
 * @Author 大帅
 * @Date 2019/7/12 15:33
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecorddrawinfoDTO extends Recorddrawinfo {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏名称
     */
    private String kindname;

    /**
     * 游戏房间
     */
    private String servername;

    private Integer gameid;

    private String inserttimeEndStr;

    private String inserttimeStartStr;

    /**
     * 损耗
     */
    private Double wasteDouble;

    /**
     * 服务费
     */
    private Double revenueDouble;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 是否是机器人
     */
    private Integer isandroid;

    /**
     * 是否是机器人
     */
    private String isandroidDesc;

    /**
     * 椅子编号
     */
    private Integer chairid;

    /**
     * 输赢金币
     */
    private Long score;

    /**
     * 输赢金币
     */
    private Double scoreDouble;

    /**
     * 游戏时长
     */
    private Integer playtimecount;





}
