package com.dxt.view;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.dxt.util.DisplayUtil;
import com.dxt.util.SDCardMedia;
import com.dxt.util.TimeFormate;


public class MediaPlayerActivity extends ListActivity {
	
	private  List<Map<String, Object>> videoDataList;
	private  Map<String, Object> videoMap;
	
	private TextView  currentTime, maxTime;//nameText
	private ImageView goView;
	private SeekBar timebar;
	private String path;
	private String filepath;

	private MediaPlayer mediaPlayer;
	private SurfaceView surfaceView;
	private boolean pause, filechanged, Handlerpost;
	private int position;
	private boolean isComeFromList;//volumeBarVisible,isMute, hasFile;
	//private static final int LIST = 1, UPDATELIST = 2, ABOUT = 3, EXIT = 4;

	private static final String TAG = "MediaPlay";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.video);
		mediaPlayer = new MediaPlayer();
		currentTime = (TextView) findViewById(R.id.curtime);
		maxTime = (TextView) findViewById(R.id.maxtime);
		goView = (ImageView) findViewById(R.id.gobutton);
		timebar = (SeekBar) findViewById(R.id.timebar);
		timebar.setOnSeekBarChangeListener(new SeekBarListener());
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

		surfaceView.setOnTouchListener(new TouchListener());
		// �����͸�surfaceView����Ƶ���棬ֱ����ʾ����Ļ��,��Ҫά��������Ļ�����
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 100);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
		//��ʾý���б�
		ShowMediaList();
		Intent intent =getIntent();
		if((filepath = intent.getStringExtra("path"))!=null){
			filepath = StringConstant.SERVICE_URL+filepath;
			playMedia();
		}
	}
	
	
	private void ShowMediaList() 
	{
		getAllVideos();
		setListAdapter(new MediaAdapter(this, R.layout.media_row, videoDataList));
	}
	
	private void getAllVideos() 
	{
		videoDataList=new SDCardMedia(this).getAllVideos();		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) //��Ŀ���ʱ�����ؽ��
	{
		videoMap = videoDataList.get(position);
		//����¼���ȡ·��
		String str=(String) videoMap.get("path");
		filepath=str;
		File file =new File(filepath);
		if(file.exists()&&file.isFile()){			
			playMedia();
		}
		Log.v("path", str);
	}
	
	
	/*
	 * ��SurfaceView���ڵ�Activity�뿪��ǰ̨,SurfaceView�ᱻdestroy,
	 * ��Activity�ֻص���ǰ̨ʱ��SurfaceView�ᱻ���´�������������OnResume()����֮�󱻴���
	 */
	private final class SurfaceCallback implements Callback {
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		public void surfaceCreated(SurfaceHolder holder) // ����SurfaceViewʱ��ʼ���ϴ�λ�ò��Ż����²���
		{
			if (isComeFromList) {
				playMedia();
				isComeFromList = false;
		} else if (position > 0 && path != null) {
				play(position);
				position = 0;
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) // �뿪SurfaceViewʱֹͣ���ţ����沥��λ��
		{
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}
	}

	@Override
	protected void onDestroy()// Activity����ʱ�ͷ�mediaPlayer����������ӹ���Ƶ�б��ٴδ�ʱ��
	{

		mediaPlayer.stop();
		mediaPlayer.release();
		mediaPlayer = null;
		super.onDestroy();
	}
	
	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		updateBarHandler.removeCallbacks(updateThread);
		updateThread=null;
		Handlerpost=false;
		super.onBackPressed();
	}


	public void mediaplay(View v)// ���ſ���
	{
		switch (v.getId()) {
		
		case R.id.playbutton:
			playMedia();
			break;
			
		case R.id.fullscreen:
			fullScreenPlay();
			break;
		case R.id.gobutton:
			continueMedia();
			break;
		}
	}
