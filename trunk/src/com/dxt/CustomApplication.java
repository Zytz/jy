package com.dxt;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.dxt.model.SearchOnlineQuestionBean;

public class CustomApplication extends Application
{
    private static final String VALUE = "";
    
    private String value;
    
    private String grade="";
    
    private String subject="";
    
    public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private SearchOnlineQuestionBean searchBean;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
        setSearchBean(new SearchOnlineQuestionBean());
    }
    
    public void showDialog(View view){
    	MyImageView image = (MyImageView)view;
    	Intent intent = new Intent(getApplicationContext(),PreviewActivity.class);
    	intent.putExtra("uri", image.getUri());
		intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
    
    public void setValue(String value)
    {
        this.value = value;
    }
    
    public String getValue()
    {
        return value;
    }

	public SearchOnlineQuestionBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(SearchOnlineQuestionBean searchBean) {
		this.searchBean = searchBean;
	}
    
    
}