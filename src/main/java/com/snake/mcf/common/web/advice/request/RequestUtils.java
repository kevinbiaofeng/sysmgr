package com.snake.mcf.common.web.advice.request;

import com.snake.mcf.common.web.exception.UnauthorizedAccessException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RequestUtils extends com.snake.mcf.common.utils.RequestUtils {
	
    public RequestUtils(ServletRequest request) {
        super(request);
    }

    /**
     * 检查Referer，避免直接通过浏览器访问某些Spring MVC URL
     *
     * @param request
     */
    public static final void checkReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        if (referer == null) {
            throw new UnauthorizedAccessException("未授权的访问");//
        }
    }

    /**
     * 判断是否为Ajax访问
     *
     * @param request
     * @return
     */
    public static final boolean isAjaxAccess(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") == null ? false : true;
    }
}
