package com.snake.mcf.sysmgr.service.index;

import java.util.List;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.DistributeGroup;

public interface DistributeStatDataService {

    /**
     * 查询金币分布统计
     *
     * @return
     */
    List<DistributeGroup> queryDistributeStatData();


}
