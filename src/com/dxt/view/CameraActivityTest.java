package com.dxt.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.kobjects.base64.Base64;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.dxt.util.ImageTools;
import com.dxt.util.ReturnMessage;
import com.dxt.util.StringUtil;
import com.dxt.util.WebPostUtil;

public class CameraActivityTest extends Activity{

	private static final String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";;
	private final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private String TAG = "dxt";

	private ImageView img = null;

	// private TextView text = null;
	private Spinner spin_rewardpoint;
	private TextView onlinequesion_submit;
	private EditText edit_Description;

	private Button askGoodStudent;

	// private
/*
	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	*/
	private static final int TAKE_PICTURE = 0;
	private static final int CHOOSE_PICTURE = 1;
	private static final int CROP = 2;
	private static final int CROP_PICTURE = 3;
	
	private static final int SCALE = 5;//照片缩小比例
	private boolean canSubmit = false;

	private String grade;
	private String subject;
	private int rewardPoint=0;

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
	private Integer[] rewPoint = { 0,2,3,5, 6, 7, 8, 9, 10, 15, 20 };
	private String fileName="";
	/*private File tempFile = new File(Environment.getExternalStorageDirectory(),
			fileName);*/
	private String imagePath;
	
	//手指向右滑动时的最小速度
		private static final int XSPEED_MIN = 200;
		
		//手指向右滑动时的最小距离
		private static final int XDISTANCE_MIN = 150;
		
		//记录手指按下时的横坐标。
		private float xDown;
		
		//记录手指移动时的横坐标。
		private float xMove;
		
