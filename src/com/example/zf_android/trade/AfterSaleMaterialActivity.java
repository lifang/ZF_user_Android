package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.AfterSaleIntent.MATERIAL_URL;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_TYPE;
import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.URLImageParser;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.DialogUtil;

/**
 * Created by Leo on 2015/3/2.
 */
public class AfterSaleMaterialActivity extends BaseActivity {

	private int mRecordType;
	private String mUrl;
	private TextView content;
	private WebView mWebView;
	private Dialog loadingDialog;
	private URLImageParser ReviewImgGetter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordType = getIntent().getIntExtra(RECORD_TYPE, 0);
		mUrl = getIntent().getStringExtra(MATERIAL_URL);
		String title = "";
		switch (mRecordType) {
		case RETURN:
			title = getString(R.string.after_sale_return_material_title);
			break;
		case CANCEL:
			title = getString(R.string.after_sale_cancel_material_title);
			break;
		case CHANGE:
			title = getString(R.string.after_sale_change_material_title);
			break;
		case UPDATE:
			title = getString(R.string.after_sale_update_material_title);
			break;
		case LEASE:
			title = getString(R.string.after_sale_lease_material_title);
			break;
		}
		setContentView(R.layout.activity_after_sale_material);
		new TitleMenuUtil(this, title).show();

			initWebView();
		mWebView.loadUrl(mUrl);

//		content = (TextView) findViewById(R.id.content);
//		content.setAutoLinkMask(Linkify.ALL);
//		content.setMovementMethod(LinkMovementMethod.getInstance());//加这句才能让里面的超链接生效
//		ReviewImgGetter = new URLImageParser(content,this);//实例化URLImageGetter类
//		content.setText(Html.fromHtml(mUrl,ReviewImgGetter,null));

	}

	private void initWebView() {
		loadingDialog = DialogUtil.getLoadingDialg(this);

		mWebView = (WebView) findViewById(R.id.after_sale_material_web_view);
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
