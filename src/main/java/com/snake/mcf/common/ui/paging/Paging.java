package com.snake.mcf.common.ui.paging;

import java.util.List;

import com.github.pagehelper.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分页对象增强
 * 
 * @ClassName:  Paging   
 * @author: hengoking
 * @date:   2018年12月28日 上午10:43:56   
 *   
 * @param <E>  
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Paging<E> extends Page<E>{

	private static final long serialVersionUID = 1L;
	
	private List<E> item;
	
	
	
	
}
