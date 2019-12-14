package com.snake.mcf.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.utils.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * md5加密
 * 
 * @ClassName:  MD5Utils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:06:46   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class MD5Utils {
	
	private static final String KEY_ALGORITHM_MD5 = "MD5";
	
	private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	private MD5Utils() {
	    throw new IllegalStateException("MD5Utils class");
	}
	
	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 转换字节数组为高位字符串
	 * @param b 字节数组
	 * @return 高位字符串
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}
	
	/**
	 * MD5 摘要计算(byte[]).
	 * 
	 * @param src byte[]
	 * @throws Exception
	 * @return 摘要数据
	 */
	public static String md5Digest(byte[] src) {
		try {
			// MD5 is 32 bit message digest
			MessageDigest alg = MessageDigest.getInstance(KEY_ALGORITHM_MD5);
			return byteArrayToHexString(alg.digest(src));
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5摘要数据:e={}", e);
			ExceptionUtils.getStackTrace(e);
			return null;
		} 
		
	}
	
	/**
	 * MD5 摘要计算(String).
	 * 
	 * @author: hengoking 
	 * @param src
	 * @return
	 */
	public static String md5Digest(String src) {
		return md5Digest(src.getBytes());
	}
	
	/**
	 * 摘要验证比对
	 * 
	 * @param oidStr 源数据
	 * @param signedStr 摘要数据
	 * @return 是否一致
	 */
	public static boolean checkSign(String oidStr, String signedStr) {
		try {
			return StringUtils.equals(signedStr, md5Digest(oidStr.getBytes(PayplatformConstant.ENCODE_UTF_8)));
		} catch (UnsupportedEncodingException e) {
			log.error("MD5摘要验证比对:e={}", e);
			ExceptionUtils.getStackTrace(e);
			return false;
		}
	}
	
}
