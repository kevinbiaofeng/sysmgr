package com.snake.mcf.sysmgr.service.sys;

import java.util.List;

import com.snake.mcf.sysmgr.base.BaseService;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysResourceDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.ResourceGroup;

public interface SysResourceService extends BaseService {

    boolean saveResource(TbSysResourceDTO resourceDto);

    List<ResourceGroup> queryResourceTree(String sysUserId);

    TbSysResourceDTO querySysResourceById(String id);

    Integer deleteSysResource(String[] ids);

    boolean updateResource(TbSysResourceDTO resourceDto);
}
