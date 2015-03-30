package com.examlpe.zf_android.util;

import java.io.File;

import com.example.zf_android.R;

 

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;

public class ImageCacheUtil {
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public static final String TAG_CACHE = "image_cache";
	public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
			.append(Environment.getExternalStorageDirectory().getAbsolutePath())
			.append(File.separator).append("besttone").append(File.separator)
			.append("imageCache").toString();

	static {
		/** init icon cache **/
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {

			/**
			 * callback function after get image successfully, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param isInCache
			 *            whether already in cache or got realtime
			 */
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage,
					View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
				//	 imageView.setImageBitmap(loadedImage);
					
					    BitmapDrawable bd= new BitmapDrawable( loadedImage);                  
				   imageView.setBackgroundDrawable(bd);
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}

				}
			}

			/**
			 * callback function before get image, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param view
			 *            view need the image
			 */
			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			/**
			 * callback function after get image failed, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param failedReason
			 *            failed reason for get image
			 */
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage,
					View view, FailedReason failedReason) {
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ")
								.append(imageUrl)
								.append(" error, failed type is: ")
								.append(failedReason.getFailedType())
								.append(", failed reason is: ")
								.append(failedReason.getCause().getMessage())
								.toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof ImageView) {
					//((ImageView) view).setImageResource(R.drawable.moren);
					((ImageView) view).setBackgroundResource(R.drawable.moren);
				}
			}
		};
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE
				.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());

		IMAGE_CACHE.setHttpReadTimeOut(10000);
		IMAGE_CACHE.setOpenWaitingQueue(true);
		IMAGE_CACHE.setValidTime(-1);
		/**
		 * close connection, default is connect keep-alive to reuse connection.
		 * if image is from different server, you can set this
		 */
	}

	public static AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}
}
