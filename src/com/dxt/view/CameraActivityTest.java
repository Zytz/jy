package com.dxt.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dxt.CustomApplication;
import com.dxt.MainActivity;
import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.util.ReturnMessage;
import com.dxt.util.WebPostUtil;

public class CameraActivityTest extends Activity {

	private static final String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";;
	private final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private String TAG = "dxt";

	private ImageView img = null;
	
	private TextView text = null;
	private TextView onlinequesion_submit;
	
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
	private boolean canSubmit=false;
	
	private Message message = new Message();
	private ReturnMessage retMessage;
	private CustomApplication app;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cameraactivitytest);
		init();
		Log.i(TAG, "" + Environment.getExternalStorageDirectory());
	}

	private void init() {
		// TODO Auto-generated method stub
		// creama = (Button) findViewById(R.id.btn_creama);
		app = (CustomApplication) getApplication();
		String session=app.getValue();
		
		img = (ImageView) findViewById(R.id.img_camera);
		img.setOnClickListener(listener);
		text = (TextView) findViewById(R.id.text);
		onlinequesion_submit=(TextView)findViewById(R.id.onlinequesion_create);
		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// ��ѡ������ʱ����
			startPhotoZoom(Uri.fromFile(tempFile));
			break;
		case PHOTO_REQUEST_GALLERY:// ��ѡ��ӱ��ػ�ȡͼƬʱ
			// ���ǿ��жϣ������Ǿ��ò����������¼��õ�ʱ��㲻�ᱨ�쳣����ͬ
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		case PHOTO_REQUEST_CUT:// ���صĽ��
			if (data != null)
				// setPicToView(data);
				sentPicToNext(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// ָ������������պ���Ƭ�Ĵ���·��
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);

		}
	};
	OnClickListener submitListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(canSubmit){
				//to submit
				if(app.getValue()=="Harvey"){
					//���ȵ�¼
					th.start();
					Toast.makeText(getApplicationContext(), "���ȵ�¼", 0).show();
				}else{
					Intent intent_return=new Intent();
					intent_return.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent_return);
					th.start();
				}
			}else{
				Toast.makeText(getApplicationContext(), "δ��ȡ��ͼƬ����Ϣ", 0).show();
			}
		}
	};
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// cropΪtrue�������ڿ�����intent��������ʾ��view���Լ���
		intent.putExtra("crop", "true");

		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY �Ǽ���ͼƬ�Ŀ��
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// �����м��ú��ͼƬ���ݵ���һ��������
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo == null) {
				// img.setImageResource(R.drawable.abc_ic_clear_normal);
				Log.v(TAG, "photo is null");
			} else {
				img.setImageBitmap(photo);
				// �����ı�����Ϊ ͼƬ����·��������
				onlinequesion_submit.setOnClickListener(submitListener);
				canSubmit=true;
			}

			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photodata = baos.toByteArray();
				System.out.println(photodata.toString());
				// Intent intent = new Intent();
				// intent.setClass(RegisterActivity.this, ShowActivity.class);
				// intent.putExtra("photo", photodata);
				// startActivity(intent);
				// finish();
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	/*
	 * 1:��ɸ������ݿ�
	 * 2����ɽ������ύ����������
	 * 
	 */
	Thread th=new Thread(){
		public void run() {
			uploadOnlineQuestion();
		};
	};
	private void uploadOnlineQuestion() {
		

		try {
			String imageViewPath=tempFile.getAbsolutePath();
			FileInputStream fis = new FileInputStream(imageViewPath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // ����Base64����
			String methodName = "uploadImage";
			Log.i(TAG, methodName+" "+getPhotoFileName()+" "+uploadBuffer);
			connectWebService(methodName, getPhotoFileName(), uploadBuffer); // ����webservice
			//retMessage=WebPostUtil.getMessage(StringConstant.SERVICE_URL, methodName, uploadBuffer);
			 //retMessage.getStatus();
			
			Log.i("connectWebService", retMessage.getMessage());
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 private boolean connectWebService(String methodName,String fileName, String imageBuffer) {  
		// TODO Auto-generated method stub
		//String namespace = "http://134.192.44.105:8080/SSH2/service/IService"; // �����ռ䣬���������˵ýӿڣ�ע����׺û��
		//String namespace = StringConstant.SERVICE_URL+"service"; 																		// .wsdl��
		// ��������������x-fireʵ��webservice�ӿڵ�
		//String url = "http://134.192.44.105:8080/SSH2/service/IService"; // ��Ӧ��url
		// ���¾��� ���ù����ˣ������׵Ļ� �뿴���webservice�ĵ�
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("filename", fileName); // ����1 ͼƬ��
		soapObject.addProperty("image", imageBuffer); // ����2 ͼƬ�ַ���
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		//envelope.dotNet = false;
		//envelope.setOutputSoapObject(soapObject);
		envelope.bodyOut = soapObject;
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		try {
			httpTranstation.call(null, envelope); // ��һ���ڴ����
			if(envelope.getResponse() != null){
				Log.v("lll---", "ok!!");
				SoapObject result = (SoapObject) envelope.bodyIn;
				String name = result.getProperty(0).toString();
				Log.i(TAG, name);
				return true;
			}else{
				//
				Log.i(TAG, "Connection failure");
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
