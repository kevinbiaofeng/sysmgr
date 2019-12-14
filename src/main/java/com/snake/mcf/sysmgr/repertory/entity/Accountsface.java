package com.snake.mcf.sysmgr.repertory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Data
@Table(name = "[WHQJAccountsDB].[dbo].[AccountsFace]")
public class Accountsface implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    @Column(name = "ID",insertable=false,updatable=false)
    private Integer id;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "FaceUrl")
    private String faceurl;

    @Column(name = "InsertTime")
    private Date inserttime;

    @Column(name = "InsertAddr")
    private String insertaddr;

    @Column(name = "InsertMachine")
    private String insertmachine;

    @Column(name = "CustomFace")
    private byte[] customface;

    private static final long serialVersionUID = 1L;

}