package com.snake.mcf.common.utils.security;

import com.snake.mcf.common.constant.PayplatformConstant;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加密解密
 * 
 * @ClassName:  AESUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:06:13   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class AESUtils {
	
	private static final String KEY_ALGORITHM_AES = "AES";
	
	private AESUtils() {
	    throw new IllegalStateException("AESUtils class");
	}
	
	/**
	 * AES加密
	 * 
	 * @param content 待加密源数据
	 * @param encryptKey 加密KEY
	 * @return 加密后二进制数据
	 * @throws Exception
	 */
	public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));
        
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), KEY_ALGORITHM_AES));
        
        return cipher.doFinal(content.getBytes(PayplatformConstant.ENCODE_UTF_8));
    }

	/**
	 * AES加密
	 * 
	 * @param content 待加密源数据
	 * @param encryptKey 加密KEY
	 * @return 加密后数据
	 * @throws Exception
	 */
	public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return Base64Utils.encode(aesEncryptToBytes(content, encryptKey));
    }
	
	/**
	 * AES解密
	 * 
	 * @param encryptBytes 已加密的二进制数据
	 * @param decryptKey 解密KEY
	 * @return 解密后明文数据
	 * @throws Exception
	 */
	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));
        
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), KEY_ALGORITHM_AES));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        
        return new String(decryptBytes);
    }
	
	/**
	 * AES解密
	 * @param encryptStr 已加密数据
	 * @param decryptKey 解密KEY
	 * @return 解密后的明文数据
	 * @throws Exception
	 */
	public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(Base64Utils.decode(encryptStr), decryptKey);
	}
}
