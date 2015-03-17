package com.dxt;

import android.app.Application;
import android.content.Intent;
import android.view.View;

import com.dxt.model.SearchOnlineQuestionBean;
import com.dxt.model.User;

public class CustomApplication extends Application
{
    private static final String VALUE = "";
    
    private String value;
    private User u;
    private String grade="";
    
    private String subject="";
    
    private String questionId="";
    
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