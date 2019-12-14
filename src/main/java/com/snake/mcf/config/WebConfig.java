package com.snake.mcf.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @ClassName WebConfig
 * @Author 大帅
 * @Date 2019/6/20 9:41
 */
//@EnableWebMvc 和 WebMvcConfigurationSupport 二者只能选一个，如果同时实现，则会失效
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
	
	
	
    /**
     * springmvc视图解析
     * @Title: viewResolver
     * @Date 2018年8月28日 下午4:46:07
     * @author OnlyMate
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        // viewResolver.setViewClass(JstlView.class); // 这个属性通常并不需要手动配置，高版本的Spring会自动检测
        return viewResolver;
    }

    /**
     * SpringBoot设置首页
     */
  /*  @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //WebMvcConfigurer.super.addViewControllers(registry);
        this.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }*/

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

       // super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());

        //1.需要定义一个convert转换消息的对象;
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        List<MediaType> supportedMediaTypes = new ArrayList<>();

        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);

        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);

        //5.将convert添加到converters当中.
        converters.add(mappingJackson2HttpMessageConverter);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        //localeResolver.setCookieName("lang");
        //设置默认区域
        localeResolver.setDefaultLocale(new Locale("en", "US"));
        //设置cookie有效期.
        localeResolver.setCookieMaxAge(-1);

        return localeResolver;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/pub/**").addResourceLocations("classpath:/pub/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        /*registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/mylogin").setViewName("login");
        registry.addViewController("/redirect").setViewName("redirect");
        registry.addViewController("/welcome").setViewName("welcome");
        registry.addViewController("/index").setViewName("index");*/

        /**
         * 权限管理
         */
        //用户管理
        registry.addViewController("/sys/user/index").setViewName("sys/user/index");
        registry.addViewController("/sys/user/toUserAddPage").setViewName("sys/user/user_add");
        registry.addViewController("/sys/user/toUserEditPage").setViewName("sys/user/user_edit");
        registry.addViewController("/sys/user/toUserRolePage").setViewName("sys/user/user_role");
        //商户域名管理
        registry.addViewController("/sys/user/merchant/toDomainPage").setViewName("sys/user/domain/domain_list");
        registry.addViewController("/sys/user/merchant/toDomainAddPage").setViewName("sys/user/domain/domain_add");
        registry.addViewController("/sys/user/merchant/toDomainEditPage").setViewName("sys/user/domain/domain_edit");
        
        //角色管理
        registry.addViewController("/sys/role/index").setViewName("sys/role/index");
        registry.addViewController("/sys/role/toRoleAddPage").setViewName("sys/role/role_add");
        registry.addViewController("/sys/role/toRoleEditPage").setViewName("sys/role/role_edit");
        registry.addViewController("/sys/role/toRoleResourcePage").setViewName("sys/role/role_resource");

        //资源管理
        registry.addViewController("/sys/resource/index").setViewName("sys/resource/index");
        registry.addViewController("/sys/resource/toResourceAddPage").setViewName("sys/resource/resource_add");
        registry.addViewController("/sys/resource/toResourceEditPage").setViewName("sys/resource/resource_edit");

        /**
         * 商户系统
         */
        //商户统计
        registry.addViewController("/merchant/stats/index").setViewName("merchant/stats/index");
        

        /**
         * 用户系统
         */
        //账户管理
        registry.addViewController("/account/user/index").setViewName("account/user/index");
        registry.addViewController("/account/user/toAccountGiveNumPage").setViewName("account/user/account_givenum");
        registry.addViewController("/account/user/toAccountGiveGoldPage").setViewName("account/user/account_givegold");
        /*registry.addViewController("/account/user/toAccountEditPage").setViewName("account/user/account_edit");*/
        /*registry.addViewController("/account/user/toAccountLinePage").setViewName("account/user/account_line");*/

        //在线玩家
        registry.addViewController("/account/online/index").setViewName("account/online/index");

        //金币赠送记录
        registry.addViewController("/account/givegold/index").setViewName("account/givegold/index");

        //任务管理
        registry.addViewController("/account/task/index").setViewName("account/task/index");
        registry.addViewController("/account/task/toTaskAddPage").setViewName("account/task/task_add");
        registry.addViewController("/account/task/toTaskEditPage").setViewName("account/task/task_edit");

        //注册赠送
        registry.addViewController("/account/reggive/index").setViewName("account/reggive/index");
        registry.addViewController("/account/reggive/toReggiveAddPage").setViewName("account/reggive/reggive_add");
        registry.addViewController("/account/reggive/toReggiveEditPage").setViewName("account/reggive/reggive_edit");

        /**
         * 充值系统
         */
        //充值设置
        registry.addViewController("/invest/setup/index").setViewName("invest/setup/index");
        registry.addViewController("/invest/setup/toSetupAddPage").setViewName("invest/setup/setup_add");
        registry.addViewController("/invest/setup/toSetupEditPage").setViewName("invest/setup/setup_edit");

        //兑换管理
        registry.addViewController("/invest/exchg/index").setViewName("invest/exchg/index");
        registry.addViewController("/invest/exchg/toExchgAddPage").setViewName("invest/exchg/exchg_add");
        registry.addViewController("/invest/exchg/toExchgEditPage").setViewName("invest/exchg/exchg_edit");

        //充值记录
        registry.addViewController("/invest/record/index").setViewName("invest/record/index");

        //人工存提
        registry.addViewController("/invest/depositWithdrawals/index").setViewName("invest/depositWithdrawals/index");
        registry.addViewController("/invest/depositWithdrawals/toDepositWithdrawalGold").setViewName("invest/depositWithdrawals/depositWithdrawalGoldCoin");
        registry.addViewController("/invest/depositWithdrawals/toUserList").setViewName("invest/depositWithdrawals/userList");

        //在线充值配置
        registry.addViewController("/invest/online/index").setViewName("invest/online/index");
        registry.addViewController("/invest/online/toOnlineAddPage").setViewName("invest/online/online_add");
        registry.addViewController("/invest/online/toOnlineEditPage").setViewName("invest/online/online_edit");

        /**
         * 维护系统
         */
        //游戏模块
        registry.addViewController("/uphold/gamemd/index").setViewName("uphold/gamemd/index");
        registry.addViewController("/uphold/gamemd/toGamemdAddPage").setViewName("uphold/gamemd/gamemd_add");
        registry.addViewController("/uphold/gamemd/toGamemdEditPage").setViewName("uphold/gamemd/gamemd_edit");

        //游戏列表
        registry.addViewController("/uphold/gamels/index").setViewName("uphold/gamels/index");
        registry.addViewController("/uphold/gamels/toGamelsAddPage").setViewName("uphold/gamels/gamels_add");
        registry.addViewController("/uphold/gamels/toGamelsEditPage").setViewName("uphold/gamels/gamels_edit");

        //系统消息
        registry.addViewController("/uphold/sysmsg/index").setViewName("uphold/sysmsg/index");
        registry.addViewController("/uphold/sysmsg/toSysmsgAddPage").setViewName("uphold/sysmsg/sysmsg_add");
        registry.addViewController("/uphold/sysmsg/toSysmsgEditPage").setViewName("uphold/sysmsg/sysmsg_edit");

        //系统设置
        //registry.addViewController("/uphold/sysset/index").setViewName("uphold/sysset/index");

        //排名管理
        registry.addViewController("/uphold/rank/index").setViewName("uphold/rank/index");
        registry.addViewController("/uphold/rank/toRankAddPage").setViewName("uphold/rank/rank_add");
        registry.addViewController("/uphold/rank/toRankEditPage").setViewName("uphold/rank/rank_edit");

        //道具管理
        registry.addViewController("/uphold/dota/index").setViewName("uphold/dota/index");
        registry.addViewController("/uphold/dota/toDotaEditPage").setViewName("uphold/dota/dota_edit");
        registry.addViewController("/uphold/dota/toDotaAddPage").setViewName("uphold/dota/dota_add");

        //签到管理
        registry.addViewController("/uphold/sign/index").setViewName("uphold/sign/index");
        // 签到礼包配置
        registry.addViewController("/uphold/sign/toSign1AddPage").setViewName("uphold/sign/sign1_add");
        registry.addViewController("/uphold/sign/toSign1EditPage").setViewName("uphold/sign/sign1_edit");
        //签到物品配置
        registry.addViewController("/uphold/sign/toSign2AddPage").setViewName("uphold/sign/sign2_add");
        registry.addViewController("/uphold/sign/toSign2EditPage").setViewName("uphold/sign/sign2_edit");
        //签到配置
        registry.addViewController("/uphold/sign/toSign3AddPage").setViewName("uphold/sign/sign3_add");
        registry.addViewController("/uphold/sign/toSign3EditPage").setViewName("uphold/sign/sign3_edit");
        //签到记录
        registry.addViewController("/uphold/sign/toSign3RecordPage").setViewName("uphold/sign/sign3_record");

        //每日分享管理
        registry.addViewController("/uphold/share/index").setViewName("uphold/share/index");
        registry.addViewController("/uphold/share/toShareAddPage").setViewName("uphold/share/share_add");
        registry.addViewController("/uphold/share/toShareEditPage").setViewName("uphold/share/share_edit");

        //推广类型设置
        registry.addViewController("/uphold/spread/index").setViewName("uphold/spread/index");
        registry.addViewController("/uphold/spread/toSpreadAddPage").setViewName("uphold/spread/spread_add");
        registry.addViewController("/uphold/spread/toSpreadEditPage").setViewName("uphold/spread/spread_edit");

        /**
         * 网站系统
         */
        //站点配置
        registry.addViewController("/website/stand/index").setViewName("website/stand/index");
        registry.addViewController("/website/stand/toStandAddPage").setViewName("website/stand/stand_add");
        registry.addViewController("/website/stand/toStandEditPage").setViewName("website/stand/stand_edit");

        //游戏规则
        registry.addViewController("/website/rule/index").setViewName("website/rule/index");
        registry.addViewController("/website/rule/toRuleAddPage").setViewName("website/rule/rule_add");
        registry.addViewController("/website/rule/toRuleEditPage").setViewName("website/rule/rule_edit");

        //广告管理
        registry.addViewController("/website/advert/index").setViewName("website/advert/index");
        registry.addViewController("/website/advert/toAdvertAddPage").setViewName("website/advert/advert_add");
        registry.addViewController("/website/advert/toAdvertEditPage").setViewName("website/advert/advert_edit");

        //新闻公告
        registry.addViewController("/website/news/index").setViewName("website/news/index");
        registry.addViewController("/website/news/toNewsAddPage").setViewName("website/news/news_add");
        registry.addViewController("/website/news/toNewsEditPage").setViewName("website/news/news_edit");

        //常见问题
        registry.addViewController("/website/question/index").setViewName("website/question/index");
        registry.addViewController("/website/question/toQuestionAddPage").setViewName("website/question/question_add");
        registry.addViewController("/website/question/toQuestionEditPage").setViewName("website/question/question_edit");

        //敏感词管理
        registry.addViewController("/website/confineContent/index").setViewName("website/confineContent/index");
        registry.addViewController("/website/confineContent/toConfineContentAddPage").setViewName("website/confineContent/confineContent_add");
        registry.addViewController("/website/confineContent/toConfineContentEditPage").setViewName("website/confineContent/confineContent_edit");

        /**
         * 金币系统
         */
        //银行记录
        registry.addViewController("/goldmgr/bank/index").setViewName("goldmgr/bank/index");

        //进出记录
        registry.addViewController("/goldmgr/inout/index").setViewName("goldmgr/inout/index");

        //游戏记录
        registry.addViewController("/goldmgr/gamerd/index").setViewName("goldmgr/gamerd/index");

        //房间模式
        registry.addViewController("/goldmgr/roommd/index").setViewName("goldmgr/roommd/index");


        /**
         * 提现系统
         */
        //卡号绑定管理
        registry.addViewController("/withdraw/bindcard/index").setViewName("withdraw/bindcard/index");
        registry.addViewController("/withdraw/bindcard/toBindcardAddPage").setViewName("withdraw/bindcard/bindcard_add");
        registry.addViewController("/withdraw/bindcard/toBindcardEditPage").setViewName("withdraw/bindcard/bindcard_edit");

        //提现审核管理
        registry.addViewController("/withdraw/record/index").setViewName("withdraw/record/index");


        /**
         *  亲友圈 系统
         */
        registry.addViewController("/ffar/manage/index").setViewName("ffar/manage/index");

        /**
         * 后台系统
         */
        //安全日志
        registry.addViewController("/back/log/index").setViewName("back/log/index");
        //银行卡变更记录
        registry.addViewController("/back/bindBankCardsChangeRecord/index").setViewName("back/bindBankCardsChangeRecord/index");





    }

    // @Autowired
    //private LoginHandlerInterceptor loginHandlerInterceptor;

    /*@Override
    protected void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(loginHandlerInterceptor);
        // 排除配置
        addInterceptor.excludePathPatterns("/error");
        //排除静态资源
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/pub/**");
        //addInterceptor.excludePathPatterns("/pages/login");
        //addInterceptor.excludePathPatterns("/login/check");
        // 拦截配置
        //addInterceptor.addPathPatterns("/**");
        //super.addInterceptors(registry);

    }*/

    // 就是这个
    @Bean
    public HttpPutFormContentFilter httpPutFormContentFilter() {
        return new HttpPutFormContentFilter();
    }




}
