package com.dxt;

import java.io.IOException;
import java.util.Random;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.model.Student;
import com.dxt.model.StudentRegist;

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
import android.widget.TextView;
import android.widget.Toast;

public class RegistFirst extends Activity {
	final static String SERVICE_NS = "http://impl.service.ws.test.gary.com";
	final static String SERVICE_URL = "http://210.40.65.242:8080/daxuetong/services/UserService?wsdl";
	final static String TAG = "dxt";

	private Button sendmobilenumber;
	private Button queren;
	
	private String mobilenumber;
	
	
	private String yangzhengma;
	private StudentRegist student = new StudentRegist();

	private String userInfo;
	private String faultMessage;
	private String retMessage;
	private Handler handler = new UIHander();

	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), "发送成功！",
						Toast.LENGTH_LONG).show();
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "发送失败！",
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
				if(mobilenumber.length()!=11){
					Toast.makeText(getApplicationContext(), "您输入的位数不对", 1).show();
				}
				yangzhengma = min + yanzhengmaRandom + "";
				mobilenumber=mobilenumber+yangzhengma;
				userInfo = JSON.toJSONString(mobilenumber);
				Log.v(TAG, userInfo);
				
				new Thread() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);
						ht.debug = true;
						SoapObject request = new SoapObject(SERVICE_NS, "registRemoteService");
						request.addProperty("userInfo", userInfo);
						SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
								SoapEnvelope.VER11);
						envelope.bodyOut = request;
						try {
							ht.call(null, envelope);
							if (envelope.getResponse() != null) {
								Log.v("lll---", "ok!!");
								SoapObject result = (SoapObject) envelope.bodyIn;
								String name = result.getProperty(0).toString();
								JSONObject ob = JSONObject.parseObject(name);
								int status = ob.getIntValue("status");
								retMessage = ob.getString("message");
								Log.v(TAG, status + "");
								Log.v(TAG, " retMessage :" + retMessage);
								Message message = new Message();
								if (status == 1) {
									message.what = 1;
								} else if (status == 0) {
									message.what = -1;
									faultMessage = ob.getString("message");

								}
								handler.sendMessage(message);
							} else {
								Toast.makeText(getApplicationContext(),
										"Connection failure", Toast.LENGTH_LONG)
										.show();
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
		
		
	queren=(Button) findViewById(R.id.regist);
	queren.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			yangzhengma = ((EditText) findViewById(R.id.yanzhemgma))
					.getText().toString().trim();
			if(yangzhengma.equals(retMessage)){
				
				Toast.makeText(getApplicationContext(), "验证码验证成功", 1).show();
			}else{
				Toast.makeText(getApplicationContext(), "服务器未开通，请开通", 1).show();
			}
		}
	});
	}

}
