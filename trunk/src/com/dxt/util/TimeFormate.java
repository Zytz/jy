package com.dxt.util;
/**
 * 自定义格式化视频时长类
 * @author liangfei
 *
 */
public class TimeFormate 
{
	 private int milliseconds;
	 public TimeFormate(int milliseconds) 
	 {
		this.milliseconds = milliseconds;
	 }
	 public String formatetime()
	 {
		  String hour=String.valueOf(milliseconds/3600000);
		  String minute=String.valueOf((milliseconds%3600000)/60000);
		  String second=String.valueOf(((milliseconds%3600000)%60000)/1000);
		  hour=deal(hour);
		  minute=deal(minute);
		  second=deal(second);
		  return hour+":"+minute+":"+second;
    }
	 private String deal(String time)
	 {
		 if(time.length()==1)
		  {
			 if(time.equals("0"))
				 time="00";
			 else
				 time="0"+time;
		  }
		 return time;
	 }
}
