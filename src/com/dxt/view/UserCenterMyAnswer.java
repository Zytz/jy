package com.dxt.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.dxt.R;
import com.dxt.model.CommonListViewModel;

public class UserCenterMyAnswer extends Activity {

	private String TAG = "dxt";
	private TextView toLogin;
	
	private ImageView img = null;
	
	private ListView lv_usercenterinf;
	private List<CommonListViewModel> data;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercenterinformation);
		init();
	}
	private void init() {
		lv_usercenterinf = (ListView) this.findViewById(R.id.lv_usercenterinf);
		data = getData();
		lv_usercenterinf.setAdapter(new MyBaseAdapter());
		lv_usercenterinf.setOnItemClickListener(usecenterlitenerinf);
	}
	private List<CommonListViewModel> getData() {

		List<CommonListViewModel> list = new ArrayList<CommonListViewModel>();
		String[] usercenter_inf = { "昵称", "学校名称","年级","性别", "电话"};

		for (int i = 1; i <= 5; i++) {
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
				convertView = View.inflate(UserCenterMyAnswer.this,
						R.layout.usercenterinf_row, null);
				viewHolder.iconIV = (ImageView) convertView
						.findViewById(R.id.iv_item_image_inf);
				viewHolder.nameTV = (TextView) convertView
						.findViewById(R.id.iv_item_inf_inf);
			/*	viewHolder.messageTV = (ImageView) convertView
						.findViewById(R.id.iv_item_imagetogo_inf);*/
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
	OnItemClickListener usecenterlitenerinf = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			CommonListViewModel userInfo = data.get(position);
			Toast.makeText(UserCenterMyAnswer.this, userInfo.getName(), 0).show();
			if (position == 0) {
				Intent intentUserCenterInf = new Intent();
				intentUserCenterInf.setClass(getApplicationContext(),
						UserCenterMyAnswer.class);
				//startActivity(intentUserCenterInf);
				//setResult(Activity.RESULT_OK,intentUserCenterInf);
				
			}
		}
	};


}
