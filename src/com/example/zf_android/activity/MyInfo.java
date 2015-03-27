package com.example.zf_android.activity;
 
 
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	private LinearLayout mi_r1,mi_r2,mi_r3,mi_r4,mi_r5,mi_r6,mi_r7;
	private TextView tv1,tv2,tv3,tv4,tv5;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private int cityId=MyApplication.NewUser.getCityId();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		new TitleMenuUtil(MyInfo.this, "我的信息").show();
		initView();
		
	}
	 

	private void initView() {
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
		mi_r1=(LinearLayout) findViewById(R.id.mi_r1);
		mi_r1.setOnClickListener(this);
		mi_r2=(LinearLayout) findViewById(R.id.mi_r2);
		mi_r2.setOnClickListener(this);
		mi_r3=(LinearLayout) findViewById(R.id.mi_r3);
		mi_r3.setOnClickListener(this);
		mi_r4=(LinearLayout) findViewById(R.id.mi_r4);
		mi_r4.setOnClickListener(this);
		mi_r5=(LinearLayout) findViewById(R.id.mi_r5);
		mi_r5.setOnClickListener(this);
		mi_r6=(LinearLayout) findViewById(R.id.mi_r6);
		mi_r6.setOnClickListener(this);
		mi_r7=(LinearLayout) findViewById(R.id.mi_r7);
		mi_r7.setOnClickListener(this);
		tv1=(TextView) findViewById(R.id.tv1);
		tv2=(TextView) findViewById(R.id.tv2);
		tv3=(TextView) findViewById(R.id.tv3);
		tv4=(TextView) findViewById(R.id.tv4);
		tv5=(TextView) findViewById(R.id.tv5);
		tv4.setText("");
		 
		getdata();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_exit:
			exit();
			break;
		case  R.id.mi_r1: 
			Intent i =new Intent(MyInfo.this,ChangeText.class);
			i.putExtra("key", 1);
			i.putExtra("name", tv1.getText().toString());
			startActivityForResult(i, 1);
			 
			break;
		case  R.id.mi_r2: // �ֻ�
			Intent i2 =new Intent(MyInfo.this,ChangeText.class);
			i2.putExtra("key", 2);
			i2.putExtra("name", tv2.getText().toString());
			startActivityForResult(i2, 2);
			 
			break;
		case  R.id.mi_r3: 
			Intent i3 =new Intent(MyInfo.this,ChangeText.class);
			i3.putExtra("key",3);
			i3.putExtra("name", tv3.getText().toString());
			startActivityForResult(i3, 3);
			 
			break;
		case  R.id.mi_r4: 
			Intent intent = new Intent(MyInfo.this, CityProvinceActivity.class);
	 
			startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			 
			break;
		case  R.id.mi_r5: 
			Intent jf =new Intent(MyInfo.this,JifenActivity.class);
		 
			startActivityForResult(jf, 3);
			 
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
			City mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
			tv4.setText(mMerchantCity.getName());
			cityId=mMerchantCity.getId();
			 
			 
		}
		switch (resultCode) {
		case 1:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv1.setText(a);
			}
			change();
			break;
		case 2:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv2.setText(a);
			}
			change();
			break;
		case 3:
		 
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv3.setText(a);
			}
			change();
			break;
 
		default:
			break;

		}
		 

	}
	private String  findcity(int id) {
		// TODO Auto-generated method stub
		String a="苏州";
        List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
        for (Province province : provinces) {
            List<City> cities = province.getCities();
          
            mCities.addAll(cities);
             
        }
		 for(City cc:mCities ){
			 if(cc.getId()==id){
				 a=cc.getName();
			 }
		 }
		 return a;
	}


	private void exit() {
		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		editor.putBoolean("islogin", false);
		editor.commit();
		Toast.makeText(getApplicationContext(), "退出成功", 1000).show();
		startActivity(new Intent(MyInfo.this,LoginActivity.class));
		finish();
	}
	private void change(){
		
		String name =tv1.getText().toString();
		String phone=tv2.getText().toString();
		String email=tv3.getText().toString();
		 API.ChangeMyInfo(MyInfo.this,MyApplication.NewUser.getId(),name,phone,email,cityId,
	        		
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
		
	 
		 API.getinfo(MyInfo.this,MyApplication.NewUser.getId(),
	        		
	                new HttpCallback<MyinfoEntity> (MyInfo.this) {

						@Override
						public void onSuccess(MyinfoEntity data) {
							tv1.setText(data.getName());
							tv2.setText(data.getPhone());
							tv3.setText(data.getEmail());
							tv5.setText(data.getIntegral()+"");
						 
							tv4.setText(findcity(data.getCity_id()));
						}

						@Override
						public TypeToken getTypeToken() {
							return  new TypeToken<MyinfoEntity>() {
							};
						};
	                });
	}
	 
}
