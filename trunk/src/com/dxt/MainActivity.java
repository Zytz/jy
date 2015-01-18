package com.dxt;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.RadialGradient;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	private TabHost tabHost;
	private ImageButton askquestion;
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
				.setContent(new Intent(this,IssueActivity.class))
				.setIndicator("�����",
						this.getResources().getDrawable(R.color.black)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_2")
				.setContent(new Intent(this,HighScoreIssue.class))
				.setIndicator("�߷�����",
						this.getResources().getDrawable(R.color.blue)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_3")
				.setContent(new Intent(this,ProblemList.class))
				.setIndicator("�����",
						this.getResources().getDrawable(R.color.red)));
		tabHost.setCurrentTab(0);
	}

	@SuppressWarnings("deprecation")
	private GestureDetector detector = new GestureDetector(
			new GestureDetector.SimpleOnGestureListener() {

				@Override
				public boolean onFling(MotionEvent e1, MotionEvent e2,
						float velocityX, float velocityY) {
					if ((e2.getRawX() - e1.getRawX()) > 80) {
						showNext();
						return true;
					}

					if ((e1.getRawX() - e2.getRawX()) > 80) {
						showPre();
						return true;
					}
					return super.onFling(e1, e2, velocityX, velocityY);
				}

			});

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * ��ǰҳ������
	 */
	int i = 0;

	/**
	 * ��ʾ��һ��ҳ��
	 */
	protected void showNext() {
		// ��Ԫ���ʽ����3��ҳ���ѭ��.
		tabHost.setCurrentTab(i = i == 2 ? i = 0 : ++i);
		Log.i("kennet", i + "");

	}

	/**
	 * ��ʾǰһ��ҳ��
	 */
	protected void showPre() {
		// ��Ԫ���ʽ����3��ҳ���ѭ��.
		tabHost.setCurrentTab(i = i == 0 ? i = 2 : --i);

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
					break;
				case R.id.main_footbar_question:
					Toast.makeText(getApplicationContext(), "main_footbar_question", Toast.LENGTH_LONG).show();
					break;
				case R.id.main_footbar_tweet:
					Toast.makeText(getApplicationContext(), "main_footbar_tweet", Toast.LENGTH_LONG).show();
					break;
				default: break;
			}
		}
	};
}
