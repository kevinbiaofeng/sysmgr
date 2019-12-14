package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GameSignIn]")
public class Gamesignin implements Serializable {

    @Id
    @Column(name = "SignID")
    private Integer signid;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "PackageID")
    private Integer packageid;

    @Column(name = "Probability")
    private Integer probability;

    @Column(name = "NeedDay")
    private Integer needday;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}