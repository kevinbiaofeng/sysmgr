package com.snake.mcf.common.utils.security;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Base64加密解密
 * 
 * @ClassName:  Base64Utils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:06:29   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class Base64Utils {
	
	//文件读取缓冲区大小
	private static final int CACHE_SIZE = 1024;
	
	/**
	 * BASE64字符串解码为二进制数据
	 * @param base64 base64字符串
	 * @return 二进制数据
	 * @throws Exception
	 */
	public static byte[] decode(String base64) throws Exception {
		return Base64.decodeBase64(base64);
	}
	
	/**
	 * 二进制数据编码为BASE64字符串
	 * @param bytes 二进制数据
	 * @return Base64字符串
	 * @throws Exception
	 */
	public static String encode(byte[] bytes) throws Exception {
		return Base64.encodeBase64String(bytes);
	}
	
	/**
	 * 将文件编码为BASE64字符串
	 * @param filePath 文件全路径
	 * @return Base64字符串
	 * @throws Exception
	 */
	public static String encodeFile(String filePath) throws Exception {
		byte[] bytes = fileToByte(filePath);
        return encode(bytes);
	}
	
	/**
	 * BASE64字符串转回文件
	 * @param filePath 文件全路经
	 * @param base64 Base字符串
	 * @throws Exception
	 */
	public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }
	
	/**
	 * 文件转换为二进制数组
	 * @param filePath 文件全路经
	 * @return 二进制数据
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
        	FileInputStream in = null;
        	ByteArrayOutputStream out = null;
        	try {
	            in = new FileInputStream(file);
	            out = new ByteArrayOutputStream(2048);
	            byte[] cache = new byte[CACHE_SIZE];
	            int nRead = 0;
	            while ((nRead = in.read(cache)) != -1) {
	                out.write(cache, 0, nRead);
	                out.flush();
	            }
	            data = out.toByteArray();
        	} finally {
        		if (out != null) { out.close(); }
        		if (in != null) { in.close(); }
        	}
            
         }
        return data;
    }
	
	/**
	 * 二进制数据写文件
	 * @param bytes 二进制数据
	 * @param filePath 文件全路经
	 * @throws Exception
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
		InputStream in = null;
		OutputStream out = null;
		try {
	        in = new ByteArrayInputStream(bytes);   
	        File destFile = new File(filePath);
	        if (!destFile.getParentFile().exists()) {
	            destFile.getParentFile().mkdirs();
	        }
	        destFile.createNewFile();
	        
	        out = new FileOutputStream(destFile);
	        byte[] cache = new byte[CACHE_SIZE];
	        int nRead = 0;
	        while ((nRead = in.read(cache)) != -1) {   
	            out.write(cache, 0, nRead);
	            out.flush();
	        }
		} finally {
			if (out != null) {out.close();}
			if (in != null) { in.close(); }
		}
    }
}
