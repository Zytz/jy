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
	 */
	public static String int2StringOfGender(int gender) {
		if (gender == 0) {
			return "��";
		} else {
			return "Ů";
		}
	}

	/**
	 * ����꼶
	 */
	public static String int2StringOfGrade(int grade) {
		String str="";
		switch (grade) {
		case 0:
			str= "Сѧ";
			break;
		case 1:
			str= "�п�";
			break;
		case 2:
			str= "�߿�";
			break;
		}
		return str;
	}

}
