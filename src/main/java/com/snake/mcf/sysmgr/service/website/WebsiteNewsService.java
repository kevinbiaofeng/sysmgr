package com.snake.mcf.sysmgr.service.website;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemnoticeDTO;

public interface WebsiteNewsService extends BaseService {

    PageResult<SystemnoticeDTO> querySystemNoticeWithPage(EasyPageFilter pageFilter, SystemnoticeDTO dto);

    SystemnoticeDTO querySystemNoticeById(SystemnoticeDTO dto);

    boolean saveSystemNotice(SystemnoticeDTO dto);

    boolean updateSystemNotice(SystemnoticeDTO dto);

    boolean removeSystemNoticeByIds(Integer[] noticeids);
}
