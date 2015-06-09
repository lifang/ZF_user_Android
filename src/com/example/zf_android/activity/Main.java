package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.CityIntent.CITY_ID;
import static com.example.zf_android.trade.Constants.CityIntent.CITY_NAME;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.DownloadUtils;
import com.examlpe.zf_android.util.ScreenUtils;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.Utils;
import com.example.zf_android.entity.PicEntity;
import com.example.zf_android.entity.VersionEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.ApplyListActivity;
import com.example.zf_android.trade.CitySelectActivity;
import com.example.zf_android.trade.Constants;
import com.example.zf_android.trade.TerminalManageActivity;
import com.example.zf_android.trade.TradeFlowActivity;
import com.example.zf_android.trade.common.CustomDialog;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.JsonParser;
import com.example.zf_android.trade.common.Response;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Main extends Activity implements OnClickListener {
	private LocationClient mLocationClient;
	private TextView LocationResult;
	private RelativeLayout main_rl_pos, main_rl_renzhen, main_rl_zdgl,
	main_rl_jyls, main_rl_Forum, main_rl_wylc, main_rl_xtgg,
	main_rl_lxwm, main_rl_my, main_rl_pos1, main_rl_gwc;
	private ImageView testbutton;
	private View citySelect;
	private TextView cityTextView;
	private TextView countShopCar;
	private int cityId;
	private String cityName;

	private Province province;
	private City city;
	public static final int REQUEST_CITY = 1;
	public static final int REQUEST_CITY_WHEEL = 2;
	public static final String TAG_BANNER = "banner";
	public static final String TAG_BANNER_URL = "banner_url";
	// vp
	private ArrayList<PicEntity> myList = new ArrayList<PicEntity>();
	private ArrayList<PicEntity> nativePicList = new ArrayList<PicEntity>();
	private ViewPager view_pager;
	private MyAdapter adapter;
	private ImageView[] indicator_imgs;// 存放引到图片数组
	private View item;
	private LayoutInflater inflater;
	private ImageView image;
	private int index_ima = 0;
	private ArrayList<String> ma = new ArrayList<String>();
	List<View> list = new ArrayList<View>();
	private SharedPreferences mySharedPreferences;
	private Boolean islogin;
	private int id;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				list.clear();
				ma.clear();

				for (int i = 0; i < myList.size(); i++) {
					item = inflater.inflate(R.layout.item_main, null);
					list.add(item);
					ma.add(myList.get(i).getPicture_url());
					if(!nativePicList.contains(myList.get(i))&&i<nativePicList.size()){
						nativePicList.get(i).setWebsite_url(myList.get(i).getWebsite_url());
						nativePicList.get(i).setPicture_url(myList.get(i).getPicture_url());
						nativePicList.get(i).update(nativePicList.get(i).getId());
					}else{
						myList.get(i).save();
					}

				}

				indicator_imgs = new ImageView[ma.size()];
				initIndicator();
				adapter.notifyDataSetChanged();
				
				ScheduledExecutorService worker =
		                Executors.newSingleThreadScheduledExecutor();

		        Runnable task = new Runnable() {
		            public void run() {
		                pageSwitcher(5);
		            }
		        };
		        worker.schedule(task, 1, TimeUnit.SECONDS);
				
				
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(), "网络未连接",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:

				break;
			case 4:
//				pagerIndex++;
////				Log.e("==pagerIndex==", ""+pagerIndex);
//				pagerIndex = pagerIndex > list.size() - 1 ? 0 : pagerIndex;
//				view_pager.setCurrentItem(pagerIndex);
//				handler.sendEmptyMessageDelayed(4, time);
				break;
			}
			super.handleMessage(msg);
		}
	};

	private int pagerIndex = 0;
	private static final int time = 5000;
	private Timer timer = null;
	private TimerTask task = null;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
	//			.showImageOnLoading(R.drawable.moren)
	.cacheInMemory(false).cacheOnDisc(true)
	.imageScaleType(ImageScaleType.EXACTLY)
	.bitmapConfig(Bitmap.Config.RGB_565)
	.displayer(new FadeInBitmapDisplayer(300)).build();;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//百度推送
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				Utils.getMetaValue(Main.this, "api_key"));
		SharedPreferences mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		Boolean isOpen_mineset = mySharedPreferences.getBoolean("isOpen_mineset", true);
		if (isOpen_mineset == false) {
			//关闭百度推送
			PushManager.stopWork(getApplicationContext());
		}
		
		SQLiteDatabase db = Connector.getDatabase();
		mySharedPreferences = getSharedPreferences("CountShopCar", MODE_PRIVATE);
		Config.countShopCar = mySharedPreferences.getInt("countShopCar", 0);

		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		islogin = mySharedPreferences.getBoolean("islogin", false);
		id = mySharedPreferences.getInt("id", 0);
		String username = mySharedPreferences.getString("name", "");
		MyApplication.getInstance().setCustomerId(id);

		MyApplication.getInstance().setUsername(username);

		MyApplication.getInstance().addActivity(this);

		initView();
		testbutton = (ImageView) findViewById(R.id.testbutton);
		testbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Main.this, LoginActivity.class);
				startActivity(i);
			}
		});
		getdata();

		// 地图功能

		mLocationClient = ((MyApplication) getApplication()).mLocationClient;

		LocationResult = (TextView) findViewById(R.id.tv_city);
		((MyApplication) getApplication()).mLocationResult = LocationResult;
		InitLocation();
		mLocationClient.start();

		System.out.println("当前城市 ID----"
				+ MyApplication.getInstance().getCityId());
