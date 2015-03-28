package com.dxt.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 校验工具类
 * 
 * @author Administrator
 *
 */
public class ValidateUtil {
	/**
	 * 
	 * 判断字符串的有效性
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isValid(String src) {
		if (src == null || "".equals(src.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 判断集合的有效性
	 * 
	 * @param col
	 * @return
	 */
	public static boolean isValid(Collection col) {
		if (col == null || col.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符是否有效
	 */
	public static boolean isValid(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return true;
	}
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
		.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
