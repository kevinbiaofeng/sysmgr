package com.snake.mcf.sysmgr.base;

import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;

import java.util.List;

/**
 * @ClassName BaseService
 * @Author 大帅
 * @Date 2019/6/21 10:00
 */
public interface BaseService {

    public int saveSysLong(TbSysLogDTO tbSysLogDTO);

    public List<String> getRoleName(List<TbSysRoleDTO> list);



}
