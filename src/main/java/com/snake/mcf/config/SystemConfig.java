package com.snake.mcf.config;

import com.snake.mcf.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @ClassName SystemConfig
 * @Author 大帅
 * @Date 2019/6/20 17:25
 */
@Configuration
@Slf4j
public class SystemConfig {
    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @Bean
    public IdWorker idWorker(){
        IdWorker idWorker = new IdWorker();
        return idWorker;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        log.info("maxFileSize:{}-------maxRequestSize:{}", maxFileSize , maxRequestSize );
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize(maxFileSize); //KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(maxRequestSize);
        return factory.createMultipartConfig();
    }

}
