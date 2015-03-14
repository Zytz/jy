package com.dxt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.alibaba.fastjson.JSON;
import com.dxt.model.OnlineQuestion;
import com.dxt.util.ImageUtil;
import com.dxt.view.CameraActivityTest;

public class QuestionDetailActivity extends Activity {
	 private static final int VIEWSWITCH_TYPE_QUESTION =1;
	 private static final int VIEWSWITCH_TYPE_ANSWER =2;
	 private static final int VIEWSWITCH_TYPE_CAMERA =3;
	 
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
	 private ImageView bootCamera;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.question_detail);
		
		initialView();
		
		initialData();
	}


	private void initialData() {
		// TODO Auto-generated method stub
		String ques = getIntent().getStringExtra("question");
		OnlineQuestion onlineQuestion = JSON.parseObject(ques, OnlineQuestion.class);
		Log.v("com.dxt",onlineQuestion.toString());
		ImageUtil.LoadImage(getApplicationContext(),onlineQuestion.getStudentIcon(), studentIcon);
		studentName.setText(onlineQuestion.getStudentName());
		grade.setText(onlineQuestion.getGrade());
		subject.setText(onlineQuestion.getSubject());
		date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", onlineQuestion.getCreated()));
		rewardPoint.setText(String.valueOf(onlineQuestion.getRewardPoint()));
		textDescription.setText(onlineQuestion.getTextDescription());
		ImageUtil.LoadImage(getApplicationContext(),onlineQuestion.getQuestionImage(),questionImage);
	}


	private void initialView() {
		// TODO Auto-generated method stub
		headerText = (TextView) findViewById(R.id.question_detail_text);
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.question_detail_viewswitcher);
		studentIcon = (MyImageView) findViewById(R.id.homework_question_item_iv_user_picture);
		studentName = (TextView) findViewById(R.id.homework_qb1_item_tv_user_name);
		subject = (TextView) findViewById(R.id.question_item_question_course);
		grade = (TextView) findViewById(R.id.question_item_user_grade);
		date	=(TextView) findViewById(R.id.question_item_question_time);
		rewardPoint = (TextView) findViewById(R.id.question_rewrad);
		textDescription = (TextView) findViewById(R.id.question_name);
		questionImage = (MyImageView) findViewById(R.id.question_picture);
		
		questionDetail = (ImageView) findViewById(R.id.question_detail_footbar);
		questionDetail.setEnabled(false);
		questionDetail.setOnClickListener(onlineQuestionClickListener);
		onlineQuestionAnswer = (ImageView) findViewById(R.id.online_question_answer_footbar);
		onlineQuestionAnswer.setOnClickListener(onlineQuestionAnswerClickListener);
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
		// 编辑器添加文本监听
		//mFootEditer.addTextChangedListener(UIHelper.getTextWatcher(this,tempCommentKey));
	}
	// 隐藏输入发表回帖状态
    private void hideEditor(View v) {
    	imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    	if(mFootViewSwitcher.getDisplayedChild()==1){
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
	private View.OnClickListener commentpubClickListener = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}};
		
	private View.OnClickListener onlineQuestionClickListener = new View.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				viewSwitch(VIEWSWITCH_TYPE_QUESTION);
			}};
	private View.OnClickListener onlineQuestionAnswerClickListener = new View.OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					viewSwitch(VIEWSWITCH_TYPE_ANSWER);
				}};
	private View.OnClickListener bootCameraClickListener = new View.OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(getApplicationContext(),CameraActivityTest.class);
						startActivity(intent);
					}};
					
}
