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
import android.widget.LinearLayout;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
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
	private String URL="http://114.215.149.242:18080/ZFMerchant/api/customers/updateAddress";
 
	private Button adresslist;
	private EditText tv1,tv2,tv3,tv5;
	private int id=MyApplication.NewUser.getId();
	private String Cityid;
	private String name,tel,stringcode ,address,cityname;
	private int isDefault=2;
	private TextView tv4;
	private CheckBox item_cb;
	private LinearLayout mi_r4;
	private int a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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
		tv1.setText(name);
		tv2.setText(tel);
		tv3.setText(stringcode);
		tv4.setText(cityname);
		tv5.setText(address);
		if(a==1){
			item_cb.setChecked(true);
		}else{
			item_cb.setChecked(false);
		}
 
		
	}
	private Boolean check() {
		// TODO Auto-generated method stub
		name=StringUtil.replaceBlank(tv1.getText().toString());
		if(name.length()==0){
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		tel=StringUtil.replaceBlank(tv2.getText().toString());
		if(tel.length()==0){
			Toast.makeText(getApplicationContext(), "手机号不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		stringcode=StringUtil.replaceBlank(tv3.getText().toString());
		if(stringcode.length()==0){
			Toast.makeText(getApplicationContext(), "邮编不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		address=StringUtil.replaceBlank(tv5.getText().toString());
		if(address.length()==0){
			Toast.makeText(getApplicationContext(), "请输入详细地址",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
//	String  cityId,
//	String  receiver,
//	String 	moblephone,
//	String 	zipCode,
//	String 	address,
//	int 	isDefault,
//	int 	customerId,
	
	private void getData() {
		// TODO Auto-generated method stub
        API.EditAdres(Adress4Edit.this, Cityid ,name,tel,stringcode , address ,isDefault,id,
        		
                new HttpCallback(Adress4Edit.this) {
           

					@Override
					public void onSuccess(Object data) {
						// TODO Auto-generated method stub
						Toast.makeText(Adress4Edit.this, " 修改地址成功", 1000).show();
						 
						Intent intent2 = new Intent();
					 
						Adress4Edit.this.setResult(22, intent2);
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
		tv4=(TextView) findViewById(R.id.tv4);
		tv4.setText(MyApplication.getInstance().getCityName());
		adresslist=(Button) findViewById(R.id.adresslist);
		adresslist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(check()){
					getData();
				}
			}
		});
		item_cb=(CheckBox) findViewById(R.id.item_cb);
		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				Intent intent = new Intent(Adress4Edit.this, CityProvinceActivity.class);
				 
				startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("resultCode"+resultCode+requestCode);
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==REQUEST_CHOOSE_CITY){
			System.out.println("REQUEST_CHOOSE_CITY"+resultCode+requestCode);
			City mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
			tv4.setText(mMerchantCity.getName());
			Cityid=mMerchantCity.getId()+"" ;	 
		}

	}
}
