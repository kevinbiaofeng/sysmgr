package com.snake.mcf.sysmgr.authority.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: AuthorityUsernamePasswordToken
 * @author: litiantong
 * @date: 2019/4/7 20:20
 * @Copyright: 2019 www.wondersgroup.com Inc. All rights reserved.
 * @matters: 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AuthorityUsernamePasswordToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    /**
     * 是否单点登陆
     * true 是
     * false 否
     */
    private boolean single = false;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 用户传递参数 aes密文
     */
    private String content;

    /**
     * http请求
     */
    public HttpServletRequest request;

    /**
     * http响应
     */
    public HttpServletResponse response;

    public AuthorityUsernamePasswordToken(String username, String password,String content,HttpServletRequest request,HttpServletResponse response) {
        this(username,password,(String)null,content);
        this.request = request;
        this.response = response;
    }

    public AuthorityUsernamePasswordToken() {
        this.setRememberMe(false);
        this.single = false;
    }

    public AuthorityUsernamePasswordToken(String content) {
        this((String)null, (String)null,content);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password,String userid,String content,boolean single) {
        this(username, (char[])password, false, (String)null,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, String password) {
        this(username,password,(String)null);
    }

    public AuthorityUsernamePasswordToken(String username, String password,String content) {
        this(username,password,(String)null,content);
    }

    public AuthorityUsernamePasswordToken(String username, String password,String userid,String content) {
        this(username,password,userid,content,false);
    }

    public AuthorityUsernamePasswordToken(String username, String password,String userid,String content,boolean single) {
        this(username, (char[])(password != null ? password.toCharArray() : null), false, (String)null,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, String host,String userid,String content,boolean single) {
        this(username, password, false, host,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, String password, String host,String userid,String content) {
        this(username, password != null ? password.toCharArray() : null, false, host,userid,content,false);
    }

    public AuthorityUsernamePasswordToken(String username, String password, String host,String userid,String content,boolean single) {
        this(username, password != null ? password.toCharArray() : null, false, host,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe) {
        this(username,password,rememberMe,(String)null,(String)null,false);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe,String userid,String content,boolean single) {
        this(username, (char[])password, rememberMe, (String)null,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, String password, boolean rememberMe) {
        this(username,password,rememberMe,(String)null,(String)null);
    }

    public AuthorityUsernamePasswordToken(String username, String password, boolean rememberMe,String content) {
        this(username,password,rememberMe,(String)null,content);
    }

    public AuthorityUsernamePasswordToken(String username, String password, boolean rememberMe,String userid,String content) {
        this(username,password,rememberMe,userid,content,false);
    }

    public AuthorityUsernamePasswordToken(String username, String password, boolean rememberMe,String userid,String content,boolean single) {
        this(username, (char[])(password != null ? password.toCharArray() : null), rememberMe, (String)null,userid,content,single);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host) {
        this(username,password,rememberMe,host,(String)null,(String)null,false);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host,String content) {
        this(username,password,rememberMe,host,(String)null,content,false);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host,String userid,String content) {
        this(username,password,rememberMe,host,userid,content,false);
    }

    public AuthorityUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host,String userid,String content,boolean single) {
        super(username,password,rememberMe,host);
        this.setRememberMe(false);
        this.content = content;
        this.userid = userid;
        this.single = single;
    }

    public AuthorityUsernamePasswordToken(String username, String password, boolean rememberMe, String host,String userid,String content,boolean single) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host,userid,content,single);
    }

    public boolean isSingle() {
        return single;
    }

    public void setSingle(boolean single) {
        this.single = single;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String sup = super.toString();
        sb.append(sup);
        sb.append("#");
        sb.append("userid =" + userid);
        sb.append("#");
        sb.append("content =" + content);
        return sb.toString();
    }
}
