package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;
/**
 * 
*    
* 类名称：AdressEdit   
* 类描述：  编辑/新加
* 创建人： ljp 
* 创建时间：2015-3-9 下午5:10:08   
* @version    
*
 */
public class AdressEdit extends BaseActivity{
	private String URL="http://114.215.149.242:18080/ZFMerchant/api/customers/insertAddress/";
//	cityId
//	receiver
//	moblephone
//	zipCode
//	address
//	isDefault
//	customerId
	private EditText tv1,tv2,tv3,tv5;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adress_edit);
		initView();
		getData();
	}
	private void getData() {
		// TODO Auto-generated method stub
        API.AddAdres(AdressEdit.this, "80" , "ljp","13011112222","邮编" , "地址地址" ,2,80,
        		
                new HttpCallback(AdressEdit.this) {
           

					@Override
					public void onSuccess(Object data) {
						// TODO Auto-generated method stub
						Toast.makeText(AdressEdit.this, "新加成功", 1000).show();
						finish();
					}

					@Override
					public TypeToken getTypeToken() {
						// TODO Auto-generated method stub
						return null;
					}
                });
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv1=(EditText) findViewById(R.id.tv1);
		tv2=(EditText) findViewById(R.id.tv2);
		tv3=(EditText) findViewById(R.id.tv3);
		tv5=(EditText) findViewById(R.id.tv5);
	}
	 
}
