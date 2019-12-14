package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[Ads]")
public class Ads implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Title")
    private String title;

    @Column(name = "ResourceURL")
    private String resourceurl;

    @Column(name = "LinkURL")
    private String linkurl;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "PlatformType")
    private Integer platformtype;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Account")
    private String account;

    private static final long serialVersionUID = 1L;

}