package com.dxt.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dxt.CustomApplication;
import com.dxt.QuestionDetailActivity;
import com.dxt.R;
import com.dxt.adapter.ListViewUserCenterMyQuestionsAdapter;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.SearchOnlineQuestionBean;
import com.dxt.model.User;
import com.dxt.util.WebPostUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class UserCenterMyAnswer extends Activity {

	final static String SERVICE_URL = StringConstant.SERVICE_URL
			+ "services/OnlineQuestionService?wsdl";
	final static String TAG = "dxt";
	private List<OnlineQuestion> listItems = new ArrayList<OnlineQuestion>();
	private PullToRefreshListView mPullRefreshListView;
	private ListViewUserCenterMyQuestionsAdapter mAdapter;
	private CustomApplication application;
	private SearchOnlineQuestionBean searchBean;
	private User u;
	private boolean flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usercentermyanswer);

		application = (CustomApplication) getApplication();
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list_usercenter);

		mAdapter = new ListViewUserCenterMyQuestionsAdapter(
				getApplicationContext(), listItems,
				R.layout.usercentermyquestion_item);

		ListView actualListView = mPullRefreshListView.getRefreshableView();

		actualListView.setAdapter(mAdapter);

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						QuestionDetailActivity.class);
				String ques = JSON.toJSONString(listItems.get(position - 1));
				intent.putExtra("question", ques);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
/*		searchBean.setPageNum(0);
		searchBean.setGrade(-1);
		searchBean.setSubject(-1);
		application.setGrade(-1);
		application.setSubject(-1);*/
	}

	private class GetDataTask extends
			AsyncTask<Void, Void, List<OnlineQuestion>> {

		// ��̨������
		@Override
		protected List<OnlineQuestion> doInBackground(Void... params) {
			// Simulates a background job.
			List<OnlineQuestion> myques=null;
			flag=false;
			if(flag){
			u = JSONObject.parseObject(application.getValue(), User.class);
			 myques = WebPostUtil.getOnlineQuestions(
					SERVICE_URL, "getOnlineMyQuestionList", u.getId());
			}
			return myques;
		}

		// �����Ƕ�ˢ�µ���Ӧ����������addFirst������addLast()�������¼ӵ����ݼӵ�LISTView��
		// ����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		@Override
		protected void onPostExecute(List<OnlineQuestion> result) {
			// ��ͷ��������������
			// ֪ͨ�������ݼ��Ѿ��ı䣬�������֪ͨ����ô������ˢ��mListItems�ļ���
			/*
			 * if(searchBean.getGrade().equals(application.getGrade())&&searchBean
			 * .getSubject().equals(application.getSubject())){
			 * listItems.addAll(result);
			 * searchBean.setPageNum(searchBean.getPageNum()+1); }else{
			 * listItems.clear();; listItems.addAll(result);
			 * application.setGrade(searchBean.getGrade());
			 * application.setSubject(searchBean.getSubject());
			 * searchBean.setPageNum(1); }
			 */
			listItems.clear();
			listItems.addAll(result);
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
			
			
			
		}
	}

}
