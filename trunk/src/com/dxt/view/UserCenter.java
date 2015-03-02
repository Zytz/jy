package com.dxt.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.LoginActivity;
import com.dxt.R;

public class UserCenter extends Activity {

	private String TAG = "dxt";
	private TextView toLogin;
	private ImageView img = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter);
		init();
		String[] user_={"我的提问","我的回答","我的回复"};
		
	}
	private void init() {
		img = (ImageView) findViewById(R.id.user_icon);
		toLogin=(TextView) this.findViewById(R.id.user_notlogin);
		
		toLogin.setOnClickListener(toLoginListener);
	}
	OnClickListener toLoginListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent tologin_intent=new Intent();
			tologin_intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(tologin_intent);
		}
	};
}
