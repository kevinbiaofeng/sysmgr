package com.snake.mcf.sysmgr.service.invest;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinepayorderDTO;

public interface InverstRecordService extends BaseService {


    PageResult<OnlinepayorderDTO> queryRecordWithPage(EasyPageFilter pageFilter, OnlinepayorderDTO dto);



}
