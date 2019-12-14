package com.snake.mcf.sysmgr.service.account;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamescorelockerDTO;

public interface AccountOnlineService extends BaseService {


    List<ComboBoxDTO> loadGameRoomComboData(Integer nullity);

    PageResult<GamescorelockerDTO> queryGamescorelockerWithPage(EasyPageFilter pageFilter, GamescorelockerDTO dto);

    boolean batchCleanLocker(GamescorelockerDTO dto);
}
