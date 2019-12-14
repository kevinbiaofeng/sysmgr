package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GamePackage]")
public class Gamepackage implements Serializable {

    @Id
    @Column(name = "PackageID")
    private Integer packageid;

    @Column(name = "Name")
    private String name;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "PlatformKind")
    private Integer platformkind;

    @Column(name = "Describe")
    private String describe;

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


    public  void test(){
        Gamepackage record = new Gamepackage();

        record.setName("123456789");
        this.name.length();
        this.name.length();
    }

}