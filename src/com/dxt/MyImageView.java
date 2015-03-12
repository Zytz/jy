package com.dxt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	private String uri;
	public MyImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyImageView(Context context, AttributeSet attrs) {
	        super(context, attrs, 0);
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
