package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[MobileKindItem]")
public class Mobilekinditem implements Serializable {

    @Id
    @Column(name = "KindID")
    private Integer kindid;

    @Column(name = "KindName")
    private String kindname;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "ModuleName")
    private String modulename;

    @Column(name = "ClientVersion")
    private Integer clientversion;

    @Column(name = "ResVersion")
    private Integer resversion;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "KindMark")
    private Integer kindmark;

    @Column(name = "Nullity")
    private Integer nullity;

    private static final long serialVersionUID = 1L;

}