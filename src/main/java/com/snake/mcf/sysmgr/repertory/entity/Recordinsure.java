package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[RecordInsure]")
public class Recordinsure implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordId;

    @Column(name = "KindID")
    private Integer kindId;

    @Column(name = "ServerID")
    private Integer serverId;

    @Column(name = "SourceUserID")
    private Integer sourceUserId;

    @Column(name = "SourceGold")
    private Long sourceGold;

    @Column(name = "SourceBank")
    private Long sourceBank;

    @Column(name = "TargetUserID")
    private Integer targetUserId;

    @Column(name = "TargetGold")
    private Long targetGold;

    @Column(name = "TargetBank")
    private Long targetBank;

    @Column(name = "SwapScore")
    private Long swapScore;

    @Column(name = "Revenue")
    private Long revenue;

    @Column(name = "IsGamePlaza")
    private Integer isGamePlaza;

    @Column(name = "TradeType")
    private Integer tradeType;

    @Column(name = "ClientIP")
    private String clientIp;

    @Column(name = "CollectDate")
    private Date collectDate;

    @Column(name = "CollectNote")
    private String collectNote;

    private static final long serialVersionUID = 1L;

}