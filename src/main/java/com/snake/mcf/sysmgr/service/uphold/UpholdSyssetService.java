package com.snake.mcf.sysmgr.service.uphold;

import java.util.List;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemstatusinfoDTO;

public interface UpholdSyssetService extends BaseService {


    List<SystemstatusinfoDTO> querySysStatusInfo();

    SystemstatusinfoDTO querySysStatusInfoByName(SystemstatusinfoDTO dto);

    boolean updateSysStatusInfo(SystemstatusinfoDTO dto);
}
