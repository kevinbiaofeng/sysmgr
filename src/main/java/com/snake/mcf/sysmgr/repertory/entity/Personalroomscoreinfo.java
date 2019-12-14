package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[PersonalRoomScoreInfo]")
public class Personalroomscoreinfo implements Serializable {

    @Id
    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "PersonalRoomGUID")
    private String personalroomguid;

    @Column(name = "RoomID")
    private Integer roomid;

    @Column(name = "Score")
    private Long score;

    @Column(name = "WinCount")
    private Integer wincount;

    @Column(name = "LostCount")
    private Integer lostcount;

    @Column(name = "DrawCount")
    private Integer drawcount;

    @Column(name = "FleeCount")
    private Integer fleecount;

    @Column(name = "WriteTime")
    private Date writetime;

    @Column(name = "PlayBackCode")
    private Integer playbackcode;

    @Column(name = "ChairID")
    private Integer chairid;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "GroupID")
    private Integer groupid;

    @Column(name = "PlayMode")
    private Integer playmode;

    @Column(name = "CellScore")
    private Long cellscore;

    @Column(name = "StartTime")
    private Date starttime;

    @Column(name = "RoomHostID")
    private Integer roomhostid;

    private static final long serialVersionUID = 1L;

}