package com.snake.mcf.common.utils;

import java.text.MessageFormat;

/**
 * 占位符格式化工具类 <br/>
 * 
 * 设置properties占位符<br/>
 * 
 * @ClassName:  MessageFormatUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:48:31   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class MessageFormatUtils {

	private MessageFormatUtils() {
		
	}
	
	/**
	 * 占位符替换
	 * 
	 * @param pattern 要替换的目标的字符串
	 * @param array   String[]
	 * @return
	 */
	public static String messageFormatByStringArray(String pattern,String[] array){
		pattern = messageFormatByObjectArray(pattern, array);
		return pattern;
	}

	/**
	 * 占位符替换
	 * 
	 * @param pattern 要替换的目标的字符串
	 * @param array   Object[]
	 * @return
	 */
	private static String messageFormatByObjectArray(String pattern,Object[] array){
		pattern = MessageFormat.format(pattern, array);
		return pattern;
	}

}
