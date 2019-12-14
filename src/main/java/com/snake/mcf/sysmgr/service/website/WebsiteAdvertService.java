package com.snake.mcf.sysmgr.service.website;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.AdsDTO;

public interface WebsiteAdvertService extends BaseService {

    PageResult<AdsDTO> queryAdsWithPage(EasyPageFilter pageFilter, AdsDTO dto);

    AdsDTO queryAdsById(AdsDTO dto);

    boolean saveAds(AdsDTO dto);

    boolean removeAdsByIds(Integer[] ids);
}
