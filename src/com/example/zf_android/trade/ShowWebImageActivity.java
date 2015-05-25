package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_TAKE_PHOTO;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_UPLOAD_IMAGE;
import static com.example.zf_android.trade.Constants.ShowWebImageIntent.IMAGE_NAMES;
import static com.example.zf_android.trade.Constants.ShowWebImageIntent.IMAGE_URLS;
import static com.example.zf_android.trade.Constants.ShowWebImageIntent.POSITION;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.RegexTools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.widget.gestureimage.GestureImageView;
import com.example.zf_android.trade.widget.gestureimage.MyViewPager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

/**
 * Created by Leo on 2015/3/5.
 */
public class ShowWebImageActivity extends BaseActivity {
	private String[] imageArray;
	private String[] imageNames;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private int position;
	private GestureImageView[] mImageViews;
	private MyViewPager viewPager;
	private int count;
	private Button upload;
	private String photoPath;
	private String realPath = "";

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
		for (int i = 0; i < imageArray.length; i++) {
			Log.e("==imageArray==", imageArray[i]);
		}
		imageNames = names.split(",");
		count = imageArray.length;
	}

	private void initViews() {
		upload = (Button) findViewById(R.id.upload_again);

		viewPager = (MyViewPager) findViewById(R.id.web_image_viewpager);
		viewPager.setPageMargin(20);
		viewPager.setAdapter(new ImagePagerAdapter(getWebImageViews()));
		viewPager.setCurrentItem(position);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				mImageViews[arg0].reset();
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});

		upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showUploadDialog();
			}
		});

	}

	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(contentUri, proj, null, null,
					null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		if (resultCode != RESULT_OK)
			return;
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == 1) {
					CommonUtil.toastShort(ShowWebImageActivity.this,
							(String) msg.obj);
					upload.setText(getString(R.string.apply_upload_again));
					upload.setClickable(false);
					String url = (String) msg.obj;
					CommonUtil.toastShort(ShowWebImageActivity.this, url);
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(realPath);
						Bitmap bitmap = BitmapFactory.decodeStream(fis);
						mImageViews[position].setImageBitmap(bitmap);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} finally {
						if (null != fis)
							try {
								fis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
					}

				} else {
					CommonUtil.toastShort(ShowWebImageActivity.this,
							getString(R.string.toast_upload_failed));
					upload.setText(getString(R.string.apply_upload_again));
					upload.setClickable(true);
				}

			}
		};
		upload.setText(getString(R.string.apply_uploading));
		upload.setClickable(false);
		new Thread() {
			@Override
			public void run() {

				if (requestCode == REQUEST_TAKE_PHOTO) {
					realPath = photoPath;
				} else {
					Uri uri = data.getData();
					if (uri != null) {
						realPath = getRealPathFromURI(uri);
					}
				}
				if (TextUtils.isEmpty(realPath)) {
					handler.sendEmptyMessage(0);
					return;
				}
				CommonUtil.uploadFile(realPath, "img",
						new CommonUtil.OnUploadListener() {
							@Override
							public void onSuccess(String result) {
								try {
									JSONObject jo = new JSONObject(result);
									String url = jo.getString("result");
									Message msg = new Message();
									msg.what = 1;
									msg.obj = url;
									handler.sendMessage(msg);
								} catch (JSONException e) {
									handler.sendEmptyMessage(0);
								}
							}

							@Override
							public void onFailed(String message) {
								handler.sendEmptyMessage(0);
							}
						});
			}
		}.start();
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

			if (RegexTools.isUrl(imageArray[i])) {

				imageLoader.displayImage(imageArray[i], imageView, options,
						new SimpleImageLoadingListener() {

							@Override
							public void onLoadingComplete(String imageUri,
									View view, Bitmap loadedImage) {
								progressBar.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingFailed(String imageUri,
									View view, FailReason failReason) {
								progressBar.setVisibility(View.GONE);
							}

							@Override
							public void onLoadingStarted(String imageUri,
									View view) {
								progressBar.setVisibility(View.VISIBLE);
							}

						});
				imageView.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						finish();
					}
				});
				views.add(view);
			} else {
				showUploadDialog();
			}
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

	private void showUploadDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				ShowWebImageActivity.this);
		final String[] items = getResources().getStringArray(
				R.array.apply_detail_upload);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0: {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, REQUEST_UPLOAD_IMAGE);
					break;
				}
				case 1: {
					String state = Environment.getExternalStorageState();
					if (state.equals(Environment.MEDIA_MOUNTED)) {
						Intent intent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						File outDir = Environment
								.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
						if (!outDir.exists()) {
							outDir.mkdirs();
						}
						File outFile = new File(outDir, System
								.currentTimeMillis() + ".jpg");
						photoPath = outFile.getAbsolutePath();
						intent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(outFile));
						intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
						startActivityForResult(intent, REQUEST_TAKE_PHOTO);
					} else {
						CommonUtil.toastShort(ShowWebImageActivity.this,
								getString(R.string.toast_no_sdcard));
					}
					break;
				}
				}
			}
		});
		builder.show();
	}

}
