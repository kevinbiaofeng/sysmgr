package com.snake.mcf.common.utils;

import org.springframework.beans.PropertyValues;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.ServletRequestParameterPropertyValues;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    private ServletRequest request;

    public RequestUtils(ServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request null!");
        }
        this.request = request;
    }

    public boolean exists(String name) {
        return StringUtils.isNotEmpty(request.getParameter(name));
    }

    public String getString(String name, String defaultValue) {
        return ParameterUtils.getStringValue(request.getParameter(name), defaultValue);
    }

    public short getShort(String name, short defaultValue) {
        return ParameterUtils.getShortValue(request.getParameter(name), defaultValue);
    }

    public short[] getShortValues(String name, short defaultValue) {
        return ParameterUtils.getShortValues(request.getParameterValues(name), defaultValue);
    }

    public int getInt(String name, int defaultValue) {
        return ParameterUtils.getIntValue(request.getParameter(name), defaultValue);
    }

    public int[] getIntValues(String name, int defaultValue) {
        return ParameterUtils.getIntValues(request.getParameterValues(name), defaultValue);

    }

    public long getLong(String name, long defaultValue) {
        return ParameterUtils.getLongValue(request.getParameter(name), defaultValue);
    }

    public long[] getLongValues(String name, long defaultValue) {
        return ParameterUtils.getLongValues(request.getParameterValues(name), defaultValue);
    }

    public float getFloat(String name, float defaultValue) {
        return ParameterUtils.getFloatValue(request.getParameter(name), defaultValue);
    }

    public float[] getFloatValues(String name, float defaultValue) {
        return ParameterUtils.getFloatValues(request.getParameterValues(name), defaultValue);
    }

    public double getDouble(String name, double defaultValue) {
        return ParameterUtils.getDoubleValue(request.getParameter(name), defaultValue);
    }

    public double[] getDoubleValues(String name, double defaultValue) {
        return ParameterUtils.getDoubleValues(request.getParameterValues(name), defaultValue);
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return ParameterUtils.getBooleanValue(request.getParameter(name), defaultValue);
    }

    public boolean getBoolean(String name, String trueFlag, boolean defaultValue) {
        return ParameterUtils.getBooleanValue(request.getParameter(name), trueFlag, defaultValue);
    }

    public boolean getBoolean(String name, String[] trueFlags, boolean defaultValue) {
        return ParameterUtils.getBooleanValue(request.getParameter(name), trueFlags, defaultValue);
    }

    public java.util.Date getDate(String name, java.util.Date defaultValue, String pattern) {
        return ParameterUtils.getDate(request.getParameter(name), defaultValue, pattern);
    }

    public java.util.Date getDate(String name, java.util.Date defaultValue) {
        return ParameterUtils.getDate(request.getParameter(name), defaultValue, "yyyy-MM-dd HH:mm:ss");
    }

    public java.sql.Date getSqlDate(String name, java.util.Date defaultValue, String pattern) {
        return ParameterUtils.getSqlDate(request.getParameter(name), defaultValue, pattern);
    }

    public java.sql.Date getSqlDate(String name, java.util.Date defaultValue) {
        return ParameterUtils.getSqlDate(request.getParameter(name), defaultValue, "yyyy-MM-dd");
    }

    /*
    public ParameterMap toParameterMap(String prefix){
        //BasicDynaClass dynaClass = new BasicDynaClass();
        //ArrayList DynaProperty
        ParameterMap parameterMap=new ParameterMap();
        if(prefix==null||prefix.length()==0){
            parameterMap.putAll(request.getParameterMap());
            return parameterMap;
        }
        for(Enumeration em = request.getParameterNames(); em.hasMoreElements();){
            String name=(String)em.nextElement();
            if(name.startsWith(prefix)){
                parameterMap.put(name.substring(prefix.length()),request.getParameterValues(name));
            }
        }
        return parameterMap;
    }

    public ParameterMap toParameterMap(){
        return toParameterMap(null);
    }
    */

    public void populate(String prefix, String suffix, Object bean) {
        Enumeration names = request.getParameterNames();
        HashMap properties = new HashMap();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String stripped = name;
            if (prefix != null) {
                if (!stripped.startsWith(prefix)) {
                    continue;
                }
                stripped = stripped.substring(prefix.length());
            }
            if (suffix != null) {
                if (!stripped.endsWith(suffix)) {
                    continue;
                }
                stripped = stripped.substring(0, stripped.length() - suffix.length());
            }
            Object parameterValue = null;
            parameterValue = request.getParameterValues(name);

            properties.put(stripped, parameterValue);
        }

        try {
            BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void populate(String prefix, Object bean) {
        populate(prefix, null, bean);
    }

    public void populate(Object bean) {
        populate(null, null, bean);
    }

    public static final Map<String, Object> getParameterMap(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            String[] values = request.getParameterValues(name);

            if (values == null || values.length == 0 || values.length > 1) {
                result.put(name, values);
            } else {
                result.put(name, values[0]);
            }
        }
        return result;
    }

    public static final int getInteger(HttpServletRequest request, String name, int defaultVal) {
        String value = request.getParameter(name);
        if (value == null) {
            return defaultVal;
        }
        return Integer.parseInt(value);
    }

    public static final <T> T bindTo(Class<T> targetClass, HttpServletRequest request) {

        T result = InstanceUtils.createInstance(targetClass);
        PropertyValues propertyValues = new ServletRequestParameterPropertyValues(request);
        DataBinder dataBinder = new DataBinder(result);
        dataBinder.bind(propertyValues);
        return result;
    }


}
