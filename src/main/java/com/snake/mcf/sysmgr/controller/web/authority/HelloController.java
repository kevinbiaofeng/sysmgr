package com.snake.mcf.sysmgr.controller.web.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.snake.mcf.sysmgr.service.authority.HelloService;

/**
 * @ClassName HelloController
 * @Author 大帅
 * @Date 2019/6/19 16:50
 */
@Controller
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping(path = "/test")
    //@ResponseBody
    public String test(){
        String a = helloService.test();
        //return "test " + a;
        return "test/test_tab";
    }

    @GetMapping(path = "/test/{type}")
    //@ResponseBody
    public String type(@PathVariable(name = "type")int type){
        //String a = helloService.test();
        //return "test " + a;
        return "test/ed_" + type;
    }





}