//		handler.sendEmptyMessageDelayed(4, time);
	}

	@Override
	public void onResume() {
		mySharedPreferences = getSharedPreferences("CountShopCar",MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putInt("countShopCar", Config.countShopCar); 
		editor.commit(); 
		
		if (Config.countShopCar != 0) {
			countShopCar.setVisibility(View.VISIBLE);
			countShopCar.setText(Config.countShopCar+"");
		}else {
			countShopCar.setVisibility(View.GONE);
		}

		if (Constants.CITY_ID_SEARCH != 0) {
			cityId = Constants.CITY_ID_SEARCH;
			cityName = Constants.CITY_NAME_SEARCH;
			cityTextView.setText(cityName);
			MyApplication.getInstance().setCityId(cityId);
		}

		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		islogin = mySharedPreferences.getBoolean("islogin", false);
		id = mySharedPreferences.getInt("id", 0);
		String username = mySharedPreferences.getString("name", "");
		MyApplication.getInstance().setUsername(username);
		MyApplication.getInstance().setCustomerId(id);

		if (!StringUtil.isNull(Config.notificationTitle)) {
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this, MyMessage.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
		}
		
//		timer = new Timer();
//		task = new TimerTask() {
//			public void run() {
//				handler.sendEmptyMessage(4);
//			}
//		};
//		timer.schedule(task, time, 6000);
		super.onResume();
//		MobclickAgent.onPageStart(this.toString());
//		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
//		MobclickAgent.onPageEnd( this.toString() );
//		MobclickAgent.onPause(this);
	}
	
	
	@Override
	protected void onStop() {

//		timer.cancel();
		super.onPause();
	}

	private void InitLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("gcj02");// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;

		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	private void getdata() {

		MyApplication
		.getInstance()
		.getClient()
		.post(Config.URL_FIGURE_GETLIST,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					Header[] headers, byte[] responseBody) {
				System.out.println("-onSuccess---");
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("LJP", responseMsg);

				Gson gson = new Gson();

				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					int a = jsonobject.getInt("code");
					if (a == Config.CODE) {
						String res = jsonobject
								.getString("result");
						myList = gson
								.fromJson(
										res,
										new TypeToken<List<PicEntity>>() {
										}.getType());
						handler.sendEmptyMessage(0);
					} else {
						code = jsonobject.getString("message");
						Toast.makeText(getApplicationContext(),
								code, 1000).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode,
					Header[] headers, byte[] responseBody,
					Throwable error) {
				error.printStackTrace();
			}
		});

		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("types", 3);
		JSONObject jsonParams = new JSONObject(params);
		StringEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
			entity.setContentType("application/json;charset=UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		MyApplication
		.getInstance()
		.getClient()
		.post(Main.this, Config.URL_GET_VERSION,entity,null,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					Header[] headers, byte[] responseBody) {
				System.out.println("-onSuccess---");
				
				String responseString = new String(responseBody)
				.toString();
				Response data;
				try {
					data = JsonParser.fromJson(responseString,new TypeToken<VersionEntity>() {
					}) ;
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							Main.this.getString(R.string.parse_data_failed), 1000).show();
					return;
				}
				if (data.getCode() == 1) {
					VersionEntity result = (VersionEntity) data.getResult();
					String version = result.getVersions();
					String url = result.getDown_url();
					Integer nowVersion = Tools.getVerCode(Main.this);
					if(Integer.parseInt(version) > nowVersion){
						
						showCustomDialog(url);
						
					}
					
				} 
				
			}

			@Override
			public void onFailure(int statusCode,
					Header[] headers, byte[] responseBody,
					Throwable error) {
				error.printStackTrace();
			}
		});

		
		
