package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.trade.AfterSaleGridActivity;
import com.example.zf_android.trade.ApplyOpenProgressActivity;


public class MenuMine extends BaseActivity implements OnClickListener{
	private ImageView search;
	private LinearLayout  ll_dd,ll_shjl,ll_wdxx,ll_sh,ll_request;
	private RelativeLayout  main_rl1, main_rl2, main_rl3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_mine);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	case R.id.ll_request:
//		startActivity(new Intent(MenuMine.this,ApplyDetail.class));
			startActivity(new Intent(this, ApplyOpenProgressActivity.class));
			break;
		case R.id.ll_sh:
			startActivity(new Intent(MenuMine.this,MerchantList.class));
			break;
		case R.id.search:
			startActivity(new Intent(MenuMine.this,MineSet.class));
			break;
		case R.id.ll_dd:
		 
			startActivity(new Intent(MenuMine.this,OrderList.class));
			break;
		case R.id.ll_shjl: 
			 
			startActivity(new Intent(MenuMine.this,AfterSaleGridActivity.class));
			break;
		case R.id.ll_wdxx: 
		 
		startActivity(new Intent(MenuMine.this,MyInfo.class));
		break;
		
		case R.id.main_rl_sy: //ϵͳ����   
			 
			  
			 startActivity(new Intent(MenuMine.this,Main.class));
			 
			break;

		case R.id.main_rl_gwc: //��ϵ����

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
