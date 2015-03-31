package com.example.zf_android.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.Constants;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
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
	private static final int TYPE_1 = 1;
	private static final int TYPE_2 = 2;
	private static final int TYPE_3 = 3;
	private static final int TYPE_4 = 4;
	private static final int TYPE_5 = 5;
	private static final int TYPE_6 = 6;
	private static final int TYPE_7 = 7;
	private static final int TYPE_8 = 8;
	private static final int TYPE_KHYH = 9;
	private static final int TYPE_10 = 10;
	private static final int TYPE_11 = 11;
	private static final int TYPE_12 = 12;
	private static final int TYPE_13 = 13;
	private static final int TYPE_14 = 14;
	private static final int TYPE_15 = 15;
	private static final int TYPE_16 = 16;
	private static final int TYPE_17 = 17;
	private int id;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tvkhyh,tv8,tv9;
	private MerchantEntity merchantEntity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id=getIntent().getIntExtra("ID", 0);
 
		new TitleMenuUtil(MerchantEdit.this, "编辑商户").show();
 
		initView();
		if(id==0){
			new TitleMenuUtil(MerchantEdit.this, "创建商户").show();
		}else{
			new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name")).show();
			getData();
		}
	}
	private void initView() {
		tv1=(TextView) findViewById(R.id.tv1);
		tv1.setOnClickListener(new ItemOnClickListener(TYPE_1, "商铺名称（商户名）"));
		tv2=(TextView) findViewById(R.id.tv2);
		tv2.setOnClickListener(new ItemOnClickListener(TYPE_2, "商户法人姓名"));
		tv3=(TextView) findViewById(R.id.tv3);
		tv3.setOnClickListener(new ItemOnClickListener(TYPE_3, "商户法人身份证号"));
		tv4=(TextView) findViewById(R.id.tv4);
		tv4.setOnClickListener(new ItemOnClickListener(TYPE_4, "营业执照登记号"));
		tv5=(TextView) findViewById(R.id.tv5);
		tv5.setOnClickListener(new ItemOnClickListener(TYPE_5, "税务证号"));
		tv6=(TextView) findViewById(R.id.tv6);
		tv6.setOnClickListener(new ItemOnClickListener(TYPE_6, "组织机构代码证号"));
		tv7=(TextView) findViewById(R.id.tv7);
		tv7.setOnClickListener(new ItemOnClickListener(TYPE_7, "商户所在地"));
		tvkhyh=(TextView) findViewById(R.id.tvkhyh);
		tvkhyh.setOnClickListener(new ItemOnClickListener(TYPE_KHYH, "开户银行"));
		tv8=(TextView) findViewById(R.id.tv8);
		tv8.setOnClickListener(new ItemOnClickListener(TYPE_8, "银行开户许可证号"));
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
							
							if(data.getCity_id() != 0){
								List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
								for (Province province : provinces) {
									List<City> cities = province.getCities();
									for (City city : cities) {
										if(city.getId() == data.getCity_id()){
											tv7.setText(city.getName());
											return;
										}
									}
								}
							}
						}

						@Override
						public TypeToken<MerchantEntity> getTypeToken() {
							return new TypeToken<MerchantEntity>() {
							};
						}
	                });

	}
	class ItemOnClickListener implements View.OnClickListener{
		private int type;
		private String title;

		public ItemOnClickListener(int type, String title) {
			super();
			this.type = type;
			this.title = title;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("title", title);
			if(merchantEntity == null){
				merchantEntity = new MerchantEntity();
			}
			switch (type) {
			case TYPE_1:
				intent.putExtra("value", merchantEntity.getTitle());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_2:
				intent.putExtra("value", merchantEntity.getLegal_person_name());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_3:
				intent.putExtra("value", merchantEntity.getLegal_person_card_id());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_4:
				intent.putExtra("value", merchantEntity.getBusiness_license_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_5:
				intent.putExtra("value", merchantEntity.getTax_registered_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_6:
				intent.putExtra("value", merchantEntity.getOrganization_code_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_7:
				intent.putExtra("value", merchantEntity.getCity_id());
				intent.setClass(MerchantEdit.this, CityProvinceActivity.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_KHYH:
				intent.putExtra("value", merchantEntity.getAccount_bank_name());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_8:
				intent.putExtra("value", merchantEntity.getBank_open_account());
				startActivityForResult(intent, type);
				break;
			default:
				break;
			}
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode!=RESULT_OK) {
			return;
		}
		String value = data.getStringExtra("value");
		switch (requestCode) {
		case TYPE_1:
			merchantEntity.setTitle(value);
			tv1.setText(value);
			break;
		case TYPE_2:
			merchantEntity.setLegal_person_name(value);
			tv2.setText(value);
			break;
		case TYPE_3:
			merchantEntity.setLegal_person_card_id(value);
			tv3.setText(value);
			break;
		case TYPE_4:
			merchantEntity.setBusiness_license_no(value);
			tv4.setText(value);
			break;
		case TYPE_5:
			merchantEntity.setTax_registered_no(value);
			tv5.setText(value);
			break;
		case TYPE_6:
			merchantEntity.setOrganization_code_no(value);
			tv6.setText(value);
			break;
		case TYPE_7:
			City city = (City)data.getSerializableExtra(Constants.CityIntent.SELECTED_CITY);
			if(city == null){
				merchantEntity.setCity_id(0);
				tv7.setText("");
			} else{
				merchantEntity.setCity_id(city.getId());
				tv7.setText(city.getName());
			}
	
			break;
		case TYPE_KHYH:
			merchantEntity.setAccount_bank_name(value);
			tvkhyh.setText(value);
			break;
		case TYPE_8:
			merchantEntity.setBank_open_account(value);
			tv8.setText(value);
			break;
		default:
			break;
		}
	}

}
