package com.snake.mcf.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtils {

    /**
     * 将对象中指定类型为空的字段，转换成默认值
     *
     * @param target
     * @param type
     * @param defaultVal
     * @param <FieldType>
     */
    public static final <FieldType> void nullToDefault(Object target, Class<FieldType> type, FieldType defaultVal) {
        if (target == null || type == null || defaultVal == null)
            return;
        Class<?> targetType = target.getClass();
        Field[] fields = targetType.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
                continue;
            }
            if (type == field.getType() || field.getType().isAssignableFrom(type)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                try {
                    Object val = field.get(target);
                    if (val == null) {
                        field.set(target, defaultVal);
                    }
                } catch (IllegalAccessException e) {
                    //
                	log.error("{}",e);
                }
            }
        }
    }
}
