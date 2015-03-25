package com.dxt.adapter;

import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.MyImageView;
import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestionAnswer;
import com.dxt.model.User;
import com.dxt.util.ImageUtil;
import com.dxt.util.WebPostUtil;

public class ListViewAnswerAdapter extends BaseAdapter {
	final static String SERVICE_URL = StringConstant.SERVICE_URL+ "services/UserService?wsdl";
	private Context context;// 运行上下文
	private List<OnlineQuestionAnswer> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器
	private int itemViewResource;// 自定义项视图源
	private static User u = new User();
	static class ListitemView {
		private ImageView authorPicture;
		private TextView authorName;
		private TextView answerContent;
		private MyImageView answerPicture;
		private TextView date;
	}

	public ListViewAnswerAdapter(Context context,
			List<OnlineQuestionAnswer> listItems, int itemViewResource) {
		this.context = context;
		this.listItems = listItems;
		this.itemViewResource = itemViewResource;
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
			convertView.setTag(listitemView);
		} else {
			listitemView = (ListitemView) convertView.getTag();
		}
		
		OnlineQuestionAnswer answer = listItems.get(position);
		String authorId = answer.getAnswerAuthorId();
		Thread thread = new Thread(new Helper(authorId));
		thread.start();
		ImageUtil.LoadImage(context, u.getIcon(), listitemView.authorPicture);
		listitemView.authorName.setText(answer.getAnswerAuthor());
		listitemView.answerContent.setText(answer.getTextAnswer());
		ImageUtil.LoadImage(context, answer.getImageAnswer(),
				listitemView.answerPicture);
		listitemView.date.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss",
				answer.getCreated()));
		return convertView;
	}
	
	static class Helper implements Runnable{

		private String id;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			 u = WebPostUtil.getUserById(SERVICE_URL, "getUserByID", id);
		}

		public Helper(String id) {
			this.id = id;
		}
		
	}
}
