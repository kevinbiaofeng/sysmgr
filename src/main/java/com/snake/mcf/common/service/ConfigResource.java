package com.snake.mcf.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import lombok.Data;

@Configuration
@Service
@PropertySource(value = "classpath:pps/config.properties", encoding = "utf-8")
@Data
public class ConfigResource {
	/**
	 * 用户头像存储主路径
	 */
	@Value("${local.user.face.path}")
	private String userFacePath;
	/**
	 * 商户信息加密key
	 */
	@Value("${merchant.info.security.key}")
	private String merchantKey;
	/**
	 * API Rsa解密key
	 */
	@Value("${api.request.rsa.private.key}")
	private String apiRequestRSAPrivateKey;
	/**
	 * API 签名 MD5 key
	 */
	@Value("${api.request.md5.key}")
	private String apiRequestMD5Key;
	/**
	 * API RSA 加密 key
	 */
	@Value("${api.response.rsa.public.key}")
	private String apiResponseRSAPublicKey;
	/**
	 * API 版本号
	 */
	@Value("${api.interface.version}")
	private String apiVersion;
	
	/**
	 * API Aes加密Key
	 */
	@Value("${api.aes.ivp.key}")
	private String apiAesKey;
	
	/**
	 * 用户信息加密向量key
	 */
	@Value("${user.info.aes.ivp.key}")
	private String aesUserKey;
	
	/**
	 * 用户二维码跳转链接参数加密
	 */
	@Value("${user.qrcode.data.des.key}")
	private String userQrCodeDesKey;
}
