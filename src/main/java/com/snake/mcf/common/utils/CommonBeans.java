package com.snake.mcf.common.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

public class CommonBeans extends BeanUtils{
	
	@SuppressWarnings("rawtypes")
	public static <T> List<T> copyNewList(List resouce,Class<T> clazz) {
		return copyNewList(resouce, clazz, null);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> copyNewList(List resouce,Class<T> clazz,String... ignoreProperties) {
        try {
			if(CollectionUtils.isNotEmpty(resouce)){
				List<T> list = new ArrayList<>();
				/*for (Object obj : resouce) {
					T t = null;
					if(ArrayUtils.isEmpty(ignoreProperties)) {
						t = copyBean(obj,clazz);
					}else {
						t = copyBean(obj,clazz,ignoreProperties);
					}
					list.add(t);
				}*/
				resouce.forEach(obj -> {
					T t = null;
					if(ArrayUtils.isEmpty(ignoreProperties)) {
						t = copyBean(obj,clazz);
					}else {
						t = copyBean(obj,clazz,ignoreProperties);
					}
					list.add(t);
				});
				return list;
			}else{
				return null;
			}
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
		}
        return null;
    }
	
	public static <T> T copyBean(Object obj , Class<T> clazz){
		return copyBean(obj, clazz, null);
	}
	
	public static <T> T copyBean(Object obj , Class<T> clazz,String... ignoreProperties){
		try {
			T t = clazz.newInstance();
			if(ArrayUtils.isEmpty(ignoreProperties)) {
				BeanUtils.copyProperties(obj, t);
			}else {
				BeanUtils.copyProperties(obj, t,ignoreProperties);
			}
			return t;
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> copyList(List resouce,Class<T> clazz,String... ignoreProperties) {
        try {
			if(CollectionUtils.isNotEmpty(resouce)){
				List<T> list = new ArrayList<>(resouce.size());
				
				Iterator iterator = resouce.iterator();
				
				while (iterator.hasNext()) {
					Object obj = iterator.next();
					T t = null;
					if(ArrayUtils.isEmpty(ignoreProperties)) {
						t = copyBean(obj,clazz);
					}else {
						t = copyBean(obj,clazz,ignoreProperties);
					}
					list.add(t);
					iterator.remove();
				}
				
				for (T t : list) {
					resouce.add(t);
				}
				
				return resouce;
			}else{
				return null;
			}
		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);
		}
        return null;
    }
	
	@SuppressWarnings("rawtypes")
	public static <T> List<?> copyList(List resouce,Class<T> clazz) {
        return copyList(resouce, clazz,null);
    }
	
	/**
	 * 拷贝时，忽略属性

	 */
	public static final String[] IGNORE_PROPS = new String[] { "", "" };

	/**
	 * @author xieguojun
	 * 拷贝属性

	 * 
	 * @param source
	 * @param target
	 * @param ignore 是否忽略BeanUtils.IGNORE_PROPS的字段

	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target, boolean ignore) throws BeansException {
		if (ignore) {
			copyProperties(source, target, IGNORE_PROPS);
		} else {
			copyProperties(source, target);
		}
	}
	
	/**
	 * @author xieguojun
	 * 拷贝属性，忽略元数据空值复制


	 * 
	 * @param source
	 * @param target
	 * @param 

	 * @throws BeansException
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target) throws BeansException {
		_copyProperties(source,target,true,true);
	}
	
	/**
	 * @author xieguojun
	 * 拷贝属性，忽略元数据空值复制

	 * 
	 * @param source
	 * @param target
	 * @param ignore 是否忽略BeanUtils.IGNORE_PROPS的字段

	 * @throws BeansException
	 */
	public static void copyPropertiesIgnoreNull(Object source, Object target, boolean ignore) throws BeansException {
		_copyProperties(source,target,ignore,true);
	}

	/**
	 * @author xieguojun
	 * @param source
	 * @param target
	 * @param ignore     是否忽略属性IGNORE_PROPS复制
	 * @param ignoreNull 是否忽略空值复制

	 * @throws BeansException
	 */
	@SuppressWarnings("rawtypes")
	private static void _copyProperties(Object source, Object target,boolean ignore, boolean ignoreNull) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

	 
		PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());
		List ignoreList = ignore ? null : Arrays.asList(IGNORE_PROPS);

		for (int i = 0; i < targetPds.length; i++) {
			PropertyDescriptor targetPd = targetPds[i];
			if (targetPd.getWriteMethod() != null
					&& (ignore || (!ignoreList.contains(targetPd.getName())))) {
				
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method getter = sourcePd.getReadMethod();
						if (!Modifier.isPublic(getter.getDeclaringClass().getModifiers())) {
							getter.setAccessible(true);
						}
						Object value = getter.invoke(source, new Object[0]);
						
						if(ignoreNull && value ==null)
							continue;
						
						Method setter = targetPd.getWriteMethod();
						if (!Modifier.isPublic(setter.getDeclaringClass().getModifiers())) {
							setter.setAccessible(true);
						}
						setter.invoke(target, new Object[] { value });
					}
					catch (Throwable ex) {
						ExceptionUtils.getStackTrace(ex);
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}
	
	
	
}
