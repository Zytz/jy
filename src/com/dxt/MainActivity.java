package com.dxt;

import static com.dxt.util.StringUtil.int2IDOfGrade;
import static com.dxt.util.StringUtil.int2IDOfSubject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dxt.constant.StringConstant;
import com.dxt.model.SearchOnlineQuestionBean;
import com.dxt.util.DoubleClickExitHelper;
import com.dxt.util.WebPostUtil;
import com.dxt.view.BadgeView;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	final static String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";
	private static final int REQUESTCODEFORCHOOSE = 1;
	private static final int REQUESTCODEFORASKQUESTION = 2;
	private TabHost tabHost;

	private TextView txt_chooseGrade;

	private RadioButton main_footbar_news;
	private BadgeView bgNews;
	private RadioButton main_footbar_question;
	private RadioButton main_footbar_tweet;

	private CustomApplication application;
	private DoubleClickExitHelper mDoubleClickExitHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 无标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mDoubleClickExitHelper = new DoubleClickExitHelper(this);
		application = (CustomApplication) getApplication();

		txt_chooseGrade = (TextView) this
				.findViewById(R.id.circle_tv_title_name);
		txt_chooseGrade.setOnClickListener(chooseGradeListener);

		// askquestion.setOnClickListener(questionListener);

		main_footbar_news = (RadioButton) this
				.findViewById(R.id.main_footbar_news);
		bgNews = new BadgeView(getApplicationContext(), main_footbar_news);
		main_footbar_question = (RadioButton) this
				.findViewById(R.id.main_footbar_question);
		main_footbar_tweet = (RadioButton) this
				.findViewById(R.id.main_footbar_tweet);

		main_footbar_news.setOnClickListener(footerListener);
		main_footbar_question.setOnClickListener(footerListener);
		main_footbar_tweet.setOnClickListener(footerListener);

		tabHost = getTabHost();
		tabHost.setup();
		tabHost.addTab(tabHost
				.newTabSpec("tab_1")
				.setContent(new Intent(this, QuestionActivity.class))
				.setIndicator("待解决",
						this.getResources().getDrawable(R.color.black)));
		tabHost.addTab(tabHost
				.newTabSpec("tab_2")
				.setContent(new Intent(this, HighScoreQuestion.class))
				.setIndicator("高分悬赏",
						this.getResources().getDrawable(R.color.blue)));
		/*
		 * tabHost.addTab(tabHost .newTabSpec("tab_3") .setContent(new
		 * Intent(this,QuestionActivityList.class))
		 * .setIndicator("问老师  "+"有五个老师在线",
		 * this.getResources().getDrawable(R.color.red)));
		 */
		tabHost.setCurrentTab(0);

		startGetCountThread();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			int count = msg.what;
			if (count > 0) {
				bgNews.setText(count + "");
				bgNews.show();
			} else {
				bgNews.hide();
			}
		}
	};
	@Override
    public boolean dispatchKeyEvent(KeyEvent event) {
		 int keyCode=event.getKeyCode();    
		         switch(keyCode)    
		         {    
		             case KeyEvent.KEYCODE_BACK: {    
		                  if(event.isLongPress())    
		                  {    
		                      System.exit(0);    
		                      return true;    
		                  }else    
		                  {    
		                	  Toast.makeText(MainActivity.this, "长按退出程序！", 200).show();  
		                      return false;    
		                  }    
		             }      
		         }    
		         return super.dispatchKeyEvent(event);    

    }


	private void startGetCountThread() {
		// TODO Auto-generated method stub
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						sleep(30 * 1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					int count = WebPostUtil.getOnlineQuestionSize(SERVICE_URL,
							"getOnlineQuestionListCount",
							JSON.toJSONString(application.getSearchBean()));
					Message message = new Message();
					message.what = count;
					handler.sendMessage(message);
				}
			}

		}.start();

	}

	// 进入选择年级activity

	private OnClickListener chooseGradeListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent toChooseGrade = new Intent(getApplicationContext(),
					ChooseGrade.class);
			startActivityForResult(toChooseGrade, REQUESTCODEFORCHOOSE);
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		SearchOnlineQuestionBean searchBean = application.getSearchBean();
		SearchOnlineQuestionBean searchBeanForHigh = application.searchBeanForHigh;
		if (resultCode == Activity.RESULT_OK
				&& requestCode == REQUESTCODEFORCHOOSE) {
			Bundle bundle = data.getExtras();
			boolean canceled = false;
			canceled = bundle.getBoolean("cancel");
			if (!canceled) {
				String grade = bundle.getString("grade");
				String subject = bundle.getString("subject");
				String title = null;
				if (!grade.equals("全部") && !subject.equals("全部")) {
					title = grade + subject;
				} else if (!grade.equals("全部") && subject.equals("全部")) {
					title = grade + "全部";
				} else {
					title = "全部问题";
				}

				bgNews.hide();
				if (application.flag == 0) {
					application.setFirstLoad(true);
					searchBean.setGrade(int2IDOfGrade(grade));
					searchBean.setSubject(int2IDOfSubject(subject));
					searchBean.setNumber(0);
					searchBean.setPageNum(0);
				} else if (application.flag == 1) {
					application.firstLoadToHigh = true;
					searchBeanForHigh.setGrade(int2IDOfGrade(grade));
					searchBeanForHigh.setSubject(int2IDOfSubject(subject));
					searchBeanForHigh.setNumber(0);
					searchBeanForHigh.setPageNum(0);
				}
				txt_chooseGrade.setText(title);
				tabHost.invalidate();
			}
		}
		/*
		 * else
		 * if(resultCode==Activity.RESULT_OK&&requestCode==REQUESTCODEFORASKQUESTION
		 * ){
		 * 
		 * }
		 */
	}

	// 进入到照相的跳转
	private OnClickListener questionListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent toAskQuestionActivity = new Intent();
			toAskQuestionActivity.setClass(getApplicationContext(),
					com.dxt.view.CameraActivityTest.class);
			startActivity(toAskQuestionActivity);
			// finish();
			// startActivityForResult(toAskQuestionActivity,REQUESTCODEFORCHOOSE);
		}
	};

	private OnClickListener footerListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.main_footbar_news:
				Toast.makeText(getApplicationContext(), "下拉产生更多数据", 150).show();
				bgNews.hide();
				break;
			case R.id.main_footbar_question:
				Intent toVideoActivity = new Intent();
				toVideoActivity.setClass(getApplicationContext(),
						com.dxt.view.CameraActivityTest.class);
				startActivity(toVideoActivity);
				break;
			case R.id.main_footbar_tweet:
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(),
						com.dxt.view.UserCenter.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

}
