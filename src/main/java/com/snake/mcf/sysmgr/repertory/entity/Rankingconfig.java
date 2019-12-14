package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[RankingConfig]")
public class Rankingconfig implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "RankType")
    private Integer ranktype;

    @Column(name = "Gold")
    private Long gold;

    @Column(name = "Diamond")
    private Integer diamond;

    @Column(name = "CollectDate")
    private Date collectdate;

    @Column(name = "AwardTicket")
    private Integer awardticket;

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