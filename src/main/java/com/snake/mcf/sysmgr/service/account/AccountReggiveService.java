package com.snake.mcf.sysmgr.service.account;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.RegistergiveDTO;

public interface AccountReggiveService {


    PageResult<RegistergiveDTO> queryReggiveWithPage(EasyPageFilter filter, RegistergiveDTO dto);


    boolean saveReggive(RegistergiveDTO dto);

    boolean isExistPlatform(RegistergiveDTO dto);


    RegistergiveDTO queryReggiveById(RegistergiveDTO dto);

    boolean updateReggive(RegistergiveDTO dto);

    boolean removeReggiveByIds(RegistergiveDTO dto);
}
