package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.City;
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
public class Adress4Edit extends BaseActivity{
	private Button adresslist;
	private EditText tv_name,tv_phone,tv_email,tv_addr;
	private int id=MyApplication.getInstance().getCustomerId();
	private String Cityid;
	private String name,tel,stringcode ,address,cityname;
	private int isDefault=2;
	private TextView tv_city;
	private CheckBox item_cb;
	private LinearLayout mi_r4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adress_edit);
		initView();
		new TitleMenuUtil(Adress4Edit.this, "修改地址").show();
		Cityid= getIntent().getStringExtra("cityId");
		name= getIntent().getStringExtra("receiver");
		tel= getIntent().getStringExtra("moblephone");
		stringcode= getIntent().getStringExtra("zipCode");
		address= getIntent().getStringExtra("address");
		int a=getIntent().getIntExtra("isDefault", 2);
		cityname= getIntent().getStringExtra("cityname");
		id=getIntent().getIntExtra("id",0);
		tv_name.setText(name);
		tv_phone.setText(tel);
		tv_email.setText(stringcode);
		tv_city.setText(cityname);
		tv_addr.setText(address);
		if(a==1){
			item_cb.setChecked(true);
		}else{
			item_cb.setChecked(false);
		}
	}
	
	private void getData() {
        API.EditAdres(Adress4Edit.this, Cityid ,name,tel,stringcode , address ,isDefault,id,
                new HttpCallback(Adress4Edit.this) {

					@Override
					public void onSuccess(Object data) {
						Toast.makeText(Adress4Edit.this, " 修改地址成功", 1000).show();
						 
						Intent intent2 = new Intent();
					 
						Adress4Edit.this.setResult(22, intent2);
						finish();
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
                });
	}
	private void initView() {
		tv_name=(EditText) findViewById(R.id.tv_name);
		tv_phone=(EditText) findViewById(R.id.tv_phone);
		tv_email=(EditText) findViewById(R.id.tv_email);
		tv_addr=(EditText) findViewById(R.id.tv_addr);
		tv_city=(TextView) findViewById(R.id.tv_city);
		tv_city.setText(MyApplication.getInstance().getCityName());
		adresslist=(Button) findViewById(R.id.adresslist);
		adresslist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(check()){
					getData();
				}
			}
		});
		item_cb=(CheckBox) findViewById(R.id.item_cb);
		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					isDefault=1;
				}else{
					isDefault=2;
				}
			}
		});
		mi_r4=(LinearLayout) findViewById(R.id.mi_r4);
		mi_r4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Adress4Edit.this, CityProvinceActivity.class);
				startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			}
		});
	}
	
	private Boolean check() {
		name=StringUtil.replaceBlank(tv_name.getText().toString());
		if(name.length()==0){
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		tel=StringUtil.replaceBlank(tv_phone.getText().toString());
		if(tel.length()==0){
			Toast.makeText(getApplicationContext(), "手机号不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!StringUtil.isMobile(tel)) {
			Toast.makeText(getApplicationContext(), "请输入正确的手机号", 
					Toast.LENGTH_SHORT).show();
			return false;
		}
		stringcode=StringUtil.replaceBlank(tv_email.getText().toString());
		if(stringcode.length()==0){
			Toast.makeText(getApplicationContext(), "邮编不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!StringUtil.isZipNO(stringcode)) {
			Toast.makeText(getApplicationContext(), "请输入正确的邮编",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		address=StringUtil.replaceBlank(tv_addr.getText().toString());
		if(address.length()==0){
			Toast.makeText(getApplicationContext(), "请输入详细地址",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode==REQUEST_CHOOSE_CITY){
			if (data != null) {
				City mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
				tv_city.setText(mMerchantCity.getName());
				Cityid=mMerchantCity.getId()+"" ;	 
			}
		}

	}
}
