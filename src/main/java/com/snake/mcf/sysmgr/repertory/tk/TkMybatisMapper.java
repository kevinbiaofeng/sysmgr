package com.snake.mcf.sysmgr.repertory.tk;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * @author  dashuai
 */
@Target(ElementType.TYPE) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
@Repository
@Mapper
public @interface TkMybatisMapper {


}
