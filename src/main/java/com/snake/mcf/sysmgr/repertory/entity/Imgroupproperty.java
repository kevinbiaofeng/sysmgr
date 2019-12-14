package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJGroupDB].[dbo].[IMGroupProperty]")
public class Imgroupproperty implements Serializable {

    @Id
    @Column(name = "GroupID")
    private Long groupid;

    @Column(name = "CreaterID")
    private Integer createrid;

    @Column(name = "CreaterGameID")
    private Integer creatergameid;

    @Column(name = "CreaterNickName")
    private String creaternickname;

    @Column(name = "GroupName")
    private String groupname;

    @Column(name = "GroupLevel")
    private Integer grouplevel;

    @Column(name = "GroupStatus")
    private Integer groupstatus;

    @Column(name = "GroupIntroduce")
    private String groupintroduce;

    @Column(name = "MemberCount")
    private Integer membercount;

    @Column(name = "MaxMemberCount")
    private Integer maxmembercount;

    @Column(name = "CreateDateTime")
    private Date createdatetime;

    private static final long serialVersionUID = 1L;

}