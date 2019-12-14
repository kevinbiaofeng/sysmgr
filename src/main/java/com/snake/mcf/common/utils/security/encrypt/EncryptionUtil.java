package com.snake.mcf.common.utils.security.encrypt;

import java.net.URLEncoder;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.utils.ExceptionUtils;
import com.snake.mcf.common.utils.security.AESUtils;
import com.snake.mcf.common.utils.security.Base64Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * 加密明文内容
 *
 * @ClassName:  EncryptionUtil
 * @Description: 加密明文内容
 * @author: hengoking
 * @date:   2018年12月24日 下午3:07:38
 *
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved.
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class EncryptionUtil {

	/**
	 *
	 * 支付平台 加密 发给商户数据
	 *
	 * 加密明文返回密文 <br/>
	 * 加密规则 (BASE64(AES("JSON 明文数据体", "密钥")))
	 *
	 * @Title: encryptContent
	 * @author: hengoking
	 * @param jsonSource 数据源
	 * @param secret   密文
	 * @return String
	 * @throws
	 */
	public static String encryptContent(String jsonSource,String secret){
		String content = null;
		try {
			content = Base64Utils.encode(AESUtils.aesEncryptToBytes(jsonSource, secret));
			//如果content含有加号 对密文encode处理
			content = URLEncoder.encode(content, PayplatformConstant.ENCODE_UTF_8);
			return content;
		} catch (Exception e) {
			log.error("支付平台 加密 发给商户数据失败: jsonSource={}, secret={}", jsonSource,secret);
			ExceptionUtils.getStackTrace(e);
			return null;
		}

	}

}
