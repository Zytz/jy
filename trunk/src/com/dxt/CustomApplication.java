package com.dxt;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;

import com.dxt.model.SearchOnlineQuestionBean;

public class CustomApplication extends Application
{
	private static final String VALUE = "";
    public int reward =0 ;//用于保存用户点击问题的奖励分数
	public int flag =0; //用于标志显示第几个Activity
    public boolean  firstLoadToHigh = true;
    private boolean firstLoad = true; //是否第一次加载问题列表，过滤产生的列表也属于第一次
    
    private   boolean islogin=false;
    
    
    private String value;
    private String username;
    
    private int grade=-1;
    
    private int subject=-1;
    
    private String questionId="";
    
   
    
	public boolean isFirstLoad() {
		return firstLoad;
	}

	public void setFirstLoad(boolean firstLoad) {
		this.firstLoad = firstLoad;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getSubject() {
		return subject;
	}

	public void setSubject(int subject) {
		this.subject = subject;
	}

	private SearchOnlineQuestionBean searchBean = new SearchOnlineQuestionBean();
	
	public SearchOnlineQuestionBean searchBeanForHigh = new SearchOnlineQuestionBean();
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // 初始化全局变量
    }
    
    
    
    @SuppressWarnings("static-access")
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


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isIslogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	public SearchOnlineQuestionBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(SearchOnlineQuestionBean searchBean) {
		this.searchBean = searchBean;
	}
	

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
    
    
}