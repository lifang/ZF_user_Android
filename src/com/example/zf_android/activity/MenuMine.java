package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

public class MenuMine extends BaseActivity implements OnClickListener{
	private ImageView search;
	private LinearLayout  ll_dd,ll_shjl,ll_wdxx,ll_sh,ll_request;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_mine);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		search=(ImageView) findViewById(R.id.search);
		search.setOnClickListener(this);
		ll_dd=(LinearLayout) findViewById(R.id.ll_dd);
		ll_dd.setOnClickListener(this);
		ll_shjl=(LinearLayout) findViewById(R.id.ll_shjl);
		ll_shjl.setOnClickListener(this);
		ll_wdxx=(LinearLayout) findViewById(R.id.ll_wdxx);
		ll_wdxx.setOnClickListener(this);
		ll_sh=(LinearLayout) findViewById(R.id.ll_sh);
		ll_sh.setOnClickListener(this);
		ll_request=(LinearLayout) findViewById(R.id.ll_request);
		ll_request.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	case R.id.ll_request:
			
			break;
		case R.id.ll_sh:
			
			break;
		case R.id.search:
			startActivity(new Intent(MenuMine.this,MineSet.class));
			break;
		case R.id.ll_dd:
		 
			startActivity(new Intent(MenuMine.this,OrderList.class));
			break;
		case R.id.ll_shjl: 
			 
			startActivity(new Intent(MenuMine.this,OrderList.class));
			break;
		case R.id.ll_wdxx: 
		 
		startActivity(new Intent(MenuMine.this,OrderList.class));
		break;
		default:
			break;
		}
	}
}
