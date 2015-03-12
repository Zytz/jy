package com.dxt.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dxt.R;
import com.dxt.model.CommonListViewModel;
import com.dxt.model.CommonListViewModel_itt;
import com.dxt.model.User;
import com.dxt.util.ReturnMessage;
import com.dxt.util.WebPostUtil;

public class UserCenterInformation extends Activity {

	private String TAG = "dxt";
	private final static String SERVICE_NS = "http://xml.apache.org/axis/wsdd/";
	private final static String SERVICE_URL = "http://210.40.65.204:8080/daxuetong/services/UserCenterService?wsdl";
	private ReturnMessage retMessage;
	private Message message = new Message();
	private static final int SUCCESS = 1;
	private static final int ERROR = 0;
	//private Thread th;
	private Handler handler = new UIHander();
	
	private TextView toLogin;
	
	private ImageView img = null;
	
	private ListView lv_usercenterinf;
	private List<CommonListViewModel_itt> data;
	private User u=new User();
	
	private String username;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenterinformation);
		
		Intent intentGetUsername=this.getIntent();
		username=intentGetUsername.getExtras().getString("user");
		u=JSONObject.parseObject(username,User.class);
		init();
		
		
	
	}
	private void init() {
		lv_usercenterinf = (ListView) this.findViewById(R.id.lv_usercenterinf);
		data = getData();
		lv_usercenterinf.setAdapter(new MyBaseAdapter());
		lv_usercenterinf.setOnItemClickListener(usecenterlitenerinf);
		

	}
	private List<CommonListViewModel_itt> getData() {

		List<CommonListViewModel_itt> list = new ArrayList<CommonListViewModel_itt>();
		String[] usercenter_inf = { "昵称", "学校名称","年级","性别", "电话"};
		String[] userInf={u.getNickName()+"2",u.getSchool(),u.getGrade(),u.getGender(),u.getMobilePhone()};
		for (int i = 1; i <= 5; i++) {
			CommonListViewModel_itt info = new CommonListViewModel_itt(
					R.drawable.usercenter1, usercenter_inf[i - 1],
					userInf[i-1]);
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
				convertView = View.inflate(UserCenterInformation.this,
						R.layout.usercenterinf_row, null);
				viewHolder.iconIV = (ImageView) convertView
						.findViewById(R.id.iv_item_image_inf);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.iv_item_inf_inf);
				viewHolder.messageTV = (TextView) convertView
						.findViewById(R.id.iv_item_inf_message);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			CommonListViewModel_itt userInfo = data.get(position);
			viewHolder.iconIV.setImageResource(userInfo.getIcon());
			viewHolder.nameTV.setText(userInfo.getName());
			viewHolder.messageTV.setText(userInfo.getMessage());

			return convertView;
		}

		class ViewHolder {
			public ImageView iconIV;
			public TextView nameTV;
			public TextView messageTV;
		}
	}
	OnItemClickListener usecenterlitenerinf = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			CommonListViewModel_itt userInfo = data.get(position);
			Toast.makeText(UserCenterInformation.this, userInfo.getName(), 0).show();
			if (position == 0) {
				Intent intentUserCenterInf = new Intent();
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterInformation.class);
				//startActivity(intentUserCenterInf);
				//setResult(Activity.RESULT_OK,intentUserCenterInf);
				
			}
		}
	};

	Thread th=new Thread(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			retMessage = WebPostUtil.getMessage(SERVICE_URL,
					"UseCenterInformation", username);
			message.what = retMessage.getStatus();
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
						u.getEmail()+"23 ", Toast.LENGTH_LONG).show();
				break;
			case ERROR:
				Toast.makeText(getApplicationContext(),
						retMessage.getMessage()+"和服务器没有链接", Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
}
