package com.snake.mcf.common.ui.paging.query;

import java.util.List;

import com.github.pagehelper.Page;
import com.snake.mcf.common.ui.paging.PagingFilter;
import com.snake.mcf.common.web.result.PageResult;

/**
 * 分页查询接口
 * 
 * @ClassName:  PageQuery   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:33:30   
 *   
 * @param <T>  
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public interface PagingQuery<T extends PagingFilter> {

	/**
	 * 开始分页
	 * 
	 * @author: hengoking 
	 * @param t
	 * @return
	 */
	public <E> Page<E> startPage(T t);
	
	/**
	 * 返回分页结果
	 * 
	 * @author: hengoking 
	 * @param sourceList
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public <R> PageResult<R> queryResult(List sourceList, Class<R> clazz);
	
	/**
	 * 获取排序结果
	 * 
	 * @author: hengoking 
	 * @param t
	 * @return
	 */
	public String sortOrderByClause(T t);
	
	
}
