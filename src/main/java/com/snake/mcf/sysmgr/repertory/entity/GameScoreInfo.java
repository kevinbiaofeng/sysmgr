package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[GameScoreInfo]")
public class GameScoreInfo implements Serializable {

    @Id
    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "Score")
    private Long score;

    @Column(name = "Revenue")
    private Long revenue;

    @Column(name = "InsureScore")
    private Long insurescore;

    @Column(name = "WinCount")
    private Integer wincount;

    @Column(name = "LostCount")
    private Integer lostcount;

    @Column(name = "DrawCount")
    private Integer drawcount;

    @Column(name = "FleeCount")
    private Integer fleecount;

    @Column(name = "UserRight")
    private Integer userright;

    @Column(name = "MasterRight")
    private Integer masterright;

    @Column(name = "MasterOrder")
    private Integer masterorder;

    @Column(name = "AllLogonTimes")
    private Integer alllogontimes;

    @Column(name = "PlayTimeCount")
    private Integer playtimecount;

    @Column(name = "OnLineTimeCount")
    private Integer onlinetimecount;

    @Column(name = "LastLogonIP")
    private String lastlogonip;

    @Column(name = "LastLogonDate")
    private Date lastlogondate;

    @Column(name = "LastLogonMachine")
    private String lastlogonmachine;

    @Column(name = "RegisterIP")
    private String registerip;

    @Column(name = "RegisterDate")
    private Date registerdate;

    @Column(name = "RegisterMachine")
    private String registermachine;

    private static final long serialVersionUID = 1L;

}