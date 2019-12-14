package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[WHQJGroupDB].[dbo].[IMGroupOption]")
public class Imgroupoption implements Serializable {

    @Id
    @Column(name = "OptionName")
    private String optionname;

    @Column(name = "OptionValue")
    private Integer optionvalue;

    @Column(name = "OptionDescribe")
    private String optiondescribe;

    @Column(name = "OptionTip")
    private String optiontip;

    @Column(name = "SortID")
    private Integer sortid;

    /*@Column(name = "Merchant")
    private String merchant;

    @Column(name = "CreatedDate")
    private Date createdDate;

    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @Column(name = "Account")
    private String account;*/

    private static final long serialVersionUID = 1L;

}