package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJRecordDB].[dbo].[RecordUseProperty]")
public class Recorduseproperty implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "SourceUserID")
    private Integer sourceuserid;

    @Column(name = "TargetUserID")
    private Integer targetuserid;

    @Column(name = "PropertyID")
    private Integer propertyid;

    @Column(name = "PropertyName")
    private String propertyname;

    @Column(name = "PropertyCount")
    private Integer propertycount;

    @Column(name = "LovelinessRcv")
    private Integer lovelinessrcv;

    @Column(name = "LovelinessSend")
    private Integer lovelinesssend;

    @Column(name = "UseResultsGold")
    private Long useresultsgold;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "ClientIP")
    private String clientip;

    @Column(name = "UseDate")
    private Date usedate;

    private static final long serialVersionUID = 1L;

}