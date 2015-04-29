package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.AfterSaleGridActivity;
import com.example.zf_android.trade.ApplyOpenProgressActivity;

public class MenuMine extends BaseActivity implements OnClickListener{
	private ImageView search;
	private LinearLayout  ll_dd,ll_shjl,ll_wdxx,ll_sh,ll_request;
	private RelativeLayout  main_rl1, main_rl2, main_rl3;
	private TextView countShopCar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.menu_mine);
		initView();
	}
	private void initView() {
		countShopCar = (TextView) findViewById(R.id.countShopCar);
		main_rl1=(RelativeLayout) findViewById(R.id.main_rl_sy);
		main_rl1.setOnClickListener(this);
		main_rl2=(RelativeLayout) findViewById(R.id.main_rl_gwc);
		main_rl2.setOnClickListener(this);
		main_rl3=(RelativeLayout) findViewById(R.id.main_rl_pos1);
		main_rl3.setOnClickListener(this);

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
	protected void onResume() {
		super.onResume();
		if (Config.countShopCar != 0) {
			countShopCar.setVisibility(View.VISIBLE);
			countShopCar.setText(Config.countShopCar+"");
		}else {
			countShopCar.setVisibility(View.GONE);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_request:
			//申请进度查询
			startActivity(new Intent(this, ApplyOpenProgressActivity.class));
			break;
		case R.id.ll_sh:
			//我的商户
			startActivity(new Intent(MenuMine.this,MerchantList.class));
			break;
		case R.id.search:
			//我的设置
			startActivity(new Intent(MenuMine.this,MineSet.class));
			break;
		case R.id.ll_dd:
			//我的订单
			startActivity(new Intent(MenuMine.this,OrderList.class));
			break;
		case R.id.ll_shjl: 
			//售后记录
			startActivity(new Intent(MenuMine.this,AfterSaleGridActivity.class));
			break;
		case R.id.ll_wdxx: 
			//我的信息
			startActivity(new Intent(MenuMine.this,MyInfo.class));
			break;

		case R.id.main_rl_sy: 

			startActivity(new Intent(MenuMine.this,Main.class));
			break;
		case R.id.main_rl_gwc: 

			startActivity(new Intent(MenuMine.this,ShopCar.class));
			break;
		case R.id.main_rl_pos1:  

			startActivity(new Intent(MenuMine.this,MyMessage.class));
			break;
		default:
			break;
		}
	}
}
