package com.snake.mcf.sysmgr.repertory.entity.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单资源树形结构
 * 
 * @ClassName:  ResourceGroup   
 * @author: hengoking
 * @date:   2019年1月2日 下午4:32:03   
 *     
 * @Copyright: 2019 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceGroup implements Serializable{

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	private String id;

    /**
     * 菜单Id
     */
	private String menuid;

    /**
     * 父菜单ID 如果是一级菜单 默认是0
     */
    private String parentMenuid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 创建者
     */
    private String createdByName;

    /**
     * 修改者
     */
    private String updatedByName;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 最后修改时间
     */
    private Date updatedDate;

    /**
     * 默认折叠
     */
    private String state;

    /**
     * 菜单等级
     */
    private Integer lev;

    /**
     * 判断是否是叶子节点
     */
    private boolean isLeaf = false;

    /**
     * 父节点下子节点
     */
    private List<ResourceGroup> children;
    
}
