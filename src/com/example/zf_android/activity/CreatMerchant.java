package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
/***
 * 
*    
* 类名称：CreatMerchant   
* 类描述：   创建商户
* 创建人： ljp 
* 创建时间：2015-3-23 上午10:10:30   
* @version    
*
 */
public class CreatMerchant extends BaseActivity{
	private EditText tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_new);
		new TitleMenuUtil(CreatMerchant.this, "创建商户").show();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv1=(EditText) findViewById(R.id.tv1);
		tv2=(EditText) findViewById(R.id.tv2);
		tv3=(EditText) findViewById(R.id.tv3);
		tv4=(EditText) findViewById(R.id.tv4);
		tv5=(EditText) findViewById(R.id.tv5);
		tv6=(EditText) findViewById(R.id.tv6);
		tv7=(EditText) findViewById(R.id.tv7);
		tv8=(EditText) findViewById(R.id.tv8);
		tv9=(EditText) findViewById(R.id.tv9);
	}
}
