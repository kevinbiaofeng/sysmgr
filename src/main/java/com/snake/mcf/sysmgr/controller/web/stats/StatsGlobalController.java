package com.snake.mcf.sysmgr.controller.web.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.sysmgr.service.stats.StatsGlobalService;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局统计
 *
 * @ClassName StatsGlobalController
 * @Author 大帅
 * @Date 2019/7/15 10:08
 */
@Slf4j
@Controller
@RequestMapping(path = "/stats/global")
public class StatsGlobalController extends BaseController {

    @Autowired
    private StatsGlobalService statsGlobalService;




}
