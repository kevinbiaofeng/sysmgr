package com.snake.mcf.common.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * HASH工具类
 * 
 * @ClassName:  HashUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:42:16   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class HashUtils {
	
    private static final int BUFFER = 1024 * 10;

    public static final String getHash(String value, String hashType) {

        MessageDigest md;
        String result;
        try {
            md = MessageDigest.getInstance(hashType);
            result = binToHex(md.digest(value.getBytes("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("不支持的[%s]算法。", hashType), e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 获取MD5码
     *
     * @param fileName
     * @return
     */
    @Deprecated
    public static final String getMD5(String fileName) {
        return getHashValue(fileName, "MD5");
    }

    @Deprecated
    public static final String getSHA1(String fileName) {
        return getHashValue(fileName, "SHA1");
    }

    public static final String getMD5Val(String message) {
        return getHash(message, "MD5");
    }

    public static final String getSHA1Val(String message) {
        return getHash(message, "SHA1");
    }

    public static final String getSHA256Val(String message) {
        return getHash(message, "SHA-256");
    }

    public static final String getSHA512Val(String message) {
        return getHash(message, "SHA-512");
    }

    /**
     * @param input    输入流
     * @param hashType hash算法
     * @return
     */
    public static String getHashValue(InputStream input, String hashType) {
        byte[] buffer = new byte[BUFFER];
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(hashType);
            if (input != null) {
                int iCnt;
                while ((iCnt = input.read(buffer)) > 0) {
                    md.update(buffer, 0, iCnt);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(String.format("不支持的[%s]算法。", hashType), e);
        }

        return binToHex(md.digest());
    }

    /**
     * 获取Hash值
     *
     * @param fileName 文件名
     * @param hashType hash算法名
     * @return
     */
    @Deprecated
    public static String getHashValue(String fileName, String hashType) {
        InputStream fis = null;
        try {
            fis = new FileInputStream(fileName);
            return getHashValue(fis, hashType);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    private static String binToHex(byte[] digest) {
        return StringUtils.binToHex(digest);
    }

}
