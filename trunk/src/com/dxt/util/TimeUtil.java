package com.dxt.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.text.format.DateFormat;

public class TimeUtil {
	/**
	 * 
	 * @param updateTime Date 类型
	 * @return spantime string 类型
	 * 
	 */
	public static String spanTime(Date updateTime){
		long upTime=updateTime.getTime();
		Date nowDate=new Date();
		long thisTime=nowDate.getTime();
		String span=" ";
		long spanTime=thisTime-upTime;
		if(spanTime<(long)60000){
			span=spanTime/1000+"秒前";
		}else if(spanTime>=(long)60000&&spanTime<3600000){
			span=spanTime/60000+"分钟前";
		}else if(spanTime>=3600000&&spanTime<86400000){
			span=spanTime/3600000+"小时前";
		}else{
			span=DateFormat.format("yyyy-MM-dd", updateTime).toString();
		}
		return span;
	}
	
	/**
	 * 用于获得指定格式的当前日期
	 * 
	 * @param format
	 *            字符串时间格式 eg:yyyy-MM-dd hh:mm:ss
	 * @return String 字符串时间
	 */
	public static String getCurrentDate(String format) {
		Date utilDate = new java.util.Date();
		SimpleDateFormat myFmt = new SimpleDateFormat(format);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// 获取中国的时区
		myFmt.setTimeZone(timeZoneChina);// 设置系统时区
		return myFmt.format(utilDate);

	}

}
