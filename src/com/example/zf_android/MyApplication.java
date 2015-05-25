package com.example.zf_android;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.NetworkTools;
import com.example.zf_android.activity.LoginActivity;
import com.example.zf_android.entity.ApplyneedEntity;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.entity.PosSelectEntity;
import com.example.zf_android.entity.User;
import com.example.zf_android.entity.UserEntity;
import com.example.zf_android.service.NetworkStateService;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MyApplication extends org.litepal.LitePalApplication {
	public TextView mLocationResult;
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;
	private ImageLoader mImageLoader;

	private Integer customerId;
	private String username;

	private List<City> mCities = new ArrayList<City>();
	private boolean haveShowHint = false;

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			if (location.getCity() != null) {
				if (!NetworkTools.isNetworkAvailable(getApplicationContext())) { // 判断网络是否存在网络
					Toast.makeText(getApplicationContext(),
							"没有连接到网络，请重新尝试连接网络", Toast.LENGTH_LONG).show();
					return;
				}
				StringBuffer sb = new StringBuffer(256);

				sb.append(location.getAddrStr());
				logMsg(location.getCity());
				mCities.clear();
				List<Province> provinces = CommonUtil
						.readProvincesAndCities(getApplicationContext());
				for (Province province : provinces) {
					List<City> cities = province.getCities();
					mCities.addAll(cities);

				}
				for (City cc : mCities) {
					if (cc.getName().contains(location.getCity())) {
						System.out.println(location.getCity()
								+ "name<--当前城市 ID----" + cc.getId());
						setCityId(cc.getId());
						setCityName(cc.getName());
					}
				}
				Log.i("BaiduLocationApiDem", sb.toString());
			} else {
				if (!haveShowHint) {
					haveShowHint = true;
					Toast.makeText(getApplicationContext(),
							"没有检测到定位权限,请到相应软件开启权限", Toast.LENGTH_LONG).show();
				}
			}
		}

	}

	/**
	 * 显示请求字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			if (mLocationResult != null)
				mLocationResult.setText(str);
			mLocationClient.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static MyApplication mInstance = null;
	// private ArrayList<Order> orderList = new ArrayList<Order>();
	/**
	 */
	private static String versionCode = "";
	private static int notifyId = 0;
	private static Boolean isSelect = false;
	private int cityId = 0;
	private String cityName = "";

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public static Boolean getIsSelect() {
		return isSelect;
	}

	public static void setIsSelect(Boolean isSelect) {
		MyApplication.isSelect = isSelect;
	}

	public static int getNotifyId() {
		return notifyId;
	}

	public static void setNotifyId(int notifyId) {
		MyApplication.notifyId = notifyId;
	}

	public static String getVersionCode() {
		return versionCode;
	}

	public static void setVersionCode(String versionCode) {
		MyApplication.versionCode = versionCode;
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(User currentUser) {
		MyApplication.currentUser = currentUser;
	}

	private static String token = "";
	AsyncHttpClient client = new AsyncHttpClient(); //

	public AsyncHttpClient getClient() {
		// client.setTimeout(6000);
		return client;
	}

	public void setClient(AsyncHttpClient client) {
		this.client = client;
	}

	public static String getToken() {
		return token;
	}

	public static void setToken(String token) {
		MyApplication.token = token;
	}

	public static PosSelectEntity pse = new PosSelectEntity();

	public static PosSelectEntity getPse() {
		return pse;
	}

	public static void setPse(PosSelectEntity pse) {
		MyApplication.pse = pse;
	}

	public static List<Good> comfirmList = new LinkedList<Good>();

	public static List<Good> getComfirmList() {
		return comfirmList;
	}

	public static void setComfirmList(List<Good> comfirmList) {
		MyApplication.comfirmList = comfirmList;
	}

	public static List<ChanelEntitiy> celist = new LinkedList<ChanelEntitiy>();

	public static List<ChanelEntitiy> getCelist() {
		return celist;
	}

	public static void setCelist(List<ChanelEntitiy> celist) {
		MyApplication.celist = celist;
	}

	public static List<ApplyneedEntity> pub = new LinkedList<ApplyneedEntity>();
	public static List<ApplyneedEntity> single = new LinkedList<ApplyneedEntity>();

	public static List<Goodlist> Goodlist = new LinkedList<Goodlist>();

	public static List<Goodlist> getGoodlist() {
		return Goodlist;
	}

	public static void setGoodlist(List<Goodlist> goodlist) {
		Goodlist = goodlist;
	}

	public static List<ApplyneedEntity> getPub() {
		return pub;
	}

	public static void setPub(List<ApplyneedEntity> pub) {
		MyApplication.pub = pub;
	}

	public static List<ApplyneedEntity> getSingle() {
		return single;
	}

	public static void setSingle(List<ApplyneedEntity> single) {
		MyApplication.single = single;
	}

	public static UserEntity NewUser = new UserEntity();

	public static UserEntity getNewUser() {
		return NewUser;
	}

	public static void setNewUser(UserEntity newUser) {
		NewUser = newUser;
	}

	public static User currentUser = new User();
	public static GoodinfoEntity gfe = new GoodinfoEntity();

	public static GoodinfoEntity getGfe() {
		return gfe;
	}

	public static void setGfe(GoodinfoEntity gfe) {
		MyApplication.gfe = gfe;
	}

	private List<Activity> mList = new LinkedList<Activity>();

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void clearHistory() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SharedPreferences mySharedPreferences = getSharedPreferences("Login",
				MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.putBoolean("islogin", false);
		editor.commit();

		Intent intent = new Intent();
		intent.setClass(MyApplication.getInstance(), LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		MyApplication.getInstance().startActivity(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		
		FrontiaApplication.initFrontiaApplication(getApplicationContext()); 
		
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		mVibrator = (Vibrator) getApplicationContext().getSystemService(
				Service.VIBRATOR_SERVICE);
		// 设置全局imageload
		initImageLoaderConfig();
		/*
		 * niemin
		 */
		initImageLoader(mInstance);
		Intent i = new Intent(this, NetworkStateService.class);
		ServiceConnection connection = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
			}
		};
		bindService(i, connection, BIND_AUTO_CREATE);
	}

	public static MyApplication getInstance() {
		return mInstance;
	}
	/*
	 * niemin
	 */
	public static void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getDisplayOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.moren)			// 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.moren)	// 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.moren)		// 设置图片加载或解码过程中发生错误显示的图片	
		.cacheInMemory(true)						// 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true)							// 设置下载的图片是否缓存在SD卡中
		.build();									// 创建配置过得DisplayImageOption对象	
		return options;
	}
	private void initImageLoaderConfig() {

		File cacheDir = StorageUtils.getOwnCacheDirectory(
				this,
				"imageloader/Cache");

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
		.memoryCacheExtraOptions(720, 1280)
		.threadPoolSize(3)
		// 线程池内加载的数量
		.threadPriority(Thread.NORM_PRIORITY - 2)	
		// 自定义缓存路径
		.discCache(new UnlimitedDiscCache(cacheDir))
		.discCacheFileCount(10)
		.tasksProcessingOrder(QueueProcessingType.FIFO)
		.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
		.memoryCacheSizePercentage(10)
		.imageDownloader(
				new BaseImageDownloader(this, 5 * 1000, 10 * 1000))
				// 超时时间 5秒
				.imageDecoder(new BaseImageDecoder(true))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.build();// 开始构建
		ImageLoader.getInstance().init(config);

	}

	public ImageLoader getImageLoader() {
		if (mImageLoader == null) {
			mImageLoader = ImageLoader.getInstance();
		}
		return mImageLoader;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private boolean hasOrderPaid;

	public boolean isHasOrderPaid() {
		return hasOrderPaid;
	}

	public void setHasOrderPaid(boolean hasOrderPaid) {
		this.hasOrderPaid = hasOrderPaid;
	}
	private ArrayList<Context> historyList = new ArrayList<Context>();

	public ArrayList<Context> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(ArrayList<Context> historyList) {
		this.historyList = historyList;
	}

	public void clearHistoryForPay(){
		try {
			for (Context activity : historyList) {
				if (activity != null)
					((Activity)activity).finish();
			}
			historyList.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int reg_cityId;

	public int getReg_cityId() {
		return reg_cityId;
	}

	public void setReg_cityId(int reg_cityId) {
		this.reg_cityId = reg_cityId;
	}

}
