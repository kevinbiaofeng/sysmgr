package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysLog]")
public class TbSysLog implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "Module")
    private String module;

    @Column(name = "Account")
    private String account;

    @Column(name = "Ip")
    private String ip;

    @Column(name = "Method")
    private String method;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "Params")
    private String params;

    @Column(name = "RoleName")
    private String roleName;

    @Column(name = "Description")
    private String description;

    private static final long serialVersionUID = 1L;

}