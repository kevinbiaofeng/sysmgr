package com.snake.mcf.sysmgr.controller.web.log;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.aop.annotation.AopLog;

/**
 * @ClassName LogTestController
 * @Author 大帅
 * @Date 2019/6/24 17:23
 */
@Controller
@RequestMapping("log/test")
public class LogTestController {

    @AopLog(module = "日志记录测试首页" , description = "")
    @RequestMapping(path = "/index")
    @ResponseBody
    public String index(){
        return "uuuuuuuuuuu";
    }




}
