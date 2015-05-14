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

import com.examlpe.zf_android.util.MyToast;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
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
public class AdressEdit extends BaseActivity{
	private Button adresslist;
	private EditText tv_name,tv_phone,tv_email,tv_addr;
	private int id=MyApplication.getInstance().getCustomerId();
	private int Cityid=MyApplication.getInstance().getCityId();
	private String name,tel,stringcode ,address;
	private int isDefault=2;
	private TextView tv_city;
	private CheckBox item_cb;
	private LinearLayout mi_r4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adress_edit);
		initView();
		new TitleMenuUtil(AdressEdit.this, "新增地址").show();
	}

	private void getData() {
		API.AddAdres(AdressEdit.this, Cityid+"" ,name,tel,stringcode , address ,isDefault,id,
				new HttpCallback(AdressEdit.this) {

			@Override
			public void onSuccess(Object data) {
				MyToast.showToast(AdressEdit.this, "添加地址成功");
				Config.newAddAddressId = Integer.parseInt(data+"");
				Intent intent2 = new Intent();
				AdressEdit.this.setResult(1, intent2);
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

				Intent intent = new Intent(AdressEdit.this, CityProvinceActivity.class);
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
				Cityid=mMerchantCity.getId() ;	 
			}
		}

	}
}
