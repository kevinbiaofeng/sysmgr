package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysResource]")
public class TbSysResource implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "Menuid")
    private String menuid;

    @Column(name = "ParentMenuid")
    private String parentMenuid;

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Url")
    private String url;

    @Column(name = "Lev")
    private Integer lev;

    @Column(name = "Icon")
    private String icon;

    @Column(name = "Sort")
    private Integer sort;

    @Column(name = "Permission")
    private String permission;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedBy")
    private String updatedBy;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    private static final long serialVersionUID = 1L;

}