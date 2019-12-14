package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GamePackageGoods]")
public class Gamepackagegoods implements Serializable {

    @Id
    @Column(name = "GoodsID")
    private Integer goodsid;

    @Column(name = "PackageID")
    private Integer packageid;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "PropertyID")
    private Integer propertyid;

    @Column(name = "GoodsNum")
    private Long goodsnum;

    @Column(name = "ResourceURL")
    private String resourceurl;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}