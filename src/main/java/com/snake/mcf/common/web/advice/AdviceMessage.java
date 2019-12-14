package com.snake.mcf.common.web.advice;

import com.snake.mcf.common.message.StatusMessage;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 统一异常处理通知消息
 * 
 * @ClassName:  AdviceMessage   
 * @Description: 统一异常处理通知消息
 * @author: hengoking
 * @date:   2018年12月24日 下午4:37:50   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class AdviceMessage extends StatusMessage {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;

}
