package com.snake.mcf.sysmgr.service.goldmgr;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.PersonalroomscoreinfoDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.StreamcreatetablefeeinfoDTO;

public interface GoldmgrRoommdService extends BaseService {

    PageResult<StreamcreatetablefeeinfoDTO> queryStreamCreateTableFeeInfoWithPage(EasyPageFilter pageFilter, StreamcreatetablefeeinfoDTO dto);

    PageResult<PersonalroomscoreinfoDTO> queryPersonalRoomScoreInfoWithPage(EasyPageFilter pageFilter, PersonalroomscoreinfoDTO dto);
}
