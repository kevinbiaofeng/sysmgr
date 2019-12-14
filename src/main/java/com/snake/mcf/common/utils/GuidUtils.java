package com.snake.mcf.common.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Guid生成策略
 * 
 * @ClassName:  GuidUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:41:56   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class GuidUtils {

    /**
     * 去 '-' 转大写
     *
     * @return
     */
    public static final String generateBlank2UpperCase() {
        return randomUUID().replaceAll("-", "").toUpperCase();
    }

    /**
     * 包含 '-' 转大写
     * @return
     */
    public static final String generate2UpperCase() {
        return randomUUID().toUpperCase();
    }

    /**
     * 去 '-' 转小写
     * @return
     */
    public static final String generateBlank2LowerCase() {
        return randomUUID().replaceAll("-", "").toLowerCase();
    }

    /**
     * 包含 '-' 转小写
     *
     * @return
     */
    public static final String generate2LowerCase() {
        return randomUUID().toLowerCase();
    }
    
    /**
     * 获取uuid
     * @return
     */
    public static final String randomUUID() {
    	return UUID.randomUUID().toString();
    }

    public static Integer generate2Random(Integer n){
        int nextInt = new Random().nextInt(n);
        return nextInt;
    }

    public static String generate2Date(){
        long nowDate = System.currentTimeMillis();
        String sid = Integer.toHexString((int)nowDate);
        return sid;
    }









}
