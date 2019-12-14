package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GameProperty]")
public class Gameproperty implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ExchangeType")
    private Integer exchangetype;

    @Column(name = "ExchangeRatio")
    private Integer exchangeratio;

    @Column(name = "Name")
    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "Kind")
    private Integer kind;

    @Column(name = "UseArea")
    private Integer usearea;

    @Column(name = "ServiceArea")
    private Integer servicearea;

    @Column(name = "BuyResultsGold")
    private Long buyresultsgold;

    @Column(name = "SendLoveLiness")
    private Long sendloveliness;

    @Column(name = "RecvLoveLiness")
    private Long recvloveliness;

    @Column(name = "UseResultsGold")
    private Long useresultsgold;

    @Column(name = "UseResultsValidTime")
    private Integer useresultsvalidtime;

    @Column(name = "UseResultsValidTimeScoreMultiple")
    private Integer useresultsvalidtimescoremultiple;

    @Column(name = "UseResultsGiftPackage")
    private Integer useresultsgiftpackage;

    @Column(name = "RegulationsInfo")
    private String regulationsinfo;

    @Column(name = "Recommend")
    private Integer recommend;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "PlatformKind")
    private Integer platformkind;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "update_date")
    private Date updateDate;

    @Column(name = "account")
    private String account;

    @Column(name = "merchant")
    private String merchant;

    private static final long serialVersionUID = 1L;

}