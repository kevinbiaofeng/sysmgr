package com.snake.mcf.common.api;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.utils.GsonUtils;
import com.snake.mcf.common.utils.security.MD5Utils;
import com.snake.mcf.common.utils.security.aes.AESCBCUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RestControllerAdvice("com.snake.mcf.sysmgr.controller.api")
public class APIResponseBodyAdvice implements ResponseBodyAdvice<Object> {
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
    private ConfigResource configResource;
	
	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (selectedConverterType == MappingJackson2HttpMessageConverter.class
				&& (selectedContentType.equals(MediaType.APPLICATION_JSON)
						|| selectedContentType.equals(MediaType.APPLICATION_JSON_UTF8))) {
			HttpServletRequest req = ((ServletServerHttpRequest) request).getServletRequest();
			if (body == null) {
				return ResultResp.NULL;
			} else if (body instanceof ResultResp) {
				return body;
			} else {
				if (returnType.getExecutable().getDeclaringClass().isAssignableFrom(BasicErrorController.class)) {
					ResultResp vo = new ResultResp(ResultCode.EXCEPTION);
					if (req.getRequestURL().toString().contains("localhost") || req.getRequestURL().toString().contains("127.0.0.1"))
						vo.setData(body);
					return vo;
				} else {
					try {
						Map map = objectMapper.readValue(objectMapper.writeValueAsString(body), Map.class);
						StringBuffer sb = new StringBuffer();
						sb.append(map.get("dateTime").toString()).append(configResource.getApiRequestMD5Key())
						.append(map.get("dateTime").toString()).append(map.get("apiVersion").toString());
						map.put("sign", MD5Utils.md5Digest(sb.toString()));//签名
						log.info("接口返回数据：{}", map);
						if (req.getRequestURL().toString().contains("localhost") || req.getRequestURL().toString().contains("127.0.0.1")) {
							return new ResultResp(map);
						}else {
							String responseText = AESCBCUtils.encrypt(GsonUtils.toString(map), configResource.getApiAesKey(), true);
							return new ResultResp(responseText);
						}
					} catch (IOException e) {
						log.error("创建签名失败.", e.getMessage());
						e.printStackTrace();
						return e;
					} catch (Exception e) {
						log.error("加密数据失败.", e.getMessage());
						e.printStackTrace();
						return e;
					}
				}
			}
		}
		return body;
	}

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		if (returnType.hasMethodAnnotation(ResultRespIgnore.class))
			return false;
		return true;
	}
	
}
