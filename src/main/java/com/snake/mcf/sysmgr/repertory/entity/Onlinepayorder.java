package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[OnLinePayOrder]")
public class Onlinepayorder implements Serializable {

    @Id
    @Column(name = "OnLineID")
    private Integer onlineid;

    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "ShareID")
    private Integer shareid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "GameID")
    private Integer gameid;

    @Column(name = "Accounts")
    private String accounts;

    @Column(name = "NickName")
    private String nickname;

    @Column(name = "OrderID")
    private String orderid;

    @Column(name = "OrderType")
    private Integer ordertype;

    @Column(name = "Amount")
    private Double amount;

    @Column(name = "Score")
    private Integer score;

    @Column(name = "ScoreType")
    private Integer scoretype;

    @Column(name = "OtherPresent")
    private Integer otherpresent;

    @Column(name = "OrderStatus")
    private Integer orderstatus;

    @Column(name = "OrderDate")
    private Date orderdate;

    @Column(name = "OrderAddress")
    private String orderaddress;

    @Column(name = "BeforeScore")
    private Long beforescore;

    @Column(name = "PayDate")
    private Date paydate;

    @Column(name = "PayAddress")
    private String payaddress;

    private static final long serialVersionUID = 1L;

}