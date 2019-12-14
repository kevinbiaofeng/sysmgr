package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.AccountsInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName AccountsInfoDTO
 * @Author 大帅
 * @Date 2019/6/24 12:05
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AccountsInfoDTO extends AccountsInfo {

    private static final long serialVersionUID = 1L;
    /**
     * 用户账号
     */
    private String accounts;
    
    /**
     * 性别描述
     */
    private String genderDesc;

    /**
     * 状态
     */
    private String nullityDesc;

    /**
     * 注册来源
     */
    private String registerOriginDesc;

    /**
     * 代理状态
     */
    private String agentStatus;

    /**
     * 携带金币
     */
    private Long score;

    /**
     * 携带金币
     */
    private Double scoreDouble = 0.0;

    /**
     * 保险柜金币
     */
    private Long insurescore;

    /**
     * 保险柜金币
     */
    private Double insurescoreDouble = 0.0;

    /**
     * 服务费
     */
    private Long revenue;

    /**
     * 服务费
     */
    private Double revenueDouble = 0.0;

    /**
     * 赢局
     */
    private Integer wincount;

    /**
     * 输局
     */
    private Integer lostcount;

    /**
     * 和局数量
     */
    private Integer drawcount;

    /**
     * 逃局
     */
    private Integer fleecount;

    /**
     * 总局 = 赢局 + 输局 + 和局 + 逃局
     */
    private Integer allCount;

    /**
     * 批量拼接 userId
     */
    private String userIds;

    /**
     * 标识
     * 0 设置转账标识
     * 1 取消转账标识
     */
    private Integer transfer;

    private String lastLogonDateStr;

    private String registerDateStr;

    private String imgFaceSrc;

    /**
     * 赠送靓号ID
     */
    private Integer giveNum;

    /**
     * 赠送原因
     */
    private String reason;

    /**
     * 赠送金币
     */
    private Long giveGold;

    private Long scoreStart;
    private Long scoreEnd;

    private Long insurescoreStart;
    private Long insurescoreEnd;

    private Long revenueStart;
    private Long revenueEnd;

    private Integer unlock;

    /**
     * 金币状态  1：充值   2：扣除
     */
    private Integer deposiWithdrawalState;

    private Integer isTransfer;

}
