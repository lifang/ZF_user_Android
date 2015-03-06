package com.example.zf_android.trade;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zf_android.R;
import com.example.zf_android.trade.widget.gestureimage.GestureImageView;
import com.example.zf_android.trade.widget.gestureimage.MyViewPager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.zf_android.trade.Constants.ShowWebImageIntent.IMAGE_URLS;
import static com.example.zf_android.trade.Constants.ShowWebImageIntent.IMAGE_NAMES;
import static com.example.zf_android.trade.Constants.ShowWebImageIntent.POSITION;


/**
 * Created by Leo on 2015/3/5.
 */
public class ShowWebImageActivity extends Activity {
	private String[] imageArray;
	private String[] imageNames;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private int position;
	private GestureImageView[] mImageViews;
	private MyViewPager viewPager;
	private TextView page;
	private int count;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_image_activity);
		getIntentValue();
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(this))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		imageLoader.init(config);
		options = new DisplayImageOptions.Builder().cacheInMemory(true)
				.cacheOnDisc(false).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		initViews();
	}

	private void getIntentValue() {
		Intent intent = getIntent();
		String urls = intent.getStringExtra(IMAGE_URLS);
		String names = intent.getStringExtra(IMAGE_NAMES);
		position = intent.getIntExtra(POSITION, 0);
		imageArray = urls.split(",");
		imageNames = names.split(",");
		count = imageArray.length;
	}

	private void initViews() {
		page = (TextView) findViewById(R.id.text_page);
		if (count <= 1) {
			page.setVisibility(View.GONE);
		} else {
			page.setVisibility(View.VISIBLE);
			page.setText(imageNames[position]);
		}

		viewPager = (MyViewPager) findViewById(R.id.web_image_viewpager);
		viewPager.setPageMargin(20);
		viewPager.setAdapter(new ImagePagerAdapter(getWebImageViews()));
		viewPager.setCurrentItem(position);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				page.setText(imageNames[arg0]);
				mImageViews[arg0].reset();
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private static int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
			// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}

	private List<View> getWebImageViews() {
		mImageViews = new GestureImageView[count];
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		List<View> views = new ArrayList<View>();
		for (int i = 0; i < count; i++) {
			View view = layoutInflater.inflate(R.layout.web_image_item, null);
			final GestureImageView imageView = (GestureImageView) view
					.findViewById(R.id.item_image);
			final ProgressBar progressBar = (ProgressBar) view
					.findViewById(R.id.item_loading);
			mImageViews[i] = imageView;
			imageLoader.displayImage(imageArray[i], imageView, options,
					new SimpleImageLoadingListener() {

						@Override
						public void onLoadingComplete(String imageUri,
						                              View view, Bitmap loadedImage) {
							progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
						                            FailReason failReason) {
							progressBar.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingStarted(String imageUri, View view) {
							progressBar.setVisibility(View.VISIBLE);
						}

					});
			imageView.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					finish();
				}
			});
			views.add(view);
		}
		viewPager.setGestureImages(mImageViews);
		return views;
	}

	@Override
	protected void onDestroy() {
		if (mImageViews != null) {
			mImageViews = null;
		}
		imageLoader.clearMemoryCache();
		super.onDestroy();
	}

	private class ImagePagerAdapter extends PagerAdapter {
		private List<View> views;

		public ImagePagerAdapter(List<View> views) {
			this.views = views;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public Object instantiateItem(View view, int position) {
			((ViewPager) view).addView(views.get(position), 0);
			return views.get(position);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}
}
