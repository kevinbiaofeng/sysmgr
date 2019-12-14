package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[ShareLog]")
public class Sharelog implements Serializable {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UserID")
    private Long userid;

    @Column(name = "LogType")
    private Integer logtype;

    @Column(name = "DateID")
    private Integer dateid;

    @Column(name = "TimeShareGold")
    private Integer timesharegold;

    @Column(name = "TimeShareDiamond")
    private Integer timesharediamond;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "LogTime")
    private Date logtime;

    private static final long serialVersionUID = 1L;

}