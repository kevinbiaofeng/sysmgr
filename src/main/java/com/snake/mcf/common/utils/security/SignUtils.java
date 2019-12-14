package com.snake.mcf.common.utils.security;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.utils.ExceptionUtils;
import com.snake.mcf.common.utils.StringUtils;
import org.apache.commons.collections4.MapUtils;

/**
 * 签名相关工具
 * 
 * @ClassName:  SignUtils   
 * @Description: 签名相关工具
 * @author: hengoking
 * @date:   2018年12月24日 下午4:35:09   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class SignUtils {
	
	private SignUtils() {
	    throw new IllegalStateException("SignUtils class");
	}

	/**
	 * 根据map生成签名 <br/>
	 * 主要用于手机端前后台交互 
	 * 
	 * @Title: getSigin    
	 * @author: hengoking  
	 * @param map  传入字符串
	 * @return String       
	 * @throws
	 */
	@Deprecated
	public static String getSigin(Map<String, String> map) {
		StringBuilder content = new StringBuilder(PayplatformConstant.NULL_CHARACTER);
		//判断map是否为空
		if(MapUtils.isEmpty(map)) {
			return null;
		}
		//不为空组装字符串
		Set<String> keySet = map.keySet();
		List<String> list = new ArrayList<>(keySet);
		//排序
		Collections.sort(list, (o1,o2) -> o1.compareToIgnoreCase(o2));
		//组装字符串
		list.forEach((k) -> {
			String value = map.get(k);
			content.append(k)
				.append(PayplatformConstant.SPLIT_SYMBOL_EQUAL)
				.append(value)
				.append(PayplatformConstant.SPLIT_SYMBOL_AND);
		});
		String result = content.toString();
		if(StringUtils.isBlank(result)) {
			return null;
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	

	/**
	 * 请求密文数据生成签名
	 *
	 * @Title: encryptSign
	 * @author: hengoking
	 * @param shellerNo  商户号
	 * @param sign       令牌签名
	 * @param content    密文数据
	 * @return String
	 * @throws
	 */
	public static String encryptSign(String shellerNo,String sign,String content){
		String s = shellerNo + sign + content; //签名 ： 乙方商户号 + 令牌B + 密文数据
		String targetSign = null;
		try {
			targetSign = MD5Utils.md5Digest(s.getBytes(PayplatformConstant.CONTENT_TYPE_ENCODE_UTF_8));
			return targetSign;
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.getStackTrace(e);
			throw new BusinessException("请求密文数据生成签名失败: shellerNo={}, sign={}", shellerNo,sign);
		}

	}


	/**
	 * 验证签名
	 *
	 * @Title: checkSign
	 * @author: hengoking
	 * @param sourceSign 源签名
	 * @param shellerNo  商户号
	 * @param tokenSign  令牌签名
	 * @param content    密文
	 * @return boolean
	 * @throws
	 */
	public static boolean checkSign(String sourceSign , String shellerNo,String tokenSign,String content){
		if(StringUtils.isBlank(content) || StringUtils.isBlank(sourceSign)){
			return false;
		}
		String plainString = shellerNo + tokenSign + content; //乙方商户号+令牌B+密文数据
		return MD5Utils.checkSign(plainString, sourceSign);
	}



}
