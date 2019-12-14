package com.snake.mcf.common.utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 * 
 * @ClassName:  IOUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:43:00   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class IOUtils extends org.apache.commons.io.IOUtils {

    public static final String readString(String path, String encoding) throws IOException {
        byte[] bytes = readBytes(path);
        return new String(bytes, encoding);
    }

    /**
     * 读取环境变量中指定文件的内容
     *
     * @param path
     * @return
     */
    public static final byte[] readBytes(String path) throws IOException {
        return toByteArray(getInputStream(path));
    }

    public static final InputStream getInputStream(String path) {

        InputStream inputStream = null;

        inputStream = IOUtils.class.getResourceAsStream(path);
        if (inputStream == null) {
            inputStream = IOUtils.class.getClassLoader().getResourceAsStream(path);
            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
                if (inputStream == null) {
                    inputStream = Runtime.getRuntime().getClass().getClassLoader().getResourceAsStream(path);
                }
            }
        }

        if (inputStream == null) {
            throw new RuntimeException("file " + path + "NOT found.");
        }

        return inputStream;
    }

}
