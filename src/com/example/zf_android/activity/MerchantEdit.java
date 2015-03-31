package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
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
	private TextView tv7;
	private EditText tv1,tv2,tv3,tv4,tv5,tv6,tv8,tv9,tvkhyh;
	private String title,legalPersonName,legalPersonCardId,businessLicenseNo,taxRegisteredNo,organizationCodeNo,
	accountBankName,bankOpenAccount,cardIdFrontPhotoPath,cardIdBackPhotoPath,bodyPhotoPath,licenseNoPicPath,taxNoPicPath,
	orgCodeNoPicPath,accountPicPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id=getIntent().getIntExtra("ID", 0);
		new TitleMenuUtil(MerchantEdit.this, "编辑商户").show();
		initView();
		if(id==0){
			
		}else{
			new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name")).show();
			 
		}
		initView();
	}
	private void initView() {
		tv1=(EditText) findViewById(R.id.tv1);
		tv2=(EditText) findViewById(R.id.tv2);
		tv3=(EditText) findViewById(R.id.tv3);
		tv4=(EditText) findViewById(R.id.tv4);
		tv5=(EditText) findViewById(R.id.tv5);
		tv6=(EditText) findViewById(R.id.tv6);
		tv7=(TextView) findViewById(R.id.tv7);
		tv8=(EditText) findViewById(R.id.tv8);
		tvkhyh=(EditText) findViewById(R.id.tvkhyh);
	}
	private void getData() {
 
		legalPersonName=tv2.getText().toString();
		legalPersonCardId=tv3.getText().toString();
		businessLicenseNo=tv4.getText().toString();
		taxRegisteredNo=tv5.getText().toString();
		organizationCodeNo=tv6.getText().toString();
 
		accountBankName=tvkhyh.getText().toString();
		bankOpenAccount=tv8.getText().toString();
	}
}
