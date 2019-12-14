package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJTreasureDB].[dbo].[BindBankCardsChangeRecord]")
public class BindBankCardsChangeRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "merchant")
    private String merchant;

    @Column(name = "bank_choice")
    private Integer bankChoice;

    @Column(name = "bank_card_id")
    private String bankCardId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "id_card_name")
    private String idCardName;

    @Column(name = "bind_time")
    private Date bindTime;

    @Column(name = "nullity")
    private Integer nullity;

    @Column(name = "operator")
    private String operator;

    @Column(name = "create_time")
    private Date createTime;

}