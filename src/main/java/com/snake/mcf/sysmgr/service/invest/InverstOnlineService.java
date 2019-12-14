package com.snake.mcf.sysmgr.service.invest;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.OnlinewechatDTO;

public interface InverstOnlineService extends BaseService {


    PageResult<OnlinewechatDTO> queryOnLineWithPage(EasyPageFilter pageFilter, OnlinewechatDTO dto);


    boolean saveOnLineWeChat(OnlinewechatDTO dto);

    OnlinewechatDTO queryOnLineWeChatById(OnlinewechatDTO dto);

    boolean updateOnLineWeChat(OnlinewechatDTO dto);

    boolean removeOnLineByIds(Integer[] configids);
}
