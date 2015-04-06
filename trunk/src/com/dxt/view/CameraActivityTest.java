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
import android.content.DialogInterface.OnCancelListener;
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
import com.dxt.util.ReturnMessage;
import com.dxt.util.StringUtil;
import com.dxt.util.ValidateUtil;
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
	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());
	private Button askGoodStudent;

	// private

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// ����
	private static final int PHOTO_REQUEST_GALLERY = 2;// �������ѡ��
	private static final int PHOTO_REQUEST_CUT = 3;// ���
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
	public String category1[] = new String[] { "Сѧ    ", "���꼶", "���꼶", "���꼶",
			"�п�   ", "��һ   ", "�߶�  ", "����    ", "�߿�   " };
	StringUtil strintUtili=new StringUtil();
	//public int[] grade1={101,4,5,6,7,8,9,10,11};
	public String category2[][] = new String[][] {
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ " , "Ӣ�� ", "���� " },
			new String[] { "��ѧ ", "���� ", "��ѧ ", "Ӣ�� ", "���� " },
			};
	private Integer[] rewPoint = { 0, 5, 6, 7, 8, 9, 10, 15, 20 };
	private String fileName="";
	
	
	
	//��ָ���һ���ʱ����С�ٶ�
		private static final int XSPEED_MIN = 200;
		
		//��ָ���һ���ʱ����С����
		private static final int XDISTANCE_MIN = 150;
		
		//��¼��ָ����ʱ�ĺ����ꡣ
		private float xDown;
		
		//��¼��ָ�ƶ�ʱ�ĺ����ꡣ
		private float xMove;
		
		//���ڼ�����ָ�������ٶȡ�
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
		// ����ѡ������ArrayAdapter��������
		adapter = new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_item, rewPoint);

		// ���������б�ķ��
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		spin_rewardpoint.setAdapter(adapter);

		// ����¼�Spinner�¼�����
		spin_rewardpoint
				.setOnItemSelectedListener(new SpinnerSelectedListener());

		// ����Ĭ��ֵ
		spin_rewardpoint.setVisibility(View.VISIBLE);
		// �Զ���dilag
		/*
		 * LayoutInflater inflater = getLayoutInflater(); final View layout =
		 * inflater.inflate(R.layout.config, (ViewGroup)
		 * findViewById(R.id.tableView));
		 */
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

	// ʹ��������ʽ����
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// view.setText("���Ѫ���ǣ�"+m[arg2]);
			rewardPoint=rewPoint[arg2];
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}
//EditText������
	OnFocusChangeListener fucusListener=new OnFocusChangeListener() {
		//��ʾ�ı����Ƿ�Ϊ��
	    private Boolean isEmpty = true;
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			 //��ȡ�����¼���EditText
            EditText clickEditText = (EditText)v;
            //���ʧȥ����
            if(hasFocus == false)
            {
                //��ȡ��ǰ�ı�
                String text =clickEditText.getText().toString().trim();
                //�����ȷ��Ϊ���������
                if(text.length()>0
                        &text.equals("ѧ���ǣ���ʦ�ǣ� ���æ������Ŷ������")== false)
                {
                    isEmpty = false;
                    //clickEditText.setTextColor();
                    clickEditText.setText(text);
                }
                else
                {
                    clickEditText.setText("ѧ���ǣ���ʦ�ǣ� ���æ������Ŷ������");
                   // clickEditText.setTextColor(Color.GRAY);
                    isEmpty = true;
                }
            }
            //�����ý���
            else
            {
             //   clickEditText.setTextColor(Color.BLACK);
                //�������δ�༭״̬������ա��������������֡��⼸����
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

			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// ָ������������պ���Ƭ�Ĵ���·��
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);

		}
	};
	// ��ѧ���¼�
	private OnClickListener askGoodListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (canSubmit) {
				fileName=getPhotoFileName();
				if (ValidateUtil.isValid(app.getValue())) {

					u = JSONObject.parseObject(app.getValue(), User.class);
					

					showSelectDialog(CameraActivityTest.this, "ѡ�������꼶��ѧ��",
							category1, category2);
				} else {
					Toast.makeText(getApplicationContext(), "�ף����ȵ�¼", 0).show();
				}

			} else {
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

	// ʹ��ϵͳ��ǰ���ڼ��Ե�����Ϊ��Ƭ������
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/*
	 * 1:��ɸ������ݿ� 2����ɽ������ύ����������
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
			String uploadBuffer = new String(Base64.encode(baos.toByteArray())); // ����Base64����
			String methodName = "uploadImage";
			Log.i(TAG, methodName + " " + getPhotoFileName() + " "
					+ uploadBuffer);
			// WebPostUtil.(methodName, getPhotoFileName(),
			// uploadBuffer,SERVICE_URL); // ����webservice
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
		dialog.setButton(AlertDialog.BUTTON_POSITIVE, "ȷ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.v("com.dxt", rewPoint+" ");
						if(rewardPoint<=u.getBalance()){
							//���ʿ۳���Ӧ�Ļ���
							u.setBalance(u.getBalance()-rewardPoint);
						
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
						}else{
							Toast.makeText(getApplicationContext(), "����", 0).show();
						}
					}
				});
		dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ȡ��",
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
			//�ɹ�
			case 1:
				Intent intentReturn = new Intent();
				intentReturn
						.setClass(getApplicationContext(), MainActivity.class);
				//startActivity(intentReturn);
				setResult(RESULT_OK, intentReturn);
				finish();
				break;
				//ʧ��
			case 0:
				Toast.makeText(getApplicationContext(), "ʧ��", 0).show();
				break;
			}
		}
	}
	
	

	private String createOnLineQuestion() {

		OnlineQuestion onlinequestionInApp = new OnlineQuestion();
		//Date date = new Date(System.currentTimeMillis());
		//dateFormat.format(date);
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
				//��ľ���
				int distanceX = (int) (xMove - xDown);
				//��ȡ˳ʱ�ٶ�
				int xSpeed = getScrollVelocity();
				//�������ľ�����������趨����С�����һ�����˲���ٶȴ��������趨���ٶ�ʱ�����ص���һ��activity
				if(distanceX > XDISTANCE_MIN && xSpeed > XSPEED_MIN) {
					finish();
					//�����л����������ұ߽��룬����˳�
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
	

}
