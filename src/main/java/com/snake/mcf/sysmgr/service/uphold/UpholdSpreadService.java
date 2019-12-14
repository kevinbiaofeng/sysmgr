package com.snake.mcf.sysmgr.service.uphold;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.SpreadtypepropertyDTO;

public interface UpholdSpreadService extends BaseService {

    PageResult<SpreadtypepropertyDTO> querySpreadTypeWithPage(EasyPageFilter pageFilter, SpreadtypepropertyDTO dto);

    boolean isExistSpreadType(SpreadtypepropertyDTO dto);

    boolean saveSpreadType(SpreadtypepropertyDTO dto);

    SpreadtypepropertyDTO querySpreadTypeById(SpreadtypepropertyDTO dto);

    boolean updateSpreadType(SpreadtypepropertyDTO dto);

    boolean removeSpreadTypeByIds(Integer[] ids);
}
