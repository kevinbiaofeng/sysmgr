package com.snake.mcf.sysmgr.service.uphold;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ShareconfigDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.SharelogDTO;

public interface UpholdShareService extends BaseService {


    PageResult<ShareconfigDTO> queryShareConfigWithPage(EasyPageFilter pageFilter, ShareconfigDTO dto);

    /**
     * 是否存在 true 存在 false 不存在
     * @param dto
     * @return
     */
    boolean isExistShareConfig(ShareconfigDTO dto);

    boolean saveShareConfig(ShareconfigDTO dto);

    ShareconfigDTO queryShareConfigById(ShareconfigDTO dto);

    boolean updateShareConfig(ShareconfigDTO dto);

    PageResult<SharelogDTO> queryShareLogWithPage(EasyPageFilter pageFilter, SharelogDTO dto);
}
