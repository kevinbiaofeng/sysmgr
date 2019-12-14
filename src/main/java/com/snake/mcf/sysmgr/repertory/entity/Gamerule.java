package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[GameRule]")
public class Gamerule implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "KindName")
    private String kindname;

    @Column(name = "KindIcon")
    private String kindicon;

    @Column(name = "KindIntro")
    private String kindintro;

    @Column(name = "RuleImg")
    private String ruleimg;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "LogID")
    private Integer logid;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "CollectDate")
    private Date collectdate;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "KindRule")
    private String kindrule;

    @Column(name = "merchant")
    private String merchant;

    private static final long serialVersionUID = 1L;

}