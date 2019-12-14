package com.snake.mcf.sysmgr.controller.web.authority;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snake.mcf.common.BaseController;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.common.web.result.Result;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.authority.token.AuthorityUsernamePasswordToken;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysUserDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AuthorityController
 * @Author 大帅
 * @Date 2019/6/20 10:49
 */
@Slf4j
@Controller(value = "authorityAuthorityController")
@RequestMapping(path = "/authority")
public class AuthorityController extends BaseController {

    /**
     * 登陆<br/>
     * 通过返回对象
     *
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @RequestMapping(path = "/shiroLogin",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Result> loginResp(@RequestParam(name = "username",defaultValue = "")String username,
                                            @RequestParam(name = "password",defaultValue = "000000")String password,
                                            @RequestParam(name = "rememberMe",defaultValue = "false")boolean rememberMe){
        log.info("username-->{}",username);

        log.info("password--->{}",password);

        log.info("rememberMe--->{}",rememberMe);

        Result result = null;

        if(StringUtils.isBlank(username)) {
            result = Result.fail("登录用户名为空");
            return ResponseEntity.ok(result);
        }
        if(StringUtils.isBlank(password)) {
            result = Result.fail("登录密码为空");
            return ResponseEntity.ok(result);
        }

        //返回值
        Map<String, Object> resultMap = new LinkedHashMap<>();
        Subject subject = SecurityUtils.getSubject();
        if(subject != null){
            SecurityUtils.getSubject().getSession().setTimeout(1800000L);
        }
        if (!subject.isAuthenticated()) {
            //登录校验
            AuthorityUsernamePasswordToken token = new AuthorityUsernamePasswordToken(username, password, null, this.getRequest(), this.getResponse());
            //记住我
            token.setRememberMe(rememberMe);
            try {
                //登录
                subject.login(token);
                resultMap.put("url", request.getContextPath() + ShiroUtils.DEFAULT_REDIRECT_URL);
                resultMap.put("message", "用户登录成功");
                result = Result.succ(resultMap);
            } catch (DisabledAccountException dax) {
                log.info("用户名为:{} 用户已经被禁用！",username);
                result =  Result.fail("帐号已被禁用");
            } catch (ExcessiveAttemptsException eae) {
                log.info("用户名为:" + username + " 用户登录次数过多，有暴力破解的嫌疑！");
                result =  Result.fail("用户登录次数过多，有暴力破解的嫌疑");
            } catch (AccountException ae) {
                log.info("用户名为:{}帐号或密码错误" , token.getPrincipal());
                result =  Result.fail("帐号或密码错误");
            } catch (AuthenticationException ae) {
                log.error("{}",ae);
                log.info("{}","------------------身份认证失败-------------------");
                result =  Result.fail("身份认证失败");
            } catch (Exception e) {
                log.error("{}",e);
                log.info("{}","未知异常信息。。。。");
                result =  Result.fail("登录认证错误");
            }
        }else {
            resultMap.put("url", request.getContextPath() + ShiroUtils.DEFAULT_REDIRECT_URL);
            resultMap.put("message", "用户登录成功");
            result = Result.succ(resultMap);
        }
        // return ResponseEntity.ok(Result.succ());
        return ResponseEntity.ok(result);
    }

    /**
     * 注销
     *
     * @author: hengoking
     * @return
     */
    @GetMapping(path = "/shiroLogout")
    public String logout() {
        //清除 登录信息
        Subject subject = SecurityUtils.getSubject();
        //true 已经登录  false 未登录
        log.info("shiroLogout:{}",subject.isAuthenticated());
        //已经通过验证 退出
        if(subject.isAuthenticated()) {
            try {
                subject.logout();
            } catch (Exception e) {
                log.error("errorMessage:" + e.getMessage());
            }
        }
        return "redirect:" + ShiroUtils.DEFAULT_LOGIN_URL;
    }

    /**
     * 查询菜单信息
     *
     * @return
     */
    @GetMapping(path = "/menuResource")
    @ResponseBody
    public ResponseEntity<Map<String,Object>> menuResource(){
        TbSysUserDTO sessionUser = ShiroUtils.getSessionUser();
        if(sessionUser == null){
            throw new BusinessException("该用户session已过期");
        }
        Map<String, Object> menuMap = sessionUser.getMenuMap();
        return ResponseEntity.ok(menuMap);
    }




}
