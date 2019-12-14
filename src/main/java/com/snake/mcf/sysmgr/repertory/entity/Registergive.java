package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJAccountsDB].[dbo].[RegisterGive]")
public class Registergive implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "ScoreCount")
    private Integer scorecount;

    @Column(name = "VisitorScoreCount")
    private Integer visitorscorecount;

    @Column(name = "UpgradeScoreCount")
    private Integer upgradescorecount;

    @Column(name = "DiamondCount")
    private Integer diamondcount;

    @Column(name = "PlatformType")
    private Integer platformtype;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Account")
    private String account;

    @Column(name = "RegistWebSite")
    private String registwebsite;

    @Column(name = "WithdrawRatio")
    private Integer withdrawratio;

    @Column(name = "WithdrawUpperLimit")
    private Integer withdrawupperlimit;

    @Column(name = "WithdrawLowerLimit")
    private Integer withdrawlowerlimit;

    @Column(name = "WithdrawScoreUpperLimit")
    private Integer withdrawscoreupperlimit;

    @Column(name = "WithdrawScoreLowerLimit")
    private Integer withdrawscorelowerlimit;

    @Column(name = "ReserveScoreLowerLimit")
    private Integer reservescorelowerlimit;

    private static final long serialVersionUID = 1L;

}