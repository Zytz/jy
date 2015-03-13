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

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
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
		case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile));
			break;
		case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
			// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		case PHOTO_REQUEST_CUT:// 返回的结果
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
			// 指定调用相机拍照后照片的储存路径
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
					//请先登录
					th.start();
					Toast.makeText(getApplicationContext(), "请先登录", 0).show();
				}else{
					Intent intent_return=new Intent();
					intent_return.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent_return);
					th.start();
				}
			}else{
				Toast.makeText(getApplicationContext(), "未获取到图片的信息", 0).show();
			}
		}
	};
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片传递到下一个界面上
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo == null) {
				// img.setImageResource(R.drawable.abc_ic_clear_normal);
				Log.v(TAG, "photo is null");
			} else {
				img.setImageBitmap(photo);
				// 设置文本内容为 图片绝对路径和名字
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

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}
	/*
	 * 1:完成更新数据库
	 * 2：完成将数据提交到服务器中
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
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // 进行Base64编码
			String methodName = "uploadImage";
			Log.i(TAG, methodName+" "+getPhotoFileName()+" "+uploadBuffer);
			connectWebService(methodName, getPhotoFileName(), uploadBuffer); // 调用webservice
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
		//String namespace = "http://134.192.44.105:8080/SSH2/service/IService"; // 命名空间，即服务器端得接口，注：后缀没加
		//String namespace = StringConstant.SERVICE_URL+"service"; 																		// .wsdl，
		// 服务器端我是用x-fire实现webservice接口的
		//String url = "http://134.192.44.105:8080/SSH2/service/IService"; // 对应的url
		// 以下就是 调用过程了，不明白的话 请看相关webservice文档
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		soapObject.addProperty("filename", fileName); // 参数1 图片名
		soapObject.addProperty("image", imageBuffer); // 参数2 图片字符串
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER10);
		//envelope.dotNet = false;
		//envelope.setOutputSoapObject(soapObject);
		envelope.bodyOut = soapObject;
		HttpTransportSE httpTranstation = new HttpTransportSE(SERVICE_URL);
		try {
			httpTranstation.call(null, envelope); // 这一步内存溢出
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
