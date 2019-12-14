package com.snake.mcf.sysmgr.base;

import com.snake.mcf.common.utils.GeneratotUtils;
import com.snake.mcf.sysmgr.base.server.ApplicationServer;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import com.snake.mcf.sysmgr.repertory.mapper.TbSysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName BaseServiceImpl
 * @Author 大帅
 * @Date 2019/6/21 10:00
 */
@Slf4j
public abstract class BaseServiceImpl implements BaseService {

    @Autowired
    private TbSysLogMapper sysLogMapper;

    @Autowired
    private ApplicationServer applicationServer;

    @Override
    public int saveSysLong(TbSysLogDTO tbSysLogDTO) {
        //主键策略
        tbSysLogDTO.setId(GeneratotUtils.generateNumber());
        //操作时间
        tbSysLogDTO.setCreatedDate(new Date());
        //当前操作人id
        //tbSysLogDTO.setAccount(tbSysLogDTO.getAccount());
        //获取当前操作人ip
       //tbSysLogDTO.setIp(applicationServer.getIP());

        sysLogMapper.insertSelective(tbSysLogDTO);
        return 0;
    }

    @Override
    public List<String> getRoleName(List<TbSysRoleDTO> list) {
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List<String> list1 = new ArrayList<>();
        list.forEach( (o) -> {
            String name = o.getName();
            list1.add(name);
        });
        return list1;
    }


}
