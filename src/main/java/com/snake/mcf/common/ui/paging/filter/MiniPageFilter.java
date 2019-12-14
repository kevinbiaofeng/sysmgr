package com.snake.mcf.common.ui.paging.filter;

import com.snake.mcf.common.ui.paging.PagingFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * miniui 分页参数
 * 
 * @ClassName:  MiniPageFilter   
 * @author: hengoking
 * @date:   2018年12月26日 下午6:17:27   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniPageFilter implements PagingFilter {

	private static final long serialVersionUID = 1L;

	private Integer pageIndex = 1;//当前第几页
	private Integer pageSize = 10;//每一页多少条
	private String sortField;//排序字段 多个之间 ,分割
	private String sortOrder = "asc";//排序默认asc
	
}
