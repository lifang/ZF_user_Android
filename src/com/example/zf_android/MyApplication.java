package com.example.zf_android;

 
 
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
 
 
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.example.zf_android.entity.ApplyneedEntity;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.PosSelectEntity;
import com.example.zf_android.entity.User;
import com.example.zf_android.entity.UserEntity;
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.trade.CitySelectActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.loopj.android.http.AsyncHttpClient;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
 
 
 

public class MyApplication extends Application{
	public TextView mLocationResult,logMsg;
	public LocationClient mLocationClient;
	public GeofenceClient mGeofenceClient;
	public MyLocationListener mMyLocationListener;
	public Vibrator mVibrator;
	


	private List<City> mCities = new ArrayList<City>();
	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location 
			StringBuffer sb = new StringBuffer(256);
 
			sb.append(location.getAddrStr());
			logMsg(location.getCity());
	        List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
            for (Province province : provinces) {
                List<City> cities = province.getCities();
              
                mCities.addAll(cities);
                 
            }
			 for(City cc:mCities ){
				 if(cc.getName().contains(location.getCity())){
					 System.out.println(location.getCity()+"name<--当前城市 ID----"+cc.getId());
					 setCITYID(cc.getId());
					 setCITYNAME(cc.getName());
				 }
			 }
			 
			Log.i("BaiduLocationApiDem", sb.toString());
		}


	}
	/**
	 * 显示请求字符串
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
	
	
	private static MyApplication  mInstance=null;
	//private ArrayList<Order> orderList = new ArrayList<Order>();
	/**
	 * ��֤��Ϣtoken
	 */
	private static  String versionCode="";
	private static int notifyId=0;
	private static Boolean isSelect=false;
	private static int CITYID=1;
	private static String   CITYNAME="苏州";
	
	public static String getCITYNAME() {
		return CITYNAME;
	}
	public static void setCITYNAME(String cITYNAME) {
		CITYNAME = cITYNAME;
	}
	public static int getCITYID() {
		return CITYID;
	}
	public static void setCITYID(int cITYID) {
		CITYID = cITYID;
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


	private static String token="";
	AsyncHttpClient client = new AsyncHttpClient(); //  
	
	public AsyncHttpClient getClient() {
		//client.setTimeout(6000);
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
	public static List<Good> comfirmList=new LinkedList<Good>();
	
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
	 
	public static List<Goodlist>  Goodlist = new LinkedList<Goodlist>();   
 

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
	public static GoodinfoEntity gfe=new GoodinfoEntity();
	 
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
    //�ر�ÿһ��list�ڵ�activity   
    public void exit() {    
        try {    
            for (Activity activity:mList) {    
                if (activity != null)    
                    activity.finish();    
            }    
        } catch (Exception e) {    
            e.printStackTrace();    
        } 
        
    }  
 

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;		 
		mLocationClient = new LocationClient(this.getApplicationContext());
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);
		mGeofenceClient = new GeofenceClient(getApplicationContext());
		
		
		mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
	}
	
//	private void initImageLoader(Context context) {
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//				context).memoryCache(new LruMemoryCache(2 * 1024 * 1024))
//				.memoryCacheSize(2 * 1024 * 1024)
//				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
//				.build();
//		ImageLoader.getInstance().init(config);
//	}
	public static MyApplication getInstance() {
		return mInstance;
	}
	
 
}
