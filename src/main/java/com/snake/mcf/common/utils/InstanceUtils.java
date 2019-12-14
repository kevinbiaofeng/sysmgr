package com.snake.mcf.common.utils;

import com.snake.mcf.common.exception.FrameworkException;

/**
 * 实例化工具，按照给定的clazz进行实例化
 */
public class InstanceUtils {

    /**
     * 根据指定类型创建一个类的实例
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FrameworkException(e);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(e);
        }
    }

    /**
     * 根据指定全类名创建实例
     *
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T createInstance(String className) {
        try {
            Class clazz = Class.forName(className);
            return (T) clazz.newInstance();
        } catch (InstantiationException e) {
            throw new FrameworkException(e);
        } catch (IllegalAccessException e) {
            throw new FrameworkException(e);
        } catch (ClassNotFoundException e) {
            throw new FrameworkException(e);
        }
    }

    public static Class toClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new FrameworkException(e);
        }
    }
}
