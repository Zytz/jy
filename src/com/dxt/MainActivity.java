package com.dxt;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TabHost;

public class MainActivity extends Activity {
	private TabHost tabHost;
	private ImageButton askquestion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//无标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		askquestion=(ImageButton) this.findViewById(R.id.img_askquestion);
		
		askquestion.setOnClickListener(questionListener);
		
		tabHost = (TabHost) this.findViewById(R.id.TabHost01);
		tabHost.setup();

		tabHost.addTab(tabHost
				.newTabSpec("tab_1")
				.setContent(R.id.LinearLayout1)
				.setIndicator("TAB1",
						this.getResources().getDrawable(R.color.black)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_2")
				.setContent(R.id.LinearLayout2)
				.setIndicator("TAB2",
						this.getResources().getDrawable(R.color.blue)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_3")
				.setContent(R.id.LinearLayout3)
				.setIndicator("TAB3",
						this.getResources().getDrawable(R.color.red)));
		tabHost.setCurrentTab(1);
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
	 * 当前页面索引
	 */
	int i = 0;

	/**
	 * 显示下一个页面
	 */
	protected void showNext() {
		// 三元表达式控制3个页面的循环.
		tabHost.setCurrentTab(i = i == 2 ? i = 0 : ++i);
		Log.i("kennet", i + "");

	}

	/**
	 * 显示前一个页面
	 */
	protected void showPre() {
		// 三元表达式控制3个页面的循环.
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
}
