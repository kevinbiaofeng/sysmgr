package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysUserRole]")
public class TbSysUserRole implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "UserId")
    private String userId;

    @Column(name = "RoleId")
    private String roleId;

    private static final long serialVersionUID = 1L;

}