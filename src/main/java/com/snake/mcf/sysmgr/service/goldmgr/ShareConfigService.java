package com.snake.mcf.sysmgr.service.goldmgr;

import java.util.List;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.Shareconfig;

public interface ShareConfigService extends BaseService {

	List<Shareconfig> getShareRewardList();
}
