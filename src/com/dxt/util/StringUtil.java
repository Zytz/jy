package com.dxt.util;

import java.util.HashMap;
import java.util.Map;


;

public class StringUtil {
	static Map<String,Integer> grades = new HashMap<String,Integer>();
	static Map<String,Integer> subject = new HashMap<String,Integer>();
	static{
		grades.put("全部", -1);
		grades.put("小学", 101);
		grades.put("七年级", 4);
		grades.put("八年级", 5);
		grades.put("九年级", 6);
		grades.put("中考", 7);
		grades.put("高一", 8);
		grades.put("高二", 9);
		grades.put("高三", 10);
		grades.put("高考", 11);
		
		subject.put("全部",-1);
		subject.put("数学", 0);
		subject.put("物理",1);
		subject.put("化学", 2);
		subject.put("英语", 3);
		subject.put("语文", 4);
	}
	static Map<Integer,String> gradeReverse = new HashMap<Integer,String>();
	static Map<Integer,String> subjectReverse = new HashMap<Integer,String>();
	static{
		gradeReverse.put(-1,"全部");
		gradeReverse.put(101, "小学");
		gradeReverse.put(4,"七年级");
		gradeReverse.put(5,"八年级");
		gradeReverse.put(6,"九年级");
		gradeReverse.put(7,"中考");
		gradeReverse.put(8,"高一");
		gradeReverse.put(9,"高二");
		gradeReverse.put(10,"高三");
		gradeReverse.put(11,"高考");
		
		subjectReverse.put(-1, "全部");
		subjectReverse.put(0, "数学");
		subjectReverse.put(1, "地理");
		subjectReverse.put(2, "化学");
		subjectReverse.put(3, "英语");
		subjectReverse.put(4, "语文");
				
	}
	
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
		
		return gradeReverse.get(grade);
	}

	
	/**
	 * 获得年级编号
	 */
	public static Integer int2IDOfGrade(String grade) {
		
		return grades.get(grade);
	}
	
	/**
	 * 获得科目
	 */
	public static String int2StringOfSubject(int subject) {
		
		return subjectReverse.get(subject);
	}
	
	/**
	 * 获得科目编号
	 */
	public static Integer int2IDOfSubject(String s) {
		
		return grades.get(s);
	}

}
