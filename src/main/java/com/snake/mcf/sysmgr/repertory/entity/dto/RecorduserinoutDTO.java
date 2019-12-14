package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recorduserinout;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecorduserinoutDTO
 * @Author 大帅
 * @Date 2019/7/12 10:21
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecorduserinoutDTO extends Recorduserinout {

    private static final long serialVersionUID = 1L;

    private String remitter;
    
    private String remitterGameId;

    private String kindname;

    private String servername;

    private String entertimeEndStr;

    private String entertimeStartStr;

    private Integer gameid;

    /**
     * 总局 = 赢局 + 输局 + 和局 + 逃局
     */
    private Integer allCount;

    private String leavereasonDesc;

    /**
     * 携带金币
     */
    private Double enterscoreDouble;

    /**
     * 银行金币
     */
    private Double enterinsureDouble;

    /**
     * 携带金币变化
     */
    private String scoreDouble;

    /**
     * 银行金币变化
     */
    private String insureDouble;

    /**
     * 游戏服务费
     */
    private Double revenueDouble;

    private String leavetimeStr;


}
