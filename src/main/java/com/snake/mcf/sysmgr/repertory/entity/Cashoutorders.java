package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[CashOutOrders]")
public class Cashoutorders implements Serializable {

    @Id
    @Column(name = "OrderID")
    private String orderid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "BankChoice")
    private Integer bankchoice;

    @Column(name = "BankCardID")
    private String bankcardid;

    @Column(name = "BankName")
    private String bankname;

    @Column(name = "IDCardName")
    private String idcardname;

    @Column(name = "Score")
    private Integer score;

    @Column(name = "ServiceRate")
    private Long servicerate;

    @Column(name = "ServiceFee")
    private Long servicefee;

    @Column(name = "RealMoney")
    private Long realmoney;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "AddTime")
    private Date addtime;

    @Column(name = "ReviewStatus")
    private Integer reviewstatus;

    @Column(name = "financialStatus")
    private Integer financialStatus;

    @Column(name = "Reviewer")
    private String reviewer;

    @Column(name = "ReviewTime")
    private Date reviewtime;

    @Column(name = "financialOperator")
    private String financialOperator;

    @Column(name = "financialTime")
    private Date financialTime;

    @Column(name = "ReMark")
    private String remark;

    private static final long serialVersionUID = 1L;

}