package com.snake.mcf.common.ui.paging.query;

import java.util.ArrayList;
import java.util.List;

import com.snake.mcf.common.ui.paging.PagingUtils;
import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.web.result.EasyPageResult;
import com.snake.mcf.common.web.result.PageResult;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * easyui 分页
 * 
 * @ClassName:  EasyPageQuery   
 * @author: hengoking
 * @date:   2018年12月26日 下午6:39:37   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
public class EasyPagingQuery implements PagingQuery<EasyPageFilter>{

	@Override
	public <E> Page<E> startPage(EasyPageFilter filter) {
		return PageHelper.startPage(filter.getPage(), filter.getRows());
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <R> PageResult<R> queryResult(List sourceList, Class<R> clazz) {
		//构建返回值对象
		EasyPageResult<R> pageResult = new EasyPageResult<R>();
		//判断查询结果是否为空
		if(CollectionUtils.isEmpty(sourceList)) {
			//传入结果为空
			sourceList = new ArrayList<>();
			pageResult.setRows(sourceList);
		}else {
			//传入结果不为空
			PageInfo<R> userPageInfo = new PageInfo<>(sourceList);
			pageResult.setTotal(userPageInfo.getTotal());//总条数
			//将传入sourceList转成 Class<R>
			List<R> targetList = CommonBeans.copyNewList(sourceList, clazz);
            pageResult.setRows(targetList);
		}
		return pageResult;
	}

	@Override
	public String sortOrderByClause(EasyPageFilter filter) {
		return PagingUtils.getOrderByClause(filter.getSort(), filter.getOrder());
	}
	
	
	

	
}
	
	
