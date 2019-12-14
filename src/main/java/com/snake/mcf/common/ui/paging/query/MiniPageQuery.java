package com.snake.mcf.common.ui.paging.query;

import java.util.ArrayList;
import java.util.List;

import com.snake.mcf.common.ui.paging.PagingUtils;
import com.snake.mcf.common.ui.paging.filter.MiniPageFilter;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.web.result.MiniPageResult;
import com.snake.mcf.common.web.result.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * miniui 分页
 * 
 * @ClassName: MiniPageQuery
 * @author: hengoking
 * @date: 2018年12月26日 下午6:59:39
 * 
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 注意：本内容仅限于
 *             内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class MiniPageQuery implements PagingQuery<MiniPageFilter> {

	@Override
	public <E> Page<E> startPage(MiniPageFilter filter) {
		return PageHelper.startPage(filter.getPageIndex(), filter.getPageSize());
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <R> PageResult<R> queryResult(List sourceList, Class<R> clazz) {
		// 构建返回值对象
		MiniPageResult<R> pageResult = new MiniPageResult<R>();
		// 判断查询结果是否为空
		if (CollectionUtils.isEmpty(sourceList)) {
			// 传入结果为空
			sourceList = new ArrayList<>();
			pageResult.setData(sourceList);
		} else {
			// 传入结果不为空
			PageInfo<R> userPageInfo = new PageInfo<>(sourceList);
			pageResult.setTotal(userPageInfo.getTotal());// 总条数
			//将传入sourceList转成 Class<R>
			List<R> targetList = CommonBeans.copyNewList(sourceList, clazz);
            pageResult.setData(targetList);
		}
		return pageResult;
	}

	@Override
	public String sortOrderByClause(MiniPageFilter filter) {
		return PagingUtils.getOrderByClause(filter.getSortField(), filter.getSortOrder());
	}

}
