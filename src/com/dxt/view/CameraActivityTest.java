package com.dxt.view;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxt.R;


public class CameraActivityTest extends Activity{

	private Button creama = null;
	private String TAG="dxt";

	private ImageView img = null;

	private TextView text = null;

	private File tempFile = new File(Environment.getExternalStorageDirectory(),
			getPhotoFileName());

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cameraactivitytest);
		init();
		Log.i(TAG, "" + Environment.getExternalStorageDirectory());

	}


	private void init() {
		// TODO Auto-generated method stub

		creama = (Button) findViewById(R.id.btn_creama);

		img = (ImageView) findViewById(R.id.img_creama);

		creama.setOnClickListener(listener);
		text = (TextView) findViewById(R.id.text);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PHOTO_REQUEST_TAKEPHOTO:// 当选择拍照时调用
			startPhotoZoom(Uri.fromFile(tempFile));
			break;
		case PHOTO_REQUEST_GALLERY:// 当选择从本地获取图片时
			// 做非空判断，当我们觉得不满意想重新剪裁的时候便不会报异常，下同
			if (data != null)
				startPhotoZoom(data.getData());
			break;
		case PHOTO_REQUEST_CUT:// 返回的结果
			if (data != null)
				// setPicToView(data);
				sentPicToNext(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(tempFile));
			startActivityForResult(cameraintent, PHOTO_REQUEST_TAKEPHOTO);

		}
	};

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	// 将进行剪裁后的图片传递到下一个界面上
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo == null) {
				//img.setImageResource(R.drawable.abc_ic_clear_normal);
				Log.v(TAG, "photo is null");
			} else {
				img.setImageBitmap(photo);
				// 设置文本内容为 图片绝对路径和名字
				text.setText(tempFile.getAbsolutePath());
			}

			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photodata = baos.toByteArray();
				System.out.println(photodata.toString());
				// Intent intent = new Intent();
				// intent.setClass(RegisterActivity.this, ShowActivity.class);
				// intent.putExtra("photo", photodata);
				// startActivity(intent);
				// finish();
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				if (baos != null) {
					try {
						baos.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 使用系统当前日期加以调整作为照片的名称
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

}
