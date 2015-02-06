package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

public class PosSelect extends BaseActivity implements  OnClickListener{
	private ImageView img_on_off;
	private LinearLayout ll_pp,ll_type, ll_pay_type,ll_paycard_type,ll_jy_type,ll_qgd_type,ll_time,ll_zdj,ll_zgj;
	private TextView tv_pp,tv_type,tv_pay_type,tv_paycard_type,tv_jy_type,tv_qgd_type,tv_time,tv_zdj,tv_zgj;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posselect);
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv_zdj=(TextView) findViewById(R.id.tv_zdj);
		tv_zgj=(TextView) findViewById(R.id.tv_zgj);
		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_qgd_type=(TextView) findViewById(R.id.tv_qgd_type);
		tv_jy_type=(TextView) findViewById(R.id.tv_jy_type);
		tv_paycard_type=(TextView) findViewById(R.id.tv_paycard_type);
		tv_pay_type=(TextView) findViewById(R.id.tv_pay_type);
		tv_type=(TextView) findViewById(R.id.tv_type);
		tv_pp=(TextView) findViewById(R.id.tv_pp);
		img_on_off=(ImageView) findViewById(R.id.img_on_off);
		img_on_off.setOnClickListener(this);
		ll_pp=(LinearLayout) findViewById(R.id.ll_pp);
		ll_pp.setOnClickListener(this);
		ll_type=(LinearLayout) findViewById(R.id.ll_type);
		ll_type.setOnClickListener(this);
		ll_pay_type=(LinearLayout) findViewById(R.id.ll_pay_type);
		ll_pay_type.setOnClickListener(this);
		ll_paycard_type=(LinearLayout) findViewById(R.id.ll_paycard_type);
		ll_paycard_type.setOnClickListener(this);
		ll_jy_type=(LinearLayout) findViewById(R.id.ll_jy_type);
		ll_jy_type.setOnClickListener(this);
		ll_qgd_type=(LinearLayout) findViewById(R.id.ll_qgd_type);
		ll_qgd_type.setOnClickListener(this);
		ll_time=(LinearLayout) findViewById(R.id.ll_time);
		ll_time.setOnClickListener(this);
		ll_zdj=(LinearLayout) findViewById(R.id.ll_zdj);
		ll_zdj.setOnClickListener(this);
		ll_zgj=(LinearLayout) findViewById(R.id.ll_zgj);
		ll_zgj.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_on_off:  // 开关
		 
			break;
		case R.id.ll_pp:  // 选择品牌
			 
			break;
		case R.id.ll_type:  // 选择品牌ll_pay_type
			 
			break;
		case R.id.ll_pay_type:  //  
			 
			break;
		case R.id.ll_paycard_type:  
			 
			break;
		case R.id.ll_qgd_type:   
			 
			break;
		case R.id.ll_time:   
		 
		break;
		default:
			break;
		}
	}
}
