package com.snake.mcf.common.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 状态消息
 * 
 * @ClassName:  StatusMessage   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:28:35   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class StatusMessage extends Message {

	private static final long serialVersionUID = 1L;
	
	private Integer httpStatusCode;

}
