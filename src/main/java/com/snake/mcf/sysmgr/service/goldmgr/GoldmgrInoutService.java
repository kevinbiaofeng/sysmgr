package com.snake.mcf.sysmgr.service.goldmgr;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecorduserinoutDTO;
import com.snake.mcf.sysmgr.repertory.entity.vo.RecorduserinoutVO;

public interface GoldmgrInoutService extends BaseService {

    PageResult<RecorduserinoutDTO> queryRecordUserInoutWithPage(EasyPageFilter pageFilter, RecorduserinoutVO vo);




}
