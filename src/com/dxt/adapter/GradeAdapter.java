package com.dxt.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.dxt.R;

public class GradeAdapter extends BaseAdapter {
	private Context context;
	public GradeAdapter(Context context) {
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return grades.length;
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
		textView.setText(grades[position]);
		if(position==0) textView.setBackgroundColor(Color.YELLOW);
		return textView;
	}
	
	private static int[] grades = {R.string.quanbu,R.string.xiaoxue,R.string.chuyi,R.string.chuer,
								   R.string.chusan,R.string.gaoyi,R.string.gaoer,R.string.gaosan};
}
