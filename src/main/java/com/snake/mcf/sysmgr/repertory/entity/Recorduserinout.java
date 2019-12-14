package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[RecordUserInout]")
public class Recorduserinout implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "EnterTime")
    private Date entertime;

    @Column(name = "EnterScore")
    private Long enterscore;

    @Column(name = "EnterGrade")
    private Long entergrade;

    @Column(name = "EnterInsure")
    private Long enterinsure;

    @Column(name = "EnterMachine")
    private String entermachine;

    @Column(name = "EnterClientIP")
    private String enterclientip;

    @Column(name = "LeaveTime")
    private Date leavetime;

    @Column(name = "LeaveReason")
    private Integer leavereason;

    @Column(name = "LeaveMachine")
    private String leavemachine;

    @Column(name = "LeaveClientIP")
    private String leaveclientip;

    @Column(name = "Score")
    private Long score;

    @Column(name = "Grade")
    private Long grade;

    @Column(name = "Insure")
    private Long insure;

    @Column(name = "Revenue")
    private Long revenue;

    @Column(name = "WinCount")
    private Integer wincount;

    @Column(name = "LostCount")
    private Integer lostcount;

    @Column(name = "DrawCount")
    private Integer drawcount;

    @Column(name = "FleeCount")
    private Integer fleecount;

    @Column(name = "PlayTimeCount")
    private Integer playtimecount;

    @Column(name = "OnLineTimeCount")
    private Integer onlinetimecount;

    private static final long serialVersionUID = 1L;

}