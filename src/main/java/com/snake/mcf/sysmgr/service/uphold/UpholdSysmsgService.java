package com.snake.mcf.sysmgr.service.uphold;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemmessageDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.GameInfoGroup;

public interface UpholdSysmsgService extends BaseService {


    PageResult<SystemmessageDTO> querySysmsgWithPage(EasyPageFilter pageFilter, SystemmessageDTO dto);

    List<GameInfoGroup> loadGameIndoData(Integer id);

    boolean saveSysmsg(SystemmessageDTO dto);

    SystemmessageDTO querySysmsgById(SystemmessageDTO dto);

    boolean updateSysmsg(SystemmessageDTO dto);

    boolean batchTransfer(SystemmessageDTO dto);

    boolean removeSysmsgByIds(Integer[] ids);
}
