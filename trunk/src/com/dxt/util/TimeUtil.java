package com.dxt.util;

import java.util.Date;

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

}
