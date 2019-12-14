package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[WHQJAccountsDB].[dbo].[ReserveIdentifier]")
public class Reserveidentifier implements Serializable {

    @Id
    @Column(name = "GameID")
    private Integer gameid;

    @Column(name = "IDLevel")
    private Integer idlevel;

    @Column(name = "Distribute")
    private Boolean distribute;

    private static final long serialVersionUID = 1L;

}