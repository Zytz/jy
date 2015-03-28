package com.dxt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
	
	private CustomApplication app;

	@SuppressLint("HandlerLeak")
	private final class UIHander extends Handler {

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			
			case SUCCESS:
				//登录成功之后，保存user对象
				th_user.start();
				//保存用户登录状态
				app.setIslogin(true);
				//保存用户名
				app.setUsername(username);
				Intent intentReturn = new Intent();
				intentReturn
						.setClass(getApplicationContext(), UserCenter.class);
				setResult(RESULT_OK, intentReturn);
				
				
				finish();
				Toast.makeText(getApplicationContext(),
						app.getValue() + "链接成功", Toast.LENGTH_LONG)
						.show();
				
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(),
						retMessage.getMessage() + "和服务器没有链接", Toast.LENGTH_LONG)
						.show();
				break;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		app = (CustomApplication) getApplication(); // 获得CustomApplication对象
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
				
				if (!ValidateUtil.isValid(username)
						|| !ValidateUtil.isValid(password)) {
					Toast.makeText(getApplicationContext(),
							R.string.login_label_usernameOrPasswordIsNotNull, 0)
							.show();
				}else if(ValidateUtil.isEmail(username)){
					user.setEmail(username);
					user.setPassword(password);
					userInfo = JSON.toJSONString(user);
					Log.v(TAG, userInfo);
					thLogin.start();
				}
				else if(ValidateUtil.isMobileNO(username)){
					user.setMobilePhone(username);
					user.setPassword(password);
					userInfo = JSON.toJSONString(user);
					Log.v(TAG, userInfo);
					thLogin.start();
				}
			}
		});
		

	}
	@SuppressWarnings("deprecation")
	protected void onDestroy() {
		super.onDestroy();
		if(thLogin.isAlive()){
			thLogin.stop();
			thLogin.destroy();
		}
		if(th_user.isAlive()){
			try {
				th_user.wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			th_user.stop();
			th_user.destroy();
		}
	};
	Thread thLogin=new Thread(){
		public void run() {
			retMessage = WebPostUtil.getMessage(SERVICE_URL,
					"login", userInfo);
			message.what = retMessage.getStatus();
			handler.sendMessage(message);	
		};
	};
	Thread th_user=	new Thread(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			retMessage = WebPostUtil.getMessage(SERVICE_URL1,
					"UseCenterInformation", username);
			app.setValue(retMessage.getMessage());
		}
	};
}
