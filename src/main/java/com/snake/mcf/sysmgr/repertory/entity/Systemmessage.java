package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[SystemMessage]")
public class Systemmessage implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MessageType")
    private Integer messagetype;

    @Column(name = "ServerRange")
    private String serverrange;

    @Column(name = "MessageString")
    private String messagestring;

    @Column(name = "StartTime")
    private Date starttime;

    @Column(name = "ConcludeTime")
    private Date concludetime;

    @Column(name = "TimeRate")
    private Integer timerate;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateMasterID")
    private Integer createmasterid;

    @Column(name = "UpdateDate")
    private Date updatedate;

    @Column(name = "UpdateMasterID")
    private Integer updatemasterid;

    @Column(name = "UpdateCount")
    private Integer updatecount;

    @Column(name = "CollectNote")
    private String collectnote;

    @Column(name = "merchant")
    private String merchant;

    private static final long serialVersionUID = 1L;

}