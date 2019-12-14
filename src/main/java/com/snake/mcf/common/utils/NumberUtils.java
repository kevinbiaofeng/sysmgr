package com.snake.mcf.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字工具类
 *
 * @ClassName:  NumberUtils
 * @author: hengoking
 * @date:   2018年12月29日 下午1:43:45
 *
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved.
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class NumberUtils extends org.springframework.util.NumberUtils {

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判断字符串是否是数值格式
     * @param str
     * @return
     */
    public static boolean isDigit(String str){
        if(str == null || str.trim().equals("")){
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 将一个小数精确到指定位数
     * @param num
     * @param scale
     * @return
     */
    public static double scale(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    // 从字符串中根据正则表达式寻找，返回找到的数字数组
    public static Double[] searchNumber(String value, String regex){
        List<Double> doubles = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            for (int i = 1; i <= result.groupCount(); i++) {
                doubles.add(Double.valueOf(result.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }

    /**
     * 生成指定位数的随机数字
     * @param len
     * @return
     */
    public static String generateCode(int len){
        len = Math.min(len, 8);
        int min = Double.valueOf(Math.pow(10, len - 1)).intValue();
        int num = new Random().nextInt(Double.valueOf(Math.pow(10, len + 1)).intValue() - 1) + min;
        return String.valueOf(num).substring(0,len);
    }


    /**
     * 取小于value的几位小数或整数的最小值，负数表示整数位<br>
     * 如：<br>
     * floor(34.234,1)=34.2<br>
     * floor(34.234,0)=34<br>
     * floor(34.234,-1)=30<br>
     * floor(-34.234,-1)=-34.3<br>
     * ...
     *
     * @param value
     * @param scale
     * @return
     */
    public static double floor(double value, int scale) {
        double fix = Math.pow(0.1, scale);
        return Math.floor(value / fix) * fix;
    }

    /**
     * 取大于value的几位小数或整数的最小值，负数表示整数位<br>
     * 如：<br>
     * ceil(34.234,1)=34.3<br>
     * ceil(34.234,0)=35<br>
     * ceil(34.234,-1)=40<br>
     * ceil(-34.234,1)=-34.2<br>
     * ...
     *
     * @param value
     * @param scale
     * @return
     */
    public static double ceil(double value, int scale) {
        double fix = Math.pow(0.1, scale);
        return Math.ceil(value / fix) * fix;
    }

    /**
     * 取接近于value的几位小数或整数的最小值，负数表示整数位<br>
     * 如：<br>
     * round(35.234,1)=34.2<br>
     * round(35.234,0)=35<br>
     * round(35.234,-1)=40<br>
     * round(-35.234,1)=-35.2<br>
     * ...
     *
     * @param value
     * @param scale
     * @return
     */
    public static double round(double value, int scale) {
        double fix = Math.pow(0.1, scale);
        return Math.round(value / fix) * fix;
    }

    /**
     * 字符串转整型加上三位随机数再转字符串，
     * 最终字符串若和原始字符串长度不一样则前面+0
     */
    public static String changeRandomNum(int len) {
        String initRandomStr = generateCode(len);
        String finalRandomStr = String.valueOf(Integer.parseInt(initRandomStr) + Integer.parseInt(generateCode(3)));
        //如果最终字符串长度小于原始长度，那么前面+0
        while (finalRandomStr.length() < initRandomStr.length()) {
            finalRandomStr = "0" + finalRandomStr;
        }
        //如果最终长度大于原始长度，字符串截取前五位
        if (finalRandomStr.length() > initRandomStr.length()) {
            finalRandomStr = finalRandomStr.substring(0, len);
        }
        return finalRandomStr;
    }



}
