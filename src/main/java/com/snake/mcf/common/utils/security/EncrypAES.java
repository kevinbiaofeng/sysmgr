package com.snake.mcf.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncrypAES {
	/** 默认密钥，实际项目中可配置注入或数据库读取 */
    private static String defaultKey = "helloworld";

    /** 加密工具 */
    private Cipher encryptCipher = null;

    /** 解密工具 */
    private Cipher decryptCipher = null;
    
    private static final char key[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};
    
    private static EncrypAES instance = new EncrypAES(defaultKey);

    public static EncrypAES getInstance() {
       return instance;
    }

    public EncrypAES(String keyvalue) {
       Security.addProvider(new com.sun.crypto.provider.SunJCE());
       if (keyvalue == null)
           keyvalue = defaultKey;
       byte[] arrBTmp = null;
       try {
           arrBTmp = keyvalue.getBytes("utf-8");
       } catch (UnsupportedEncodingException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       }
       // 创建一个空的length位字节数组（默认值为0）
       byte[] arrB = new byte[16];

       // 将原始字节数组转换为8位
       for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
           arrB[i] = arrBTmp[i];
       }
       // 生成密钥
       Key key = new javax.crypto.spec.SecretKeySpec(arrB, "AES");

       // 生成Cipher对象,指定其支持的DES算法
       try {
           encryptCipher = Cipher.getInstance("AES");
           encryptCipher.init(Cipher.ENCRYPT_MODE, key);

           decryptCipher = Cipher.getInstance("AES");
           decryptCipher.init(Cipher.DECRYPT_MODE, key);
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (NoSuchPaddingException e) {
           e.printStackTrace();
       } catch (InvalidKeyException e) {
           e.printStackTrace();
       }
    }

    /**
    * 对字符串加密
    * 
     * @param str
    * @return
    * @throws InvalidKeyException
    * @throws IllegalBlockSizeException
    * @throws BadPaddingException
    */
    public String encrytor(String str) throws InvalidKeyException,
           IllegalBlockSizeException, BadPaddingException {
       byte[] src = null;
       try {
           src = str.getBytes("utf-8");
       } catch (UnsupportedEncodingException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       return parseByte2HexStr(encryptCipher.doFinal(src));
    }

    /**
    * 对字符串解密
    * 
     * @param buff
    * @return
    * @throws InvalidKeyException
    * @throws IllegalBlockSizeException
    * @throws BadPaddingException
    */
    public String decryptor(String buff) throws InvalidKeyException,
           IllegalBlockSizeException, BadPaddingException {
       return new String(decryptCipher.doFinal(parseHexStr2Byte(buff)));
    }

    /**
    * @param args
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    * @throws BadPaddingException
    * @throws IllegalBlockSizeException
    * @throws InvalidKeyException
    */
    public static void main(String[] args) throws Exception {
       EncrypAES de1 = new EncrypAES("cgkey");
//       String msg =  "{\"shareholderAccount\":\"S001\",\"funkAccount\":\"C001\",\"secCode\":\"600001\",\"marketCode\":\"1\",\"opAmount\":\"150000\",\"typeKEY\":\"1\",\"opDate\":\"2017-06-29\",\"buyOrSale\":\"1\",\"entrustList\":[{\"groupID\":\"97049515164435152\",\"typeKEY\":\"1\",\"buyQuantity\":\"0\",\"saleQuantity\":\"50000\"},{\"groupID\":\"97049515164435152\",\"typeKEY\":\"2\",\"buyQuantity\":\"1100\",\"saleQuantity\":\"1100\"},{\"groupID\":\"96996825126407067\",\"typeKEY\":\"50\",\"buyQuantity\":\"1800\",\"saleQuantity\":\"1200\"},{\"groupID\":\"96996825126407067\",\"typeKEY\":\"5\",\"buyQuantity\":\"1500\",\"saleQuantity\":\"1300\"},{\"groupID\":\"96996825126407067\",\"typeKEY\":\"3\",\"buyQuantity\":\"1100\",\"saleQuantity\":\"1300\"}],\"sysName\":\"E_APP\",\"isTry\":\"0\",\"verification\":\"XX\"}";   
       String msg =  "士大夫";
       String demsg = null;

       System.out.println("明文是:" + msg);
       demsg = de1.encrytor(msg);
       System.out.println("加密后:" + demsg);
       System.out.println("解密后:" + de1.decryptor(demsg));
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
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

    public static String parseByte2HexStr(byte buf[]) {
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

}
