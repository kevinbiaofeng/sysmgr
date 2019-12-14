package com.snake.mcf.common.utils.security.rsa;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import sun.misc.BASE64Encoder;

public class RSAUtils {
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
	
	/**
     * RSA最大加密明文大小
     */
//    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
	
	public static void main(String[] args) throws Exception {
		//生成公钥和私钥
//		genKeyPair();
		//加密字符串
//		String message = "cgRequestKey";
		keyMap.put(0, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD1mggF0tHkS2DRqK8WopHnTHLe\r\n" + 
				"WPXNT0M6oecf4rpTK1TMC1v22lzyjHIV0828tMki8w+FGSkwi10Rb/7NsxJf/B4Y\r\n" + 
				"rf9WwlBOHfe4rYQjEQDraldzXmjISg50sxMNB5QBxCi4vGfgkY+xrZA71+wo1l0m\r\n" + 
				"+CU+F4nkL3bZXy7KHwIDAQAB");
		keyMap.put(1, "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAPWaCAXS0eRLYNGo\r\n" + 
				"rxaikedMct5Y9c1PQzqh5x/iulMrVMwLW/baXPKMchXTzby0ySLzD4UZKTCLXRFv\r\n" + 
				"/s2zEl/8Hhit/1bCUE4d97ithCMRAOtqV3NeaMhKDnSzEw0HlAHEKLi8Z+CRj7Gt\r\n" + 
				"kDvX7CjWXSb4JT4XieQvdtlfLsofAgMBAAECgYEAgTb44Zd5NlMyLRxCgfYmLTrb\r\n" + 
				"dH/5UntRM5fasbvnq7owcunHCKj6HeImY7V7mLzE1c5ra+tA8HliDbqOpEqCb1Wf\r\n" + 
				"iTKk1Xr4E6fGBDyPYasph4CAMCM0qimp1NAG192zSJ31+cEfT4Lzkf4G1F34xGxF\r\n" + 
				"fJyzMzYnm9mmqc8v1YECQQD/w5Yd8QCMBdOpr7295lOn1w/gRztKz4Zvuts/EQ0Y\r\n" + 
				"k/nt682CFOsCP7ufy4f2QMkY3G7zoluX49+Z1j8ch2glAkEA9dQLZYrkDZAZ/rj7\r\n" + 
				"gsZU+abtZxS6fJOTHrjSo3BM4MfpxdrbYCbh0sOhGsUo+eyxUGB1tvDE9+fu9Gfp\r\n" + 
				"kiyD8wJACKLtHcO2lgPYWuGBUblGV+OOOK+PE/6f9oPjxWRpAcx1YxBUVzEnomLO\r\n" + 
				"jh3MUIdtgvZmlLmFyEI8mbA/X1I/NQJBANuqSSzJeZNtCu2E2qo3E8OqGEWeTi33\r\n" + 
				"qOUw/ngxZ2ENAqZcNN+snuo5CYLkXwNFMATEzStV5own4mzfVeXahWUCQGsz6HX8\r\n" + 
				"ZwXUim3vV/oJ2QK1CpIuOCVnN4pL59/HTDOMs4zYX1TRzldMWHEjxFXe6vFPqPi9\r\n" + 
				"7npBJ0qxWnqcdPE=");
//		System.out.println("随机生成的公钥为:" + keyMap.get(0));
//		System.out.println("随机生成的私钥为:" + keyMap.get(1));
//		String param = "{\"platformType\":\"0\"}";
//		String messageEn = encrypt(param,keyMap.get(0));
//		System.out.println("加密后的字符串为:" + messageEn);
//		String messageDe = decrypt(messageEn,keyMap.get(1));
//		System.out.println("还原后的字符串为:" + messageDe);
		
//		String source = "{apiVersion=20191002, valid=true, sign=fe9fad82dc89521be86dd73c69532594, dateTime=1573027388065, systemConfig={isOpenMall=0, isPayBindSpread=0, bindSpreadPresent=0, rankingListType=7, payChannel=3, diamondBuyPropCount=0, realNameAuthentPresent=600, effectiveFriendGame=0, goldBuyPropCount=0, enjoinInsure=0, transferStauts=1, mobileBattleRecordMask=7, isOpenGameSignIn=0, isOpenRedemptionCenter=0, iosnotStorePaySwitch=0}, groupConfig={maxMemberCount=10, maxCreateGroupCount=5, createGroupTakeIngot=1, createGroupDeductIngot=100, maxJoinGroupCount=10, groupPayType=3, groupPayTypeChange=1, groupRoomType=1}, customerService={phone=400-000-7043, weiXin=4000007043, qq=4000007043, link=http://cg.tg9qipai.com/Upload/site/qrcustomer.png}, systemNotice=[{noticeID=7, noticeTitle=胖虎测试, moblieContent=胖虎测试, publisherTime=1570281388890, platformType=1}, {noticeID=6, noticeTitle=111, moblieContent=1111, publisherTime=1570281222970, platformType=1}, {noticeID=1, noticeTitle=欢迎光临CG棋牌平台, moblieContent=欢迎光临最新棋牌平台——CG Game,这里有最精彩、最刺激的棋牌游戏!, publisherTime=1569494728513, platformType=1}, {noticeID=8, noticeTitle=test001, moblieContent=2019年元旦节已经过去啦，各位应该很开心吧~\r\n" + 
//        		"而我们的活动也完美结束。现在给各位玩家开奖~ , publisherTime=1570771322233, platformType=1}, {noticeID=2, noticeTitle=服务器迁移公告, moblieContent=服务器近期迁移，敬请留意, publisherTime=1569820189683, platformType=2}, {noticeID=5, noticeTitle=second222, moblieContent=second222, publisherTime=1569822678903, platformType=1}, {noticeID=3, noticeTitle=222, moblieContent=22222, publisherTime=1569820223330, platformType=1}, {noticeID=4, noticeTitle=333, moblieContent=33333, publisherTime=1569822476197, platformType=1}], adsList=[{title=大厅广告, resourceURL=http://cg.tg9qipai.com/Upload/advert/20191101150301661.png, linkURL=ad_to_createroom_action, sortID=0, platformType=1, fileMD5=null}, {title=大厅广告, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.jpg, linkURL=ad_to_video_action, sortID=1, platformType=1, fileMD5=null}, {title=大厅广告, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.jpg, linkURL=ad_to_identify_action, sortID=2, platformType=1, fileMD5=null}], adsAlertList=[], activityList=[{title=推广分享, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/bs.png, linkURL=2, sortID=0, platformType=3, fileMD5=null}, {title=内部看的，跳转签到系统, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/qd1.png, linkURL=, sortID=1, platformType=2, fileMD5=5B15FD4D9DB9311C1DD97ECFD2843232}, {title=H5活动广告, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.jpg, linkURL=, sortID=1, platformType=4, fileMD5=null}, {title=H5活动广告, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.jpg, linkURL=, sortID=2, platformType=4, fileMD5=null}, {title=签到, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/bs.png, linkURL=1, sortID=2, platformType=3, fileMD5=null}, {title=推广分享, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/fx1.png, linkURL=2, sortID=2, platformType=2, fileMD5=110748A484CEC9F2F097331A0DA5F396}, {title=内部看的，跳转比赛系统, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/bs1.png, linkURL=3, sortID=3, platformType=2, fileMD5=39333088C8BEACE0641ED2DD91F103B7}, {title=H5活动广告, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.jpg, linkURL=, sortID=3, platformType=4, fileMD5=null}, {title=优惠多多 豪礼大放送, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/39.png, linkURL=4, sortID=4, platformType=3, fileMD5=null}, {title=内部看的，跳转商城系统, resourceURL=http://cg.tg9qipai.com/Upload/Initialize/sc1.png, linkURL=4, sortID=4, platformType=2, fileMD5=5B4E591E71AA164393C968AC831A46F4}], imageServerHost=http://cg.tg9qipai.com/Upload, mobileConfig={configKey=MobilePlatformVersion, configID=, configName=, configString=, sortID=0, field1=http://dl.tg9qipai.com:8001/Download/Phone, field2=21, field3=157, field4=, field5=http://dl.tg9qipai.com:8002/Download/Phone818, field6=, field7=http://dl.tg9qipai.com/Download/Phone818/, field8=1.0, field9=0.8, field10=, field11=, field12=, field13=}}";
//		System.out.println("=====================");
		String source = "{\"userId\" : \"13037\"}";
		
		String messageEn = encrypt128(source, keyMap.get(0));
		System.out.println("加密后的字符串为111111:" + messageEn);
		
//		String st = "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";
//		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		String messageDe1 = decrypt128(messageEn, keyMap.get(1));
		System.out.println("还原后的字符串为222222:" + messageDe1);
	}

	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static void genKeyPair() throws NoSuchAlgorithmException {  
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
		// 初始化密钥对生成器，密钥大小为96-1024位  
		keyPairGen.initialize(1024,new SecureRandom());  
		// 生成一个密钥对，保存在keyPair中  
		KeyPair keyPair = keyPairGen.generateKeyPair();  
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥  
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥  
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));  
		// 得到私钥字符串  
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
		// 将公钥和私钥保存到Map
		keyMap.put(0,publicKeyString);  //0表示公钥
		keyMap.put(1,privateKeyString);  //1表示私钥
	}
	
	/** 
	 * RSA公钥加密  128位
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt128(String str, String publicKey) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		
		byte[] dataReturn=null;
        byte[] data= str.getBytes("UTF-8");
        if(data.length <= 100){
        	dataReturn=cipher.doFinal(data);
        }else{
           for (int i = 0; i < data.length; i += 100) {  
               byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(data, i, i + 100));  
               dataReturn = ArrayUtils.addAll(dataReturn, doFinal);  
           } 
        }
        str = (new BASE64Encoder()).encode(dataReturn);
		return str;
	}
	
	/** 
	 * RSA公钥加密 
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt(String str, String publicKey) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}
	
	
	/** 
	 * RSA私钥解密 128
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws IOException 
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt128(String str, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] data = Base64.decodeBase64(str.getBytes("UTF-8"));
        int inputLength = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        while (inputLength - offset > 0) {
            if (inputLength - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offset, inputLength - offset);
            }
            out.write(cache);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
		return new String(decryptedData);
	}
	
	
	/** 
	 * RSA私钥解密
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt(String str, String privateKey) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
	
}
