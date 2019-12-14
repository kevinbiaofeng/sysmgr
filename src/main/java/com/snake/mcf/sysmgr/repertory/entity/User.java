package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Data
@Table(name = "[JavaSystemManager].[dbo].[user]")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    private static final long serialVersionUID = 1L;

}