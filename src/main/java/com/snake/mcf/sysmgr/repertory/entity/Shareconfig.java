package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[ShareConfig]")
public class Shareconfig implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DayShareLmt")
    private Integer daysharelmt;

    @Column(name = "TimeShareGold")
    private Integer timesharegold;

    @Column(name = "TimeShareDiamond")
    private Integer timesharediamond;

    @Column(name = "Nullity")
    private Integer nullity;

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