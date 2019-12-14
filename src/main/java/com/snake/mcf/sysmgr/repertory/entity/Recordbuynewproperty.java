package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJRecordDB].[dbo].[RecordBuyNewProperty]")
public class Recordbuynewproperty implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "PropertyID")
    private Integer propertyid;

    @Column(name = "PropertyName")
    private String propertyname;

    @Column(name = "ExchangeType")
    private Integer exchangetype;

    @Column(name = "ExchangeRatio")
    private Integer exchangeratio;

    @Column(name = "BuyNum")
    private Integer buynum;

    @Column(name = "BeforeCurrency")
    private Long beforecurrency;

    @Column(name = "Currency")
    private Long currency;

    @Column(name = "ClinetIP")
    private String clinetip;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}