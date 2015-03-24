package com.dxt.util;

;

public class StringUtil {
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
		String str="";
		switch (grade) {
		case 101:
			str= "Сѧ";
			break;
		case 0:
			str="���꼶";
			break;
		
		case 1:
			str= "���꼶";
			break;
		
		case 2:
			str= "���꼶";
			break;
		case 3:
			str="С��";
			break;
		case 4:
			str="���꼶";
			break;
		case 5:
			str="���꼶";
			break;
		case 6:
			str="���꼶";
			break;
		case 7:
			str="�п�";
			break;
		case 8:
			str="��һ";
			break;
		case 9:
			str="�߶�";
			break;
		case 10:
			str="����";
			break;
		case 11:
			str="�߿�";
			break;
		}
		return str;
	}
	/**
	 * ����꼶
	 */
	/*public static int string2IntOfGrade(String grade) {
		int grades=-1;
		switch (grade) {
		case "Сѧ":
			grades= 101;
			break;
		case "���꼶":
			grades=0;
			break;
		case "���꼶":
			grades=1;
			break;
		case "���꼶":
			grades=2;
			break;
		case "С��":
			grades=3;
			break;
		case "���꼶":
			grades=4;
			break;
		case "���꼶":
			grades=5;
			break;
		case "���꼶":
			grades=6;
			break;
		case "�п�":
			grades=7;
			break;
		case "��һ":
			grades=8;
			break;
		case "�߶�":
			grades=9;
			break;
		case "����":
			grades=10;
			break;
		case "�߿�":
			grades=11;
			break;
		}
		return grades;
	}*/
	/**
	 * ���ѧ��
	 * Сѧ�Ժ� ����1��2��3��4��5��6�꼶 ��С��
	 */
	public static String int2StringOfSubject(int subject) {
		String str="";
		switch (subject) {
		case 0:
			str="��ѧ";
			break;
		
		case 1:
			str= "����";
			break;
		
		case 2:
			str= "��ѧ";
			break;
		case 3:
			str="Ӣ��";
			break;
		case 4:
			str="����";
			break;
		}
		return str;
	}
	/**
	 * ����꼶
	 */
	/*public static int string2IntOfSubject(String subject) {
		int subjects=-1;
		switch (subject) {
		case "��ѧ":
			subjects=0;
			break;
		case "����":
			subjects=1;
			break;
		case "��ѧ":
			subjects=2;
			break;
		case "Ӣ��":
			subjects=3;
			break;
		case "����":
			subjects=4;
			break;
		}
		return subjects;
	}*/


}
