package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;


public class PayFromCar extends BaseActivity{
		private TextView tv_pay;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pay);
			 
			tv_pay=(TextView) findViewById(R.id.tv_pay);
			 
			
			new TitleMenuUtil(PayFromCar.this, "选择支付方式").show();
		}
}
