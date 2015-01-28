package com.dxt;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.model.User;
import com.dxt.util.ReturnMessage;
import com.dxt.util.WebPostUtil;

public class RegistFirst extends Activity {
	final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	final static String SERVICE_URL = "http://10.82.21.244:8080/daxuetong/services/UserService?wsdl";
	final static String TAG = "dxt";
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	
	private Button sendmobilenumber;
	private Button queren;

	private String mobilenumber;

	private String yangzhengma;

	private String userInfo;
	private Message message = new Message();
	private ReturnMessage retMessage;
	private User user =new User();
	private Handler handler = new UIHander();

	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SUCCESS:
				Toast.makeText(getApplicationContext(), retMessage.getMessage(),
						Toast.LENGTH_LONG).show();
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(), retMessage.getMessage(),
						Toast.LENGTH_LONG).show();
				break;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist_first);
		sendmobilenumber = (Button) findViewById(R.id.sendmobilenumber);
		sendmobilenumber.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mobilenumber = ((EditText) findViewById(R.id.mobilenumber))
						.getText().toString().trim();

				Random rand = new Random();
				int min = 100000;
				int yanzhengmaRandom = rand.nextInt(99999);
				if (mobilenumber.length() != 11) {
					Toast.makeText(getApplicationContext(), "您输入的位数不对", 1)
							.show();
				}
				yangzhengma = min + yanzhengmaRandom + "";
				mobilenumber = mobilenumber + yangzhengma;
				userInfo = JSON.toJSONString(mobilenumber);
				Log.v(TAG, userInfo);

				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						retMessage=WebPostUtil.getMessage(SERVICE_URL, "registRemoteService", userInfo);
						message.what=retMessage.getStatus();
						handler.sendMessage(message);
					}
				}.start();
			}
		});

		queren = (Button) findViewById(R.id.regist);
		queren.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				user.setEmail(((EditText)findViewById(R.id.email)).getText().toString().trim());
				user.setPassword((((EditText)findViewById(R.id.password)).getText().toString().trim()));
				userInfo =JSONObject.toJSONString(user);
				Log.v(TAG, userInfo);
				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						retMessage=WebPostUtil.getMessage(SERVICE_URL, "registByEmail", userInfo);
						message.what=retMessage.getStatus();
						handler.sendMessage(message);
					}
				}.start();
				/*
				 * yangzhengma = ((EditText) findViewById(R.id.yanzhemgma))
				 * .getText().toString().trim();
				 * if(yangzhengma.equals(retMessage)){
				 * Toast.makeText(getApplicationContext(), "验证码验证成功", 1).show();
				 * }else{ Toast.makeText(getApplicationContext(), "服务器未开通，请开通",
				 * 1).show(); }
				 */
			}
		});
	}

}
