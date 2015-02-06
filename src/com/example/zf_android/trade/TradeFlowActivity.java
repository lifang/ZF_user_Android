package com.example.zf_android.trade;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.zf_android.R;

import java.util.List;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowActivity extends FragmentActivity{

	private PagerTabWidget mPagerTabWidget;

	private List<TradeFlowFragment> mFragments;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trade_flow);
		initViews();
	}

	private void initViews() {
		mPagerTabWidget = (PagerTabWidget) findViewById(R.id.tab_widget);
		String[] tabs = getResources().getStringArray(R.array.trade_flow_tabs);
		for (String tab : tabs) {
			mPagerTabWidget.addTab(tab);
		}

	}

	


}
