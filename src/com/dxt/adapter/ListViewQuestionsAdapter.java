package com.dxt.adapter;

import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dxt.MyImageView;
import com.dxt.R;
import com.dxt.model.OnlineQuestion;
import com.dxt.util.ImageUtil;
import com.dxt.util.StringUtil;

public class ListViewQuestionsAdapter extends BaseAdapter {
	private Context 					context;//运行上下文
	private List<OnlineQuestion> 		listItems;//数据集合
	private LayoutInflater 				listContainer;//视图容器
	private int 						itemViewResource;//自定义项视图源 
	
	static class ListItemView{				//自定义控件集合  
        public TextView grade;
        public TextView subject;
	    public TextView date;  
	    public TextView rewardPoint;
	    public TextView textDescription;
	    public MyImageView questionImage;
	    public MyImageView studentIcon;
	    public TextView studentName;
	    public TextView answerCount;
 }  
	public ListViewQuestionsAdapter(Context context,List<OnlineQuestion> data,int resource) {
		this.context = context;
		this.listItems=data;
		this.itemViewResource = resource;
		this.listContainer = LayoutInflater.from(context);
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
			listItemView.grade = (TextView) convertView.findViewById(R.id.waitforanswer_question_item_user_grade);
			listItemView.subject = (TextView) convertView.findViewById(R.id.waitforanswer_question_item_question_course);
			listItemView.date	=(TextView) convertView.findViewById(R.id.waitforanswer_question_item_question_time);
			listItemView.rewardPoint = (TextView) convertView.findViewById(R.id.waitforanswer_question_rewrad);
			listItemView.textDescription = (TextView) convertView.findViewById(R.id.waitforanswer_question_name);
			listItemView.questionImage = (MyImageView) convertView.findViewById(R.id.waitforanswer_question_picture);
			listItemView.studentIcon = (MyImageView) convertView.findViewById(R.id.homework_question_item_iv_user_picture);
			listItemView.studentName = (TextView) convertView.findViewById(R.id.homework_question_item_tv_user_name);
			listItemView.answerCount = (TextView) convertView.findViewById(R.id.homework_questionlist_item_bottom_ans_num_text_id);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView)convertView.getTag();
		}
		OnlineQuestion onlineQuestion = listItems.get(position);
		listItemView.grade.setText(StringUtil.int2StringOfGrade(onlineQuestion.getGrade()));
		listItemView.subject.setText(StringUtil.int2StringOfGrade(onlineQuestion.getSubject()));
		listItemView.date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", onlineQuestion.getCreated()));
		listItemView.rewardPoint.setText(String.valueOf(onlineQuestion.getRewardPoint()));
		listItemView.textDescription.setText(onlineQuestion.getTextDescription());
		ImageUtil.LoadImage(context, onlineQuestion.getQuestionImage(), listItemView.questionImage);
		//ImageLoader.getInstance().displayImage(StringConstant.SERVICE_URL+onlineQuestion.getQuestionImage(), listItemView.questionImage, options, animateFirstListener);
		listItemView.questionImage.setUri(onlineQuestion.getQuestionImage());
		//ImageLoader.getInstance().displayImage(StringConstant.SERVICE_URL+onlineQuestion.getStudentIcon(), listItemView.studentIcon, options, animateFirstListener);
		ImageUtil.LoadImage(context, onlineQuestion.getStudentIcon(), listItemView.studentIcon);
		listItemView.studentIcon.setUri(onlineQuestion.getStudentIcon());
		
		listItemView.studentName.setText(onlineQuestion.getStudentName());
		listItemView.answerCount.setText(String.valueOf(onlineQuestion.getAnswerCount()));
		return convertView;
	}
}
