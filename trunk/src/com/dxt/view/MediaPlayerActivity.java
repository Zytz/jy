package com.dxt.view;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
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
	//private String[] filepaths;
	//private Map<String, Object> videoMap;
	private MediaPlayer mediaPlayer;
	//private AudioManager audioManager;
	private SurfaceView surfaceView;
	//private ProgressDialog dialog;
	private boolean pause, filechanged, Handlerpost;
	private int position;
	//private int Index;
	//private int count;
	//private int maxVolume, curVolume, i;
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
		// 把输送给surfaceView的视频画面，直接显示到屏幕上,不要维持它自身的缓冲区
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 100);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
		//显示媒体列表
		ShowMediaList();
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
	protected void onListItemClick(ListView l, View v, int position, long id) //条目点击时，返回结果
	{
		videoMap = videoDataList.get(position);
		//点击事件获取路径
		String str=(String) videoMap.get("path");
		filepath=str;
		File file =new File(filepath);
		if(file.exists()&&file.isFile()){			
			playMedia();
		}
		Log.v("path", str);
	}
	
	
	/*
	 * 当SurfaceView所在的Activity离开了前台,SurfaceView会被destroy,
	 * 当Activity又回到了前台时，SurfaceView会被重新创建，并且是在OnResume()方法之后被创建
	 */
	private final class SurfaceCallback implements Callback {
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}

		public void surfaceCreated(SurfaceHolder holder) // 创建SurfaceView时开始从上次位置播放或重新播放
		{
			if (isComeFromList) {
				playMedia();
				isComeFromList = false;
		} else if (position > 0 && path != null) {
				play(position);
				position = 0;
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) // 离开SurfaceView时停止播放，保存播放位置
		{
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				position = mediaPlayer.getCurrentPosition();
				mediaPlayer.stop();
			}
		}
	}

	@Override
	protected void onDestroy()// Activity销毁时释放mediaPlayer，保存已添加过视频列表，再次打开时用
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


	public void mediaplay(View v)// 播放控制
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
 * 播放视频有两种方式，一种是http 另外一种是在线本地播放
 */
	public void playMedia()// 播放视频
	{
		
		if (filepath != null && !filepath.equals("")) {
			if (filepath.startsWith("http"))// 在线播放HTTP视频资源
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
			} else// 播放本地视频
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

	public void pauseMedia() // 暂停播放
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			pause = true;
			goView.setVisibility(ViewGroup.VISIBLE);
		} else {
			continueMedia();
		}
	}

	public void replayMedia() // 重播
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
	public void stopMedia()// 停止播放
	{
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			timebar.setProgress(0);
			currentTime.setText("00:00:00");
			maxTime.setText("00:00:00");
		}
	}
	public void continueMedia()// 继续播放暂停的视频
	{
		if (pause) {
			goView.setVisibility(ViewGroup.INVISIBLE);
			mediaPlayer.start();
			pause = false;
		}
	}

	private void play(int position)// 设置mediaPlayer播放视频
	{
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(path);
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.prepare();// 缓冲
			mediaPlayer.setOnPreparedListener(new PrepareListener(position));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final class PrepareListener implements OnPreparedListener// 监听mediaPlayer是否缓冲完毕
	{
		private int position;

		public PrepareListener(int position) {
			this.position = position;
		}

		public void onPrepared(MediaPlayer mp)// 缓冲完毕
		{
			mediaPlayer.start();// 播放视频
			if (position > 0)
				mediaPlayer.seekTo(position);
		}
	}
	class TouchListener implements OnTouchListener// SurfaceView触摸监听类
	{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN)// 触摸屏幕时停止播放并显示停止控件
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
	
	Handler updateBarHandler = new Handler()// 内部消息队列类，主要获取updateThread发来的CurrentPosition和MaxPosition设置给SeekBar
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
	Runnable updateThread = new Runnable()// 内部线程类，主要获取mediaPlayer的CurrentPosition和MaxPosition发送给Handler处理
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
				Thread.sleep(500);// 设置当前线程睡眠500毫秒
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

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener// SeekBar监听类
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
		public void onStopTrackingTouch(SeekBar seekBar) // 当用户结束对滑块的滑动时，将mediaPlayer播放位置设为滑块结束对应位置
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