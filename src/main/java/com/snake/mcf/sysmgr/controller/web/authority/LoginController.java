package com.snake.mcf.sysmgr.controller.web.authority;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.snake.mcf.common.BaseController;

/**
 * @ClassName LoginController
 * @Author 大帅
 * @Date 2019/6/19 17:57
 */
@Controller
public class LoginController extends BaseController {

    @GetMapping(path = {"/","/login","/mylogin"})
    public String toLoginPage(){
        return "login";
    }

    /**
     * 设置页面
     *
     * @author: hengoking
     * @param userId
     * @return
     */
    @GetMapping(path = "/setting")
    public String setting(Long userId) {
        this.getRequest().setAttribute("userId", userId);
        return "/setting";
    }

    /**
     * 用户页面
     *
     * @author: hengoking
     * @param userId
     * @return
     */
    @GetMapping(path = "/userinfo")
    public String userinfo(Long userId) {
        this.getRequest().setAttribute("userId", userId);
        return "/userinfo";
    }

    /**
     * 未授权登录
     *
     * @author: hengoking
     * @return
     */
    @GetMapping(path = "/unauthorized")
    public String unauthorized() {
        return "/unauthorized";
    }


    @GetMapping(path = "/index")
    public String index() {
        return "/index";
    }

    @GetMapping(path = "/welcome")
    public String welcome() {
        return "/welcome";
    }

    @GetMapping(path = "/redirect")
    public String redirect() {
        return "/redirect";
    }






}
