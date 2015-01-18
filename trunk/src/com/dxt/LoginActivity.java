package com.dxt;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.model.User;

public class LoginActivity extends Activity {
	final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
    final static String SERVICE_URL = "http://210.40.65.202:8080/daxuetong/services/UserService?wsdl";
	final static String TAG="dxt";
    
    private static final int SUCCESS = 1;
	private static final int ERROR = -1;
	private String faultMessage;
	private String username;
	private String password;
	private Button login;
	private Button regist;
	private User user =new User();
	private String userInfo;
	private Handler handler = new UIHander();

	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SUCCESS:
				  Toast.makeText(getApplicationContext(), "µ«»Î≥…π¶£°", Toast.LENGTH_LONG).show(); 
				  Intent intent = new Intent();
				 /* intent.setClass(LoginActivity.this, SearchBooks.class);
				 * startActivity(intent);
				 */
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(), faultMessage, Toast.LENGTH_LONG).show();
				break;
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		login = (Button) findViewById(R.id.login);
		regist = (Button) findViewById(R.id.regist);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//EditText et=findViewById(R.id.u)
				username = ((EditText) findViewById(R.id.username)).getText().toString().trim();
				password = ((EditText) findViewById(R.id.password)).getText()
						.toString();
				user.setLoginName(username);
				user.setPassword(password);
			    userInfo = JSON.toJSONString(user);
				Log.v(TAG,userInfo);
				new Thread() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpTransportSE ht =new HttpTransportSE(SERVICE_URL);
						ht.debug=true;
						SoapObject request =new SoapObject(SERVICE_NS, "login");
						request.addProperty("userInfo", userInfo);
						SoapSerializationEnvelope envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.bodyOut=request;
						try {
							ht.call(null, envelope);
							if(envelope.getResponse() != null){
								Log.v("lll---","ok!!");
				                SoapObject result = (SoapObject) envelope.bodyIn;
				                String name = result.getProperty(0).toString();
				                JSONObject ob =JSONObject.parseObject(name);
				                int status = ob.getIntValue("status");
				                String retMessage=ob.getString("message");
				                Log.v(TAG, status+"");
				                Log.v(TAG," retMessage :" +retMessage);
				                Message message =new Message();
				                if(status==1){
				                	message.what=1;
				                	System.out.println(retMessage);
				                }else if(status==0){
				                	message.what=-1;
				                	faultMessage=ob.getString("message");

				                }
				                handler.sendMessage(message);
				            }else{
				            	Toast.makeText(getApplicationContext(), "Connection failure", Toast.LENGTH_LONG).show();
				            }
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (XmlPullParserException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
			}
		});
		regist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent();
				intent.setClass(LoginActivity.this,Regist.class);
				startActivity(intent);
			}
		});
	}
}
