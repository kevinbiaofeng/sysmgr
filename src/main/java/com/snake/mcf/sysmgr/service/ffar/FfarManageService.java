package com.snake.mcf.sysmgr.service.ffar;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgroupmemberDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.ImgrouppropertyDTO;

public interface FfarManageService extends BaseService {

    PageResult<ImgrouppropertyDTO> queryIMGroupPropertyWithPage(EasyPageFilter pageFilter, ImgrouppropertyDTO dto);

    boolean groupstatusUpate(ImgrouppropertyDTO dto);

    PageResult<ImgroupmemberDTO> queryGroupMemberWithPage(EasyPageFilter pageFilter, ImgroupmemberDTO dto);
}
