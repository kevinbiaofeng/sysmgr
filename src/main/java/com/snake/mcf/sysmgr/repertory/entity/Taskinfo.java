package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[TaskInfo]")
public class Taskinfo implements Serializable {

    @Id
    @Column(name = "TaskID")
    private Integer taskid;

    @Column(name = "TaskName")
    private String taskname;

    @Column(name = "TaskDescription")
    private String taskdescription;

    @Column(name = "TaskType")
    private Integer tasktype;

    @Column(name = "UserType")
    private Integer usertype;

    @Column(name = "TimeType")
    private Integer timetype;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "MatchID")
    private Integer matchid;

    @Column(name = "TaskAward")
    private String taskaward;

    @Column(name = "Innings")
    private Integer innings;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "CollectDate")
    private Date collectdate;

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