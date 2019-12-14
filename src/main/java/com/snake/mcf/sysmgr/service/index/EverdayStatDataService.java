package com.snake.mcf.sysmgr.service.index;

import java.util.List;

import com.snake.mcf.sysmgr.repertory.entity.dto.group.EverdayGroup;

/**
 * 每日统计
 */
public interface EverdayStatDataService {


    List<EverdayGroup> queryEverdayStatData();
}
