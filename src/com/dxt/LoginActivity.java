package com.dxt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.dxt.constant.StringConstant;
import com.dxt.model.User;
import com.dxt.util.ReturnMessage;
import com.dxt.util.ValidateUtil;
import com.dxt.util.WebPostUtil;
import com.dxt.view.UserCenter;

public class LoginActivity extends Activity {
	final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	final static String SERVICE_URL = StringConstant.SERVICE_URL+"services/UserService?wsdl";
	final static String SERVICE_URL1=StringConstant.SERVICE_URL+"services/UserCenterService?wsdl";
	final static String TAG = "dxt";
	private SharedPreferences preference;
	private SharedPreferences.Editor editor;
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	private String username;
	private String password;
	private Button login;
	private Button toRegist;
	private Message message = new Message();
	private ReturnMessage retMessage;
	private User user = new User();
	private String userInfo = null;
	private Handler handler = new UIHander();
	private Thlogin thLogin;
	private CustomApplication app;

	@SuppressLint("HandlerLeak")
	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			
			case SUCCESS:
				//��¼�ɹ�֮�󣬱���user����
				app.setValue(retMessage.getData());
				//�����û���¼״̬
				app.setIslogin(true);
				//�����û���
				app.setUsername(username);
				preference = getSharedPreferences("dxtUser", MODE_PRIVATE);
				editor = preference.edit();
				editor.putString("username", app.getUsername());
				editor.putString("value", app.getValue());
				editor.putBoolean("isLogin", true);
				editor.commit();
				Intent intentReturn = new Intent();
				intentReturn
						.setClass(getApplicationContext(), UserCenter.class);
				setResult(RESULT_OK, intentReturn);
				finish();
				Toast.makeText(getApplicationContext(), "���ӳɹ�", Toast.LENGTH_LONG)
						.show();
				
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(),
						retMessage.getMessage() + "�ͷ�����û������", Toast.LENGTH_LONG)
						.show();
				try {
					thLogin.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		app = (CustomApplication) getApplication(); // ���CustomApplication����
		toRegist = (Button) findViewById(R.id.toregist_button);

		toRegist.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent toResistIntent = new Intent();
				toResistIntent.setClass(LoginActivity.this, RegistFirst.class);
				startActivity(toResistIntent);
			}
		});

		login = (Button) findViewById(R.id.signin_button);

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = ((EditText) findViewById(R.id.username)).getText()
						.toString().trim();
				password = ((EditText) findViewById(R.id.password)).getText()
						.toString().trim();
				thLogin = new Thlogin();
				if (!ValidateUtil.isValid(username)
						|| !ValidateUtil.isValid(password)) {
					Toast.makeText(getApplicationContext(),
							R.string.login_label_usernameOrPasswordIsNotNull, 0)
							.show();
				}else if(ValidateUtil.isEmail(username)){
					user.setEmail(username);
					user.setPassword(password);
					userInfo = JSON.toJSONString(user);
					thLogin.start();
				}
				else if(ValidateUtil.isMobileNO(username)){
					user.setMobilePhone(username);
					user.setPassword(password);
					userInfo = JSON.toJSONString(user);
					thLogin.start();
				}else{
					Toast.makeText(getApplicationContext(),
							R.string.login_label_usernameOrPasswordIsNotValid, 0)
							.show();
				}
			}
		});
	}
	
	private class Thlogin extends Thread{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			retMessage = WebPostUtil.getMessage(SERVICE_URL,
					"login", userInfo);
			Log.v("dxt",retMessage.toString());
			message.what = retMessage.getStatus();
			handler.sendMessage(message);
		}
		
	}
	
}
