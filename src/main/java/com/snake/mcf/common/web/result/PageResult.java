package com.snake.mcf.common.web.result;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class PageResult<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long total = 0L;// 总条数
	
}
