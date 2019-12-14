package com.snake.mcf.sysmgr.repertory.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @ClassName TbMerchant
 * @Author 大帅
 * @Date 2019/7/18 16:38
 */
@Data
@Table(name = "TbMerchant")
public class TbMerchant implements Serializable {

    @Id
    @Column(name = "Merchant")
    private String merchant;

//    @Column(name = "MerchantID")
//    private Integer merchantid;

    @Column(name = "Name")
    private String name;

    @Column(name = "CommissionRatio")
    private Integer commissionratio;
    
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "mail")
    private String mail;
    
    @Column(name = "qq_account")
    private String qqAccount;
    
    @Column(name = "wechat_account")
    private String wechatAccount;
    
    @Column(name = "safe_password")
    private String safePassword;
    
    private static final long serialVersionUID = 1L;


}
