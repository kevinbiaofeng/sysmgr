package com.snake.mcf.common.utils.security.decrypt;

import java.net.URLDecoder;

import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.utils.ExceptionUtils;
import com.snake.mcf.common.utils.security.AESUtils;
import com.snake.mcf.common.utils.security.Base64Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * 解密密文内容
 *
 * @ClassName: DecryptUtil
 * @Description: 解密密文内容
 * @author: hengoking
 * @date: 2018年12月24日 下午3:02:41
 *
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 注意：本内容仅限于
 *             内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class DecryptionUtil {

	private DecryptionUtil() {
		throw new IllegalStateException("DecryptionUtil class");
	}

	/**
	 * 支付平台 解密 商户发过来的数据
	 *
	 * @param text
	 * @param secret
	 * @return
	 */
	public static String decryptContent(String text, String secret) {
		String content = null;
		try {
			text = URLDecoder.decode(text, PayplatformConstant.ENCODE_UTF_8);
			content = AESUtils.aesDecryptByBytes(Base64Utils.decode(text), secret);
			return content;
		} catch (Exception e) {
			log.error("支付平台 解密 商户发过来的数据失败: text={}, secret={}", text, secret);
			ExceptionUtils.getStackTrace(e);
			return null;
		}
	}

}
