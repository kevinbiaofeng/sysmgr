package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[OnLineWeChat]")
public class Onlinewechat implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "ConfigName")
    private String configname;

    @Column(name = "WeChat")
    private String wechat;

    @Column(name = "NickName")
    private String nickname;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "TagID")
    private Integer tagid;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "CollectDate")
    private Date collectdate;

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