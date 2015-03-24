package com.dxt.util;

import java.util.HashMap;
import java.util.Map;


;

public class StringUtil {
	static Map<String,Integer> grades = new HashMap<String,Integer>();
	static Map<String,Integer> subject = new HashMap<String,Integer>();
	static{
		grades.put("ȫ��", -1);
		grades.put("Сѧ", 101);
		grades.put("���꼶", 4);
		grades.put("���꼶", 5);
		grades.put("���꼶", 6);
		grades.put("�п�", 7);
		grades.put("��һ", 8);
		grades.put("�߶�", 9);
		grades.put("����", 10);
		grades.put("�߿�", 11);
		
		subject.put("ȫ��",-1);
		subject.put("��ѧ", 0);
		subject.put("����",1);
		subject.put("��ѧ", 2);
		subject.put("Ӣ��", 3);
		subject.put("����", 4);
	}
	static Map<Integer,String> gradeReverse = new HashMap<Integer,String>();
	static Map<Integer,String> subjectReverse = new HashMap<Integer,String>();
	static{
		gradeReverse.put(-1,"ȫ��");
		gradeReverse.put(101, "Сѧ");
		gradeReverse.put(4,"���꼶");
		gradeReverse.put(5,"���꼶");
		gradeReverse.put(6,"���꼶");
		gradeReverse.put(7,"�п�");
		gradeReverse.put(8,"��һ");
		gradeReverse.put(9,"�߶�");
		gradeReverse.put(10,"����");
		gradeReverse.put(11,"�߿�");
		
		subjectReverse.put(-1, "ȫ��");
		subjectReverse.put(0, "��ѧ");
		subjectReverse.put(1, "����");
		subjectReverse.put(2, "��ѧ");
		subjectReverse.put(3, "Ӣ��");
		subjectReverse.put(4, "����");
				
	}
	
	/**
	 * ���ַ���ת��������,����tag�ָ�
	 */
	public static String[] str2Arr(String src, String tag) {
		if (ValidateUtil.isValid(src)) {
			// ���ַ�ʽ����̫����������������ʽ��
			return src.split(tag);
		}
		return null;
	}

	/**
	 * �ж���values �����Ƿ���ָ��value�ַ���
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
	 * ������任���ַ�����ʹ�á������� �ָ�
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
	 * ����ַ���������Ϣ
	 */
	public static String getDescString(String str) {
		if (str != null && str.trim().length() > 30) {
			return str.substring(0, 30);
		}
		return str;
	}

	/**
	 * ����Ա�
	 * 0��man
	 * 1:woman
	 */
	public static String int2StringOfGender(int gender) {
		if (gender == 0) {
			return "��";
		} else {
			return "Ů";
		}
	}
	/**
	 * �������Ա�
	 * 
	 * 0��man
	 * 1:woman
	 */
	public static int string2IntOfGender(String gender) {
		if (gender == "��") {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * ����꼶
	 * Сѧ�Ժ� ����1��2��3��4��5��6�꼶 ��С��
	 */
	public static String int2StringOfGrade(int grade) {
		
		return gradeReverse.get(grade);
	}

	
	/**
	 * ����꼶���
	 */
	public static Integer int2IDOfGrade(String grade) {
		
		return grades.get(grade);
	}
	
	/**
	 * ��ÿ�Ŀ
	 */
	public static String int2StringOfSubject(int subject) {
		
		return subjectReverse.get(subject);
	}
	
	/**
	 * ��ÿ�Ŀ���
	 */
	public static Integer int2IDOfSubject(String s) {
		
		return grades.get(s);
	}

}
