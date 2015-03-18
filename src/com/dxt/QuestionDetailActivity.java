package com.dxt;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ViewSwitcher;

import com.alibaba.fastjson.JSON;
import com.dxt.adapter.ListViewAnswerAdapter;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.OnlineQuestionAnswer;
import com.dxt.util.ImageUtil;
import com.dxt.util.WebPostUtil;
import com.dxt.view.BadgeView;
import com.dxt.view.CameraActivityTest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class QuestionDetailActivity extends Activity {
	
	final static String SERVICE_URL = StringConstant.SERVICE_URL+ "services/OnlineQuestionService?wsdl";
	 private static final int VIEWSWITCH_TYPE_QUESTION =1;
	 private static final int VIEWSWITCH_TYPE_ANSWER =2;
	 
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
		adapter = new ListViewAnswerAdapter(getApplicationContext(), listitems,R.layout.online_question_answer_listitem);
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		actualListView.setAdapter(adapter);

	}
	
	private class GetDataTask extends AsyncTask<Void, Void, List<OnlineQuestionAnswer>> {

		// 后台处理部分
		@Override
		protected List<OnlineQuestionAnswer> doInBackground(Void... params) {
			// Simulates a background job.
			
			List<OnlineQuestionAnswer> ques = WebPostUtil.getOnlineQuestionAnswer(SERVICE_URL, "getOnlineQuestionAnswer", application.getQuestionId());
			
			return ques;
		}

		// 这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(List<OnlineQuestionAnswer> result) {
			// 在头部增加新添内容
			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			if(listitems!=null)listitems.clear();
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
		OnlineQuestion onlineQuestion = JSON.parseObject(ques, OnlineQuestion.class);
		Log.v("com.dxt",onlineQuestion.toString());
		application.setQuestionId(onlineQuestion.getId());
		ImageUtil.LoadImage(getApplicationContext(),onlineQuestion.getStudentIcon(), studentIcon);
		studentName.setText(onlineQuestion.getStudentName());
		grade.setText(onlineQuestion.getGrade());
		subject.setText(onlineQuestion.getSubject());
		date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", onlineQuestion.getCreated()));
		rewardPoint.setText(String.valueOf(onlineQuestion.getRewardPoint()));
		textDescription.setText(onlineQuestion.getTextDescription());
		ImageUtil.LoadImage(getApplicationContext(),onlineQuestion.getQuestionImage(),questionImage);
		if(onlineQuestion.getAnswerCount()>0){
			bvAnswer.setText(onlineQuestion.getAnswerCount()+"");
			bvAnswer.show();
		}else{
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
		date	=(TextView) findViewById(R.id.question_item_question_time);
		rewardPoint = (TextView) findViewById(R.id.question_rewrad);
		textDescription = (TextView) findViewById(R.id.question_name);
		questionImage = (MyImageView) findViewById(R.id.question_picture);
		
		questionDetail = (ImageView) findViewById(R.id.question_detail_footbar);
		questionDetail.setEnabled(false);
		questionDetail.setOnClickListener(onlineQuestionClickListener);
		onlineQuestionAnswer = (ImageView) findViewById(R.id.online_question_answer_footbar);
		onlineQuestionAnswer.setOnClickListener(onlineQuestionAnswerClickListener);
		
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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
					
	
					
}
