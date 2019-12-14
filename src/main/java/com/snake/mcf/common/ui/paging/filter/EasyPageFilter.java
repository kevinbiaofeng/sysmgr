package com.snake.mcf.common.ui.paging.filter;

import com.snake.mcf.common.ui.paging.PagingFilter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * easyui 分页参数
 * 
 * @ClassName:  EasyPageFilter   
 * @author: hengoking
 * @date:   2018年12月26日 下午6:15:28   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EasyPageFilter implements PagingFilter {

	private static final long serialVersionUID = 1L;
	
	private Integer page = 1;//当前第几页
	private Integer rows = 10;//每一页多少条
	private String sort;//排序字段 多个之间用 , 分割
	private String order = "asc";//排序默认asc
	
	
}
