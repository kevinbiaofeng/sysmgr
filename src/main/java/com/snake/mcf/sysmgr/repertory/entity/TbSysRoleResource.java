package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "[JavaSystemManager].[dbo].[TbSysRoleResource]")
public class TbSysRoleResource implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "RoleId")
    private String roleId;

    @Column(name = "ResourceId")
    private String resourceId;

    private static final long serialVersionUID = 1L;

}