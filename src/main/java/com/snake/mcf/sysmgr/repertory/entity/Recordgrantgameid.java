package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJRecordDB].[dbo].[RecordGrantGameID]")
public class Recordgrantgameid implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "MasterID")
    private Integer masterid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "CurGameID")
    private Integer curgameid;

    @Column(name = "ReGameID")
    private Integer regameid;

    @Column(name = "IDLevel")
    private Integer idlevel;

    @Column(name = "ClientIP")
    private String clientip;

    @Column(name = "CollectDate")
    private Date collectdate;

    @Column(name = "Reason")
    private String reason;

    private static final long serialVersionUID = 1L;

}