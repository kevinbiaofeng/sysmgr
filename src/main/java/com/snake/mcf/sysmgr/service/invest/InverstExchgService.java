package com.snake.mcf.sysmgr.service.invest;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.CurrencyexchconfigDTO;

public interface InverstExchgService extends BaseService {


    PageResult<CurrencyexchconfigDTO> queryExchgWithPage(EasyPageFilter pageFilter, CurrencyexchconfigDTO dto);

    boolean saveExchConfig(CurrencyexchconfigDTO dto);

    boolean isExistDiamond(CurrencyexchconfigDTO dto);

    CurrencyexchconfigDTO queryExchgById(CurrencyexchconfigDTO dto);

    boolean updateExchConfig(CurrencyexchconfigDTO dto);

    boolean removeExchgConfigIds(Integer[] configids);
}
