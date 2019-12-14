package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysRole]")
public class TbSysRole implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @Column(name = "IsDeleted")
    private String isDeleted;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Permission")
    private String permission;

    @Column(name = "MerchantAggent")
    private String merchantAggent;

    private static final long serialVersionUID = 1L;

}