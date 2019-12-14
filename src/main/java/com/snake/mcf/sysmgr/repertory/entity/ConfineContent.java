package com.snake.mcf.sysmgr.repertory.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJAccountsDB].[dbo].[ConfineContent]")
@ToString
public class ConfineContent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ContentId")
    private Integer contentId;

    @Column(name = "String")
    @NotNull
    private String string;//敏感词汇

    @Column(name = "EnjoinOverDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date enjoinOverDate;//有效时间

    @Column(name = "CollectDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date collectDate;//录入日期

}