package com.snake.mcf.common.ui.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.pagehelper.Page;
import com.snake.mcf.common.constant.PayplatformConstant;
import com.snake.mcf.common.utils.ArrayUtils;
import com.snake.mcf.common.utils.CommonBeans;
import com.snake.mcf.common.utils.ExceptionUtils;
import com.snake.mcf.common.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * paging 分页工具类
 * 
 * @ClassName:  PagingUtils   
 * @author: hengoking
 * @date:   2018年12月28日 上午10:49:02   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
public class PagingUtils {
	
	/**
	 * 排序字符串返回
	 * 
	 * @author: hengoking 
	 * @param sort   排序字段
	 * @param order  asc desc
	 * @return
	 */
	public static String getOrderByClause(String sort , String order) {
		StringBuilder sortBuilder = new StringBuilder();
		if(StringUtils.isBlank(sort)) {
			return null;
		}
		if(StringUtils.isBlank(order)) {
			return null;
		}
    	// 都不为空
    	String[] sortArr = sort.split(PayplatformConstant.SPLIT_SYMBOL_COMMA);
    	String[] orderArr = order.split(PayplatformConstant.SPLIT_SYMBOL_COMMA);
    	for (int i = 0; i < orderArr.length; i++) {
    		sortBuilder.append(sortArr[i])
    			.append(PayplatformConstant.SPLIT_SYMBOL_NULL)
    			.append(orderArr[i])
    			.append(PayplatformConstant.SPLIT_SYMBOL_COMMA);
		}
        String orderByClause = sortBuilder.toString().substring(0, sortBuilder.toString().length() - 1);
        return orderByClause;
	}

	public static <T> Paging<T> copyPageList(Page resouce, Class<T> clazz) {
		return copyPageList(resouce, clazz, null);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> Paging<T> copyPageList(Page resouce,Class<T> clazz,String... ignoreProperties) {
        try {
			if(CollectionUtils.isNotEmpty(resouce)){
				Paging<T> paging = new Paging<>();
				Collections.copy(paging, resouce);
				List<T> list = new ArrayList<>();
				for (Object obj : resouce) {
					T t = null;
					if(ArrayUtils.isEmpty(ignoreProperties)) {
						t = CommonBeans.copyBean(obj,clazz);
					}else {
						t = CommonBeans.copyBean(obj,clazz,ignoreProperties);
					}
					list.add(t);
				}
				paging.setItem(list);
				return paging;
			}else{
				return null;
			}
		} catch (Exception e) {
			log.error("分页对象那个转换异常:e={}", e);
			ExceptionUtils.getStackTrace(e);
		}
        return null;
    }
}
