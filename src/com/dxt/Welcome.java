package com.dxt;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

public class Welcome extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
		th_wait.start();
	}

	Thread th_wait = new Thread() {
		public void run() {
			try {
				th_wait.sleep(2000);
				th_wait1.start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
	};
	Thread th_wait1 = new Thread() {
		public void run() {
			Intent it = new Intent();
			it.setClass(getApplicationContext(), MainActivity.class);
			startActivity(it);
			finish();
		};
	};

	protected void onDestroy() {
		super.onDestroy();
	};
}
