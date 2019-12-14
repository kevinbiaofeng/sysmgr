package com.snake.mcf.common.utils.security;

import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DesUtils {
	private static final String IV = "70247bf7";
	private static final Charset SECURITY_CHARSET = Charset.forName("utf-8");

	/**
	 * 加密
	 * 
	 * @param srcStr
	 * @param charset
	 * @param sKey
	 * @return
	 */
	public static String encrypt(String srcStr, String sKey) {
		try {
			byte[] src = srcStr.getBytes(SECURITY_CHARSET);
			byte[] buf = encrypt(src, sKey);
			return parseByte2HexStr(buf);
		} catch (Exception e) {
			log.error("加密失败:str={}，key={}", srcStr, sKey);
			log.error(e.getMessage());
			e.printStackTrace();
			return srcStr;
		}
	}

	/**
	 * 解密
	 *
	 * @param hexStr
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String hexStr, String sKey) {
		try {
			byte[] src = parseHexStr2Byte(hexStr);
			byte[] buf = decrypt(src, sKey);
			return new String(buf, SECURITY_CHARSET);
		} catch (Exception e) {
			log.error("解密失败:str={}，key={}", hexStr, sKey);
			log.error(e.getMessage());
			e.printStackTrace();
			return hexStr;
		}
		
	}

	/**
	 * 加密
	 * 
	 * @param data
	 * @param sKey
	 * @return
	 */
	private static byte[] encrypt(byte[] data, String sKey) throws Exception {
		byte[] key = sKey.getBytes();
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		DESKeySpec desKey = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] src, String sKey) throws Exception  {
		byte[] key = sKey.getBytes();
		IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
		DESKeySpec desKey = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
		return cipher.doFinal(src);
	}

	/**
	 * 将二进制转换成16进制
	 *
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) throws Exception {
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
	 * 将16进制转换为二进制
	 *
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) throws Exception{
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	public static void main(String[] args) {
		String a = DesUtils.encrypt("中国电放费dd", "d6df4c04!B314aa186");
		System.out.println(a);
		
		try {
			System.out.println(DesUtils.decrypt(a, "d6df4c04!B314aa186"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
