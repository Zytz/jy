package com.dxt.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ListViewQuestionsAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<OnlineQuestion> 		listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	
	DisplayImageOptions options;
	ImageLoadingListener animateFirstListener;
	static class ListItemView{				//自定义控件集合  
        public TextView grade;
        public TextView subject;
	    public TextView date;  
	    public TextView rewardPoint;
	    public TextView textDescription;
	    public ImageView questionImage;
	    public ImageView studentIcon;
	    public TextView studentName;
	    public TextView answerCount;
 }  
	public ListViewQuestionsAdapter(Context context,List<OnlineQuestion> data,int resource) {
		this.context = context;
		this.listItems=data;
		this.itemViewResource = resource;
		this.listContainer = LayoutInflater.from(context);
		initalImageLoader();
	}
	
	private void initalImageLoader() {
		// TODO Auto-generated method stub
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
										  .writeDebugLogs().build();
		animateFirstListener = new AnimateFirstDisplayListener();
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new RoundedBitmapDisplayer(20))
		.build();
		
		ImageLoader.getInstance().init(config);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
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
		ListItemView listItemView = null;
		if(convertView==null){
			convertView = listContainer.inflate(this.itemViewResource, null);
			
			listItemView = new ListItemView();
			listItemView.grade = (TextView) convertView.findViewById(R.id.question_item_user_grade);
			listItemView.subject = (TextView) convertView.findViewById(R.id.question_item_question_course);
			listItemView.date	=(TextView) convertView.findViewById(R.id.question_item_question_time);
			listItemView.rewardPoint = (TextView) convertView.findViewById(R.id.question_rewrad);
			listItemView.textDescription = (TextView) convertView.findViewById(R.id.question_name);
			listItemView.questionImage = (ImageView) convertView.findViewById(R.id.question_picture);
			listItemView.studentIcon = (ImageView) convertView.findViewById(R.id.homework_question_item_iv_user_picture);
			listItemView.studentName = (TextView) convertView.findViewById(R.id.homework_question_item_tv_user_name);
			listItemView.answerCount = (TextView) convertView.findViewById(R.id.homework_questionlist_item_bottom_ans_num_text_id);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		OnlineQuestion onlineQuestion = listItems.get(position);
		listItemView.grade.setText(onlineQuestion.getGrade());
		listItemView.subject.setText(onlineQuestion.getSubject());
		listItemView.date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", onlineQuestion.getCreated()));
		listItemView.rewardPoint.setText(String.valueOf(onlineQuestion.getRewardPoint()));
		listItemView.textDescription.setText(onlineQuestion.getTextDescription());
		ImageLoader.getInstance().displayImage(StringConstant.SERVICE_URL+onlineQuestion.getQuestionImage(), listItemView.questionImage, options, animateFirstListener);
		ImageLoader.getInstance().displayImage(StringConstant.SERVICE_URL+onlineQuestion.getStudentIcon(), listItemView.studentIcon, options, animateFirstListener);
		listItemView.studentName.setText(onlineQuestion.getStudentName());
		listItemView.answerCount.setText(String.valueOf(onlineQuestion.getAnswerCount()));
		return convertView;
	}
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
