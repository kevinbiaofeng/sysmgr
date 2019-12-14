package com.snake.mcf.sysmgr.service.sys;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;
import com.snake.mcf.sysmgr.repertory.entity.dto.MerchantDomainDTO;

public interface SysMerchantDomainService extends BaseService {
	
	/**
	 * 根据domainId获取对象
	 * @param domainId
	 * @return
	 */
    public TbMerchantDomain getMerchantDomainById(String domainId);
    
    /**
	 * 根据domainId获取对象
	 * @param merchant   根据商户号获取域名
	 * @return
	 */
    public List<TbMerchantDomain> getMerchantDomainByMerchantAndType(String merchant, String type);
    
    /**
	 * 根据条件获取List
	 * @param domainId 主键
	 * @param status 状态   0正常 1删除	非必填
	 * @param type 类型  1：推广   2：API 非必填
	 * @return 
	 */
    public List<TbMerchantDomain> queryListByExample(EasyPageFilter pageFilter, String userId, String _status, String _type);
    
    /**
     * 根据条件获取Page List
     * @param pageFilter
     * @param userId
     * @param status
     * @param type
     * @return
     */
    public PageResult<MerchantDomainDTO> queryPageListByExample(EasyPageFilter pageFilter, String merchant, String _status, String _type);
    
    /**
     * 新增域名信息
     * @param merchantDomain
     * @return
     */
    public boolean saveMerchantDomain(TbMerchantDomain merchantDomain);
    
    /**
     * 删除域名信息
     * @param merchantDomain
     * @return
     */
    public boolean deleteMerchantDomainById(String id);
}
