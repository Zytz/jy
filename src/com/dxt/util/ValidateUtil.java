 package com.dxt.util;

import java.util.Collection;


/**
 * 
 * У�鹤����
 * @author Administrator
 *
 */
public class ValidateUtil {
	/**
	 * 
	 * �ж��ַ�������Ч��
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
	 * �жϼ��ϵ���Ч��
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
	 * �ж��ַ��Ƿ���Ч
	 */
	public static boolean isValid(Object[] arr){
		if(arr== null || arr.length==0){
			return false;
		}
		return true;
	}
}



