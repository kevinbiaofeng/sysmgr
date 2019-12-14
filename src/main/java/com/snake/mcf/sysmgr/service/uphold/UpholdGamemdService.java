package com.snake.mcf.sysmgr.service.uphold;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamegameitemDTO;

public interface UpholdGamemdService extends BaseService {


    PageResult<GamegameitemDTO> queryGamemdWithPage(EasyPageFilter pageFilter, GamegameitemDTO dto);

    List<ComboBoxDTO> loadDataBaseInfo();

    GamegameitemDTO queryGamemdById(GamegameitemDTO dto);

    boolean isExistGamemd(GamegameitemDTO dto);

    boolean saveGamemd(GamegameitemDTO dto);

    boolean updateGamemd(GamegameitemDTO dto);

    boolean removeGamemdByIds(Integer[] gameids);
}
