package com.snake.mcf.sysmgr.base.log;

import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.TbSysLog;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName SysLogServiceImpl
 * @Author 大帅
 * @Date 2019/6/24 17:17
 */
@Slf4j
@Transactional
@Service
public class SysLogServiceImpl extends BaseServiceImpl implements SysLogService {

    @Autowired
    private TbSysLogMapper sysLogMapper;

    @Override
    public boolean saveSysLog(TbSysLogDTO sysLogDTO) {
        TbSysLog record = new TbSysLog();
        CommonBeans.copyPropertiesIgnoreNull(sysLogDTO,record);
        int count = sysLogMapper.insertSelective(record);
        return count > 0;

    }




}
