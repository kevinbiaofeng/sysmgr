package com.snake.mcf.sysmgr.service.withdraw;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.CashoutordersDTO;

public interface WithdrawRecordService extends BaseService {


    PageResult<CashoutordersDTO> queryCashOutOrdersWithPage(EasyPageFilter pageFilter, CashoutordersDTO dto);

    boolean updateCashOutOrders(CashoutordersDTO dto);

    boolean updateFinancialStatus(CashoutordersDTO dto);
}
