package com.dxt.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dxt.MyImageView;
import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.OnlineQuestionAnswer;
import com.dxt.model.User;
import com.dxt.util.ImageUtil;
import com.dxt.util.ReturnMessage;
import com.dxt.util.WebPostUtil;

public class ListViewAnswerAdapter extends BaseAdapter {
	final static String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/UserService?wsdl";
	final static String SERVICE_URL1 = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";
	private OnlineQuestion onlineQuestion;
	private String userInfo;
	private Context context;// 运行上下文
	private List<OnlineQuestionAnswer> listItems;// 数据集合
	private List<ImageView> views = new ArrayList<ImageView>();
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private static User answerAuthor = new User();
	private ReturnMessage m = new ReturnMessage();
	private boolean flag =false;
	static class ListitemView {
		private ImageView adopted;
		private ImageView authorPicture;
		private TextView authorName;
		private TextView answerContent;
		private MyImageView answerPicture;
		private TextView date;
	}
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
				case -1: Toast.makeText(context, "请先登入", 200).show();
						break;
				case -2: Toast.makeText(context, "你不是该问题的提问者！", 200).show();
						break;
				case -3: Toast.makeText(context, "不能采纳自己的回答！", 200).show();
						break;
				case -4: Toast.makeText(context, m.getMessage(),200 ).show();
						break;
				case -5: Toast.makeText(context, m.getMessage(), 200).show();
						break;
				case 1: Toast.makeText(context, "采纳成功！", 200).show();
				default: break;
			}
		}
		
	};
	public ListViewAnswerAdapter(Context context,
			List<OnlineQuestionAnswer> listItems, int itemViewResource,OnlineQuestion onlineQuestion,String userInfo) {
		this.context = context;
		this.listItems = listItems;
		this.itemViewResource = itemViewResource;
		this.listContainer = LayoutInflater.from(context);
		this.onlineQuestion = onlineQuestion;
		this.userInfo = userInfo;
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
		ListitemView listitemView = null;
		if (convertView == null) {
			convertView = listContainer.inflate(itemViewResource, null);
			listitemView = new ListitemView();
			listitemView.authorPicture = (ImageView) convertView
					.findViewById(R.id.answer_listitem_userface);
			listitemView.authorName = (TextView) convertView
					.findViewById(R.id.answer_listitem_username);
			listitemView.answerContent = (TextView) convertView
					.findViewById(R.id.asnwer_listitem_content);
			listitemView.answerPicture = (MyImageView) convertView
					.findViewById(R.id.answer_listitem_picture);
			listitemView.date = (TextView) convertView
					.findViewById(R.id.answer_listitem_date);
			listitemView.adopted = (ImageView) convertView.findViewById(R.id.answer_listitem_adopt);
			ImageView v = listitemView.adopted;
			views.add(v);
			listitemView.adopted.setOnClickListener(new AdoptedListener(position,v));
			convertView.setTag(listitemView);
		} else {
			listitemView = (ListitemView) convertView.getTag();
		}

		OnlineQuestionAnswer answer = listItems.get(position);
		String authorId = answer.getAnswerAuthorId();
		Thread thread = new Thread(new Helper(authorId));
		thread.start();
		if (answerAuthor.getIcon() == null)
			ImageUtil.LoadImage(context, "static/avatarimages/logo.png",
					listitemView.authorPicture);
		ImageUtil.LoadImage(context, answerAuthor.getIcon(), listitemView.authorPicture);
		listitemView.authorName.setText(answer.getAnswerAuthor());
		listitemView.answerContent.setText(answer.getTextAnswer());
		if (answer.getImageAnswer() == null)
			ImageUtil.LoadImage(context, "static/onlineQuestionAndAnswerImages/answer_default.png",
					listitemView.answerPicture);
		ImageUtil.LoadImage(context, answer.getImageAnswer(),
					listitemView.answerPicture);
		listitemView.date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
				answer.getCreated()));
		if(!flag){
			if(answer.getAdopted()==1) {
				listitemView.adopted.setPressed(true);
				listitemView.adopted.setEnabled(false);
				flag = true;
			}
		}else{
			listitemView.adopted.setEnabled(false);
		}
		return convertView;
	}

	static class Helper implements Runnable {

		private String id;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			answerAuthor = WebPostUtil.getUserById(SERVICE_URL, "getUserByID", id);
		}

		public Helper(String id) {
			this.id = id;
		}

	}
	
	 class Helper1 implements Runnable {

		private int position;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			OnlineQuestionAnswer answer = listItems.get(position);
			if(userInfo.equals("")) {
				message.what =-1;
				handler.sendMessage(message);
				return;
			}
			User u = JSONObject.parseObject(userInfo, User.class);
			if(!u.getId().equals(onlineQuestion.getStudentId())){
				message.what =-2;
				handler.sendMessage(message);
				return;
			}
			if(onlineQuestion.getStudentId().equals(answer.getAnswerAuthorId())){
				message.what =-3;
				handler.sendMessage(message);
				return;
			}
			String ret = WebPostUtil.processOrder(SERVICE_URL1, "processAdopt",JSONObject.toJSONString(onlineQuestion), JSONObject.toJSONString(answer));
			if(ret == null){
				message.what =-5;
			}else{
				m = (ReturnMessage) JSONObject.parseObject(ret, ReturnMessage.class);
				message.what = m.getStatus();
			}
			handler.sendMessage(message);
		}

		public Helper1(int position) {
			this.position = position;
		}

	}
	
	 class AdoptedListener implements View.OnClickListener{
		 
		private final int position;
		private ImageView view;
		public AdoptedListener(int position,ImageView v) {
			this.position = position;
			this.view =v;
		}
		 	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 AlertDialog.Builder builder = new Builder(context);
			 builder.setMessage("确定采纳此答案吗？");
			 builder.setTitle("提示");
			 builder.setPositiveButton("确认", new OnClickListener() {
				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 Thread t = new Thread(new Helper1(position));
					 t.start();
					 view.setPressed(true);
					 view.setEnabled(false);
					 for(ImageView t1:views){
						 t1.setEnabled(false);
					 }
					 dialog.dismiss();
				 }
		     });
			 builder.setNegativeButton("取消", new OnClickListener() {
				 @Override
				 public void onClick(DialogInterface dialog, int which) {
					 dialog.dismiss();
				 }
			  });
			 builder.create().show();
		}	
	};
}
