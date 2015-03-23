package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.EditText;

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
	private EditText tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id=getIntent().getIntExtra("ID", 0);
		if(id==0){
			new TitleMenuUtil(MerchantEdit.this, "创建商户").show();
		}else{
			new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name")).show();
			getData();
		}
	}
	private void getData() {
		// TODO Auto-generated method stub
		
	}
}
