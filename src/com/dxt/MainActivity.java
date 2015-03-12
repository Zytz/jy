package com.dxt;

import com.dxt.model.SearchOnlineQuestionBean;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	private static final int REQUESTCODEFORCHOOSE=1;
	private TabHost tabHost;
	private ImageButton askquestion;

	private TextView txt_chooseGrade;
	
	private RadioButton main_footbar_news;
	private RadioButton main_footbar_question;
	private RadioButton main_footbar_tweet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//�ޱ���
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		askquestion=(ImageButton) this.findViewById(R.id.img_askquestion);
		
		txt_chooseGrade=(TextView) this.findViewById(R.id.circle_tv_title_name);
		txt_chooseGrade.setOnClickListener(chooseGradeListener);
		
		askquestion.setOnClickListener(questionListener);

		main_footbar_news =(RadioButton) this.findViewById(R.id.main_footbar_news);
		main_footbar_question =(RadioButton) this.findViewById(R.id.main_footbar_question);
		main_footbar_tweet =(RadioButton) this.findViewById(R.id.main_footbar_tweet);
		
		main_footbar_news.setOnClickListener(footerListener);
		main_footbar_question.setOnClickListener(footerListener);
		main_footbar_tweet.setOnClickListener(footerListener);
	
		tabHost = getTabHost();
		tabHost.setup();

		tabHost.addTab(tabHost
				.newTabSpec("tab_1")
				.setContent(new Intent(this,QuestionActivity.class))
				.setIndicator("�����",
						this.getResources().getDrawable(R.color.black)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_2")
				.setContent(new Intent(this,HighScoreQuestion.class))
				.setIndicator("�߷�����",
						this.getResources().getDrawable(R.color.blue)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_3")
				.setContent(new Intent(this,QuestionActivityList.class))
				.setIndicator("�����",
						this.getResources().getDrawable(R.color.red)));
		tabHost.setCurrentTab(0);
	}


	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//����ѡ���꼶activity
	
	private OnClickListener chooseGradeListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent toChooseGrade = new Intent(getApplicationContext(),ChooseGrade.class);
			startActivityForResult(toChooseGrade,REQUESTCODEFORCHOOSE);
		}
	};
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		SearchOnlineQuestionBean searchBean = ((CustomApplication)getApplication()).getSearchBean();
		if(resultCode==Activity.RESULT_OK&&requestCode==REQUESTCODEFORCHOOSE){
			Bundle bundle = data.getExtras();
			boolean canceled = false;
			canceled = bundle.getBoolean("cancel");
			if(!canceled){
				String grade = bundle.getString("grade");
				String subject = bundle.getString("subject");
				txt_chooseGrade.setText(grade+subject);
				searchBean.setGrade(grade);
				searchBean.setSubject(subject);
			}
		}
	}

	//���뵽�������ת
	private OnClickListener questionListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent toAskQuestionActivity=new Intent();
			toAskQuestionActivity.setClass(getApplicationContext(), com.dxt.view.CameraActivityTest.class);
			startActivity(toAskQuestionActivity);
		}
	};

	

	
	private OnClickListener footerListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
				case R.id.main_footbar_news:
					Toast.makeText(getApplicationContext(), "main_footbar_news", Toast.LENGTH_LONG).show();
					v.setPressed(true);
					break;
				case R.id.main_footbar_question:
					Toast.makeText(getApplicationContext(), "main_footbar_question", Toast.LENGTH_LONG).show();
					Intent toVideoActivity=new Intent();
					toVideoActivity.setClass(getApplicationContext(), com.dxt.view.MediaPlayerActivity.class);
					startActivity(toVideoActivity);
					break;
				case R.id.main_footbar_tweet:
					Toast.makeText(getApplicationContext(), "main_footbar_tweet", Toast.LENGTH_LONG).show();
					Intent intent =new Intent();
					intent.setClass(getApplicationContext(), com.dxt.view.UserCenter.class);
					startActivity(intent);
					break;
				default: break;
			}
		}
	};

}
