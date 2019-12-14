package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[RecordDrawInfo]")
public class Recorddrawinfo implements Serializable {

    @Id
    @Column(name = "DrawID")
    private Integer drawid;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "TableID")
    private Integer tableid;

    @Column(name = "UserCount")
    private Integer usercount;

    @Column(name = "AndroidCount")
    private Integer androidcount;

    @Column(name = "Waste")
    private Long waste;

    @Column(name = "Revenue")
    private Long revenue;

    @Column(name = "StartTime")
    private Date starttime;

    @Column(name = "ConcludeTime")
    private Date concludetime;

    @Column(name = "InsertTime")
    private Date inserttime;

    @Column(name = "DrawCourse")
    private byte[] drawcourse;

    private static final long serialVersionUID = 1L;

}