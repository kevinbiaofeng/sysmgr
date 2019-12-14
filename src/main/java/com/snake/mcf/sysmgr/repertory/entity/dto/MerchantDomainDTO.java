package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName AccountsfaceDTO
 * @Author 大帅
 * @Date 2019/6/26 20:12
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class MerchantDomainDTO extends TbMerchantDomain {

    private static final long serialVersionUID = 1L;
    
    private String createUserName;
    
    private String updateUserName;
}
