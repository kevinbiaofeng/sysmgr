package com.snake.mcf.sysmgr.controller.web.authority;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ErrorController
 * @Author 大帅
 * @Date 2019/7/9 14:18
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    @GetMapping(path = "/401")
    public String error401(){
        return "error/401";
    }
    @GetMapping(path = "/404")
    public String error404(){
        return "error/404";
    }

    @GetMapping(path = "/500")
    public String error500(){
        return "error/500";
    }

}