//		API.getVersion(this,3,new HttpCallback<VersionEntity> (this) {
//			@Override
//			public void onSuccess(VersionEntity data) {
//				String version = data.getVersions();
//				String url = data.getDown_url();
//				Integer nowVersion = Tools.getVerCode(Main.this);
//				if(Integer.parseInt(version) > nowVersion){
//					
//					showCustomDialog(url);
//					
//				}
//			}
//
//			@Override
//			public TypeToken<VersionEntity> getTypeToken() {
//				return  new TypeToken<VersionEntity>() {
//				};
//			}
//		} );
		
	}

	private void initView() {
		countShopCar = (TextView) findViewById(R.id.countShopCar);
		citySelect = findViewById(R.id.titleback_linear_back);
		cityTextView = (TextView) findViewById(R.id.tv_city);
		cityTextView.setMaxWidth(ScreenUtils.getScreenWidth(this) / 5);
		citySelect.setOnClickListener(this);
		main_rl_gwc = (RelativeLayout) findViewById(R.id.main_rl_gwc);
		main_rl_gwc.setOnClickListener(this);
		main_rl_pos = (RelativeLayout) findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen = (RelativeLayout) findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl = (RelativeLayout) findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls = (RelativeLayout) findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum = (RelativeLayout) findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc = (RelativeLayout) findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm = (RelativeLayout) findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg = (RelativeLayout) findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);
		main_rl_my = (RelativeLayout) findViewById(R.id.main_rl_my);
		main_rl_my.setOnClickListener(this);
		main_rl_pos1 = (RelativeLayout) findViewById(R.id.main_rl_pos1);
		main_rl_pos1.setOnClickListener(this);

		view_pager = (ViewPager) findViewById(R.id.view_pager);
		// allow use api level>11
		// view_pager.setPageTransformer(true, new DepthPageTransformer());
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);

		view_pager.setAdapter(adapter);
		// 绑定动作监听器：如翻页的动画
		view_pager.setOnPageChangeListener(new MyListener());
		// index_ima

		list.clear();
		ma.clear();
		myList = (ArrayList<PicEntity>) DataSupport.findAll(PicEntity.class);
		nativePicList = myList;
		if (myList.size() > 0) {
			for (int i = 0; i < myList.size(); i++) {
				item = inflater.inflate(R.layout.item, null);
				list.add(item);
				ma.add(myList.get(i).getPicture_url());
			}
			indicator_imgs = new ImageView[ma.size()];
			initIndicator();
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.titleback_linear_back:
			Intent intent = new Intent(Main.this, CitySelectActivity.class);
			cityName = cityTextView.getText().toString();
			intent.putExtra(CITY_NAME, cityName);
			startActivityForResult(intent, REQUEST_CITY);
			break;

		case R.id.main_rl_pos1: // 我的消息
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this, MyMessage.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}

			break;

		case R.id.main_rl_my: // 我的
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this, MenuMine.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}

			break;
		case R.id.main_rl_pos: // 买POS机

			startActivity(new Intent(Main.this, PosListActivity.class));

			break;
		case R.id.main_rl_renzhen: // 开通认证
			if (islogin && id != 0) {
				Intent i = new Intent(Main.this, ApplyListActivity.class);
				startActivity(i);
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}

			break;
		case R.id.main_rl_zdgl: // 终端管理
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this,
						TerminalManageActivity.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
		case R.id.main_rl_jyls: // 交易流水
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this, TradeFlowActivity.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
		case R.id.main_rl_Forum: // 我要贷款
			startActivity(new Intent(Main.this, LoanActivity.class));
			break;
		case R.id.main_rl_wylc: // 我要理财
			startActivity(new Intent(Main.this, FianceActivity.class));
			break;
		case R.id.main_rl_xtgg: // 系统公告

			startActivity(new Intent(Main.this, SystemMessage.class));

			break;

		case R.id.main_rl_lxwm: // 联系我们

			startActivity(new Intent(Main.this, ContentUs.class));
			break;
		case R.id.main_rl_gwc: // 购物车
			if (islogin && id != 0) {
				startActivity(new Intent(Main.this, ShopCar.class));
			} else {
				Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CITY:
			cityId = data.getIntExtra(CITY_ID, 0);
			cityName = data.getStringExtra(CITY_NAME);
			cityTextView.setText(cityName);

			Constants.CITY_ID_SEARCH = cityId;
			Constants.CITY_NAME_SEARCH = cityName;
			break;
		case REQUEST_CITY_WHEEL:
			province = (Province) data.getSerializableExtra(SELECTED_PROVINCE);
			city = (City) data.getSerializableExtra(SELECTED_CITY);
			cityTextView.setText(city.getName());
			break;
		}
	}

	private void initIndicator() {

		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
		((ViewGroup) v).removeAllViews();
		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(
					10, 10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;

			if (i == 0) { // 初始化第一个为选中状态

				indicator_imgs[i]
						.setBackgroundResource(R.drawable.white_solid_point);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.white_hollow_point);
			}
			((ViewGroup) v).addView(indicator_imgs[i]);
		}

	}

	/**
	 * 适配器，负责装配 、销毁 数据 和 组件 。
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;
		private int index;

		public MyAdapter(List<View> list) {
			mList = list;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			return mList.size();
		}

		/**
		 * Remove a page for the given position. 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position) This method was
		 * deprecated in API level . Use instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mList.get(position));

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container,
				final int position) {

			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
//			image.setScaleType(ScaleType.FIT_XY);
			// ImageCacheUtil.IMAGE_CACHE.get(ma.get(position),
			// image);

			MyApplication.getInstance().getImageLoader()
			.displayImage(ma.get(position), image, options);

			container.addView(mList.get(position));
			setIndex(position);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i =new Intent (Main.this,GoodDeatail.class);
					i.putExtra("id", Integer.valueOf(myList.get(position).getGoodid()));
					startActivity(i);
					
//					Intent intent = new Intent();
//					intent.setAction("android.intent.action.VIEW");
//					Uri content_url = Uri.parse(myList.get(position)
//							.getWebsite_url().toString());
//					intent.setData(content_url);
//					startActivity(intent);
				}
			});
			return mList.get(position);
		}
	}

	/**
	 * 动作监听器，可异步加载图片
	 * 
	 */
	private class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == 0) {
				// new MyAdapter(null).notifyDataSetChanged();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(final int position) {
			pagerIndex = position;
			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {
				indicator_imgs[i].setBackgroundResource(R.drawable.white_hollow_point);
			}

			// 改变当前背景图片为：选中
			index_ima = position;
			indicator_imgs[position]
					.setBackgroundResource(R.drawable.white_solid_point);
			View v = list.get(position);
			v.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i =new Intent (Main.this,GoodDeatail.class);
					i.putExtra("id", Integer.valueOf(myList.get(position).getGoodid()));
					startActivity(i);
					
//					Intent intent = new Intent();
//					intent.setAction("android.intent.action.VIEW");
//					Uri content_url = Uri.parse(myList.get(index_ima)
//							.getWebsite_url().toString());
//					intent.setData(content_url);
//					startActivity(intent);
				}
			});
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		MyApplication.getInstance().getImageLoader().clearMemoryCache();
		super.onDestroy();
	}

	private void showCustomDialog(final String url) {
		final CustomDialog dialog = new CustomDialog(this);
		dialog.setSoftKeyValue("取消", "确认");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContent("检测到新版本立即更新？");
		dialog.setLeftListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}

		});
		dialog.setRightListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				upgrading(url);
			}
		});
		dialog.show();
	}
	
	private void upgrading(String apkUrl) {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getResources().getString(R.string.updata_check));
		fileDownLoad(pd, apkUrl);
		pd.show();
	}

	private void fileDownLoad(ProgressDialog dialog, final String url) {
		try {
			final DownloadListener listener = new DownloadListener(this, dialog);
			final File file = new File(DownloadUtils.getFilePath(url));
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						DownloadUtils.download(url,
								file, false, listener);
					} catch (Exception e) {
						Log.e("userPhone", "", e);
					}
				}
			}.start();
		} catch (Exception e) {
			dialog.dismiss();
		}
	}

	private static class DownloadListener implements
	DownloadUtils.DownloadListener {
		private Context context;
		private ProgressDialog pd;

		public DownloadListener(Context context, ProgressDialog pd) {
			super();
			this.context = context;
			pd.setMax(100);
			pd.setProgress(0);
			this.pd = pd;
		}

		@Override
		public void downloading(int progress) {
			pd.setProgress(progress);
		}

		@Override
		public void downloaded(File dest) {
			pd.dismiss();
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			intent.setDataAndType(Uri.fromFile(dest),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}

		@Override
		public void exception(Exception e) {
		}
	}
	
	//switcher
    public void pageSwitcher(int seconds) {
        timer = new Timer(); // At this line a new Thread will be created
        timer.scheduleAtFixedRate(new RemindTask(), 0, seconds * 1000); // delay
    }

    // this is an inner class...
    class RemindTask extends TimerTask {

        @Override
        public void run() {

            runOnUiThread(new Runnable() {
                public void run() {
                	pagerIndex++;
                    if (pagerIndex >= myList.size()) { // In my case the number of pages are 5
                    	pagerIndex = 0;
                    }
                    view_pager.setCurrentItem(pagerIndex);
                }
            });

        }
    }
	private long exitTime = 0;  
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {  
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();  
				exitTime = System.currentTimeMillis();  
			} else {  
				finish();  
				System.exit(0);  
			}  
		}

		return true; 
	}
}
