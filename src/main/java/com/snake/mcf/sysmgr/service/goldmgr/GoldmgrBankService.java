package com.snake.mcf.sysmgr.service.goldmgr;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordinsureDTO;

public interface GoldmgrBankService extends BaseService {

    PageResult<RecordinsureDTO> queryRecordInsureWithPage(EasyPageFilter pageFilter, RecordinsureDTO dto);
}
