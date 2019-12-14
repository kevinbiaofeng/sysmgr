package com.snake.mcf.common.ui.paging.query;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.VuePageFilter;
import com.snake.mcf.common.web.result.PageResult;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;

/**
 * vue 分页
 * 
 * @ClassName:  VuePageQuery   
 * @author: hengoking
 * @date:   2018年12月26日 下午7:03:06   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class VuePageQuery implements PagingQuery<VuePageFilter> {

	@Override
	public <E> Page<E> startPage(VuePageFilter t) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <R> PageResult<R> queryResult(List sourceList, Class<R> clazz) {
		return null;
	}

	@Override
	public String sortOrderByClause(VuePageFilter t) {
		return null;
	}

}
