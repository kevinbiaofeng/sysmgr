package com.snake.mcf.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消息处理
 * 
 * @ClassName:  Message   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:28:20   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String msg;

}
