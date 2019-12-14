package com.snake.mcf.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 版本号控制
 *
 * @ClassName VersionUtils
 * @Author 大帅
 * @Date 2019/6/29 18:39
 */
@Slf4j
public class VersionUtils {

    /**
     * 序列化版本号
     *
     * @param version 版本号一共有四位数字中间用点分开
     *                例如: 6.7.0.1
     * @return
     *          1 对字符串先根据 \\. 进行切分成数组
     *          2 对各个数字进行运算
     *                第一个数组 位运算 左移 24位
     *                第二个数字 位运算 左移 16位
     *                第三个数字 位运算 左移 8位
     *                第四个数字 不进行运算
     *          3 对以上运算后的结果进行求和
     */
    public static int serializeVersion(String version){
        if(StringUtils.isBlank(version)){
            return 0;
        }
        String[] versionArr = version.split("\\.");
        //第一位 数字 左移 24 位
        int a = Integer.valueOf(versionArr[0]).intValue() << 24;
        //log.info("a = {}" , a);

        //第二位 数字 左移 16 位
        int b = Integer.valueOf(versionArr[1]) << 16;
        //log.info("b = {}" , b);

        //第三位 数字 左移 8 位
        int c = Integer.valueOf(versionArr[2]) << 8;
        //log.info("c = {}" , c);

        //第四位 数字 直接默认
        int d = Integer.valueOf(versionArr[3]);
        //log.info("d = {}" , d);

        return (a + b + c + d);
    }


    /**
     * 反序列化版本号
     *
     * @param version 序列化以后版本号数字
     *                例如: 101122049
     * @return
     */
    public static String deserializeVersion(int version){
        //第一位数字 右移 24 位
        int a = version >> 24;
        //log.info("a = {}" , a);

        //第二位数字 1 右移 16位 2 左移24位 3 右移 24 位
        int b = (((version >> 16) << 24) >> 24);
        //log.info("b = {}" , b);

        //第三位数字 1 右移 8位 2 左移 24位 3 右移 24位
        int c = (((version >> 8) << 24) >> 24);
        //log.info("c = {}" , c);

        //第四位 1 左移24位 2 右移 24位
        int d = ((version << 24) >> 24);
        //log.info("d = {}" , d);

        return (a + "." + b + "." + c + "." + d);

    }

    /**
     * 将一个数 根据二进制分解
     *
     * @param value 传入的参数
     *          例如: 7
     * @return
     *       分解以后返回结果
     *       7 = 1 + 2 + 4
     *       List中存储的就是 1 2 4
     */
    public static List<Integer> transferBinary(int value){
        //接收组成数
        List<Integer> numbers = new ArrayList<>();
        //接收二进制各位数
        List<Integer> bi = new ArrayList<>();
        //二进制转换
        while(value > 0) {
            bi.add(value%2);
            value = value/2;
        }
        int number = 0;
        //计算组成数
        for(int i = 0; i < bi.size(); i++) {
            if((number = bi.get(i)) != 0) {
                numbers.add(number * (int)Math.pow(2, i));
            }
        }
        return numbers;
    }







}
