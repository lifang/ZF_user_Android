package com.example.zf_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {

	protected Dialog loadingDialog;
	protected String error;
	protected Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mContext = this;
		Log.e("==mContext==",this.toString());
		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		MobclickAgent.updateOnlineConfig(this);
		Log.e("------", getDeviceInfo(this));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mContext.toString());
		MobclickAgent.onResume(mContext);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( mContext.toString() );
		MobclickAgent.onPause(mContext);
	}

	public static String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}

			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
