package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[SpreadTypeProperty]")
public class Spreadtypeproperty implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "GradeID")
    private Integer gradeid;

    @Column(name = "GradeName")
    private String gradename;

    @Column(name = "TeamKpiStart")
    private Long teamkpistart;

    @Column(name = "TeamKpiEnd")
    private Long teamkpiend;

    @Column(name = "Percentage")
    private Long percentage;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "Account")
    private String account;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    private static final long serialVersionUID = 1L;

}