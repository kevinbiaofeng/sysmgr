package com.snake.mcf.common.utils.security.aes;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.snake.mcf.common.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AESCBCUtils {
	private static final byte[] key = {0x3A, 0x6C, 0x25, 0x26,
			  0x2F, 0x1F, 0x30, 0x34,
			  0x19, 0x1B, 0x1D, 0x3D,
			  0x05, 0x4D, 0x2C, 0x2C};
	private static final String encodingFormat = "GBK";
	private static final String encodingFormatUTF8 = "UTF-8";
	
	/**
	 * 
	 * @param str
	 * @param ivParameter  向量
	 * @param utf8Bool   true: utf-8  false:gbk
	 * @return
	 */
    public static String encrypt(String str, String ivParameter, boolean utf8Bool) {
    	try {
    		if(StringUtils.isNotEmpty(str)) {
    			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    	        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    	        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
    	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    	        byte[] encrypted = cipher.doFinal(str.getBytes(utf8Bool ? encodingFormatUTF8 : encodingFormat));
    	        return parseByte2HexStr(encrypted);
    		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    		log.error("加密失败：{}" , str);
    		log.error(ex.getMessage());
    	}
    	return str;
    }
    
    public static String encrypt(String str, String ivParameter) {
    	return AESCBCUtils.encrypt(str, ivParameter, false);
    }
    
    /**
	 * 
	 * @param str
	 * @param ivParameter  向量
	 * @param utf8Bool   true: utf-8  false:gbk
	 * @return
	 */
    public static String decrypt(String str, String ivParameter, boolean utf8Bool){
        try {
        	if(StringUtils.isNotEmpty(str)) {
	            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
	            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
	            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	            byte[] encrypted1 = parseHexStr2Byte(str);//先用base64解密
	            byte[] original = cipher.doFinal(encrypted1);
	            String originalString = new String(original, utf8Bool ? encodingFormatUTF8 : encodingFormat);
	            return originalString;
        	}
        } catch (Exception ex) {
//            ex.printStackTrace();
    		log.error("解密失败：{}" , str);
    		log.error(ex.getMessage());
        }
        return str;
    }
    
    public static String decrypt(String str, String ivParameter) {
    	return AESCBCUtils.decrypt(str, ivParameter, false);
    }

    /**
     * 将二进制转换成十六进制
     * 
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**
     * 将十六进制转换为二进制
     * 
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];
            for (int i = 0; i < hexStr.length() / 2; i++) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte) (high * 16 + low);
            }
            return result;
        }
    }
    
    @SuppressWarnings("unused")
	private static byte[] getBytes(char[] chars) {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }
    
    
    /**
     * 将16进制转为字符串
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
	private static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
        	n = str.indexOf(hexs[2 * i]) * 16;
        	n += str.indexOf(hexs[2 * i + 1]);
        	bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
    
    public static void main(String[] args) {
    	try {
			String str = AESCBCUtils.encrypt("{\"customId\": \"3064\", \"userId\": \"3062\", \"faceUrl\": \"http://cg.tg9qipai.com/Upload/Head/2.png\"}", "ARONHAVELOTGRILS");
			System.out.println("加密：" + str);
			str = AESCBCUtils.decrypt(str, "ARONHAVELOTGRILS");
			System.out.println("解密：" + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
