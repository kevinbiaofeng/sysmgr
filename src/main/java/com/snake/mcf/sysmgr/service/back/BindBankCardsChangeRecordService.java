package com.snake.mcf.sysmgr.service.back;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindBankCardsChangeRecordDto;
import com.snake.mcf.sysmgr.repertory.entity.vo.BindBankCardsChangeRecordVo;

public interface BindBankCardsChangeRecordService extends BaseService {


    PageResult<BindBankCardsChangeRecordDto> queryBindBankCardsChangeRecordWithPage(EasyPageFilter pageFilter, BindBankCardsChangeRecordVo vo);
}
