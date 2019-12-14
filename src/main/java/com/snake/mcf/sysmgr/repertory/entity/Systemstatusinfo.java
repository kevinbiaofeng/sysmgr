package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[WHQJAccountsDB].[dbo].[SystemStatusInfo]")
public class Systemstatusinfo implements Serializable {

    @Id
    @Column(name = "StatusName")
    private String statusname;

    @Column(name = "StatusValue")
    private Integer statusvalue;

    @Column(name = "StatusString")
    private String statusstring;

    @Column(name = "StatusTip")
    private String statustip;

    @Column(name = "StatusDescription")
    private String statusdescription;

    @Column(name = "SortID")
    private Integer sortid;

    private static final long serialVersionUID = 1L;

}