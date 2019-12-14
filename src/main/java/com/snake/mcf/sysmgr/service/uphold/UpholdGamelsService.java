package com.snake.mcf.sysmgr.service.uphold;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.MobilekinditemDTO;

public interface UpholdGamelsService extends BaseService {


    PageResult<MobilekinditemDTO> queryGamelsWithPage(EasyPageFilter pageFilter, MobilekinditemDTO dto);

    List<ComboBoxDTO> loadGameModuleData();

    boolean isExistGamels(MobilekinditemDTO dto);

    boolean saveGamels(MobilekinditemDTO dto);

    MobilekinditemDTO queryGamelsById(MobilekinditemDTO dto);

    boolean updateGamels(MobilekinditemDTO dto);

    boolean batchTransfer(MobilekinditemDTO dto);

    boolean removeGamelsByIds(Integer[] kindids);
}
