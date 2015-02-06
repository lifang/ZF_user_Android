package com.examlpe.zf_android.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

 
import com.example.zf_android.CancleUpdate;
import com.example.zf_android.Config;
import com.example.zf_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

 
 
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class ClientUpdate {
	 
	private String Title,dataVersion;
	private String ReleaseNote;
	private Activity activity = null;
	private static NotificationManager mNotificationManager = null;
	private Notification notification = null;
	private RemoteViews remoteviews = null;
	public int count = 0;
	private String DOWN_PATH="";
	private int newVerCode, verCodeMin;
	public Builder dialog;
	private File file = null;
	private static int whitch = -1;// 用来记录是从哪种更新点击的取消 0为普通更新，1为强制更新
	private Update_AsyncTask mUpdate_AsyncTask = new Update_AsyncTask();
	public static Update_AsyncTask mUpdate;
	private boolean isConnOk = true;// 是否连接成功

	public ClientUpdate(Activity activity) {
		this.activity = activity;

	}

	public ClientUpdate() {
	}

	// 检测版本
	public void check() {
		if (!isNetworkAvailable(this.activity)) {
		 
			Toast.makeText(activity, "网络异常！", 1000).show();
			return;
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				checkVersion();
			}
		}).start();
	}

	// 检测版本系统设置页
	public void checkSetting() {
		if (!isNetworkAvailable(this.activity)) {
		 
			Toast.makeText(activity, "网络异常！", 1000).show();
			return;
		}
		checkVersion();
 
	}

	// 网络获取数据检测版本
	private void checkVersion() { 
		getVersion();

		String sign;
		AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象

		RequestParams params = new RequestParams();

//		Map<String, String> paramValues = new HashMap<String, String>();
		//params.put("dataVersion", dataVersion );
		params.setUseJsonStreamer(true);

		client.get(Config.checkVersion, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String response = new String(responseBody).toString();
				Log.i("MSG", response);
				Gson gson = new Gson();
				JSONObject jsonobject = null;
				int code = 0;
				 
					//jsonobject = new JSONObject(response);
				//	code = jsonobject.getInt("code");
					if(code==-2){
						 
					}else if(code==0){
//						VersionEntity ve = gson.fromJson(jsonobject.getString("result"),
//	 		 					new TypeToken<VersionEntity>() {
//	 							}.getType());
//						String a=MyApplication.getVersionCode();
//						if(!a.equals(ve.getAppVersionNumber())){
//						 	 DOWN_PATH=	ve.getAppVersionUrl();
//						 //	DOWN_PATH="http://baidu.com/apk_0.01/";
//			 				 showUpdateDialog("New Apk version");
//						}else{
//							Toast.makeText(activity, "Current is the last version", 1000).show();
//						}
// 
//					} 
					
				} 

 
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
			}
		});
	
	}

	public String getVersion() {
		// int version = Integer.parseInt(activity.getResources().getString(R.string.version));
		  PackageManager packageManager =activity.getPackageManager();
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(activity.getPackageName(),0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dataVersion= packInfo.versionName;
			  
			  System.out.println("当前版本号···"+dataVersion);
		return dataVersion;
	}

	// 选择是否更新对话框
	public void showUpdateDialog(String a) {
		new UpdateDialog(activity, new UpdateDialog.ICallBack() {
			@Override
			public void callwitch() {
				whitch = 0;
				sendNotification();
			}
		}, a, "").show();
	}

	// 选择最低版本强制更新对话框
//	public void showMinUpdateDialog() {
//		whitch = 1;
//		upEnDialog = new UpdateEnforceDialog(activity, new UpdateEnforceDialog.IEnforceCallBack() {
//			@Override
//			public void callwitch(int i) {
//				if (i == 1) {
//					mUpdate_AsyncTask.cancel(true);
//					activity.finish();
//				}
//			}
//		});
//		upEnDialog.show();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					file = new File(getFilePath());
//					mUpdate_AsyncTask.execute(new URL(DOWN_PATH));
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
// 
//	}

	// 判断网络是否通畅
	public static boolean isNetworkAvailable(Context ctx) {
		try {
			ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			return (info != null && info.isConnected());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 发送下载消息
	private void sendNotification() {
		mNotificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification(R.drawable.mail_icon, "ICES", System.currentTimeMillis());
		notification.flags = Notification.FLAG_NO_CLEAR;
		Intent i = new Intent(activity, CancleUpdate.class);
 		i.putExtra("quxiao", "Cancle");
			PendingIntent pendingintent = PendingIntent.getActivity(activity, 0, i, 0);
		remoteviews = new RemoteViews(activity.getPackageName(), R.layout.my_notification);
		remoteviews.setOnClickPendingIntent(R.id.notification_cancle, pendingintent);
		remoteviews.setImageViewResource(R.id.logo_image, R.drawable.mail_icon);
		notification.contentView = remoteviews;
		mNotificationManager.notify(88888, notification);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					file = new File(getFilePath());
					mUpdate = mUpdate_AsyncTask;
					mUpdate_AsyncTask.execute(new URL(DOWN_PATH));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// 这个内部类用来异步下载新版本app 通过服务端下载
	public class Update_AsyncTask extends AsyncTask<URL, Integer, Object> {
		private long lastProgressUpdate = 0;
		private double totalSize,nowSize;
		@Override
		protected Object doInBackground(URL... params) {
			InputStream in = null;
			FileOutputStream out = null;
			try {
				URLConnection conn = params[0].openConnection();
				int okCode = ((HttpURLConnection) conn).getResponseCode();
				if (HttpURLConnection.HTTP_OK != okCode) {
					isConnOk = false;
					return null;
				}
				in = conn.getInputStream();
				int contentlength = conn.getContentLength();// 得到下载的总长度
				totalSize=((int)(contentlength/(1024.00*1024)*100))/100.00;
				if (!file.exists()) {
					file.getParentFile().mkdirs();
					file.createNewFile();
				}
				out = new FileOutputStream(file);
				int current = 0;
				int x = 0;
				byte[] arr = new byte[1024 * 8];
				while ((current = in.read(arr)) != -1 && !this.isCancelled()) {
					out.write(arr, 0, current);
					x = x + current;
					count = (int) ((double) x / (double) contentlength * 100);// 计算下载的百分百
					nowSize = ((int)(x/(1024.00*1024)*100)/100.00);
					if (!this.isCancelled() && new Date().getTime() - lastProgressUpdate > 500) {
						publishProgress(count);
						lastProgressUpdate = new Date().getTime();
					}
				}
			} catch (IOException e) {
				isConnOk = false;
			} catch (Exception e) {
			} finally {
				if (out != null)
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				if (in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!isCancelled() && isConnOk) {
				if (whitch == 0) {
 				remoteviews.setProgressBar(R.id.downProgressBar, 100, 100, false);
 				remoteviews.setTextViewText(R.id.downPercent, 100 + "%");
					mNotificationManager.notify(88888, notification);
					// 关闭通知
					mNotificationManager.cancel(88888);
				}
				// 系统安装文件的隐式意图
				Intent notify_Intent = new Intent(Intent.ACTION_VIEW);
				notify_Intent.setDataAndType(Uri.fromFile(new File(new ClientUpdate().getFilePath())), "application/vnd.android.package-archive");
				activity.startActivity(notify_Intent);
				Toast.makeText(activity, "Download finish", 1000).show();
			 
			} else {
				if (mNotificationManager != null)
					mNotificationManager.cancel(88888);
				Toast.makeText(activity, "failed to download", 1000).show();
			 
			}
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
			if (whitch == 0) {
				mNotificationManager.cancel(88888);
			 
				Toast.makeText(activity, "you have cancle download", 1000).show();
			}

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);

			if (values.length == 0)
				return;
			int step = values[0];
			if (step >= 0 && step < 99) {// 紫色的step 是异步下载计算出来设置进度的值
				if (whitch == 0) {
					remoteviews.setProgressBar(R.id.downProgressBar, 100, step, false);
					remoteviews.setTextViewText(R.id.downPercent, step + "%");
					remoteviews.setTextViewText(R.id.tv_size,nowSize+"M / "+totalSize+"M");
					mNotificationManager.notify(88888, notification);
				} else if (whitch == 1) {
						//upEnDialog.setProgre(count);
				}

			}
		}
	}

	// 获得应用下载路径
	public String getFilePath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) // 如果SD卡存在，则获取跟目录
		{
			return Environment.getExternalStorageDirectory().toString() + File.separator + "ICES.apk";// 获取跟目录
		} else {
			return activity.getCacheDir().getAbsolutePath() + File.separator + "ICES.apk";// 获取跟目录
		}
	}

	/**
	 * 用于setting页面的版本检测
	 * 
	 * @return
	 */
	public void settingCheckVersion() {
//		TxtRequest_UTF8 stringRequest = new TxtRequest_UTF8(Config.URLVERSION, new Listener<String>() {
//
//			@Override
//			public void onResponse(String arg0) {
//				System.out.println("setting============arg0:"+arg0);
//				JSONArray array;
//				try {
//					array = new JSONArray(arg0);
//					JSONObject jsonObj = array.getJSONObject(0);
//					JSONArray jsArray = jsonObj.getJSONArray("apks");
//					ApplicationInfo appInfo = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
//					String SourceIdentity = appInfo.metaData.get("BaiduMobAd_CHANNEL").toString();
//					for(int i=0;i<jsArray.length();i++){
//						if(jsArray.getJSONObject(i).getString("no").equals(SourceIdentity)){
//							DOWN_PATH=jsArray.getJSONObject(i).getString("url");
//							break;
//						}
//					}
//					if(DOWN_PATH.equals(""))
//						DOWN_PATH=jsonObj.getString("url");
//					newVerCode = jsonObj.getInt("verCode");
//					verCodeMin = jsonObj.getInt("verCodeMin");
//					Title = jsonObj.getString("Title");
//					ReleaseNote = jsonObj.getString("ReleaseNote");
//					if (newVerCode > (getVersion())) {
//						if (verCodeMin > getVersion()) {
//							showMinUpdateDialog();
//						} else {
//							showUpdateDialog();
//						}
//					} else {
//						new ToastShow(activity, "您已经是最新版本").show();
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				} catch (NameNotFoundException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}, ((AntsApplication) activity.getApplicationContext()).<String> getDefaultErrorListenerT());
//		((AntsApplication) activity.getApplicationContext()).getRequestQueue().add(stringRequest, activity);
	}

}