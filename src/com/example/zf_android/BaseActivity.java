package com.example.zf_android;

import com.example.zf_android.trade.common.DialogUtil;

import android.app.Activity;
import android.app.Dialog;

public class BaseActivity extends Activity{
	
	protected Dialog loadingDialog ;
	protected String error;
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
}
