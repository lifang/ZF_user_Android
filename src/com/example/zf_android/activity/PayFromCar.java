package com.example.zf_android.activity;

import android.os.Bundle;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

/**
 * 
*    
* ����ƣ�PayFromCar   
* ��������   ���ﳵ֧��
* �����ˣ� ljp 
* ����ʱ�䣺2015-2-9 ����4:41:50   
* @version    
*
 */
public class PayFromCar extends BaseActivity{
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.pay);
			new TitleMenuUtil(PayFromCar.this, "选择支付方式").show();
		}
}
