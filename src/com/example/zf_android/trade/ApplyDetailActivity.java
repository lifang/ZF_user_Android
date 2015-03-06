package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.widget.MyTabWidget;
import com.google.gson.reflect.TypeToken;

import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;

/**
 * Created by Leo on 2015/3/5.
 */
public class ApplyDetailActivity extends Activity {

	private int mTerminalId;

	private MyTabWidget mTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_detail);
		new TitleMenuUtil(this, getString(R.string.title_apply_open)).show();
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);

		initViews();
		loadData();
	}

	private void initViews() {
		mTab = (MyTabWidget) findViewById(R.id.apply_detail_tab);
		mTab.setOnTabSelectedListener(new MyTabWidget.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position) {

			}
		});
	}

	private void loadData() {
		API.getApplyDetail(this, mTerminalId, 80, 1, new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {

			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
		API.getApplyDetail(this, mTerminalId, 80, 2, new HttpCallback(this) {
			@Override
			public void onSuccess(Object data) {

			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}


}
