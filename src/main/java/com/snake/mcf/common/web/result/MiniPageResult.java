package com.snake.mcf.common.web.result;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * miniui 分页数据结构
 * 
 * @ClassName:  MiniPageResult   
 * @author: hengoking
 * @date:   2018年12月26日 上午10:27:35   
 *   
 * @param <T>  
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@EqualsAndHashCode(callSuper=false) 
@NoArgsConstructor
@AllArgsConstructor
public class MiniPageResult<T> extends PageResult<T> {

	private static final long serialVersionUID = 1L;
	
	private List<T> data;// 当前页数据
	
}
