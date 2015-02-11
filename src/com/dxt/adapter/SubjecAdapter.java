package com.dxt.adapter;

import com.dxt.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SubjecAdapter extends BaseAdapter {

	private Context context;
	
	public SubjecAdapter(Context context) {
		this.context = context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return subjects.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView textView;
		if(convertView==null){
			textView = new TextView(context);
			textView.setLayoutParams(new GridView.LayoutParams(65, 65));
            textView.setPadding(8, 8, 8, 8);
		}else{
			textView = (TextView) convertView;
		}
		textView.setText(subjects[position]);
		return textView;
	}
	
	private static final int[] subjects = {R.string.quanbu,R.string.shuxue,R.string.yuwen,R.string.yingyu,
										   R.string.wuli,R.string.huaxue,R.string.shengwu,R.string.zhengzhi,
										   R.string.lishi,R.string.dili};

}
