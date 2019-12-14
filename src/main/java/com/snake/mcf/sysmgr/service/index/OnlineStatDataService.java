package com.snake.mcf.sysmgr.service.index;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.OnlineGroup;

/**
 * 在线统计
 */
public interface OnlineStatDataService {

    /**
     * 查询在线统计
     *
     * @return
     */
    OnlineGroup queryOnlineStatData();
}
