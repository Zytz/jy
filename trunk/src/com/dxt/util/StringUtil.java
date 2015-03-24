package com.dxt.util;

;

public class StringUtil {
	/**
	 * 将字符串转换成数组,按照tag分割
	 */
	public static String[] str2Arr(String src, String tag) {
		if (ValidateUtil.isValid(src)) {
			// 这种方式做的太死，不能有其他方式做
			return src.split(tag);
		}
		return null;
	}

	/**
	 * 判读在values 数组是否含有指定value字符串
	 */
	public static boolean contains(String[] values, String value) {
		if (ValidateUtil.isValid(values)) {
			for (String s : values) {
				if (s.equals(value)) {
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
		String temp = "";
		if (ValidateUtil.isValid(value)) {
			for (String s : value) {
				temp = temp + s + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}

	public static String arr2Str(Object[] value) {
		String temp = "";
		if (ValidateUtil.isValid(value)) {
			for (Object s : value) {
				temp = temp + s + ",";
			}
			return temp.substring(0, temp.length() - 1);
		}
		return temp;
	}

	/**
	 * 获得字符串描述信息
	 */
	public static String getDescString(String str) {
		if (str != null && str.trim().length() > 30) {
			return str.substring(0, 30);
		}
		return str;
	}

	/**
	 * 获得性别
	 * 0：man
	 * 1:woman
	 */
	public static String int2StringOfGender(int gender) {
		if (gender == 0) {
			return "男";
		} else {
			return "女";
		}
	}
	/**
	 * 逆向获得性别
	 * 
	 * 0：man
	 * 1:woman
	 */
	public static int string2IntOfGender(String gender) {
		if (gender == "男") {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * 获得年级
	 * 小学以后 包含1、2、3、4、5、6年级 和小考
	 */
	public static String int2StringOfGrade(int grade) {
		String str="";
		switch (grade) {
		case 101:
			str= "小学";
			break;
		case 0:
			str="四年级";
			break;
		
		case 1:
			str= "五年级";
			break;
		
		case 2:
			str= "六年级";
			break;
		case 3:
			str="小考";
			break;
		case 4:
			str="七年级";
			break;
		case 5:
			str="八年级";
			break;
		case 6:
			str="九年级";
			break;
		case 7:
			str="中考";
			break;
		case 8:
			str="高一";
			break;
		case 9:
			str="高二";
			break;
		case 10:
			str="高三";
			break;
		case 11:
			str="高考";
			break;
		}
		return str;
	}
	/**
	 * 获得年级
	 */
	/*public static int string2IntOfGrade(String grade) {
		int grades=-1;
		switch (grade) {
		case "小学":
			grades= 101;
			break;
		case "四年级":
			grades=0;
			break;
		case "五年级":
			grades=1;
			break;
		case "六年级":
			grades=2;
			break;
		case "小考":
			grades=3;
			break;
		case "七年级":
			grades=4;
			break;
		case "八年级":
			grades=5;
			break;
		case "九年级":
			grades=6;
			break;
		case "中考":
			grades=7;
			break;
		case "高一":
			grades=8;
			break;
		case "高二":
			grades=9;
			break;
		case "高三":
			grades=10;
			break;
		case "高考":
			grades=11;
			break;
		}
		return grades;
	}*/
	/**
	 * 获得学科
	 * 小学以后 包含1、2、3、4、5、6年级 和小考
	 */
	public static String int2StringOfSubject(int subject) {
		String str="";
		switch (subject) {
		case 0:
			str="数学";
			break;
		
		case 1:
			str= "物理";
			break;
		
		case 2:
			str= "化学";
			break;
		case 3:
			str="英语";
			break;
		case 4:
			str="语文";
			break;
		}
		return str;
	}
	/**
	 * 获得年级
	 */
	/*public static int string2IntOfSubject(String subject) {
		int subjects=-1;
		switch (subject) {
		case "数学":
			subjects=0;
			break;
		case "物理":
			subjects=1;
			break;
		case "化学":
			subjects=2;
			break;
		case "英语":
			subjects=3;
			break;
		case "语文":
			subjects=4;
			break;
		}
		return subjects;
	}*/


}
