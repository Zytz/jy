package com.dxt.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.dxt.R;

public class UserCenter extends Activity {

	private String TAG = "dxt";
	private ImageView img = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter);
		init();
		String[] user_={"�ҵ�����","�ҵĻش�","�ҵĻظ�"};
		
	}
	private void init() {
		img = (ImageView) findViewById(R.id.user_icon);
	}
}
