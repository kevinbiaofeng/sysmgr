package com.snake.mcf.sysmgr.service.index.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.DistributeGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.EverdayGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.GlobalGroup;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.OnlineGroup;
import com.snake.mcf.sysmgr.service.index.DistributeStatDataService;
import com.snake.mcf.sysmgr.service.index.EverdayStatDataService;
import com.snake.mcf.sysmgr.service.index.GlobalStatDataService;
import com.snake.mcf.sysmgr.service.index.IndexService;
import com.snake.mcf.sysmgr.service.index.OnlineStatDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName IndexServiceImpl
 * @Author 大帅
 * @Date 2019/7/17 15:02
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class IndexServiceImpl extends BaseServiceImpl implements IndexService {

    //查询全局统计
    @Autowired
    private GlobalStatDataService globalStatDataService;

    //查询每日统计图表
    @Autowired
    private EverdayStatDataService everdayStatDataService;

    //在线统计
    @Autowired
    private OnlineStatDataService onlineStatDataService;

    //分布统计
    @Autowired
    private DistributeStatDataService distributeStatDataService;

    @Override
    public Map<String, Object> queryIndexFront() {
        Map<String, Object> map = new HashMap<>();

        //查询全局统计数据
        GlobalGroup globalGroup = globalStatDataService.queryGlobalStatData();
        map.put("globalGroup",globalGroup);

        //查询每日统计图表
        List<EverdayGroup> everdayGroupList = everdayStatDataService.queryEverdayStatData();
        map.put("everdayGroupList",everdayGroupList);

        //查询在线统计数据
        OnlineGroup onlineGroup = onlineStatDataService.queryOnlineStatData();
        map.put("onlineGroup",onlineGroup);

        //查询分布统计数据
        List<DistributeGroup> distributeGroupList = distributeStatDataService.queryDistributeStatData();
        map.put("distributeGroupList",distributeGroupList);

        return map;
    }


}
