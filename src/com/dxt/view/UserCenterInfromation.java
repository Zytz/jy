package com.dxt.view;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dxt.R;
import com.dxt.model.CommonListViewModel;

public class UserCenterInfromation extends Activity {

	private String TAG = "dxt";
	private TextView toLogin;
	
	private ImageView img = null;
	
	private ListView lv_usercenter;
	private List<CommonListViewModel> data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter);
		
	}
	private void init() {
	}
	
}
