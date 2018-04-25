package com.kexin.dkt.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 * @auther shr
 * @date 2017/10/18
 * @time 9:13
 * @desc
 **/
public class DateUtils {
	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_YMD = "yyyy-MM-dd";

	public static String getCurrentTime() {
		return new DateTime().toString(DATE_FORMAT_DEFAULT);
	}

	public static String getCurrentDay() {
		return new DateTime().toString(DATE_FORMAT_YMD);
	}

	public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
	}

	public static String getCurrentDate(String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(new Date());
	}
	/**
	 * time 转Date
	 * @param time
	 * @return
	 */
	public static Date convertTimeToDate(long time){
		SimpleDateFormat format =  new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		Date date = null;
		String s = format.format(time);
		try {
			date = format.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 将时间字符串类型转为Date
	 * @param dateString
	 * @return
	 */
	public static Date converStrTimeToDate(String dateString){
		if(dateString.length()==10){
			dateString=dateString+" 00:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 将Date转为字符串时间
	 * @param date
	 * @return
	 */
	public static String converDateToStrTime(Date date){
		if (date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
			return sdf.format(date);
		}else {
			return null;
		}
	}

	/**
	 * 将Date转为字符串时间
	 * 格式：yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static String converDateToStrYMD(Date date){
		if (date!=null) {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YMD);
			return sdf.format(date);
		}else {
			return null;
		}
	}

	/**
	 * 格式化日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		if(null!=date){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return null;
		}
	}

	/**
	 * 日期天数加减
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addDay(Date date, int i) {
		if(null!=date){
			return new Date(date.getTime()+i*24*60*60*1000);
		}
		return null;
	}

	/**
	 * 日期月数加减
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addMonth(Date date, int i){
		if(null!=date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, i);
			return c.getTime();
		}
		return null;
	}

	/**
	 * 日期月数加减
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date addYear(Date date, int i){
		if(null!=date){
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.YEAR, i);
			return c.getTime();
		}
		return null;
	}

	/**
	 * 字符串转换成指定格式的date
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date str2Date(String date, String format){
		if(StringUtils.notEmpty(date)){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new Date();
		}else{
			return null;
		}
	}

	/**
	 * 计算时间相差的分钟数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Integer differMinutes(Date d1, Date d2){
		DateTime dt1 = new DateTime(d1);
		DateTime dt2 = new DateTime(d2);
		int minutes = Minutes.minutesBetween(dt1, dt2).getMinutes();
		return minutes;
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date,String format){
		if(date!=null){
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}else{
			return "";
		}
	}

	public static void main(String[] args) {
		System.out.println(format(addDay(new Date(),-1), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 根据传入的格式、传进的时间，减去月份，
	 * @param date
	 * @param month
	 * @param format
	 * @return
	 */
	public static String getSubtractionMonth(Date date,Integer month,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,-(month-1));//month下标从0开始
		Date nowDate = cal.getTime();
		return sdf.format(nowDate);
	}
}
