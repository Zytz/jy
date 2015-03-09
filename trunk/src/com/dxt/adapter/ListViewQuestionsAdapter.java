package com.dxt.adapter;

import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.R;
import com.dxt.model.OnlineQuestion;

public class ListViewQuestionsAdapter extends BaseAdapter {
	private Context 					context;//����������
	private List<OnlineQuestion> 		listItems;//���ݼ���
	private LayoutInflater 				listContainer;//��ͼ����
	private int 						itemViewResource;//�Զ�������ͼԴ 
	static class ListItemView{				//�Զ���ؼ�����  
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
		listItemView.questionImage.setBackgroundResource(R.drawable.ic_launcher);
		listItemView.studentIcon.setBackgroundResource(R.drawable.ic_launcher);
		listItemView.studentName.setText(onlineQuestion.getStudentName());
		listItemView.answerCount.setText(String.valueOf(onlineQuestion.getAnswerCount()));
		return convertView;
	}
}
