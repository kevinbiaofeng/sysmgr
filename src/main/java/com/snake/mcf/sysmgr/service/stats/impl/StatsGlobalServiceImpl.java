package com.snake.mcf.sysmgr.service.stats.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.mapper.stats.StatsGlobalMapper;
import com.snake.mcf.sysmgr.service.stats.StatsGlobalService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName StatsGlobalServiceImpl
 * @Author 大帅
 * @Date 2019/7/15 10:09
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class StatsGlobalServiceImpl extends BaseServiceImpl implements StatsGlobalService {

    @Autowired
    private StatsGlobalMapper statsGlobalMapper;




}
