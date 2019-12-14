package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[GameScoreLocker]")
public class Gamescorelocker implements Serializable {

    @Id
    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "ServerID")
    private Integer serverid;

    @Column(name = "EnterID")
    private Integer enterid;

    @Column(name = "EnterIP")
    private String enterip;

    @Column(name = "EnterMachine")
    private String entermachine;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}