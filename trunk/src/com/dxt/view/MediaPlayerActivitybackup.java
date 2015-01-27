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
import android.os.Environment;
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
import android.widget.Button;
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
		// �����͸�surfaceView����Ƶ���棬ֱ����ʾ����Ļ��,��Ҫά��������Ļ�����
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceView.getHolder().setFixedSize(176, 100);
        surfaceView.getHolder().setKeepScreenOn(true);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
		
		
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		//��ʾý���б�
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
	protected void onListItemClick(ListView l, View v, int position, long id) //��Ŀ���ʱ�����ؽ��
	{
		videoMap = videoDataList.get(position);
		//����¼���ȡ·��
		String str=(String) videoMap.get("path");
		filepath=str;
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

/*	private void showUpdateDialog() // ��ʾ�ȴ��Ի���
	{
		dialog = new ProgressDialog(this);
		dialog.setTitle("���Ժ�");// ����ProgressDialog ����
		dialog.setMessage("����ɨ��洢��......");// ����ProgressDialog��ʾ��Ϣ
		dialog.setIcon(R.drawable.search);// ����ProgressDialog����ͼ��
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// ���ý�������񣬷��ΪԲ�Σ���ת��
		dialog.setIndeterminate(false);// ����ProgressDialog �Ľ������Ƿ���ȷ false
										// ���ǲ�����Ϊ����ȷ
		dialog.setCancelable(true); // ����ProgressDialoaaag �Ƿ���԰��˻ؼ�ȡ��
		dialog.show();
		String rootpath = Environment.getExternalStorageDirectory().getPath();
		new Thread(new UpdateThread(rootpath)).start();// �������̣߳������߳���ɨ��SD��
	}
*/
	class UpdateThread implements Runnable {
		private String rootpath;

		public UpdateThread(String rootpath) {
			this.rootpath = rootpath;
		}

		@Override
		public void run() {
			int count = SDCardMedia.scanSDMedia(rootpath);// ɨ��SD���ϵ���Ƶ�ļ�
			dialog.cancel();
			Message msg = handler.obtainMessage();
			Bundle bundle = msg.getData();
			bundle.putInt("count", count);
			msg.setData(bundle);
			handler.sendMessage(msg);// ɨ����Ϸ�����Ϣ��������ʾ
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int count = msg.getData().getInt("count");
			Toast.makeText(MediaPlayerActivitybackup.this,
					"��������" + count + "����¼�������鿴�б�ť��MENU���鿴", 1).show();
		}
	};


	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		if (item.getItemId() == LIST)// ���²����б�˵���
		{
			ShowMediaList();// ��ʾ�����б�
		}
		if (item.getItemId() == UPDATELIST)// ���¸����б�˵���
		{
			//Intent intent = new Intent(this, UpdateMediaListActivity.class);
			//startActivityForResult(intent, UPDATE_RESULT_CODE);
		}
		if (item.getItemId() == ABOUT) // ���¹��ڲ˵���
		{
			showAboutDialog();
		}
		if (item.getItemId() == EXIT)// �����˳��˵���
		{
			finish();
		}
		return super.onMenuItemSelected(featureId, item);
	}

	class CheckListenet implements OnClickListener {
		@Override
		public void onClick(View v) {
			ShowMediaList();// �鿴��Ƶ�б�
		}
	}
/**
 * 
 * ��������
 */

	public void mediaplay(View v)// ���ſ���
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
					//updateBarHandler.post(updateThread);
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

	public void playLastMedia() // ������һ����Ƶ
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

	public void palyNextMedia() // ������һ����Ƶ
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
			mediaPlayer.reset();
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

/*	Handler updateBarHandler = new Handler()// �ڲ���Ϣ�����࣬��Ҫ��ȡupdateThread������CurrentPosition��MaxPosition���ø�SeekBar
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
/*	Runnable updateThread = new Runnable()// �ڲ��߳��࣬��Ҫ��ȡmediaPlayer��CurrentPosition��MaxPosition���͸�Handler����
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
	};*/

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
	private void showAboutDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Product Information");
		builder.setMessage("Soft Name: \nVideoPlayer Version1.0 \nAuthor: \nMade By FlyLiang,\n@2012,June,CUMT");
		Dialog dialog = builder.create();
		dialog.show();
	}
}