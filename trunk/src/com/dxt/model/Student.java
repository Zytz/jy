package com.dxt.model;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
public class Student implements KvmSerializable{
	private String name;
	private String password;
	private int age;
	private String number;
	private String address;
	private String hobby;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0){
		case 0:
			return name;
		case 1:
			return password;
		case 2:
			return age;
		case 3:
			return number;
		case 4:
			return address;
		case 5:
			return hobby;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		 switch(index){
	        case 0:
	        	info.type=PropertyInfo.STRING_CLASS;//设置info type的类型
	        	info.name="name";
	        break;
	        case 1:
	        	info.type=PropertyInfo.STRING_CLASS;
	        	info.name="password";
	        	break;
	        case 2:
	        	info.type=PropertyInfo.INTEGER_CLASS;
	        	info.name="age";
	        	break;
	        case 3:
	        	info.type=PropertyInfo.STRING_CLASS;
	        	info.name="number";
	        	break;
	        case 4:
	        	info.type=PropertyInfo.STRING_CLASS;
	        	info.name="address";
	        	break;
	        case 5:
	        	info.type=PropertyInfo.STRING_CLASS;
	        	info.name="hobby";
	        	break;
	        default:
	        	break;
	        }
	}

	@Override
	public void setProperty(int index, Object value) {
		// TODO Auto-generated method stub
		 switch(index){
	        case 0:
	        	name=value.toString();
	        	break;
	        case 1:
	        	password=value.toString();
	        	break;
	        case 2:
	        	age=Integer.parseInt(value.toString());
	        	break;
	        case 3:
	        	number=value.toString();
	        	break;
	        case 4:
	        	address=value.toString();
	        	break;
	        case 5:
	        	hobby=value.toString();
	        	break;
	        default:
	        	break;
	        }
	}

}
