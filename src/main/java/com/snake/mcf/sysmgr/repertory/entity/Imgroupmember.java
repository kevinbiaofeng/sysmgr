package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJGroupDB].[dbo].[IMGroupMember]")
public class Imgroupmember implements Serializable {
    @Id
    @Column(name = "GroupID")
    private Long groupid;

    @Column(name = "UserID")
    private Long userid;

    @Column(name = "BattleCount")
    private Integer battlecount;

    @Column(name = "JoinDateTime")
    private Date joindatetime;

    private static final long serialVersionUID = 1L;

}