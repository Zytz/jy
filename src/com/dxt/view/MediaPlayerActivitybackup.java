package com.dxt.view;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
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


public class MediaPlayerActivitybackup extends ListActivity {
	
	private  List<Map<String, Object>> videoDataList;
	private  Map<String, Object> videoMap;
	
	private TextView nameText, currentTime, maxTime;
	private ImageView goView;
	private SeekBar timebar;
	private String path;
	private String filepath;
	private String[] filepaths;
	//private Map<String, Object> videoMap;
	private MediaPlayer mediaPlayer;
	private AudioManager audioManager;
	private SurfaceView surfaceView;
	private ProgressDialog dialog;
	private boolean pause, filechanged, Handlerpost;
	private int position;
	private int Index;
	private int count;
	//private int maxVolume, curVolume, i;
	private boolean isMute, hasFile, isComeFromList, volumeBarVisible;
	private static final int LIST = 1, UPDATELIST = 2, ABOUT = 3, EXIT = 4;
	private static final int FILE_RESULT_CODE = 1;
	private static final int LIST_RESULT_CODE = 2;
	private static final int UPDATE_RESULT_CODE = 3;

	private static final String TAG = "MediaPlay";

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
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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

/*	private void showUpdateDialog() // 显示等待对话框
	{
		dialog = new ProgressDialog(this);
		dialog.setTitle("请稍后");// 设置ProgressDialog 标题
		dialog.setMessage("正在扫描存储卡......");// 设置ProgressDialog提示信息
		dialog.setIcon(R.drawable.search);// 设置ProgressDialog标题图标
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条风格，风格为圆形，旋转的
		dialog.setIndeterminate(false);// 设置ProgressDialog 的进度条是否不明确 false
										// 就是不设置为不明确
		dialog.setCancelable(true); // 设置ProgressDialoaaag 是否可以按退回键取消
		dialog.show();
		String rootpath = Environment.getExternalStorageDirectory().getPath();
		new Thread(new UpdateThread(rootpath)).start();// 启动子线程，在子线程中扫描SD卡
	}
*/
	class UpdateThread implements Runnable {
		private String rootpath;

		public UpdateThread(String rootpath) {
			this.rootpath = rootpath;
		}

		@Override
		public void run() {
			int count = SDCardMedia.scanSDMedia(rootpath);// 扫描SD卡上的视频文件
			dialog.cancel();
			Message msg = handler.obtainMessage();
			Bundle bundle = msg.getData();
			bundle.putInt("count", count);
			msg.setData(bundle);
			handler.sendMessage(msg);// 扫描完毕发送消息，给出提示
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int count = msg.getData().getInt("count");
			Toast.makeText(MediaPlayerActivitybackup.this,
					"共更新了" + count + "条记录，请点击查看列表按钮或按MENU键查看", 1).show();
		}
	};


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == LIST)// 按下播放列表菜单项
		{
			ShowMediaList();// 显示播放列表
		}
		if (item.getItemId() == UPDATELIST)// 按下更新列表菜单项
		{
			//Intent intent = new Intent(this, UpdateMediaListActivity.class);
			//startActivityForResult(intent, UPDATE_RESULT_CODE);
		}
		if (item.getItemId() == ABOUT) // 按下关于菜单项
		{
			showAboutDialog();
		}
		if (item.getItemId() == EXIT)// 按下退出菜单项
		{
			finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	class CheckListenet implements OnClickListener {
		@Override
		public void onClick(View v) {
			ShowMediaList();// 查看视频列表
		}
	}
/**
 * 
 * 音量控制
 */

	public void mediaplay(View v)// 播放控制
	{
		switch (v.getId()) {
		
		case R.id.playbutton:
			playMedia();
			break;
			
		case R.id.fullscreen:
			fullScreenPlay();
			break;
	//	case R.id.pausebutton:
		//	pauseMedia();
		//	break;
		//case R.id.resetbutton:
		//	fullScreenPlay();
		//	break;
		//case R.id.stopbutton:
		//	stopMedia();
		//	break;
	//	case R.id.backbutton:
		//	playLastMedia();
		//	break;
		//case R.id.nextbutton:
		//	palyNextMedia();
		//	break;
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
					//updateBarHandler.post(updateThread);
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
						//updateBarHandler.post(updateThread);
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

	public void playLastMedia() // 播放上一个视频
	{
		if (filepath != null && !filepath.equals("")) {
			Index--;
			if (Index == -1)
				Index = count - 1;
			filepath = filepaths[Index];
			path = filepath;
			if (pause)
				goView.setVisibility(ViewGroup.INVISIBLE);
			play(0);
			filechanged = true;
			nameText.setText(filepath.substring(filepath.lastIndexOf("/") + 1));
			if (!Handlerpost) {
				//updateBarHandler.post(updateThread);
				Handlerpost = true;
			}
		} else
			Toast.makeText(this, R.string.notchoose, 1).show();
	}

	public void palyNextMedia() // 播放下一个视频
	{
		Log.v(TAG, "begin paly");
		if (filepath != null && !filepath.equals("")) {
			Index++;
			if (Index == count)
				Index = 0;
			filepath = filepaths[Index];
			path = filepath;
			if (pause)
				goView.setVisibility(ViewGroup.INVISIBLE);
			play(0);
			filechanged = true;
			nameText.setText(filepath.substring(filepath.lastIndexOf("/") + 1));
			if (!Handlerpost) {
				//updateBarHandler.post(updateThread);
				Handlerpost = true;
			}
		} else
			Toast.makeText(this, R.string.notchoose, 1).show();
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
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
					pause = true;

					goView.setVisibility(ViewGroup.VISIBLE);
				}
				return true;
			}
			return false;
		}
	}

/*	Handler updateBarHandler = new Handler()// 内部消息队列类，主要获取updateThread发来的CurrentPosition和MaxPosition设置给SeekBar
	{
		public void handleMessage(Message msg) {
			if (mediaPlayer.isPlaying()) {
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

	};*/
/*	Runnable updateThread = new Runnable()// 内部线程类，主要获取mediaPlayer的CurrentPosition和MaxPosition发送给Handler处理
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
	};*/

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
	private void showAboutDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Product Information");
		builder.setMessage("Soft Name: \nVideoPlayer Version1.0 \nAuthor: \nMade By FlyLiang,\n@2012,June,CUMT");
		Dialog dialog = builder.create();
		dialog.show();
	}
}