package com.dxt;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dxt.adapter.ListViewQuestionsAdapter;
import com.dxt.constant.StringConstant;
import com.dxt.model.OnlineQuestion;
import com.dxt.model.SearchOnlineQuestionBean;
import com.dxt.util.WebPostUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
/**
 * @author Administrator
 *
 */
public class QuestionActivity extends Activity {
	
	final static String SERVICE_URL = StringConstant.SERVICE_URL+ "services/OnlineQuestionService?wsdl";
	final static String TAG = "dxt";
	private List<OnlineQuestion> listItems = new ArrayList<OnlineQuestion>();
	private PullToRefreshListView mPullRefreshListView;
	private ListViewQuestionsAdapter mAdapter;
	private CustomApplication application ;
	private SearchOnlineQuestionBean searchBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issue_activity);

		application = (CustomApplication) getApplication();
		
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {

					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {

						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						new GetDataTask().execute();

					}
				});
		
		mAdapter = new ListViewQuestionsAdapter(getApplicationContext(), listItems, R.layout.question_item);
		
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		
		actualListView.setAdapter(mAdapter);
		
		
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"hello", 1000).show();
			}
		});
	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		new GetDataTask().execute();
	}

	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		searchBean.setPageNum(0);
		searchBean.setGrade("");
		searchBean.setSubject("");
		application.setGrade("");
		application.setSubject("");
	}

	

	private class GetDataTask extends AsyncTask<Void, Void, List<OnlineQuestion>> {

		// 后台处理部分
		@Override
		protected List<OnlineQuestion> doInBackground(Void... params) {
			// Simulates a background job.
			searchBean = application.getSearchBean();
			List<OnlineQuestion> ques = WebPostUtil.getOnlineQuestions(SERVICE_URL, "getOnlineQuestionList", JSON.toJSONString(searchBean));
			
			return ques;
		}

		// 这里是对刷新的响应，可以利用addFirst（）和addLast()函数将新加的内容加到LISTView中
		// 根据AsyncTask的原理，onPostExecute里的result的值就是doInBackground()的返回值
		@Override
		protected void onPostExecute(List<OnlineQuestion> result) {
			// 在头部增加新添内容
			// 通知程序数据集已经改变，如果不做通知，那么将不会刷新mListItems的集合
			if(searchBean.getGrade().equals(application.getGrade())&&searchBean.getSubject().equals(application.getSubject())){
				listItems.addAll(result);
				searchBean.setPageNum(searchBean.getPageNum()+1);
			}else{
				listItems.removeAll(listItems);
				listItems.addAll(result);
				application.setGrade(searchBean.getGrade());
				application.setSubject(searchBean.getSubject());
				searchBean.setPageNum(1);
			}
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

}
