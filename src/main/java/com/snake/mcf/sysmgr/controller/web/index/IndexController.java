package com.snake.mcf.sysmgr.controller.web.index;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.sysmgr.service.index.IndexService;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName IndexController
 * @Author 大帅
 * @Date 2019/7/17 15:00
 */
@Slf4j
@Controller
@RequestMapping(path = "/api/index")
public class IndexController extends BaseController {

    @Autowired
    private IndexService indexService;

    /**
     * 查询首页数据信息
     *
     * @return
     */
    @GetMapping(path = "/queryIndexFront")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> queryIndexFront(){
        log.info("{}","queryIndexFront");
        Map<String,Object> map = indexService.queryIndexFront();
        return ResponseEntity.ok(map);
    }




}
