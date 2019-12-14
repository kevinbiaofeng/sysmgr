package com.snake.mcf.common.aop.annotation;

import java.lang.annotation.*;

/**
 * @ClassName AopLog
 * @Author 大帅
 * @Date 2019/6/24 17:22
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface AopLog {

    String module() default "";

    String description() default "";

}
