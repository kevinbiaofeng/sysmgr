package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[WHQJPlatformDB].[dbo].[GameGameItem]")
public class Gamegameitem implements Serializable {

    @Id
    @Column(name = "GameID")
    private Integer gameid;

    @Column(name = "GameName")
    private String gamename;

    @Column(name = "SuportType")
    private Integer suporttype;

    @Column(name = "DataBaseAddr")
    private String databaseaddr;

    @Column(name = "DataBaseName")
    private String databasename;

    @Column(name = "ServerVersion")
    private Integer serverversion;

    @Column(name = "ClientVersion")
    private Integer clientversion;

    @Column(name = "ServerDLLName")
    private String serverdllname;

    @Column(name = "ClientExeName")
    private String clientexename;

    private static final long serialVersionUID = 1L;

}