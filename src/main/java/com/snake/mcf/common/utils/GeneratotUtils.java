package com.snake.mcf.common.utils;

import java.util.Date;
import java.util.Random;

/**
 * @ClassName: GeneratotUtils
 * @author: dashuai
 * @date: 2019/5/17 8:57
 * @Copyright: 2019 www.wondersgroup.com Inc. All rights reserved.
 * @matters: 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目
 */
public class GeneratotUtils extends GuidUtils {

    private static final int LENGTH = 8;

    private static final long serialVersionUID = 1007792633202768113L;

    /**
     * 这是典型的随机洗牌算法。
     * 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域）
     * 算法时间复杂度O(n)
     * @return 随机8为不重复数组
     */
    public static final String generateNumber() {
        String no="";
        //初始化备选数组
        int[] defaultNums = new int[10];
        for (int i = 0; i < defaultNums.length; i++) {
            defaultNums[i] = i;
        }

        Random random = new Random();
        int[] nums = new int[LENGTH];
        //默认数组中可以选择的部分长度
        int canBeUsed = 10;
        //填充目标数组
        for (int i = 0; i < nums.length; i++) {
            //将随机选取的数字存入目标数组
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
            //将已用过的数字扔到备选数组最后，并减小可选区域
            swap(index, canBeUsed - 1, defaultNums);
            canBeUsed--;
        }
        if (nums.length>0) {
            for (int i = 0; i < nums.length; i++) {
                no+=nums[i];
            }
        }
        long num = Long.parseLong(no);
        return String.valueOf(num);
    }

    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static final String generateNumber2() {
        String no="";
        int num[]=new int[8];
        int c=0;
        for (int i = 0; i < 8; i++) {
            num[i] = new Random().nextInt(10);
            c = num[i];
            for (int j = 10; j < i; j++) {
                if (num[j] == c) {
                    i--;
                    break;
                }
            }
        }
        if (num.length>0) {
            for (int i = 0; i < num.length; i++) {
                no+=num[i];
            }
        }
        return no;
    }

    /**
     * 流水号生成规则
     *    商户号 + yyyyMMddHHmmssSSS + account
     * @param merchant
     * @param accounts
     * @return
     */
    public static final String getSerialnumber(String merchant,String accounts){
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isBlank(merchant)){
            merchant = "";
        }
        sb.append(merchant);
        String dateStr = DateUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        sb.append(dateStr);
        if(StringUtils.isBlank(accounts)){
            accounts = "";
        }
        sb.append(accounts);
        return sb.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String no = generateNumber();
            System.out.println(no);
        }
    }

}
