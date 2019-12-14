package com.snake.mcf.common.exception;

import com.snake.mcf.common.utils.StringUtils;

/**
 * 框架异常
 * 
 * @ClassName:  FrameworkException   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:27:56   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class FrameworkException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FrameworkException(String message) {
        super(message);
    }

    public FrameworkException(Throwable cause) {
        super(cause);
    }

    public FrameworkException(String fmt, Object... args) {
        super(format(fmt, args));
    }

    public FrameworkException(Throwable cause, String fmt, Object... args) {
        super(format(fmt, args), cause);
    }

    /**
     * @param fmt    错误消息格式字符串，使用{}替代参数，每一{}替代一个参数
     * @param params 参数清单
     * @return
     */
    private static final String format(String fmt, Object... params) {
        return StringUtils.format(fmt, params);
    }

}
