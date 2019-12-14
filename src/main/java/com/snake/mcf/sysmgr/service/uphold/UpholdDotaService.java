package com.snake.mcf.sysmgr.service.uphold;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.Gameproperty;
import com.snake.mcf.sysmgr.repertory.entity.dto.GamepropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordbuynewpropertyDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.RecordusepropertyDTO;

/**
 * @ClassName UpholdDotaService
 * @Author 大帅
 * @Date 2019/7/2 17:57
 */
public interface UpholdDotaService extends BaseService {


    PageResult<GamepropertyDTO> queryDotaWithPage(EasyPageFilter pageFilter, GamepropertyDTO dto);

    GamepropertyDTO queryDotaById(GamepropertyDTO dto);

    boolean updateDota(GamepropertyDTO dto);

    boolean isExistDota(GamepropertyDTO dto);

    PageResult<RecordbuynewpropertyDTO> queryBuyDotaWithPage(EasyPageFilter pageFilter, RecordbuynewpropertyDTO dto);

    PageResult<RecordusepropertyDTO> queryUseDotaWithPage(EasyPageFilter pageFilter, RecordusepropertyDTO dto);

    boolean saveOrUpdateDota(Gameproperty vo);
}
