package com.dxt;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ChooseGrade extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_grade_vw_item);
	}
	
}
