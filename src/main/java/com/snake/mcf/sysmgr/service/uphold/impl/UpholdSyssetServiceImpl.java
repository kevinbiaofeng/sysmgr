package com.snake.mcf.sysmgr.service.uphold.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.uphold.UpholdSyssetMapper;
import com.snake.mcf.sysmgr.repertory.entity.Systemstatusinfo;
import com.snake.mcf.sysmgr.repertory.entity.dto.SystemstatusinfoDTO;
import com.snake.mcf.sysmgr.repertory.mapper.SystemstatusinfoMapper;
import com.snake.mcf.sysmgr.service.uphold.UpholdSyssetService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName UpholdSyssetServiceImpl
 * @Author 大帅
 * @Date 2019/7/1 20:24
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class UpholdSyssetServiceImpl extends BaseServiceImpl implements UpholdSyssetService {

    @Autowired
    private UpholdSyssetMapper upholdSyssetMapper;

    @Autowired
    private SystemstatusinfoMapper systemstatusinfoMapper;

    @Override
    public List<SystemstatusinfoDTO> querySysStatusInfo() {
        List<SystemstatusinfoDTO> list = upholdSyssetMapper.querySysStatusInfo();
        return list;
    }

    @Override
    public SystemstatusinfoDTO querySysStatusInfoByName(SystemstatusinfoDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入 对象 为空");
        }
        String statusname = dto.getStatusname();
        if(StringUtils.isBlank(statusname)){
            throw new BusinessException("根据id查询,传入 对象 statusname 为空");
        }
        Systemstatusinfo systemstatusinfo = systemstatusinfoMapper.selectByPrimaryKey(statusname);
        SystemstatusinfoDTO record = new SystemstatusinfoDTO();
        CommonBeans.copyPropertiesIgnoreNull(systemstatusinfo,record);
        return record;
    }

    @Override
    public boolean updateSysStatusInfo(SystemstatusinfoDTO dto) {
        if(dto == null){
            throw new BusinessException("根据id查询,传入 对象 为空");
        }
        String statusname = dto.getStatusname();
        if(StringUtils.isBlank(statusname)){
            throw new BusinessException("根据id查询,传入 对象 statusname 为空");
        }
        Systemstatusinfo record = new Systemstatusinfo();
        CommonBeans.copyPropertiesIgnoreNull(dto,record);
        int count = systemstatusinfoMapper.updateByPrimaryKeySelective(record);
        return count > 0;
    }



}
