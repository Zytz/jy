package com.dxt;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchBooks extends Activity {
	final static String SERVICE_NS = "http://impl.service.ws.test.gary.com";
    final static String SERVICE_URL = "http://192.168.1.119:8080/testWebServiceCXF/BookService";
	private Spinner spinner;
	private Button search;
	private String book;
	private String[] books;
	private StringBuffer sb=new StringBuffer();
	private Handler handler =new UIHandler();
	private final class UIHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 1:
				Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_LONG).show();
				sb.delete(0, sb.length());
				break;
			}
		}
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchbook);
		books=getResources().getStringArray(R.array.MyBooks);
		search =(Button) findViewById(R.id.search);
		spinner =(Spinner) findViewById(R.id.books);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				book=books[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HttpTransportSE ht =new HttpTransportSE(SERVICE_URL);
						ht.debug=true;
						SoapObject request =new SoapObject(SERVICE_NS, "query");
						request.addProperty("name",book);
						SoapSerializationEnvelope envelope =new SoapSerializationEnvelope(SoapEnvelope.VER11);
						envelope.bodyOut=request;
						try {
							ht.call(null, envelope);
							if(envelope.getResponse() != null){
								Log.v("lll---","ok!!");
				                SoapObject result = (SoapObject) envelope.bodyIn;
				                for(int   i=0; i <result.getPropertyCount(); i++){
				                	SoapObject so = (SoapObject)result.getProperty(0);
				                	 sb.append("书名:"+so.getProperty("book_name").toString()+"\n");
				                     sb.append("简介: "+so.getProperty("book_info").toString()+"\n");
				                     sb.append("书价:"+so.getProperty("price").toString()+"\n"+"\n");
				                }
				                Message message =new Message();
				                message.what=1;
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
					
				}.start();;
			}
		});
	}
	
}
