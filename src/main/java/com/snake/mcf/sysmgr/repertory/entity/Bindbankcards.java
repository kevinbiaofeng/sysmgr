package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[BindBankCards]")
public class Bindbankcards implements Serializable {

    @Id
    @Column(name = "BindID")
    private Long bindid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "Merchant")
    private String merchant;

    @Column(name = "BankChoice")
    private Integer bankchoice;

    @Column(name = "BankCardID")
    private String bankcardid;

    @Column(name = "BankName")
    private String bankname;

    @Column(name = "IDCardName")
    private String idcardname;

    @Column(name = "BindTime")
    private Date bindtime;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "Type")
    private Integer type;

    private static final long serialVersionUID = 1L;

}