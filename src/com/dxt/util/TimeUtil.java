package com.dxt.util;

import java.util.Date;

public class TimeUtil {
	/**
	 * 
	 * @param updateTime Date ����
	 * @return spantime string ����
	 * 
	 */
	private String spanTime(Date updateTime){
		long upTime=updateTime.getTime();
		Date nowDate=new Date();
		long thisTime=nowDate.getTime();
		String span=" ";
		long spanTime=thisTime-upTime;
		if(spanTime<(long)60000){
			span=spanTime/1000+"��";
		}else if(spanTime>=(long)60000&&spanTime<3600000){
			span=spanTime/60000+"��";
		}else if(spanTime>=3600000&&spanTime<86400000){
			span=spanTime/3600000+"ʱ";
		}else{
			span=spanTime/86400000+"��";
		}
		return span;
	}

}
