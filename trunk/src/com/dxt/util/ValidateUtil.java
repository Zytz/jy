 package com.dxt.util;

import java.util.Collection;


/**
 * 
 * 校验工具类
 * @author Administrator
 *
 */
public class ValidateUtil {
	/**
	 * 
	 * 判断字符串的有效性
	 * @param src
	 * @return
	 */
	public static boolean isValid(String src){
		if(src==null||"".equals(src.trim())){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 判断集合的有效性
	 * @param col
	 * @return
	 */
	public static boolean isValid(Collection col){
		if(col==null||col.isEmpty()){
			return false;
		}
		return true;
	}
	/**
	 * 判断字符是否有效
	 */
	public static boolean isValid(Object[] arr){
		if(arr== null || arr.length==0){
			return false;
		}
		return true;
	}
}



