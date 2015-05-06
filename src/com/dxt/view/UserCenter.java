package com.dxt.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dxt.CustomApplication;
import com.dxt.LoginActivity;
import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.model.CommonListViewModel;
import com.dxt.model.User;
import com.dxt.util.ReturnMessage;
import com.dxt.util.ValidateUtil;
import com.dxt.util.WebPostUtil;

public class UserCenter extends Activity implements OnTouchListener{

	private String TAG = "dxt";
	private final static int RequestIntentToLogin=1;
	private final static int RequestUserCenterInformation=2;
	private final static int RequestUserCenterMyQuestion=3;
	private final static int RequestUserCenterMyAnswer=4;
	private final static int RequestUserCenterMyMoney=5;
	
	
	private final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	//private final static String SERVICE_URL = "http://210.40.65.204:8080/daxuetong/services/UserCenterService?wsdl";
	private final static String SERVICE_URL = StringConstant.SERVICE_URL+"services/UserCenterService?wsdl";
	private ReturnMessage retMessage;
	private Message message = new Message();
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	
	private boolean flag_th=false;
	//private Thread th;
	//private Handler handler = new UIHander();
	private User u=new User();
	private CustomApplication app;
	
	
	
	
	
	private TextView toLogin;

	private ImageView img = null;

	private ListView lv_usercenter;
	private List<CommonListViewModel> data;
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
		setContentView(R.layout.usercenter);
		LinearLayout usercenter_sliding=(LinearLayout) findViewById(R.id.usercenter_sliding);
		usercenter_sliding.setOnTouchListener(this);
		init();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == RequestIntentToLogin) {
			init();
		}
	}
	private void init() {
		img = (ImageView) findViewById(R.id.user_icon);
		toLogin = (TextView) this.findViewById(R.id.user_notlogin);
		app = (CustomApplication) getApplication();
		if(app.isIslogin()){
			
			//u=JSONObject.parseObject(app.getValue(),User.class);
				toLogin.setText(app.getUsername());
				toLogin.setEnabled(false);
				//th.start();
		}
		else{
		toLogin.setOnClickListener(toLoginListener);
		
		}
		lv_usercenter = (ListView) this.findViewById(R.id.lv_usercenter);
		data = getData();
		lv_usercenter.setAdapter(new MyBaseAdapter());
		lv_usercenter.setOnItemClickListener(usecenterlitener);
	}

	// 如果未登录的时候

	private List<CommonListViewModel> getData() {

		List<CommonListViewModel> list = new ArrayList<CommonListViewModel>();
		String[] usercenter_inf = { "个人中心", "我的提问", "我的回答", "我的金币" };

		for (int i = 1; i <= 4; i++) {
			CommonListViewModel info = new CommonListViewModel(
					R.drawable.usercenter1, usercenter_inf[i - 1],
					R.drawable.usercenter2 + i);
			list.add(info);
		}
		return list;
	}

	class MyBaseAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(UserCenter.this,
						R.layout.usercenter_row, null);
				viewHolder.iconIV = (ImageView) convertView
						.findViewById(R.id.iv_item_image);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.iv_item_inf);
				viewHolder.messageTV = (ImageView) convertView
						.findViewById(R.id.iv_item_imagetogo);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			CommonListViewModel userInfo = data.get(position);
			viewHolder.iconIV.setImageResource(userInfo.getIcon());
			viewHolder.nameTV.setText(userInfo.getName());
			viewHolder.messageTV.setImageResource(userInfo.getMessage());

			return convertView;
		}

		class ViewHolder {
			public ImageView iconIV;
			public TextView nameTV;
			public ImageView messageTV;
		}
	}

	OnClickListener toLoginListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent tologin_intent = new Intent();
			tologin_intent.setClass(getApplicationContext(),
					LoginActivity.class);
			startActivityForResult(tologin_intent, RequestIntentToLogin);
		}
	};

	OnItemClickListener usecenterlitener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			CommonListViewModel userInfo = data.get(position);
			//Toast.makeText(UserCenter.this, userInfo.getName(), 0).show();
			Intent intentUserCenterInf = new Intent();
			if(!app.isIslogin()){
				Toast.makeText(getApplicationContext(), "未登录", 0).show();
				intentUserCenterInf.setClass(getApplicationContext(),
						LoginActivity.class);
				startActivityForResult(intentUserCenterInf, RequestIntentToLogin);
			}else{
			/*	budle.putString("user", retMessage.getMessage());
				intentUserCenterInf.putExtras(budle);*/
				
			switch(position){
			case 0:
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterInformation.class);
				startActivityForResult(intentUserCenterInf, RequestUserCenterInformation);
				break;
				case 1:
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterMyQuestion.class);
				startActivityForResult(intentUserCenterInf, RequestUserCenterMyQuestion);
				break;
				case 2:
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterMyAnswer.class);
				startActivityForResult(intentUserCenterInf, RequestUserCenterMyAnswer);
				break;
				case 3:
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterMyMoney.class);
				startActivityForResult(intentUserCenterInf, RequestUserCenterMyMoney);
				break;
			}
			
			
			}

		}
	};


	@Override
	public boolean onTouch(View v, MotionEvent event) {
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
	
	/**
	 * 创建VelocityTracker对象，并将触摸content界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 *        
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}
	
	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}
	
	/**
	 * 获取手指在content界面滑动的速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
	
	/*
	Thread th=new Thread(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			retMessage = WebPostUtil.getMessage(SERVICE_URL,
					"UseCenterInformation", app.getValue());
			message.what = retMessage.getStatus();
			app.setValue(retMessage.getMessage());
			flag_th=true;
			handler.sendMessage(message);
		}
	};
	


	@SuppressLint("HandlerLeak")
	private final class UIHander extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case SUCCESS:
				u=JSONObject.parseObject(retMessage.getMessage(),User.class);
				Toast.makeText(getApplicationContext(),
						app.getValue(), Toast.LENGTH_LONG).show();
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(),
						retMessage.getMessage()+"和服务器没有链接", Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
*/
	//
}
