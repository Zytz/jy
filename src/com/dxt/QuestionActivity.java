package com.dxt;

import java.util.ArrayList;
import java.util.Date;
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

import com.dxt.adapter.ListViewQuestionsAdapter;
import com.dxt.model.OnlineQuestion;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class QuestionActivity extends Activity {

	private List<OnlineQuestion> listItems = new ArrayList<OnlineQuestion>();
	private PullToRefreshListView mPullRefreshListView;
	private ListViewQuestionsAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issue_activity);

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
		initialData();
		mAdapter = new ListViewQuestionsAdapter(getApplicationContext(), listItems, R.layout.question_item);

		// �������󶨷�������һ
		// ����һ
		// mPullRefreshListView.setAdapter(mAdapter);
		// ������
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

	private class GetDataTask extends AsyncTask<Void, Void, OnlineQuestion> {

		// ��̨������
		@Override
		protected OnlineQuestion doInBackground(Void... params) {
			// Simulates a background job.
			OnlineQuestion question2 = new OnlineQuestion();
			question2.setGrade("��һһ");
			question2.setSubject("��ѧ");
			question2.setCreated(new Date(2014, 4, 13));
			question2.setRewardPoint(50);
			question2.setTextDescription("1+3=?");
			question2.setQuestionImage(R.drawable.ask_icon_normal);
			question2.setStudentIcon(R.drawable.go);
			question2.setStudentName("����");
			question2.setAnswerCount(10000);
			listItems.add(question2);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return question2;
		}

		// �����Ƕ�ˢ�µ���Ӧ����������addFirst������addLast()�������¼ӵ����ݼӵ�LISTView��
		// ����AsyncTask��ԭ��onPostExecute���result��ֵ����doInBackground()�ķ���ֵ
		@Override
		protected void onPostExecute(OnlineQuestion result) {
			// ��ͷ��������������
			// ֪ͨ�������ݼ��Ѿ��ı䣬�������֪ͨ����ô������ˢ��mListItems�ļ���
			
			mAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private void initialData() {
		
		OnlineQuestion question1 = new OnlineQuestion();
		question1.setGrade("��һ");
		question1.setSubject("��ѧ");
		question1.setCreated(new Date());
		question1.setRewardPoint(50);
		question1.setTextDescription("1+1=?");
		question1.setQuestionImage(R.drawable.ask_icon_normal);
		question1.setStudentIcon(R.drawable.go);
		question1.setStudentName("��ѧ");
		question1.setAnswerCount(10000);
		
		OnlineQuestion question2 = new OnlineQuestion();
		question2.setGrade("��һһ");
		question2.setSubject("��ѧ");
		question2.setCreated(new Date(2014, 4, 13));
		question2.setRewardPoint(50);
		question2.setTextDescription("1+2=?");
		question2.setQuestionImage(R.drawable.ask_icon_normal);
		question2.setStudentIcon(R.drawable.go);
		question2.setStudentName("��֪");
		question2.setAnswerCount(10000);
		
		listItems.add(question1);
		listItems.add(question2);
	}
}
