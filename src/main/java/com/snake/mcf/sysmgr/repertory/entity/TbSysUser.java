package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysUser]")
public class TbSysUser implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "Name")
    private String name;

    @Column(name = "LoginName")
    private String loginName;

    @Column(name = "Salt")
    private String salt;

    @Column(name = "LoginPassword")
    private String loginPassword;

    @Column(name = "IsLocked")
    private String isLocked;

    @Column(name = "IsDeleted")
    private String isDeleted;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedBy")
    private String updatedBy;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "MerchantID")
    private Integer merchantid;

    @Column(name = "SecretKey")
    private String secretKey;

    @Column(name = "MerchantAggent")
    private String merchantAggent;

    @Column(name = "PreLogintime")
    private Date preLogintime;

    @Column(name = "PreLoginIP")
    private String preLoginIp;

    @Column(name = "LastLogintime")
    private Date lastLogintime;

    @Column(name = "LastLoginIP")
    private String lastLoginIp;

    @Column(name = "LoginTimes")
    private Long loginTimes;

    private static final long serialVersionUID = 1L;

}