		//用于计算手指滑动的速度。
		private VelocityTracker mVelocityTracker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cameraactivitytest);
		RelativeLayout ll = (RelativeLayout) findViewById(R.id.camera_sliding);
		
		ll.setOnTouchListener(slidingListener);
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
		
		
		edit_Description.setOnFocusChangeListener(fucusListener);
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
//EditText监听器
	OnFocusChangeListener fucusListener=new OnFocusChangeListener() {
		//表示文本框是否为空
	    private Boolean isEmpty = true;
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			 //获取触发事件的EditText
            EditText clickEditText = (EditText)v;
            //如果失去焦点
            if(hasFocus == false)
            {
                //获取当前文本
                String text =clickEditText.getText().toString().trim();
                //如果的确人为输入过内容
                if(text.length()>0
                        &text.equals("学霸们，老师们， 帮帮忙！有赏哦！！！")== false)
                {
                    isEmpty = false;
                    //clickEditText.setTextColor();
                    clickEditText.setText(text);
                }
                else
                {
                    clickEditText.setText("学霸们，老师们， 帮帮忙！有赏哦！！！");
                   // clickEditText.setTextColor(Color.GRAY);
                    isEmpty = true;
                }
            }
            //如果获得焦点
            else
            {
             //   clickEditText.setTextColor(Color.BLACK);
                //如果处于未编辑状态，则清空“请输入您的名字”这几个字
                if(isEmpty == true)
                {
                    clickEditText.setText("");
                }
            }
		}
	};
	
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

	/*		Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));*/
		//	startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
			showPicturePicker(CameraActivityTest.this,true);
		}
	};
	// 问学霸事件
	private OnClickListener askGoodListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (canSubmit) {
				//fileName=getPhotoFileName();
				if (app.isIslogin()) {

					u = JSONObject.parseObject(app.getValue(), User.class);
					//扣除积分
					
					if(rewardPoint<=u.getBalance()){
						double balance= (u.getBalance()-rewardPoint);
						u.setBalance(balance);
						showSelectDialog(CameraActivityTest.this, "选择所在年级和学科",
								category1, category2);
					}else{
						Toast.makeText(getApplicationContext(), "余额不足", 0).show();
					}
					
				} else {
					Toast.makeText(getApplicationContext(), "亲，请先登录", 0).show();
				}

			} else {
				Toast.makeText(getApplicationContext(), "未获取到图片的信息", 0).show();
			}
		}
	};

	

	/*
	 * 1:完成更新数据库 2：完成将数据提交到服务器中
	 */
	Thread th_uploadImage = new Thread() {
		public void run() {
			uploadOnlineQuestion();
		};
	};

	private void uploadOnlineQuestion() {

		try {
			//String imageViewPath = tempFile.getAbsolutePath();
			FileInputStream fis = new FileInputStream(new File(imagePath));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = fis.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // 进行Base64编码
			String methodName = "uploadImage";
			/*Log.i(TAG+"zhouwenwei", methodName + " " + getPhotoFileName() + " "
					+ uploadBuffer);*/
			WebPostUtil.uploadImage(methodName, fileName,
					uploadBuffer, SERVICE_URL);

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
						//Log.v("com.dxt", rewardPoint+" "+u.getBalance());
						
							//提问扣除相应的积分
						//	u.setBalance(u.getBalance()-rewardPoint);
						
						int leftPosition = wheelLeft.getCurrentItem();
						grade = left[leftPosition].trim().toString();
						//grade=grade1[leftPosition];
						subject = right[leftPosition][wheelRight.getCurrentItem()].trim().toString();
						// btn.setText(vLeft + "-" + vRight);
						Log.v("com.dxt", grade+":fd "+subject+":"+u.getBalance());
						
						
						
						th_uploadImage.start();
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
						//createOnLineQuestion();
						dialog.dismiss();
						/*}else{
							Toast.makeText(getApplicationContext(), "余额不足", 0).show();
						}*/
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
				//startActivity(intentReturn);
				setResult(RESULT_OK, intentReturn);
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
		onlinequestionInApp.setGrade(StringUtil.int2IDOfGrade(grade));
		onlinequestionInApp.setTextDescription(edit_Description.getText()
				.toString());
		Log.i("com.dxt", subject+" first");
		int x=StringUtil.int2IDOfSubject(subject);
		
		Log.i("com.dxt", x+" jkjk");
		onlinequestionInApp.setSubject(StringUtil.int2IDOfSubject(subject));
		onlinequestionInApp.setRewardPoint(rewardPoint);
		//onlinequestionInApp.setQuestionImage(fileName);
		//onlinequestionInApp.setCreated(date);
		onlinequestionInApp.setStudentId(u.getId());
		onlinequestionInApp.setStudentName(u.getNickName());// nickname
		onlinequestionInApp.setStudentIcon(u.getIcon());
		onlinequestionInApp.setQuestionImage("static/onlineQuestionAndAnswerImages/"+fileName);
		return  JSON.toJSONString(onlinequestionInApp);
	}
	OnTouchListener slidingListener=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			createVelocityTracker(event);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDown = event.getRawX();
				break;
			case MotionEvent.ACTION_MOVE:
				xMove = event.getRawX();
				//活动的距离
				int distanceX = (int) (xMove - xDown);
				//获取顺时速度
				int xSpeed = getScrollVelocity();
				//当滑动的距离大于我们设定的最小距离且滑动的瞬间速度大于我们设定的速度时，返回到上一个activity
				if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
					finish();
					//设置切换动画，从右边进入，左边退出
					overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
				}
				break;
			case MotionEvent.ACTION_UP:
				recycleVelocityTracker();
				break;
			default:
				break;
			}
			return true;
		}

		private void createVelocityTracker(MotionEvent event) {
			// TODO Auto-generated method stub
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			}
			mVelocityTracker.addMovement(event);
		}

		private int getScrollVelocity() {
			// TODO Auto-generated method stub
			mVelocityTracker.computeCurrentVelocity(1000);
			int velocity = (int) mVelocityTracker.getXVelocity();
			return Math.abs(velocity);
		}

		private void recycleVelocityTracker() {
			// TODO Auto-generated method stub
			mVelocityTracker.recycle();
			mVelocityTracker = null;
		}
	};
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case TAKE_PICTURE:
				//将保存在本地的图片取出并缩小后显示在界面上
				Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/image.jpg");
				Bitmap newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);
				//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
				bitmap.recycle();
				
				//将处理过的图片显示在界面上，并保存到本地
				img.setImageBitmap(newBitmap);
				ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), String.valueOf(System.currentTimeMillis()));
				
				break;

			case CHOOSE_PICTURE:
				ContentResolver resolver = getContentResolver();
				//照片的原始资源地址
				Uri originalUri = data.getData(); 
	            try {
	            	//使用ContentProvider通过URI获取原始图片
					Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
					if (photo != null) {
						//为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
						Bitmap smallBitmap = ImageTools.zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
						//释放原始图片占用的内存，防止out of memory异常发生
						photo.recycle();
						
						img.setImageBitmap(smallBitmap);
					}
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
				
			case CROP:
				Uri uri = null;
				if (data != null) {
					uri = data.getData();
					System.out.println("Data");
					String[] proj = {MediaStore.Images.Media.DATA};
					Cursor cursor = managedQuery(uri, proj, null, null, null); 
					//按我个人理解 这个是获得用户选择的图片的索引值 
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
					cursor.moveToFirst(); 
					//最后根据索引值获取图片路径 
					imagePath = cursor.getString(column_index); 
				}else {
					System.out.println("File");
					String fileName = getSharedPreferences("temp",Context.MODE_WORLD_WRITEABLE).getString("tempName", "");
					uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
				
				}
				
				
				/*if(data.equals(Intent.ACTION_GET_CONTENT)){
					
					Cursor cursor = managedQuery(uri, proj, null, null, null); 
					//按我个人理解 这个是获得用户选择的图片的索引值 
					int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA); 
					cursor.moveToFirst(); 
					//最后根据索引值获取图片路径 
					imagePath = cursor.getString(column_index); 
				}*/
				
				
				System.out.println("~~~~~~~~~~~~"+imagePath);
				
				String[] pathss=imagePath.split("/");
				fileName=pathss[pathss.length-1];
				System.out.println("~~~~~~~~~~~~~~~"+fileName);
				cropImage(uri, 200, 500, CROP_PICTURE);
				break;
			
			case CROP_PICTURE:
				Bitmap photo = null;
				Uri photoUri = data.getData();
				if (photoUri != null) {
					photo = BitmapFactory.decodeFile(photoUri.getPath());

				}
				if (photo == null) {
					Bundle extra = data.getExtras();
					if (extra != null) {
		                photo = (Bitmap)extra.get("data");  
		                ByteArrayOutputStream stream = new ByteArrayOutputStream();  
		                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		            }  
				}
				img.setImageBitmap(photo);
				break;
			default:
				break;
			}
		}
	}
	
	public void showPicturePicker(Context context,boolean isCrop){
		final boolean crop = isCrop;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("图片来源");
		builder.setNegativeButton("取消", null);
		builder.setItems(new String[]{"拍照","相册"}, new DialogInterface.OnClickListener() {
			//类型码
			int REQUEST_CODE;
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case TAKE_PICTURE:
					Uri imageUri = null;
					//String fileName = null;
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (crop) {
						REQUEST_CODE = CROP;
						//删除上一次截图的临时文件
						SharedPreferences sharedPreferences = getSharedPreferences("temp",Context.MODE_WORLD_WRITEABLE);
						ImageTools.deletePhotoAtPathAndName(Environment.getExternalStorageDirectory().getAbsolutePath(), sharedPreferences.getString("tempName", ""));
						
						//保存本次截图临时文件名字
						fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
						
						Editor editor = sharedPreferences.edit();
						editor.putString("tempName", fileName);
						
						editor.commit();
					}else {
						REQUEST_CODE = TAKE_PICTURE;
						fileName = "image.jpg";
					}
					imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),fileName));
					imagePath=imageUri.getPath();
					//指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(openCameraIntent, REQUEST_CODE);
					break;
					
				case CHOOSE_PICTURE:
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					if (crop) {
						REQUEST_CODE = CROP;
					}else {
						REQUEST_CODE = CHOOSE_PICTURE;
					}
					openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(openAlbumIntent, REQUEST_CODE);
					break;

				default:
					break;
				}
			}
		});
		askGoodStudent.setOnClickListener(askGoodListener);
		canSubmit = true;
		builder.create().show();
	}

	//截取图片
	public void cropImage(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        intent.putExtra("crop", "true");  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        intent.putExtra("outputX", outputX);   
        intent.putExtra("outputY", outputY); 
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);  
	    startActivityForResult(intent, requestCode);
	}
	
}
