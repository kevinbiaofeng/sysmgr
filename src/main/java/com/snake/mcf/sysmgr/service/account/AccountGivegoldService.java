package com.snake.mcf.sysmgr.service.account;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordgranttreasureDTO;

public interface AccountGivegoldService extends BaseService {


    PageResult<RecordgranttreasureDTO> queryGivegoldWithPage(EasyPageFilter pageFilter, RecordgranttreasureDTO dto);


}
