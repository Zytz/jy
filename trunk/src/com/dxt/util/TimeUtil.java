package com.dxt.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.text.format.DateFormat;

public class TimeUtil {
	/**
	 * 
	 * @param updateTime Date ����
	 * @return spantime string ����
	 * 
	 */
	public static String spanTime(Date updateTime){
		long upTime=updateTime.getTime();
		Date nowDate=new Date();
		long thisTime=nowDate.getTime();
		String span=" ";
		long spanTime=thisTime-upTime;
		if(spanTime<(long)60000){
			span=spanTime/1000+"��ǰ";
		}else if(spanTime>=(long)60000&&spanTime<3600000){
			span=spanTime/60000+"����ǰ";
		}else if(spanTime>=3600000&&spanTime<86400000){
			span=spanTime/3600000+"Сʱǰ";
		}else{
			span=DateFormat.format("yyyy-MM-dd", updateTime).toString();
		}
		return span;
	}
	
	/**
	 * ���ڻ��ָ����ʽ�ĵ�ǰ����
	 * 
	 * @param format
	 *            �ַ���ʱ���ʽ eg:yyyy-MM-dd hh:mm:ss
	 * @return String �ַ���ʱ��
	 */
	public static String getCurrentDate(String format) {
		Date utilDate = new java.util.Date();
		SimpleDateFormat myFmt = new SimpleDateFormat(format);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");// ��ȡ�й���ʱ��
		myFmt.setTimeZone(timeZoneChina);// ����ϵͳʱ��
		return myFmt.format(utilDate);

	}

}
