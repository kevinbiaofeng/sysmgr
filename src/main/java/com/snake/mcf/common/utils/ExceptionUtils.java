package com.snake.mcf.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理
 * 
 * @ClassName:  ExceptionUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:05:39   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class ExceptionUtils {

    /**
     * 获取堆栈信息
     *
     * @param cause
     * @return
     */
    public static final String getStackTrace(Throwable cause) {
        StringWriter writer = new StringWriter();
        cause.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }
    
}

