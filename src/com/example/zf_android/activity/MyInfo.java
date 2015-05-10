package com.example.zf_android.activity;


import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MyinfoEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;

public class MyInfo extends BaseActivity implements OnClickListener{
	private List<City> mCities = new ArrayList<City>();
	private Button btn_exit;
	private LinearLayout mi_name,mi_phone,mi_email,mi_location,mi_point,mi_r6,mi_r7;
	private TextView tv_name,tv_phone,tv_email,tv_location,tv_point;
	private int cityId=MyApplication.getInstance().getCityId();
	private int customerId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.myinfo);
		customerId = MyApplication.getInstance().getCustomerId();
		new TitleMenuUtil(MyInfo.this, "我的信息").show();
		initView();

	}

	private void initView() {
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
		mi_name=(LinearLayout) findViewById(R.id.mi_name);
		mi_name.setOnClickListener(this);
		mi_phone=(LinearLayout) findViewById(R.id.mi_phone);
		mi_phone.setOnClickListener(this);
		mi_email=(LinearLayout) findViewById(R.id.mi_email);
		mi_email.setOnClickListener(this);
		mi_location=(LinearLayout) findViewById(R.id.mi_location);
		mi_location.setOnClickListener(this);
		mi_point=(LinearLayout) findViewById(R.id.mi_point);
		mi_point.setOnClickListener(this);
		mi_r6=(LinearLayout) findViewById(R.id.mi_r6);
		mi_r6.setOnClickListener(this);
		mi_r7=(LinearLayout) findViewById(R.id.mi_r7);
		mi_r7.setOnClickListener(this);
		tv_name=(TextView) findViewById(R.id.tv_name);
		tv_phone=(TextView) findViewById(R.id.tv_phone);
		tv_email=(TextView) findViewById(R.id.tv_email);
		tv_location=(TextView) findViewById(R.id.tv_location);
		tv_point=(TextView) findViewById(R.id.tv_point);
		tv_location.setText("");

		getdata();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.btn_exit:
			exit();
			break;
		case  R.id.mi_name: 
			intent =new Intent(MyInfo.this,ChangeText.class);
			intent.putExtra("key", 1);
			intent.putExtra("name", tv_name.getText().toString());
			startActivityForResult(intent, 1);

			break;
		case  R.id.mi_phone: // �ֻ� 
			
			intent =new Intent(MyInfo.this,ChangePhone.class);
			intent.putExtra("key", 2);
			intent.putExtra("name", tv_phone.getText().toString());
			startActivityForResult(intent, 2);
//			intent =new Intent(MyInfo.this,ChangeText.class);
//			intent.putExtra("key", 2);
//			intent.putExtra("name", tv_phone.getText().toString());
//			startActivityForResult(intent, 2);

			break;
		case  R.id.mi_email: 
			intent =new Intent(MyInfo.this,ChangeEmail.class);
			intent.putExtra("key",3);
			intent.putExtra("name", tv_email.getText().toString());
			startActivityForResult(intent, 3);
			
//			intent =new Intent(MyInfo.this,ChangeText.class);
//			intent.putExtra("key",3);
//			intent.putExtra("name", tv_email.getText().toString());
//			startActivityForResult(intent, 3);

			break;
		case  R.id.mi_location: 
			intent = new Intent(MyInfo.this, CityProvinceActivity.class);

			startActivityForResult(intent, REQUEST_CHOOSE_CITY);

			break;
		case  R.id.mi_point: 
			intent =new Intent(MyInfo.this,JifenActivity.class);

			startActivityForResult(intent, 3);

			break;
		case  R.id.mi_r6: 

			startActivity(new Intent(MyInfo.this,AdressList.class));

			break;
		case  R.id.mi_r7:
			startActivity(new Intent(MyInfo.this,ChangePassword.class));

			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultCode"+resultCode+requestCode);
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CHOOSE_CITY){
			System.out.println("REQUEST_CHOOSE_CITY"+resultCode+requestCode);
			if (data != null) {
				City mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
				tv_location.setText(mMerchantCity.getName());
				cityId=mMerchantCity.getId();
				
				change();
			}

		}
		switch (resultCode) {
		case 1:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv_name.setText(a);
			}
			change();
			break;
		case 2:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv_phone.setText(a);
			}
			change();
			break;
		case 3:

			if(data!=null){
				String  a =data.getStringExtra("text");
				tv_email.setText(a);
			}
			change();
			break;

		default:
			break;

		}

	}
	private String  findcity(int id) {
		String addr="苏州";
		List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
		for (Province province : provinces) {
			List<City> cities = province.getCities();

			mCities.addAll(cities);

		}
		for(City city:mCities ){
			if(city.getId()==id){
				addr=city.getName();
			}
		}
		return addr;
	}


	private void exit() {
		Toast.makeText(getApplicationContext(), "退出成功", 1000).show();
		MyApplication.getInstance().exit();
	}
	private void change(){

		String name =tv_name.getText().toString();
		String phone=tv_phone.getText().toString();
		String email=tv_email.getText().toString();
		API.ChangeMyInfo(MyInfo.this,MyApplication.getInstance().getCustomerId(),name,phone,email,cityId,

				new HttpCallback (MyInfo.this) {

			@Override
			public void onSuccess(Object data) {
				Toast.makeText(getApplicationContext(), "信息更新成", 1000).show();
			}

			@Override
			public TypeToken getTypeToken() {
				return   null;
			};
		});
	}
	private void getdata(){


		API.getinfo(MyInfo.this,customerId,
				new HttpCallback<MyinfoEntity> (MyInfo.this) {

			@Override
			public void onSuccess(MyinfoEntity data) {
				tv_name.setText(data.getName());
				tv_phone.setText(data.getPhone());
				tv_email.setText(data.getEmail());
				tv_point.setText(data.getIntegral()+"");

				tv_location.setText(findcity(Integer.valueOf(data.getCity_id())));
			}
			@Override
			public TypeToken getTypeToken() {
				return  new TypeToken<MyinfoEntity>() {
				};
			};
		});
	}

}
