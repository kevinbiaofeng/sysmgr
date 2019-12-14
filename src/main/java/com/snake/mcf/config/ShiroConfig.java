package com.snake.mcf.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.snake.mcf.common.constant.UniversalConstant;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.StringUtils;
import com.snake.mcf.sysmgr.authority.realm.LoginAuthorRealm;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName ShiroConfig
 * @Author 大帅
 * @Date 2019/6/20 10:24
 */
@Slf4j
@Configuration
public class ShiroConfig {

    @Value("${shiro.anno}")
    private String shiroAnno;

    @Value("${shiro.logout}")
    private String shiroLogout;

    @Value("${shiro.authc}")
    private String shiroAuthc;
    
    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.timeout}")
    private int timeout;

    private static LinkedHashMap<String, List<String>> filterMap = new LinkedHashMap<>();
    public static final String INTERCEPTOR_AUTHORITY_ANON = "anon";
    public static final String INTERCEPTOR_AUTHORITY_AUTHC = "authc";
    public static final String INTERCEPTOR_AUTHORITY_LOGOUT = "logout";

    static{
        try {
            //为保证 filterMap 按照 anon logout authc 顺序
            //设置为空
            filterMap.put(INTERCEPTOR_AUTHORITY_ANON,new LinkedList<String>());
            filterMap.put(INTERCEPTOR_AUTHORITY_LOGOUT,new LinkedList<String>());
            filterMap.put(INTERCEPTOR_AUTHORITY_AUTHC,new LinkedList<String>());

        } catch (Exception e) {
            log.error("解析拦截路径错误:{}",e);
        }

    }

    /**
     * 构建定义filterMap
     *
     * @param key
     * @param value
     */
    private static void structureChainDefinitionFilterMap(String key , String value){
        List<String> list = filterMap.get(key);
        //如果 value 以逗号分隔 切割成数组 在存入 list中
        if(StringUtils.isNotBlank(value)){
            String[] array = value.split(UniversalConstant.SEPARATOR_SYMBOL_COMMA);
            List<String> asList = Arrays.asList(array);
            //将value切割以后存入list
            list.addAll(asList);
        }
    }

    public LinkedHashMap<String, String> buildFilterChainDefinitionMap() {
        log.info("{}","buildFilterChainDefinitionMap");
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        log.info("拦截路径资源:{}", JsonUtils.toString(filterMap));
        for (Map.Entry<String,List<String>> entry : filterMap.entrySet()) {
            String key = entry.getKey();
            List<String> valueList = entry.getValue();

            if(!CollectionUtils.isEmpty(valueList)){
                for (String url : valueList) {
                    map.put(url,key);
                }
            }

        }
        log.info("拦截路径资源:{}",JsonUtils.toString(map));
        return map;
    }

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        // 授权成功时链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        structureChainDefinitionFilterMap(INTERCEPTOR_AUTHORITY_ANON,shiroAnno);
        structureChainDefinitionFilterMap(INTERCEPTOR_AUTHORITY_LOGOUT,shiroLogout);
        structureChainDefinitionFilterMap(INTERCEPTOR_AUTHORITY_AUTHC,shiroAuthc);
        log.info("filterMap={}",filterMap);
        LinkedHashMap<String, String> filterChainDefinitionMap = buildFilterChainDefinitionMap();
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("{}","Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }
    
    /**
     * 自定义ModularRealmAuthenticator
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(redisCacheManager());
        List<Realm> realms = new ArrayList<>();
        realms.add(loginAuthorRealm());
        securityManager.setRealms(realms);
        securityManager.setRememberMeManager(rememberMeManager());
        securityManager.setSessionManager(sessionManager());
        log.info("安全管理器已经加载");
        return securityManager;
    }

	
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 配置rememberMeCookie
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }
    
    /**
     * shiro AOP 支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }
    
    /**
     * redis管理器
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        log.info("shiro redisManager加载");
        return redisManager;
    }
    
    /**
     * redis缓存管理器
     * @return
     */
    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        redisCacheManager.setPrincipalIdFieldName("userid");
        log.info("redis缓存管理器加载");
        return redisCacheManager;
    }
    
    @Bean
    public SimpleCookie cookie() {
        SimpleCookie cookie = new SimpleCookie("SID");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        return cookie;
    }
    
    /**
     * 使用uuid作为sessionID
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    /**
     * sessionDao
     * @return
     */
    @Bean
    public RedisSessionDAO sessionDAO() {
        RedisSessionDAO sessionDAO = new RedisSessionDAO();
        sessionDAO.setRedisManager(redisManager());
        sessionDAO.setSessionIdGenerator(sessionIdGenerator());
        return sessionDAO;
    }
    
    /**
     * session管理器
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(1000*100);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(cookie());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        log.info("sessionManager加载");
        return sessionManager;
    }
    
    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public LoginAuthorRealm loginAuthorRealm() {
        LoginAuthorRealm realm = new LoginAuthorRealm();
        realm.setCachingEnabled(false);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("MD5");
        matcher.setHashIterations(1);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

}
