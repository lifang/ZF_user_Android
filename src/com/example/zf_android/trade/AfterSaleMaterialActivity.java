package com.example.zf_android.trade;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.DialogUtil;

/**
 * Created by Leo on 2015/3/2.
 */
public class AfterSaleMaterialActivity extends Activity {

	private int mRecordType;
	private String mUrl;

	private WebView mWebView;
	private Dialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordType = getIntent().getIntExtra(AfterSaleListActivity.RECORD_TYPE, 0);
		mUrl = getIntent().getStringExtra(AfterSaleDetailActivity.MATERIAL_URL);
		String title = "";
		switch (mRecordType) {
			case AfterSaleListActivity.RECORD_RETURN:
				title = getString(R.string.after_sale_return_material_title);
				break;
			case AfterSaleListActivity.RECORD_CANCEL:
				title = getString(R.string.after_sale_cancel_material_title);
				break;
			case AfterSaleListActivity.RECORD_CHANGE:
				title = getString(R.string.after_sale_change_material_title);
				break;
			case AfterSaleListActivity.RECORD_UPDATE:
				title = getString(R.string.after_sale_update_material_title);
				break;
			case AfterSaleListActivity.RECORD_LEASE:
				title = getString(R.string.after_sale_lease_material_title);
				break;
		}
		setContentView(R.layout.activity_after_sale_material);
		new TitleMenuUtil(this, title).show();

		initWebView();
		mWebView.loadUrl(mUrl);
	}

	private void initWebView() {
		loadingDialog = DialogUtil.getLoadingDialg(this);

		mWebView = (WebView) findViewById(R.id.after_sale_material_web_view);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		mWebView.getSettings().setAllowFileAccess(true);
		mWebView.setHorizontalScrollBarEnabled(true);
		mWebView.setVerticalScrollBarEnabled(true);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					loadingDialog.dismiss();
				} else {
					loadingDialog.show();
				}
			}
		});
	}
}
