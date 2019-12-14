package com.snake.mcf.sysmgr.repertory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name = "TbMerchantDomain")
public class TbMerchantDomain implements Serializable {
	@Id
	@Column(name = "id",insertable=false,updatable=false)
    private Long id;

    @Column(name = "domain_url")
    private String domainUrl;

    @Column(name = "status")
    private Integer status;
    
    @Column(name = "type")
    private Integer type;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "remark")
    private String remark;
    
    @Column(name = "create_user")
    private Long createUser;
    
    @Column(name = "create_date")
    private Date createDate;
    
    @Column(name = "update_user")
    private Long updateUser;
    
    @Column(name = "update_date")
    private Date updateDate;
    
    private static final long serialVersionUID = 1L;
}
