package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJRecordDB].[dbo].[RecordGrantTreasure]")
public class Recordgranttreasure implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "MasterID")
    private Integer masterid;

    @Column(name = "ClientIP")
    private String clientip;

    @Column(name = "CollectDate")
    private Date collectdate;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "CurGold")
    private Long curgold;

    @Column(name = "AddGold")
    private Long addgold;

    @Column(name = "Reason")
    private String reason;

    private static final long serialVersionUID = 1L;

}