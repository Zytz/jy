 package com.dxt.util;;

public class StringUtil {
	/**
	 * 将字符串转换成数组,按照tag分割
	 */
	public static String[] str2Arr(String src,String tag){
		if(ValidateUtil.isValid(src)){
			//这种方式做的太死，不能有其他方式做
			return src.split(tag);
		}
		return null;
	}
	/**
	 * 判读在values 数组是否含有指定value字符串
	 */
	public static boolean contains(String[] values,String value){
		if(ValidateUtil.isValid(values)){
			for(String s:values){
				if(s.equals(value)){
					return true;
				}
			}
			
		}
		return false;
	}
	/**
	 * 将数组变换成字符串，使用“，”号 分割
	 */
	public static String arr2Str(String[] value) {
		String temp="";
		if(ValidateUtil.isValid(value)){
			for(String s:value){
				temp=temp+s+",";
			}
			return temp.substring(0,temp.length()-1);
		}
		return temp;
	}
	public static String arr2Str(Object[] value) {
		String temp="";
		if(ValidateUtil.isValid(value)){
			for(Object s:value){
				temp=temp+s+",";
			}
			return temp.substring(0,temp.length()-1);
		}
		return temp;
	}

	/**
	 * 获得字符串描述信息
	 */
	public static String getDescString(String str){
		if(str!=null&& str.trim().length()>30){
			return str.substring(0,30);
		}
		return str;
	}
	
}
