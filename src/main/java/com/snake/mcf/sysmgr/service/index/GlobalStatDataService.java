package com.snake.mcf.sysmgr.service.index;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.GlobalGroup;

/**
 * 全局统计
 */
public interface GlobalStatDataService {

    /**
     * 查询全局统计数据
     *
     * @return
     */
    GlobalGroup queryGlobalStatData();
}
