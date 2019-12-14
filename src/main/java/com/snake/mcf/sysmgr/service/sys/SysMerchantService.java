package com.snake.mcf.sysmgr.service.sys;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchant;

public interface SysMerchantService extends BaseService {

	TbMerchant queryMerchantById(String id);

}
