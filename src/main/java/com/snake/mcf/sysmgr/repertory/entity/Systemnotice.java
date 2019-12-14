package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[SystemNotice]")
public class Systemnotice implements Serializable {

    @Id
    @Column(name = "NoticeID")
    private Integer noticeid;

    @Column(name = "NoticeTitle")
    private String noticetitle;

    @Column(name = "MoblieContent")
    private String mobliecontent;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Publisher")
    private String publisher;

    @Column(name = "PublisherTime")
    private Date publishertime;

    @Column(name = "IsHot")
    private Integer ishot;

    @Column(name = "IsTop")
    private Integer istop;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "PlatformType")
    private Integer platformtype;

    @Column(name = "WebContent")
    private String webcontent;

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