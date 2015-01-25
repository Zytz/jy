package com.dxt;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
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
import android.widget.TextView;
import android.widget.Toast;

import com.dxt.model.Student;


public class Regist extends Activity {
	final static String SERVICE_NS = "http://impl.service.ws.test.gary.com";
    final static String SERVICE_URL = "http://210.40.65.242:8080/testWebServiceCXF/StudentService";
	private String name;
	private String password;
	private int age;
	private String number;
	private String address;
	private String hobby;
	private Button regist;
	private Student student=new Student();
	private Handler handler = new UIHander();

	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				Toast.makeText(getApplicationContext(), "×¢²á³É¹¦£¡", Toast.LENGTH_LONG).show();
				Intent intent =new Intent();
				intent.setClass(Regist.this, SearchBooks.class);
				startActivity(intent);
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "×¢²áÊ§°Ü£¡", Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);
		regist=(Button) findViewById(R.id.regist);
		regist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name=((TextView)findViewById(R.id.username)).getText().toString();
				password=((TextView)findViewById(R.id.password)).getText().toString();
				age=Integer.parseInt(((TextView)findViewById(R.id.age)).getText().toString());
				number=((TextView)findViewById(R.id.number)).getText().toString();
				address=((TextView)findViewById(R.id.address)).getText().toString();
				hobby=((TextView)findViewById(R.id.hobby)).getText().toString();
				student.setName(name);
				student.setPassword(password);
				student.setAge(age);
				student.setNumber(number);
				student.setAddress(address);
				student.setHobby(hobby);
				new Thread() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpTransportSE ht =new HttpTransportSE(SERVICE_URL);
						ht.debug=true;
						SoapObject request =new SoapObject(SERVICE_NS, "save");
						PropertyInfo info =new PropertyInfo();
						info.setName("student");
						info.setValue(student);
						info.setType(student.getClass());
						request.addProperty(info);
						SoapSerializationEnvelope envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.bodyOut=request;
						envelope.addMapping(SERVICE_NS, "Student", info.getClass());
						try {
							ht.call(null, envelope);
							if(envelope.getResponse() != null){
								Log.v("lll---","ok!!");
				                SoapObject result = (SoapObject) envelope.bodyIn;
				                String name = result.getProperty(0).toString();
				                Message message =new Message();
				                if(name.equals("SUCCESS")){
				                	message.what=1;
				                }else if(name.equals("ERROR")){
				                	message.what=-1;
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
	}
	
}
