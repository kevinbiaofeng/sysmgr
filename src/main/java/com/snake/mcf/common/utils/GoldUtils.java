package com.snake.mcf.common.utils;

import java.math.BigDecimal;

/**
 * @ClassName GoldUtils
 * @Author 大帅
 * @Date 2019/7/11 11:47
 */
public class GoldUtils {

    private static final int DEFAULT_RATIO = 100;
    
    private static final BigDecimal DEFAULT_RATIO_BD = new BigDecimal(100);
    
    /**
     * 根据传入的值按比例 放大
     *
     * @param value
     * @param ratio
     * @return
     */
    public static long boots(long value , int ratio){
        return value * ratio;
    }

    /**
         * 根据传入的值按比例 放大
         *  默认比例 100
     * @param value
     * @return
     */
    public static long boots(long value){
        return boots(value, DEFAULT_RATIO);
    }

    /**
     * 根据传入的值按比例 缩小
     *
     * @param value 传入的值
     * @param ratio 比例
     * @return
     */
    public static double reduce(long value , int ratio){
        if(ratio == 0){
            return 0L;
        }
        double result = (double)value/ratio;
        return result;
    }

    /**
     * 根据传入的值按比例 缩小
     *      默认比例 100
     * @param value
     * @return
     */
    public static double reduce(long value){
        return reduce(value,DEFAULT_RATIO);
    }
    
    
    /**
         *  乘法
     * @param value
     * @param ratio
     * @return
     */
    public static BigDecimal boots(BigDecimal value , BigDecimal ratio){
        return value.multiply(ratio); 
    }
    
    
    /**
        * 根据传入的值按比例 放大   乘法
        *  默认比例 100
	 * @param value
	 * @return
	 */
    public static BigDecimal boots(String value) {
    	return boots(new BigDecimal(value), DEFAULT_RATIO_BD);
    }
    
    /**
	  *  除法
	 * @param value
	 * @param ratio
	 * @return
	 */
	public static BigDecimal divide(BigDecimal value , BigDecimal ratio){
	    return value.divide(ratio); 
	}
	
	/**
	  * 根据传入的值按比例缩小   除法
        *  默认比例 100
	 * @param value
	 * @return
	 */
	 public static BigDecimal divide(Integer value) {
	 	return divide(new BigDecimal(value), DEFAULT_RATIO_BD);
	 }

}
