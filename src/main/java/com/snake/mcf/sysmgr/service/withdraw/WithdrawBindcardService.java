package com.snake.mcf.sysmgr.service.withdraw;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.AccountsInfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.BindbankcardsDTO;

public interface WithdrawBindcardService extends BaseService {


    PageResult<AccountsInfoDTO> queryAccountUserWithPage(EasyPageFilter pageFilter, AccountsInfoDTO dto);

    PageResult<BindbankcardsDTO> queryBindBankCardsWithPage(EasyPageFilter pageFilter, BindbankcardsDTO dto);

    boolean saveBindBankCards(BindbankcardsDTO dto);

    BindbankcardsDTO queryBindBankCardsById(BindbankcardsDTO dto);

    boolean removeBindBankCardsByIds(Long[] ids);
}
