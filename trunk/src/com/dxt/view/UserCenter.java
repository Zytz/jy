package com.dxt.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dxt.LoginActivity;
import com.dxt.R;
import com.dxt.model.CommonListViewModel;

public class UserCenter extends Activity {

	private String TAG = "dxt";
	private TextView toLogin;
	
	private ImageView img = null;
	
	private ListView lv_usercenter;
	private List<CommonListViewModel> data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenter);
		init();

		String[] user_={"个人中心","我的提问","我的解答","我的金币"};

		
	}
	private void init() {
		img = (ImageView) findViewById(R.id.user_icon);
		toLogin=(TextView) this.findViewById(R.id.user_notlogin);

		toLogin.setOnClickListener(toLoginListener);

		lv_usercenter =(ListView) this.findViewById(R.id.lv_usercenter);
		data = getData();
		lv_usercenter.setAdapter(new MyBaseAdapter());
		
		//lv_usercenter.setOnItemClickListener(listener);
		lv_usercenter.setOnItemClickListener(usecenterlitener);
	}

	//如果未登录的时候

	private List<CommonListViewModel> getData() {

		List<CommonListViewModel> list = new ArrayList<CommonListViewModel>();
		String[] usercenter_inf={"个人中心","我的提问","我的回答","我的金币"};

		for (int i = 1; i <= 4; i++) {
			CommonListViewModel info = new CommonListViewModel(R.drawable.usercenter1, usercenter_inf[i-1], R.drawable.usercenter2
					+ i);
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

	OnClickListener toLoginListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent tologin_intent=new Intent();
			tologin_intent.setClass(getApplicationContext(), LoginActivity.class);
			startActivity(tologin_intent);
		}
	};
	
	OnItemClickListener usecenterlitener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			CommonListViewModel userInfo = data.get(position);
			Toast.makeText(UserCenter.this, userInfo.getName(), 0).show();
		}
	};
}
