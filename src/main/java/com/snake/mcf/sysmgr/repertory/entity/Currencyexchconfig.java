package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[CurrencyExchConfig]")
public class Currencyexchconfig implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "ConfigName")
    private String configname;

    @Column(name = "Diamond")
    private Integer diamond;

    @Column(name = "ExchGold")
    private Long exchgold;

    @Column(name = "ImageType")
    private Integer imagetype;

    @Column(name = "SortID")
    private Integer sortid;

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