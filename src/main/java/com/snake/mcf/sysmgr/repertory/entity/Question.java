package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[Question]")
public class Question implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "QuestionTitle")
    private String questiontitle;

    @Column(name = "Answer")
    private String answer;

    @Column(name = "SortID")
    private Integer sortid;

    @Column(name = "UpdateAt")
    private Date updateat;

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