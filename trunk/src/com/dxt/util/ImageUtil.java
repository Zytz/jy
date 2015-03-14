package com.dxt.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.View;
import android.widget.ImageView;

import com.dxt.R;
import com.dxt.constant.StringConstant;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageUtil {
	/**
	 * Ðý×ªBitmap
	 * @param b
	 * @param rotateDegree
	 * @return
	 */
	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
	.showImageOnLoading(R.drawable.ic_stub)
	.showImageForEmptyUri(R.drawable.ic_empty)
	.showImageOnFail(R.drawable.ic_error)
	.cacheInMemory(true)
	.cacheOnDisk(true)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.displayer(new RoundedBitmapDisplayer(20))
	.build();;
	
	private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();;
	
	public static Bitmap getRotateBitmap(Bitmap b, float rotateDegree){
		Matrix matrix = new Matrix();
		matrix.postRotate((float)rotateDegree);
		Bitmap rotaBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, false);
		return rotaBitmap;
	}
	
	public static void LoadImage(Context context,String uri,ImageView view){
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		  .writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
		ImageLoader.getInstance().displayImage(StringConstant.SERVICE_URL+uri, view, options, animateFirstListener);
	}

	
	private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
