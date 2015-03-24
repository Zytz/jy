package com.dxt;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.dxt.model.SearchOnlineQuestionBean;
import com.dxt.model.User;

public class CustomApplication extends Application
{
    private static final String VALUE = "";
    
    
    
    private   boolean islogin=false;
    
    
    private String value;
    private String username;
    
    private int grade=-1;
    
    private int subject=-1;
    
    private String questionId="";
    
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

	private SearchOnlineQuestionBean searchBean;
    
    @Override
    public void onCreate()
    {
        super.onCreate();
        setValue(VALUE); // ��ʼ��ȫ�ֱ���
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