package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[ConfigInfo]")
public class Configinfo implements Serializable {

    @Id
    @Column(name = "ConfigID")
    private Integer configid;

    @Column(name = "ConfigKey")
    private String configKey;

    @Column(name = "ConfigName")
    private String configname;

    @Column(name = "ConfigString")
    private String configstring;

    @Column(name = "Field1")
    private String field1 = "";

    @Column(name = "Field2")
    private String field2 = "";

    @Column(name = "Field3")
    private String field3 = "";

    @Column(name = "Field4")
    private String field4 = "";

    @Column(name = "Field5")
    private String field5 = "";

    @Column(name = "Field6")
    private String field6 = "";

    @Column(name = "Field7")
    private String field7 = "";

    @Column(name = "Field8")
    private String field8 = "";

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Field9")
    private String field9;

    @Column(name = "Field10")
    private String field10;

    @Column(name = "Field11")
    private String field11;

    @Column(name = "Field12")
    private String field12;

    @Column(name = "Field13")
    private String field13;

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