package com.dxt;

import android.app.Application;

public class CustomApplication extends Application
{
    private static final String VALUE = "Harvey";
    
    private String value;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
    }
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }
}