package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName RoleResourceGroup
 * @Author 大帅
 * @Date 2019/6/22 17:26
 */
@Data
public class RoleResourceGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String menuid;

    private String text;

    private String state;

    private boolean checked = false;

    private List<RoleResourceGroup> children;



}
