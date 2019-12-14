package com.snake.mcf.sysmgr.base.log;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;

/**
 * @ClassName SysLogService
 * @Author 大帅
 * @Date 2019/6/24 17:16
 */
public interface SysLogService extends BaseService {

    public boolean saveSysLog(TbSysLogDTO sysLogDTO);


}
