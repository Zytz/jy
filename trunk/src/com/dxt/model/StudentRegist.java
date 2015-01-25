package com.dxt.model;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
public class StudentRegist implements KvmSerializable{
	private String mobilename;
	private String yangzhengma;
	
	
	
	public String getMobilename() {
		return mobilename;
	}
	public void setMobilename(String mobilename) {
		this.mobilename = mobilename;
	}
	public String getYangzhengma() {
		return yangzhengma;
	}
	public void setYangzhengma(String yangzhengma) {
		this.yangzhengma = yangzhengma;
	}
	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		switch(arg0){
		case 0:
			return mobilename;
		case 1:
			return yangzhengma;
		}
		return null;
	}
	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
		// TODO Auto-generated method stub
		switch(index){
        case 0:
        	info.type=PropertyInfo.STRING_CLASS;//设置info type的类型
        	info.name="mobilename";
        break;
        case 1:
        	info.type=PropertyInfo.STRING_CLASS;
        	info.name="yangzhengma";
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
        	mobilename=value.toString();
        	break;
        case 1:
        	yangzhengma=value.toString();
        	break;
        default:
       		break;
	}
	
	}

}
