package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GameRoomInfo]")
public class Gameroominfo implements Serializable {

    @Id
    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "ServerName")
    private String servername;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "NodeID")
    private Integer nodeid;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "GameID")
    private Integer gameid;

    @Column(name = "TableCount")
    private Integer tablecount;

    @Column(name = "ServerKind")
    private Integer serverkind;

    @Column(name = "ServerType")
    private Integer servertype;

    @Column(name = "ServerPort")
    private Integer serverport;

    @Column(name = "ServerLevel")
    private Integer serverlevel;

    @Column(name = "ServerPasswd")
    private String serverpasswd;

    @Column(name = "DataBaseName")
    private String databasename;

    @Column(name = "DataBaseAddr")
    private String databaseaddr;

    @Column(name = "CellScore")
    private Long cellscore;

    @Column(name = "RevenueRatio")
    private Integer revenueratio;

    @Column(name = "ServiceScore")
    private Long servicescore;

    @Column(name = "RestrictScore")
    private Long restrictscore;

    @Column(name = "MinTableScore")
    private Long mintablescore;

    @Column(name = "MinEnterScore")
    private Long minenterscore;

    @Column(name = "MaxEnterScore")
    private Long maxenterscore;

    @Column(name = "MinEnterMember")
    private Integer minentermember;

    @Column(name = "MaxEnterMember")
    private Integer maxentermember;

    @Column(name = "MaxPlayer")
    private Integer maxplayer;

    @Column(name = "ServerRule")
    private Integer serverrule;

    @Column(name = "DistributeRule")
    private Integer distributerule;

    @Column(name = "MinDistributeUser")
    private Integer mindistributeuser;

    @Column(name = "DistributeTimeSpace")
    private Integer distributetimespace;

    @Column(name = "DistributeDrawCount")
    private Integer distributedrawcount;

    @Column(name = "MinPartakeGameUser")
    private Integer minpartakegameuser;

    @Column(name = "MaxPartakeGameUser")
    private Integer maxpartakegameuser;

    @Column(name = "AttachUserRight")
    private Integer attachuserright;

    @Column(name = "ServiceMachine")
    private String servicemachine;

    @Column(name = "CustomRule")
    private String customrule;

    @Column(name = "PersonalRule")
    private String personalrule;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "ServerNote")
    private String servernote;

    @Column(name = "CreateDateTime")
    private Date createdatetime;

    @Column(name = "ModifyDateTime")
    private Date modifydatetime;

    @Column(name = "EnterPassword")
    private String enterpassword;

    @Column(name = "MaxTableScore")
    private Long maxtablescore;

    private static final long serialVersionUID = 1L;

}