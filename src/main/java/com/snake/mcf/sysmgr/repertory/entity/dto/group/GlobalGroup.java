package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 全局统计
 *
 * @ClassName GlobalGroup
 * @Author 大帅
 * @Date 2019/7/17 15:10
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GlobalGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户统计
     */
    /**
     * 用户总数
     */
    private Long totalAccount;
    /**
     * 今日新增
     */
    private Long todayNews;
    /**
     * 今日活跃
     */
    private Long todayActive;
    /**
     * 昨日新增
     */
    private Long yesterdayNews;

    /**
     * 金币统计
     */
    /**
     * 身上金币总量
     */
    private Double totalScore;
    /**
     * 保险柜总量
     */
    private Double totalInsureScore;
    /**
     * 保险柜+身上总量
     */
    private Double totalAllScore;
    /**
     * 今日充值金币
     */
    private Double todayPayScore;
    /**
     * 今日充值金币金额
     */
    private Double todayPayScoreAmount;

    /**
     * 充值统计
     */
    /**
     * 总充值金额
     */
    private Double totalAmount;
    /**
     * 	今日充值金额
     */
    private Double todayAmount;
    /**
     * 昨日充值金额
     */
    private Double yesterdayAmount;

    /**
     * 服务费统计
     */
    /**
     * 游戏总服务费
     */
    private Double totalRevenue;
    /**
     * 今日游戏服务费
     */
    private Double todayRevenue;
    /**
     * 银行总服务费
     */
    private Double totalInsureRevenue;
    /**
     * 今日银行服务费
     */
    private Double todayInsureRevenue;
    /**
     * 银行+游戏总服务费
     */
    private Double totalAllRevenue;

    /**
     * 损耗统计
     */
    /**
     * 损耗总量
     */
    private Double totalWaste;
    /**
     * 今日损耗
     */
    private Double todayWaste;
    /**
     * 昨日损耗
     */
    private Double yesterdayWaste;

    /**
     * 开房统计
     */
    /**
     * 房间模式次数(金币约战)
     * 总次数
     */
    private Long totalRoomCount;

    /**
     * 今日约战次数
     */
    private Long todayRoomCount;

    /**
     * 昨日约战次数
     */
    private Long yesterdayRoomCount;
}