/*
 * ������Ƶ�����ַ�ʽ��һ����http ����һ�������߱��ز���
 */
	public void playMedia()// ������Ƶ
	{
		
		if (filepath != null && !filepath.equals("")) {
			if (filepath.startsWith("http"))// ���߲���HTTP��Ƶ��Դ
			{
				path = filepath;
				if (pause)
					goView.setVisibility(ViewGroup.INVISIBLE);
				play(0);
				filechanged = true;
				if (!Handlerpost) {
					updateBarHandler.post(updateThread);
					Handlerpost = true;
				}
			} else// ���ű�����Ƶ
			{
				File file = new File(filepath);
				if (file.exists()) {
					path = file.getAbsolutePath();
					if (pause)
						goView.setVisibility(ViewGroup.INVISIBLE);
					play(0);
					filechanged = true;
					if (!Handlerpost) {
						updateBarHandler.post(updateThread);
						Handlerpost = true;
					}
				} else {
					path = null;
					//Toast.makeText(this, R.string.filenoexsit, 1).show();
				}
			}
		} else
			Toast.makeText(this, R.string.notchoose, 1).show();
	}

	public void pauseMedia() // ��ͣ����
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			pause = true;
			goView.setVisibility(ViewGroup.VISIBLE);
		} else {
			continueMedia();
		}
	}

	public void replayMedia() // �ز�
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.setDisplay(surfaceView.getHolder());
			Point p = DisplayUtil.getScreenMetrics(getApplicationContext());
			surfaceView.getHolder().setFixedSize(p.y, p.x);
			Log.v(TAG, "PX :" + p.x + " PY:" + p.y);
		} else {
			if (path != null) {
				if (pause)
					goView.setVisibility(ViewGroup.INVISIBLE);
				play(0);
			}
		}
		if (path != null) {
			if (!Handlerpost) {
				//updateBarHandler.post(updateThread);
				Handlerpost = true;
			}
		}
	}

	public void fullScreenPlay(){
		Point p = DisplayUtil.getScreenMetrics(getApplicationContext());
		surfaceView.getHolder().setFixedSize(p.y, p.x);
		Log.v(TAG, "PX :" + p.x + " PY:" + p.y);
		mediaPlayer.setDisplay(surfaceView.getHolder());
		mediaPlayer.start();
		playMedia();
	}
	public void stopMedia()// ֹͣ����
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			timebar.setProgress(0);
			currentTime.setText("00:00:00");
			maxTime.setText("00:00:00");
		}
	}
	public void continueMedia()// ����������ͣ����Ƶ
	{
		if (pause) {
			goView.setVisibility(ViewGroup.INVISIBLE);
			mediaPlayer.start();
			pause = false;
		}
	}

	private void play(int position)// ����mediaPlayer������Ƶ
	{
		try {
			//mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepare();// ����
			mediaPlayer.setOnPreparedListener(new PrepareListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final class PrepareListener implements OnPreparedListener// ����mediaPlayer�Ƿ񻺳����
	{
		private int position;

		public PrepareListener(int position) {
			this.position = position;
		}

		public void onPrepared(MediaPlayer mp)// �������
		{
			mediaPlayer.start();// ������Ƶ
			if (position > 0)
				mediaPlayer.seekTo(position);
		}
	}
	class TouchListener implements OnTouchListener// SurfaceView����������
	{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN)// ������Ļʱֹͣ���Ų���ʾֹͣ�ؼ�
			{
				if (mediaPlayer.isPlaying()){
					mediaPlayer.pause();
					pause = true;

					goView.setVisibility(ViewGroup.VISIBLE);
				}
				return true;
			}
			return false;
		}
	}
	
	Handler updateBarHandler = new Handler()// �ڲ���Ϣ�����࣬��Ҫ��ȡupdateThread������CurrentPosition��MaxPosition���ø�SeekBar
	{
		public void handleMessage(Message msg) {
			if (mediaPlayer!=null && mediaPlayer.isPlaying()) {
				if (filechanged) {
					timebar.setMax(msg.getData().getInt("MaxPosition") - 1);
					maxTime.setText(new TimeFormate(mediaPlayer.getDuration())
							.formatetime());
					filechanged = false;
				}
				timebar.setProgress(msg.getData().getInt("CurrentPosition"));
				currentTime.setText(new TimeFormate(mediaPlayer
						.getCurrentPosition()).formatetime());
			}
			updateBarHandler.post(updateThread);
		}

	};
	Runnable updateThread = new Runnable()// �ڲ��߳��࣬��Ҫ��ȡmediaPlayer��CurrentPosition��MaxPosition���͸�Handler����
	{
		int CurrentPosition = 0, MaxPosition = 0;

		@Override
		public void run() {
			Message msg = updateBarHandler.obtainMessage();
			Bundle bundle = msg.getData();
			CurrentPosition = mediaPlayer.getCurrentPosition();
			bundle.putInt("CurrentPosition", CurrentPosition);
			if (filechanged) {
				MaxPosition = mediaPlayer.getDuration();
				bundle.putInt("MaxPosition", MaxPosition);
			}
			msg.setData(bundle);
			try {
				Thread.sleep(500);// ���õ�ǰ�߳�˯��500����
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if ((CurrentPosition > MaxPosition - 1)
					&& (CurrentPosition != 0 && MaxPosition != 0)) {
				updateBarHandler.removeCallbacks(updateThread);
				CurrentPosition = 0;
				MaxPosition = 0;
				Handlerpost = false;
			} else {
				updateBarHandler.sendMessage(msg);
			}
		}
	};

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener// SeekBar������
	{
		int startPosition;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			currentTime.setText(new TimeFormate(seekBar.getProgress())
					.formatetime());
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			startPosition = seekBar.getProgress();
		}
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) // ���û������Ի���Ļ���ʱ����mediaPlayer����λ����Ϊ���������Ӧλ��
		{
			currentTime.setText(new TimeFormate(seekBar.getProgress())
					.formatetime());
			if (mediaPlayer.isPlaying())
				mediaPlayer.seekTo(seekBar.getProgress());
			else
				seekBar.setProgress(startPosition);
		}
	}
	
}