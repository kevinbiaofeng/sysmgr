package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Cashoutorders;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName CashoutordersDTO
 * @Author 大帅
 * @Date 2019/7/27 13:13
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CashoutordersDTO extends Cashoutorders {

    private static final long serialVersionUID = 1L;

    private String bankchoiceDesc;

    private Integer gameid;

    private String nickname;

    private Double scoreDouble;
    
    private Double realMoneyDouble;
    
    private Double servicefeeDouble;

    private String reviewstatusDesc;

    private String financialStatusDesc;

    private String orderids;




}
