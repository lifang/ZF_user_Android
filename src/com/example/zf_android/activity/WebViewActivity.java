package com.example.zf_android.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.webkit.WebView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;

public class WebViewActivity extends BaseActivity{
	private String IMAGE_PATH;
	private WebView webview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		new TitleMenuUtil(this, "查看详情").show();
		IMAGE_PATH = getIntent().getExtras().getString("IMAGE_PATH");

		webview = (WebView) findViewById(R.id.webview);
		webview.loadUrl(IMAGE_PATH);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	//	finish();
		return super.onTouchEvent(event);
	}

}
