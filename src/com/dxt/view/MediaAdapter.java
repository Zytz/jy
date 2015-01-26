package com.dxt.view;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dxt.R;

public class MediaAdapter extends BaseAdapter 
{
	private  List<Map<String, Object>> videoDataList;
	private  Map<String, Object> videoMap;
	private int resource;
	private LayoutInflater inflater;
	private TextView mediaNameView;
	private TextView mediaNameViewCache;
	
	public MediaAdapter(Context conrext,int resource, List<Map<String, Object>> videoDataList)
	{
		inflater=(LayoutInflater)conrext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.resource = resource;
		this.videoDataList = videoDataList;
	}
	
	@Override
	public int getCount()
	{
		return videoDataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return videoDataList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(convertView==null)
		{
		 convertView=inflater.inflate(resource, null);
		 mediaNameView=(TextView) convertView.findViewById(R.id.mediaName);
		 mediaNameViewCache=mediaNameView;
		 convertView.setTag(mediaNameViewCache);
		}
		else
		{
			mediaNameView=(TextView) convertView.getTag();
		}
		videoMap = videoDataList.get(position);
		mediaNameView.setText((String)videoMap.get("title"));
		return convertView;
	}
}
