package com.dxt;

import static com.dxt.util.StringUtil.int2StringOfGrade;
import static com.dxt.util.StringUtil.int2StringOfSubject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kobjects.base64.Base64;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.adapter.ListViewAnswerAdapter;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.OnlineQuestionAnswer;
import com.dxt.model.User;
import com.dxt.util.ImageUtil;
import com.dxt.util.WebPostUtil;
import com.dxt.view.BadgeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class QuestionDetailActivity extends Activity {

	final static String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";
	private static final int VIEWSWITCH_TYPE_QUESTION = 1;
	private static final int VIEWSWITCH_TYPE_ANSWER = 2;

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果
	private File tempFile;
	private String fileName ="answer_default.png" ;//答案的默认图片

	private String TAG = "dxt";
	private ImageView img = null;

	private TextView headerText;
	private TextView grade;
	private TextView subject;
	private TextView date;
	private TextView rewardPoint;
	private TextView textDescription;
	private MyImageView questionImage;
	private MyImageView studentIcon;
	private TextView studentName;
	private ViewSwitcher mViewSwitcher;
	private ViewSwitcher mFootViewSwitcher;
	private ImageView mFootEditebox;
	private EditText mFootEditer;
	private Button mFootPubcomment;
	private InputMethodManager imm;

	private ImageView questionDetail;
	private ImageView onlineQuestionAnswer;
	private BadgeView bvAnswer;
	private ImageView bootCamera;

	private PullToRefreshListView mPullRefreshListView;
	private List<OnlineQuestionAnswer> listitems = new ArrayList<OnlineQuestionAnswer>();
	private ListViewAnswerAdapter adapter;
	private CustomApplication application;
	private OnlineQuestion onlineQuestion = new OnlineQuestion();
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case 1:
					Toast.makeText(getApplicationContext(), "回答成功", 150).show();
					bvAnswer.setText(onlineQuestion.getAnswerCount()+1+"");
					bvAnswer.show();
					initialAnswerData();
					break;
				default:
					Toast.makeText(getApplicationContext(), "回答失败", 150).show();
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.question_detail);
		application = (CustomApplication) getApplication();
		initialView();
		initialData();

		initialAnswerView();
		initialAnswerData();
	}

	private void initialAnswerView() {
		// TODO Auto-generated method stub
		img = new ImageView(getApplicationContext());
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();

					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();

					}

				});
		adapter = new ListViewAnswerAdapter(getApplicationContext(), listitems,
				R.layout.online_question_answer_listitem);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(adapter);

	}

	private class GetDataTask extends
			AsyncTask<Void, Void, List<OnlineQuestionAnswer>> {

		// 后台处理部分
		@Override
		protected List<OnlineQuestionAnswer> doInBackground(Void... params) {
			// Simulates a background job.

			List<OnlineQuestionAnswer> ques = WebPostUtil
					.getOnlineQuestionAnswer(SERVICE_URL,
							"getOnlineQuestionAnswer", onlineQuestion.getId());

			return ques;
		}

		// 这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(List<OnlineQuestionAnswer> result) {
			// 在头部增加新添内容
			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			if (listitems != null)
				listitems.clear();
			listitems.addAll(result);
			adapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private void initialAnswerData() {
		// TODO Auto-generated method stub
		new GetDataTask().execute();
	}

	private void initialData() {
		// TODO Auto-generated method stub
		String ques = getIntent().getStringExtra("question");
		onlineQuestion = JSON.parseObject(ques, OnlineQuestion.class);
		Log.v("com.dxt", onlineQuestion.toString());
		ImageUtil.LoadImage(getApplicationContext(),
				onlineQuestion.getStudentIcon(), studentIcon);
		studentName.setText(onlineQuestion.getStudentName());
		grade.setText(int2StringOfGrade(onlineQuestion.getGrade()));
		subject.setText(int2StringOfSubject(onlineQuestion.getSubject()));
		date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
				onlineQuestion.getCreated()));
		rewardPoint.setText(String.valueOf(onlineQuestion.getRewardPoint()));
		textDescription.setText(onlineQuestion.getTextDescription());
		ImageUtil.LoadImage(getApplicationContext(),
				onlineQuestion.getQuestionImage(), questionImage);
		if (onlineQuestion.getAnswerCount() > 0) {
			bvAnswer.setText(onlineQuestion.getAnswerCount() + "");
			bvAnswer.show();
		} else {
			bvAnswer.setText("");
			bvAnswer.hide();
		}
	}

	private void initialView() {
		// TODO Auto-generated method stub
		headerText = (TextView) findViewById(R.id.question_detail_text);
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.question_detail_viewswitcher);
		studentIcon = (MyImageView) findViewById(R.id.homework_question_item_iv_user_picture);
		studentName = (TextView) findViewById(R.id.homework_qb1_item_tv_user_name);
		subject = (TextView) findViewById(R.id.question_item_question_course);
		grade = (TextView) findViewById(R.id.question_item_user_grade);
		date = (TextView) findViewById(R.id.question_item_question_time);
		rewardPoint = (TextView) findViewById(R.id.question_rewrad);
		textDescription = (TextView) findViewById(R.id.question_name);
		questionImage = (MyImageView) findViewById(R.id.question_picture);

		questionDetail = (ImageView) findViewById(R.id.question_detail_footbar);
		questionDetail.setEnabled(false);
		questionDetail.setOnClickListener(onlineQuestionClickListener);
		onlineQuestionAnswer = (ImageView) findViewById(R.id.online_question_answer_footbar);
		onlineQuestionAnswer
				.setOnClickListener(onlineQuestionAnswerClickListener);

		bvAnswer = new BadgeView(getApplicationContext(), onlineQuestionAnswer);
		bvAnswer.setBackgroundResource(R.drawable.widget_count_bg2);
		bvAnswer.setIncludeFontPadding(false);
		bvAnswer.setGravity(Gravity.CENTER);
		bvAnswer.setTextSize(8f);
		bvAnswer.setTextColor(Color.WHITE);

		
		
		bootCamera = (ImageView) findViewById(R.id.friend_ib_camera);
		bootCamera.setOnClickListener(bootCameraClickListener);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		mFootViewSwitcher = (ViewSwitcher) findViewById(R.id.question_detail_foot_viewswitcher);
		mFootPubcomment = (Button) findViewById(R.id.question_detail_foot_pubcomment);
		mFootPubcomment.setOnClickListener(commentpubClickListener);

		mFootEditebox = (ImageView) findViewById(R.id.question_detail_footbar_editebox);
		mFootEditebox.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mFootViewSwitcher.showNext();
				mFootEditer.setVisibility(View.VISIBLE);
				mFootEditer.requestFocus();
				mFootEditer.requestFocusFromTouch();
			}
		});
		mFootEditer = (EditText) findViewById(R.id.question_detail_foot_editer);
		mFootEditer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					imm.showSoftInput(v, 0);
				} else {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					hideEditor(v);
				}
			}
		});
		mFootEditer.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					hideEditor(v);
					return true;
				}
				return false;
			}
		});

	}

	// 隐藏输入发表回帖状态
	private void hideEditor(View v) {
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		if (mFootViewSwitcher.getDisplayedChild() == 1) {
			mFootViewSwitcher.setDisplayedChild(0);
			mFootEditer.clearFocus();
			mFootEditer.setVisibility(View.GONE);
		}
	}

	private void viewSwitch(int type) {
		switch (type) {
		case VIEWSWITCH_TYPE_QUESTION:
			questionDetail.setEnabled(false);
			onlineQuestionAnswer.setEnabled(true);
			mViewSwitcher.setDisplayedChild(0);
			headerText.setText("问题详情");
			break;
		case VIEWSWITCH_TYPE_ANSWER:
			questionDetail.setEnabled(true);
			onlineQuestionAnswer.setEnabled(false);
			mViewSwitcher.setDisplayedChild(1);
			headerText.setText("在线答案");
			break;
		}
	}

	private View.OnClickListener commentpubClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(application.isIslogin()){
				new Thread() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(tempFile!=null) uploadOnlineQuestionAnswerImage();
						int flag = createOnLineQuestionAnswer();
						Message message = new Message();
						message.what=flag;
						handler.sendMessage(message);
					}
					
				}.start();
				
			}else{
				Toast.makeText(getApplicationContext(),"请登入",100).show();
			}
			hideEditor(v);
		}
	};

	private void uploadOnlineQuestionAnswerImage() {

		try {
			String imageViewPath = tempFile.getAbsolutePath();
			fileName = getPhotoFileName();
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
			WebPostUtil.uploadImage(methodName, fileName, uploadBuffer,
					SERVICE_URL);

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int createOnLineQuestionAnswer() {
		OnlineQuestionAnswer answer = new OnlineQuestionAnswer();
		User u = JSONObject.parseObject(application.getValue(), User.class);
		answer.setTextAnswer(mFootEditer.getText().toString());
		answer.setImageAnswer("static/onlineQuestionImages/" + fileName);
		answer.setCreated(new Date());
		answer.setAnswerAuthor(u.getNickName());
		answer.setAnswerAuthorId(u.getId());
		answer.setOnlineQuestionId(onlineQuestion.getId());
		return WebPostUtil.saveOnlineQuestionAnswer(SERVICE_URL,
				"saveOnlineQuestionAnswer", JSONObject.toJSONString(answer));

	}

	private View.OnClickListener onlineQuestionClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewSwitch(VIEWSWITCH_TYPE_QUESTION);
		}
	};
	private View.OnClickListener onlineQuestionAnswerClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewSwitch(VIEWSWITCH_TYPE_ANSWER);
		}
	};
	private View.OnClickListener bootCameraClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			tempFile = new File(Environment.getExternalStorageDirectory(),
				 getPhotoFileName());
			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);
		}
	};

	@Override
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
				sentPicToNext(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

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
				Log.v(TAG, "photo is null");
			} else {
				Log.v(TAG, photo.toString());
				img.setImageBitmap(photo);
			}

			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photodata = baos.toByteArray();
				System.out.println(photodata.toString());

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

}
