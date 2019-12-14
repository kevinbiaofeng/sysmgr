package com.snake.mcf.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class MethodUtils extends org.apache.commons.beanutils.MethodUtils{

    /**
     * 属性方法类型
     *
     * @author xieguojun
     * @author ($Date$ modification by $Author$)
     * @version $Revision$ 2011-1-10
     * @since 1.0
     */
    public enum PropertyType {
        SETTER,
        GETTER
    }

    /**
     * 根据方法名获取对应类型的方法
     *
     * @param targetType
     * @param methonName
     * @return
     */
    public static Method getMethod(Class<?> targetType, String methonName) {

        Method[] methods = targetType.getMethods();
        for (Method method : methods) {
            if (Modifier.isPrivate(method.getModifiers()))
                continue;

            if (method.getName().equals(methonName))
                return method;
        }

        Method[] declaredMethods = targetType.getDeclaredMethods();
        for (Method method : declaredMethods) {

            if (!Modifier.isPublic(method.getModifiers())) {
                method.setAccessible(true);
            }

            if (method.getName().equals(methonName))
                return method;
        }

        return null;
    }

    /**
     * 获取对应的方法
     *
     * @param targetType
     * @param property
     * @param type
     * @return
     */
    public static Method getMethod(Class<?> targetType, String property, PropertyType type) {

        return getMethod(targetType, getMethodName(targetType, property, type));
    }

    /**
     * 获取函数名
     *
     * @param targetType
     * @param property
     * @param type
     * @return
     */
    public static String getMethodName(Class<?> targetType, String property, PropertyType type) {

        StringBuffer methonName = new StringBuffer(property.length() + 3);

        if (PropertyType.SETTER == type) {
            methonName.append("set");
        }

        if (PropertyType.GETTER == type) {
            methonName.append("get");
        }

        methonName.append(Character.toUpperCase(property.charAt(0)));

        methonName.append(property.substring(1));

        return methonName.toString();
    }

    /**
     * 获取getter或者setter
     *
     * @param targetType
     * @param property
     * @return
     */
    public static Map<PropertyType, Method> getMethods(Class<?> targetType, String property) {

        Map<PropertyType, Method> methods = new HashMap<PropertyType, Method>();

        methods.put(PropertyType.SETTER, getMethod(targetType, property, PropertyType.SETTER));
        methods.put(PropertyType.GETTER, getMethod(targetType, property, PropertyType.GETTER));

        return methods;
    }


}
