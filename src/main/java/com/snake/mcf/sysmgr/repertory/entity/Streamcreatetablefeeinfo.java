package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[StreamCreateTableFeeInfo]")
public class Streamcreatetablefeeinfo implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "NickName")
    private String nickname;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "RoomID")
    private Integer roomid;

    @Column(name = "CellScore")
    private Long cellscore;

    @Column(name = "JoinGamePeopleCount")
    private Integer joingamepeoplecount;

    @Column(name = "CountLimit")
    private Integer countlimit;

    @Column(name = "TimeLimit")
    private Integer timelimit;

    @Column(name = "CreateTableFee")
    private Long createtablefee;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "DissumeDate")
    private Date dissumedate;

    @Column(name = "TaxAgency")
    private Long taxagency;

    @Column(name = "TaxCount")
    private Long taxcount;

    @Column(name = "TaxRevenue")
    private Long taxrevenue;

    @Column(name = "PayMode")
    private Integer paymode;

    @Column(name = "RoomStatus")
    private Integer roomstatus;

    @Column(name = "NeedRoomCard")
    private Integer needroomcard;

    @Column(name = "GameMode")
    private Integer gamemode;

    @Column(name = "GroupID")
    private Integer groupid;

    @Column(name = "PlayMode")
    private Integer playmode;

    @Column(name = "RoomScoreInfo")
    private byte[] roomscoreinfo;

    private static final long serialVersionUID = 1L;

}