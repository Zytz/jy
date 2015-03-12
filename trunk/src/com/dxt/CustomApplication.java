package com.dxt;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.dxt.model.SearchOnlineQuestionBean;

public class CustomApplication extends Application
{
    private static final String VALUE = "Harvey";
    
    private String value;
    
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