package com.dxt.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kobjects.base64.Base64;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.CustomApplication;
import com.dxt.MainActivity;
import com.dxt.R;
import com.dxt.adapter.ArrayWheelAdapter;
import com.dxt.adapter.OnWheelChangedListener;
import com.dxt.adapter.WheelView;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.User;
import com.dxt.util.ReturnMessage;
import com.dxt.util.StringUtil;
import com.dxt.util.ValidateUtil;
import com.dxt.util.WebPostUtil;

public class CameraActivityTest extends Activity {

	private static final String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";;
	private final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private String TAG = "dxt";

	private ImageView img = null;

	// private TextView text = null;
	private Spinner spin_rewardpoint;
	private TextView onlinequesion_submit;
	private EditText edit_Description;
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());
	private Button askGoodStudent;

	// private

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private boolean canSubmit = false;

	private String grade;
	private String subject;
	private int rewardPoint;

	private Message message = new Message();
	private ReturnMessage retMessage;
	private CustomApplication app;
	private ArrayAdapter<Integer> adapter;
	private User u;
	private Handler handler = new UIHander();
	public String category1[] = new String[] { "小学    ", "七年级", "八年级", "九年级",
			"中考   ", "高一   ", "高二  ", "高三    ", "高考   " };
	StringUtil strintUtili=new StringUtil();
	//public int[] grade1={101,4,5,6,7,8,9,10,11};
	public String category2[][] = new String[][] {
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 " , "英语 ", "语文 " },
			new String[] { "数学 ", "物理 ", "化学 ", "英语 ", "语文 " },
			};
	private Integer[] rewPoint = { 0, 5, 6, 7, 8, 9, 10, 15, 20 };
	private String fileName="";
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
		String session = app.getValue();

		img = (ImageView) findViewById(R.id.img_camera);
		img.setOnClickListener(listener);
		// text = (TextView) findViewById(R.id.text);
		edit_Description = (EditText) findViewById(R.id.edit_textDescription);
		spin_rewardpoint = (Spinner) findViewById(R.id.rewardpoint);
		askGoodStudent = (Button) findViewById(R.id.ask_goodStudent);

		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, rewPoint);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spin_rewardpoint.setAdapter(adapter);

		// 添加事件Spinner事件监听
		spin_rewardpoint
				.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		spin_rewardpoint.setVisibility(View.VISIBLE);
		// 自定义dilag
		/*
		 * LayoutInflater inflater = getLayoutInflater(); final View layout =
		 * inflater.inflate(R.layout.config, (ViewGroup)
		 * findViewById(R.id.tableView));
		 */
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

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// view.setText("你的血型是："+m[arg2]);
			rewardPoint=rewPoint[arg2];
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
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
	// 问学霸事件
	private OnClickListener askGoodListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (canSubmit) {
				fileName=getPhotoFileName();
				if (ValidateUtil.isValid(app.getValue())) {

					u = JSONObject.parseObject(app.getValue(), User.class);
					

					showSelectDialog(CameraActivityTest.this, "选择所在年级和学科",
							category1, category2);
				} else {
					Toast.makeText(getApplicationContext(), "亲，请先登录", 0).show();
				}

			} else {
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
				// onlinequesion_submit.setOnClickListener(submitListener);
				askGoodStudent.setOnClickListener(askGoodListener);
				canSubmit = true;
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
	 * 1:完成更新数据库 2：完成将数据提交到服务器中
	 */
	Thread th = new Thread() {
		public void run() {
			uploadOnlineQuestion();
		};
	};

	private void uploadOnlineQuestion() {

		try {
			String imageViewPath = tempFile.getAbsolutePath();
			FileInputStream fis = new FileInputStream(imageViewPath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // 进行Base64编码
			String methodName = "uploadImage";
			Log.i(TAG, methodName + " " + getPhotoFileName() + " "
					+ uploadBuffer);
			// WebPostUtil.(methodName, getPhotoFileName(),
			// uploadBuffer,SERVICE_URL); // 调用webservice
			WebPostUtil.uploadImage(methodName, fileName,
					uploadBuffer, SERVICE_URL);
			// retMessage=WebPostUtil.getMessage(StringConstant.SERVICE_URL,
			// methodName, uploadBuffer);
			// retMessage.getStatus();

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showSelectDialog(Context context, String title,
			final String[] left, final String[][] right) {
		AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.setTitle(title);
		LinearLayout llContent = new LinearLayout(context);
		llContent.setOrientation(LinearLayout.HORIZONTAL);
		final WheelView wheelLeft = new WheelView(context);
		wheelLeft.setVisibleItems(5);
		wheelLeft.setCyclic(false);
		wheelLeft.setAdapter(new ArrayWheelAdapter<String>(left));
		final WheelView wheelRight = new WheelView(context);
		wheelRight.setVisibleItems(5);
		wheelRight.setCyclic(true);
		wheelRight.setAdapter(new ArrayWheelAdapter<String>(right[0]));
		LinearLayout.LayoutParams paramsLeft = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		paramsLeft.gravity = Gravity.LEFT;
		LinearLayout.LayoutParams paramsRight = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				(float) 0.6);
		paramsRight.gravity = Gravity.RIGHT;
		llContent.addView(wheelLeft, paramsLeft);
		llContent.addView(wheelRight, paramsRight);
		wheelLeft.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				wheelRight.setAdapter(new ArrayWheelAdapter<String>(
						right[newValue]));
				wheelRight.setCurrentItem(right[newValue].length / 2);
			}
		});
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int leftPosition = wheelLeft.getCurrentItem();
						grade = left[leftPosition].trim().toString();
						//grade=grade1[leftPosition];
						subject = right[leftPosition][wheelRight.getCurrentItem()].trim().toString();
						// btn.setText(vLeft + "-" + vRight);
						Log.v("com.dxt", grade+":fd "+subject+":");
						
						createOnLineQuestion();
						dialog.dismiss();
						th.start();
						new Thread(){
							@Override
							public void run() {
								// TODO Auto-generated method stub
								retMessage = WebPostUtil.getMessage(SERVICE_URL,
										"createOnlineQuestion", createOnLineQuestion());
								message.what = retMessage.getStatus();
								handler.sendMessage(message);
							}
						}.start();
					}
				});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		dialog.setView(llContent);
		if (!dialog.isShowing()) {
			dialog.show();
		}
	}
	@SuppressLint("HandlerLeak")
	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			//成功
			case 1:
				Intent intentReturn = new Intent();
				intentReturn
						.setClass(getApplicationContext(), MainActivity.class);
				startActivity(intentReturn);
				finish();
				break;
				//失败
			case 0:
				Toast.makeText(getApplicationContext(), "失败", 0).show();
				break;
			}
		}
	}
	
	

	private String createOnLineQuestion() {

		OnlineQuestion onlinequestionInApp = new OnlineQuestion();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		dateFormat.format(date);
		//Log.i("com.dxt", grade+" first");
		
		//Log.i("com.dxt", x+" jkjk");
		onlinequestionInApp.setGrade(StringUtil.int2IDOfGrade(grade));
		onlinequestionInApp.setTextDescription(edit_Description.getText()
				.toString());
		Log.i("com.dxt", subject+" first");
		int x=StringUtil.int2IDOfSubject(subject);
		
		Log.i("com.dxt", x+" jkjk");
		onlinequestionInApp.setSubject(StringUtil.int2IDOfSubject(subject));
		onlinequestionInApp.setRewardPoint(rewardPoint);
		onlinequestionInApp.setQuestionImage(getPhotoFileName());
		onlinequestionInApp.setCreated(date);
		onlinequestionInApp.setStudentId(u.getId());
		onlinequestionInApp.setStudentName(u.getNickName());// nickname
		onlinequestionInApp.setStudentIcon(u.getIcon());
		onlinequestionInApp.setQuestionImage("static/images/"+fileName);
		return  JSON.toJSONString(onlinequestionInApp);
	}

}
