package com.snake.mcf.common.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @ClassName:  DateUtils   
 * @author: hengoking
 * @date:   2018年12月29日 下午1:05:25   
 *     
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved. 
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	public static final String DF_YYYYMMDD = "yyyyMMdd";

	public static final String DF_YYYYMMDDHHMM = "yyyyMMddHHmm";
	
	public static final String DF_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static final String DF_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

	public static final String DF_YYMMDDHHMMSS = "yyMMddHHmmss";

	public static final String DF_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String DF_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	
	public static final String DF_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String DF_START = "000000";

	public static final String DF_END = "235959";

    public static final String DF_START_FMT = " 00:00:00";

    public static final String DF_END_FMT = " 23:59:59";

	/**
	 * 日期增加天操作
	 *
	 * @param dateStr
	 *            日期字符串
	 * @param fmtStr
	 *            格式化字符串
	 * @param days
	 *            增加天数，可以为负数
	 * @return
	 */
	public static String addDay(String dateStr, String fmtStr, int days) {
		SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		Date date;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			return dateStr;
		}
		DateTime dateTime = new DateTime(date.getTime());

		dateTime = dateTime.plusDays(days);
		return format.format(dateTime.toDate());
	}

	/**
	 * 日期增加天操作
	 * 
	 * @author: hengoking 
	 * @param dateStr
	 * @param days
	 * @return
	 */
	public static String addDay(String dateStr, int days) {
		return addDay(dateStr, DF_YYYYMMDD, days);
	}

	/**
	 * 日期增加天操作
	 * 
	 * @author: hengoking 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDay(Date date, int days) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.plusDays(days).toDate();
	}

	/**
	 * 时间增加分钟操作
	 * 
	 * @author: hengoking 
	 * @param dateStr
	 * @param minutes
	 * @return
	 */
	public static String addMinute(String dateStr, int minutes) {
		return addMinute(dateStr, DF_YYYYMMDDHHMM, minutes);
	}

	/**
	 * 时间增加分钟操作
	 * 
	 * @author: hengoking 
	 * @param dateStr
	 * @param fmtStr
	 * @param minutes
	 * @return
	 */
	public static String addMinute(String dateStr, String fmtStr, int minutes) {
		SimpleDateFormat format = new SimpleDateFormat(fmtStr);
		Date date;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			return dateStr;
		}
		return format.format(addMinute(date, minutes));
	}

	/**
	 * 时间增加分钟操作
	 * 
	 * @author: hengoking 
	 * @param date
	 * @param minutes
	 * @return
	 */
	public static Date addMinute(Date date, int minutes) {
		DateTime dateTime = new DateTime(date.getTime());
		return dateTime.plusMinutes(minutes).toDate();
	}

	/**
	 * 获取当前日期
	 *
	 * @return
	 */
	public static LocalDateTime getNowDate(){
		return LocalDateTime.now();
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static LocalDateTime getYesterdayDate(){
		return LocalDateTime.now().plusDays(-1);
	}

	/**
	 * 获取当前时间
	 * 
	 * @author: hengoking 
	 * @param pattern
	 * @return
	 */
	public static String getNow(String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime now = LocalDateTime.now();
		return now.format(formatter);
	}

	/**
	 * 获取当前时间
	 * 
	 * @author: hengoking 
	 * @return
	 */
	public static String getNow() {
		return getNow(DF_YYYY_MM_DD_HH_MM_SS);
	}

    /**
     * 获取今日开始
     *
     * @return
     *      例如：2019-07-17 00:00:00
     */
    public static String getNowStart() {
        return getNow(DF_YYYY_MM_DD) + DF_START_FMT;

    }

    /**
     * 获取今日开始
     *
     * @return
     *      例如：2019-07-17 23:59:59
     */
    public static String getNowEnd() {
        return getNow(DF_YYYY_MM_DD) + DF_END_FMT;
    }

	/**
	 * 获取距离指定日期 指定天数的日期
	 * @param dateTime
	 * @param pattern
	 * @param days
	 * @return
	 */
	public static String getPlusday(LocalDateTime dateTime,String pattern,int days){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime localDateTime = dateTime.plusDays(days);
		return localDateTime.format(formatter);
	}

	/**
	 * 获取距离指定日期 指定天数的日期开始
	 * @param dateTime
	 * @param days
	 * @return
	 */
	public static String getPlusdayStart(LocalDateTime dateTime,int days){
		return getPlusday(dateTime,DF_YYYY_MM_DD,days) + DF_START_FMT;
	}

	/**
	 * 获取距离指定日期 指定天数的日期结束
	 * @param dateTime
	 * @param days
	 * @return
	 */
	public static String getPlusdayEnd(LocalDateTime dateTime,int days){
		return getPlusday(dateTime,DF_YYYY_MM_DD,days) + DF_END_FMT;
	}

	/**
	 * 获取距离今日指定天数的日期
	 *
	 * @param pattern
	 * @param days
	 * @return
	 */
	public static String getNowPlusday(String pattern,int days){
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime now = LocalDateTime.now();
		//日期计算 加几天
		LocalDateTime localDateTime = now.plusDays(days);
		return localDateTime.format(formatter);*/
		return getPlusday(LocalDateTime.now(),pattern,days);
	}

	/**
	 * 返回获取距离今日指定日期开始
	 *
	 * @param days
	 * @return
	 */
	public static String getNowPlusdayStart(int days){
		return getNowPlusday(DF_YYYY_MM_DD, days) + DF_START_FMT;
	}

	/**
	 * 返回获取距离今日指定日期结束
	 *
	 * @param days
	 * @return
	 */
	public static String getNowPlusdayEnd(int days){
		return getNowPlusday(DF_YYYY_MM_DD, days) + DF_END_FMT;
	}

	/**
	 * 获取昨天日期
	 *
	 * @param pattern
	 * @return
	 */
	public static String getYesterday(String pattern){
		/*DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateTime now = LocalDateTime.now();
		//日期计算 加几天
		LocalDateTime yesterday = now.plusDays(-1);
		return yesterday.format(formatter);*/
		return getNowPlusday(pattern,-1);
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static String getYesterday(){
		return getYesterday(DF_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static String getYesterdayStart(){
		return getYesterday(DF_YYYY_MM_DD) + DF_START_FMT;
	}

	/**
	 * 获取昨天日期
	 *
	 * @return
	 */
	public static String getYesterdayEnd(){
		return getYesterday(DF_YYYY_MM_DD) + DF_END_FMT;
	}

	/**
	 * 按照指定字符串转成Date
	 * 
	 * @author: hengoking 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateStr, String pattern) {
		try {
			return parseDate(dateStr, new String[]{pattern});
		} catch (ParseException var3) {
			return null;
		}
	}
	
	/**
	 * 按照yyyy-MM-dd HH:mm:ss字符串转成Date
	 * 
	 * @author: hengoking 
	 * @param dateStr
	 * @return
	 */
	public static Date parseDate(String dateStr) {
		return parseDate(dateStr,DF_YYYY_MM_DD_HH_MM_SS);
	}

	public static Date getDateOfWeek(Date date, int dayOfWeek) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(truncate(date, 5));
		calendar.set(7, dayOfWeek);
		return calendar.getTime();
	}

	public static Date getDateOfMonth(Date date, int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(truncate(date, 5));
		calendar.set(5, dayOfMonth);
		return calendar.getTime();
	}

	/**
	 * date按照指定格式转成字符串
	 * 
	 * @author: hengoking 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return (new SimpleDateFormat(pattern)).format(date);
	}
	
	/**
	 * date按照指yyyy-MM-dd HH:mm:ss转成字符串
	 * 
	 * @author: hengoking 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date,DF_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 返回两个日期之间 相差月数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	/*public static int getMonthSpace(Date date1 , Date date2){
		int result = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

		return result == 0 ? 1 : Math.abs(result);
	}*/

	/**
	 * 返回两个日期之间 相差天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int getDaySpace(Date date1 , Date date2){
		int result = 0;

		Calendar calst = Calendar.getInstance();
		Calendar caled = Calendar.getInstance();

		calst.setTime(date1);
		caled.setTime(date2);

		//设置时间为0时
		calst.set(Calendar.HOUR_OF_DAY, 0);
		calst.set(Calendar.MINUTE, 0);
		calst.set(Calendar.SECOND, 0);

		caled.set(Calendar.HOUR_OF_DAY, 0);
		caled.set(Calendar.MINUTE, 0);
		caled.set(Calendar.SECOND, 0);

		//得到两个日期相差的天数
		//int days = ((int)(caled.getTime().getTime()/1000)-(int)(calst.getTime().getTime()/1000))/3600/24;
		long t1 = caled.getTime().getTime();
		long t2 = calst.getTime().getTime();
		long l = (t1 - t2) / (1000 * 3600 * 24);
		return (int)l;
	}
	
	public static void main(String[] args) {
		Date end = DateUtils.addDay(new Date(), -2);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(end));
	}

}
