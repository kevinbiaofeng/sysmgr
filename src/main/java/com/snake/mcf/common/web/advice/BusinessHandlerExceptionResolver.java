package com.snake.mcf.common.web.advice;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snake.mcf.common.exception.FrameworkException;
import com.snake.mcf.common.web.advice.request.RequestUtils;
import com.snake.mcf.common.web.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 全局异常处理
 */
public class BusinessHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

    private final String defaultMessage = "未知错误，请联系管理员！";

    public BusinessHandlerExceptionResolver() {
        super.setOrder(1000);
    }

    /**
     * 发回JSON数据
     */
    private View DEFAULT_VIEW = new MappingJackson2JsonView();

    @Override
    @ExceptionHandler
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof FrameworkException) {
            Result result = createErrorMessage(ex);
            if (RequestUtils.isAjaxAccess(request)) {
                return new ModelAndView(DEFAULT_VIEW).addObject(result);
            }
            try {
                request.setAttribute("error", result);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (IOException e) {
                logger.error("{}", e);
            }
        }
        return null;
    }

    /**
     * 创建错误消息
     *
     * @param e
     * @return
     */
    private Result createErrorMessage(Exception e) {
        Result result;
        Throwable target = getTargetException(e);
        if (supportException(target)) {
            result = Result.fail(target.getMessage());
        } else {
            result = Result.fail(defaultMessage);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取最原始的异常对象
     *
     * @param e
     * @return
     */
    private Throwable getTargetException(Throwable e) {
        if (e.getCause() == null) {
            return e;
        }
        return getTargetException(e.getCause());
    }

    /**
     * 判断异常是否为支持处理的异常
     *
     * @param cause
     * @return
     */
    private boolean supportException(Throwable cause) {
        return FrameworkException.class.isAssignableFrom(cause.getClass());
    }

}
