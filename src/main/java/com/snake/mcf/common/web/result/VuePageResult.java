package com.snake.mcf.common.web.result;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Vue框架Vuetify分页数据结构
 * 
 * @ClassName:  VuePageResult   
 * @author: hengoking
 * @date:   2018年12月26日 上午10:24:36   
 *   
 * @param <T>  
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class VuePageResult<T> extends PageResult<T> {

	private static final long serialVersionUID = 1L;

    private List<T> items;// 当前页数据

    public VuePageResult(Long total, List<T> items){
        super(total);
        this.items = items;
    }
    
}
