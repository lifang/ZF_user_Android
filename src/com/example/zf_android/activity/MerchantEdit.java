package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;
/**
 * 
*    
* 类名称：MerchantEdit   
* 类描述：   编辑新建
* 创建人： ljp 
* 创建时间：2015-3-12 下午3:28:08   
* @version    
*
 */
public class MerchantEdit extends BaseActivity{
	private int id;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tvkhyh,tv8,tv9;
	private MerchantEntity merchantEntity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id=getIntent().getIntExtra("ID", 0);
		initView();
		if(id==0){
			new TitleMenuUtil(MerchantEdit.this, "创建商户").show();
		}else{
			new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name")).show();
			 
		}
		initView();
	}
	private void initView() {
		tv1=(TextView) findViewById(R.id.tv1);
		tv2=(TextView) findViewById(R.id.tv2);
		tv3=(TextView) findViewById(R.id.tv3);
		tv4=(TextView) findViewById(R.id.tv4);
		tv5=(TextView) findViewById(R.id.tv5);
		tv6=(TextView) findViewById(R.id.tv6);
		tv7=(TextView) findViewById(R.id.tv7);
		tv8=(TextView) findViewById(R.id.tv8);
		tvkhyh=(TextView) findViewById(R.id.tvkhyh);
	}
	private void getData() {
		getInfo();
	}
	
	private void getInfo() {
		 API.merchantInfo(MerchantEdit.this,id,
	                new HttpCallback<MerchantEntity> (MerchantEdit.this) {

						@Override
						public void onSuccess(MerchantEntity data) {
							merchantEntity = data;
							tv1.setText(data.getTitle());
							tv2.setText(data.getLegal_person_name());
							tv3.setText(data.getLegal_person_card_id());
							tv4.setText(data.getBusiness_license_no());
							tv5.setText(data.getTax_registered_no());
							tv6.setText(data.getOrganization_code_no());
							tvkhyh.setText(data.getAccount_bank_name());
							tv8.setText(data.getBank_open_account());

						}

						@Override
						public TypeToken<MerchantEntity> getTypeToken() {
							return new TypeToken<MerchantEntity>() {
							};
						}
	                });

	}

}
