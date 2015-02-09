package com.dxt.util;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * У�鹤����
 * 
 * @author Administrator
 *
 */
public class ValidateUtil {
	/**
	 * 
	 * �ж��ַ�������Ч��
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
	 * �жϼ��ϵ���Ч��
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
	 * �ж��ַ��Ƿ���Ч
	 */
	public static boolean isValid(Object[] arr) {
		if (arr == null || arr.length == 0) {
			return false;
		}
		return true;
	}
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
		.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
	
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}
}
