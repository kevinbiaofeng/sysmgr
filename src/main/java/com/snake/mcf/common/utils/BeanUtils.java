package com.snake.mcf.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.snake.mcf.common.exception.FrameworkException;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 获取指定obj的property属性的值
     *
     * @param obj
     * @param propery
     * @return
     */
    public static Object getValue(Object obj, String propery) {

        Method method = MethodUtils.getMethod(obj.getClass(), propery, MethodUtils.PropertyType.GETTER);

        try {
            return method.invoke(obj);
        } catch (IllegalArgumentException e) {
            throw new FrameworkException(e);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(e);
        } catch (InvocationTargetException e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * 获取指定obj中多个属性值，属性名通过properties指定，返回值与传入的属性名顺序一致
     *
     * @param obj
     * @param properties
     * @return
     */
    public static Object[] getValues(Object obj, String... properties) {

        if (properties.length == 0) {
            return null;
        }

        Object[] result = new Object[properties.length];
        int index = 0;
        for (String property : properties) {
            result[index++] = getValue(obj, property);
        }

        return result;
    }

    public static void populate(Object bean, Map properties) {
        try {
            BeanUtilsBean.getInstance().populate(bean, properties);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(e);
        } catch (InvocationTargetException e) {
            throw new FrameworkException(e);
        }
    }

    static {
        BeanUtilsBean.getInstance().getConvertUtils().register(new Converter() {
            @Override
            public Object convert(Class type, Object value) {
                if (value == null) {
                    return null;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                try {
                    return dateFormat.parse(value.toString());
                } catch (ParseException e) {
                    //
                }

                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return dateFormat.parse(value.toString());
                } catch (ParseException e) {
                    //
                }
                return null;
            }
        }, java.util.Date.class);
    }
}

