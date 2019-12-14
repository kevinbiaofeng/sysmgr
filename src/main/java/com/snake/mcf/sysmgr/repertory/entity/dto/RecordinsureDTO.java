package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.Recordinsure;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName RecordinsureDTO
 * @Author 大帅
 * @Date 2019/7/11 15:57
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RecordinsureDTO extends Recordinsure {

    private static final long serialVersionUID = 1L;

    /**
     * 汇款人
     */
    private String remitter;
    
    /**
     * 汇款人GameId
     */
    private String remitterGameId;

    /**
     * 收款人
     */
    private String payee;
    
    /**
     * 收款人GameId
     */
    private String payeeGameId;

    /**
     * 交易类型
     */
    private String tradetypeDesc;

    /**
     * SourceGold：操作用户操作前金币
     */
    private Double sourcegoldDouble;

    /**
     * SourceBank：操作用户操作前银行金币
     */
    private Double sourcebankDouble;

    /**
     * TargetGold：接收用户接收前金币
     */
    private Double targetgoldDouble;

    /**
     * TargetBank：接收用户接收前银行金币
     */
    private Double targetbankDouble;

    /**
     * SwapScore：交易金额
     */
    private Double swapscoreDouble;

    /**
     * 服务费
     */
    private Double revenueDouble;

    private String collectdateEndStr;

    private String collectdateStartStr;

    private Integer sourceGameId;

    private Integer targetGameId;





}
