package com.dxt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.dxt.util.ImageUtil;

public class PreviewActivity extends Activity {

	private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.preview_activity);
		Intent intent = getIntent();
		String uri = intent.getStringExtra("uri");
		imageView = (ImageView) findViewById(R.id.prviewImage);
		ImageUtil.LoadImage(getApplicationContext(), uri, imageView);
	}
	
}
