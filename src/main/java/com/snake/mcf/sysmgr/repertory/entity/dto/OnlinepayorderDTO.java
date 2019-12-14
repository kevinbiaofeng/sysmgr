package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Onlinepayorder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName OnlinepayorderDTO
 * @Author 大帅
 * @Date 2019/6/28 22:11
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class OnlinepayorderDTO extends Onlinepayorder {

    private static final long serialVersionUID = 1L;

    private String scoretypeDesc;

    private String orderstatusDesc;

    private String shareidDesc;

    private String afterAllScore;

    private String beforeAllScore;

    private String orderdateStartStr;

    private String orderdateEndStr;

    /**
     * 充值数值
     */
    private Double scoreDouble;

    /**
     * 额外赠送
     */
    private Double otherpresentDouble;


}
