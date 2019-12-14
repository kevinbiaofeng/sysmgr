package com.snake.mcf.sysmgr.service.index.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.common.utils.DateUtils;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.mapper.index.GlobalStatDataMapper;
import com.snake.mcf.sysmgr.repertory.entity.dto.group.GlobalGroup;
import com.snake.mcf.sysmgr.service.index.GlobalStatDataService;

import lombok.extern.slf4j.Slf4j;

/**
 * 查询全局统计数据
 *
 * @ClassName GlobalStatDataServiceImpl
 * @Author 大帅
 * @Date 2019/7/18 12:19
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class GlobalStatDataServiceImpl implements GlobalStatDataService {

    @Autowired
    private GlobalStatDataMapper globalStatDataMapper;

    @Override
    public GlobalGroup queryGlobalStatData() {
        String now = DateUtils.getNow();
        log.info("今日：{}",now);
        String nowStart = DateUtils.getNowStart();
        log.info("今日 start ：{}",nowStart);
        String nowEnd = DateUtils.getNowEnd();
        log.info("今日 end ：{}",nowEnd);

        String yesterday = DateUtils.getYesterday();
        log.info("昨日：{}",yesterday);
        String yesterdayStart = DateUtils.getYesterdayStart();
        log.info("昨日 start：{}",yesterdayStart);
        String yesterdayEnd = DateUtils.getYesterdayEnd();
        log.info("昨日 end：{}",yesterdayEnd);

        Map<String,Object> paramMap = new HashMap<>();
        //今日开始
        paramMap.put("todayStart",nowStart);
        //今日结束
        paramMap.put("todayEnd",nowEnd);
        //昨日开始
        paramMap.put("yesterdayStart",yesterdayStart);
        //昨日结束
        paramMap.put("yesterdayEnd",yesterdayEnd);
        //是否是管理员  不是 需要加一个字段过滤
        if(!ShiroUtils.isAdminSessionUserId()){
            paramMap.put("merchant",ShiroUtils.getSessionMerchant());
        }
        GlobalGroup group = globalStatDataMapper.queryGlobalStatData(paramMap);
        return group;
    }
}
