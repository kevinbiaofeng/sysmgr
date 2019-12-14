package com.snake.mcf.sysmgr.service.invest;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.ApppayconfigDTO;

public interface InverstSetupService {


    PageResult<ApppayconfigDTO> querySetupWithPage(EasyPageFilter pageFilter, ApppayconfigDTO dto);

    boolean saveAppConfig(ApppayconfigDTO dto);

    boolean isExistAppleid(ApppayconfigDTO dto);

    ApppayconfigDTO queryAppConfigById(ApppayconfigDTO dto);

    boolean updateAppConfig(ApppayconfigDTO dto);

    boolean removeAppConfigIds(Integer[] configids);
}
