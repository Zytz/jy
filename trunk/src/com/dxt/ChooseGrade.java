package com.dxt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.adapter.GradeAdapter;
import com.dxt.adapter.SubjecAdapter;
import com.dxt.util.ValidateUtil;

public class ChooseGrade extends Activity {
	private static int[] grades = {R.string.quanbu,R.string.xiaoxue,R.string.chuyi,R.string.chuer,
		   R.string.chusan,R.string.zhongkao,R.string.gaoyi,R.string.gaoer,R.string.gaosan,R.string.gaokao};
	private static final int[] subjects = {R.string.quanbu,R.string.shuxue,R.string.yuwen,R.string.yingyu,
		   R.string.wuli,R.string.huaxue,R.string.shengwu,R.string.zhengzhi,
		   R.string.lishi,R.string.dili};

	private Button complete ;
	private ImageView cancle;
	private GridView grade_gridview;
	private GridView subject_gridview;
	private int old_grade=0;
	private int old_subject=0;
	private TextView tempView;
	private String temp1="",temp2="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.homework_choose_dialog_grade);
		
		
		tempView = new TextView(getApplicationContext());
		
		grade_gridview = (GridView) this.findViewById(R.id.choose_grade_gv_content);
		subject_gridview = (GridView) this.findViewById(R.id.choose_subject_gv_content);
		
		grade_gridview.setAdapter(new GradeAdapter(getApplicationContext()));
		grade_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
					parent.getChildAt(old_grade).setBackgroundColor(Color.WHITE);
					view.setBackgroundColor(Color.YELLOW);
					old_grade = position;
					tempView.setText(grades[old_grade]);
					temp1 = tempView.getText().toString();
			}
			
		});
		subject_gridview.setAdapter(new SubjecAdapter(getApplicationContext()));
		subject_gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
					parent.getChildAt(old_subject).setBackgroundColor(Color.WHITE);
					view.setBackgroundColor(Color.YELLOW);
					old_subject = position;
					tempView.setText(subjects[old_subject]);
					temp2 = tempView.getText().toString();
			}
			
		});
		
		complete = (Button) this.findViewById(R.id.homework_button_complete);
		complete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//预处理
				//判断是否有点击事件
				
				String grade=null;
				String subject=null;
				if(!ValidateUtil.isValid(temp1)&&!ValidateUtil.isValid(temp2)){
					temp1="全部问题";
					grade="";
					subject="";
				}else if(!ValidateUtil.isValid(temp1)&&ValidateUtil.isValid(temp2)){
					temp1="全部";
					grade="";
				}else if(ValidateUtil.isValid(temp1)&&!ValidateUtil.isValid(temp2)){
					temp2="全部";
					subject="";
				}
				if(grade==null) grade = temp1;
				if(subject==null) subject = temp2;
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				intent.putExtra("cancel", false);
				intent.putExtra("grade", grade);
				intent.putExtra("subject", subject);
				intent.putExtra("title_id", grades[old_grade]+" "+subjects[old_subject]);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		cancle = (ImageView) this.findViewById(R.id.homework_questionlist_circle_iv_choose);
		cancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),MainActivity.class);
				intent.putExtra("cancel", true);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}
	
}
