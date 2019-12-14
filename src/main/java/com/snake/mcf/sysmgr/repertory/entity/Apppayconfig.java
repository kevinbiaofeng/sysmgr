package com.snake.mcf.sysmgr.repertory.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[AppPayConfig]")
public class Apppayconfig implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "AppleID")
    private String appleid;

    @Column(name = "PayName")
    private String payname;

    @Column(name = "PayType")
    private Integer paytype;

    @Column(name = "PayPrice")
    private BigDecimal payprice;

    @Column(name = "PayIdentity")
    private Integer payidentity;

    @Column(name = "ImageType")
    private Integer imagetype;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Score")
    private Integer score;

    @Column(name = "ScoreType")
    private Integer scoretype;

    @Column(name = "FristPresent")
    private Integer fristpresent;

    @Column(name = "PresentScore")
    private Integer presentscore;

    @Column(name = "ConfigTime")
    private Date configtime;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Account")
    private String account;

    private static final long serialVersionUID = 1L;

}