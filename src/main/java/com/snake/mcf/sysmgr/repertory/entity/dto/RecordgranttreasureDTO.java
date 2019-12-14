package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordgranttreasure;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordgranttreasureDTO
 * @Author 大帅
 * @Date 2019/6/26 14:03
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordgranttreasureDTO extends Recordgranttreasure {

    private static final long serialVersionUID = 1L;

    private Integer gameId;

    private String nickName;

    private String accounts;

    private Long allGold;

    private String masterName;

    /**
     * 开始日期
     */
    private String collectdateStartStr;

    /**
     * 结束日期
     */
    private String collectdateEndStr;

    /**
     * 赠送前身上金币
     */
    private Double curgoldDouble = 0.0;

    /**
     * 赠送金币
     */
    private Double addgoldDouble = 0.0;

    /**
     * 赠送后身上金币
     */
    private Double allGoldDouble = 0.0;




}
