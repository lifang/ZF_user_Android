package com.example.zf_android.activity;


import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;

public class Register4phone extends BaseActivity   implements OnClickListener{
	private TextView tv_code,tv_check,tv_jy_type;
	private EditText login_edit_email,login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletemali,login_linear_deletcode,login_linear_deletpass 
	,login_linear_deletpass2,login_linear_signin,ll_jy_type;
	private int Countmun=120;
	private Boolean isRun=true;
	private ImageView img_check,img_check_n;
	public  int cityid=80;
	private String email,pass;
	private Boolean chenckcode=false;
	private Runnable runnable;
	final Handler handler = new Handler(){          // handle  
		public void handleMessage(Message msg){  
			switch (msg.what) {  
			case 1:  
				if(Countmun==0){

					isRun=false;
					tv_code.setClickable(true);

					tv_code.setText("发送验证码");
					System.out.println("destroy`"+Countmun);
				}else{
					Countmun--;  
					tv_code.setText(  Countmun+"秒后重新发送");  
					System.out.println("Countmun`D2`"+Countmun);
				}

			}  
			super.handleMessage(msg);  
		}  
	};  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cityid=MyApplication.getInstance().getCityId();
		setContentView(R.layout.register_phone);
		new TitleMenuUtil(Register4phone.this, "注册").show();
		System.out.println("Register4phone---");

		initView();
		tv_jy_type.setText("请选择地区");
		login_edit_code.setText(Config.reg_phoneCode);
		img_check.setVisibility(View.VISIBLE);
		img_check_n.setVisibility(View.GONE);
		chenckcode=true;
		runnable = new Runnable() {  
			@Override  
			public void run() {  
				if(Countmun==0){

					Countmun=120;
					tv_code.setClickable(true);
					tv_code.setText("发送验证码");
				}else{

					Countmun--;  
					tv_code.setText( Countmun+"秒后重新发送");  

					handler.postDelayed(this, 1000);  
				}

			}  
		};
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) return;
		switch (requestCode) {
		case REQUEST_CHOOSE_CITY:
			if (data != null) {
				Province	mMerchantProvince = (Province) data.getSerializableExtra(SELECTED_PROVINCE);
				City	mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
				System.out.println(mMerchantCity.getId()+"mMerchantCity"+mMerchantCity.toString());
				tv_jy_type.setText(mMerchantCity.getName());
				cityid=mMerchantCity.getId();
			}
			break;

		default:
			break;
		}
	}
	@Override
	public void onClick(View v) {
		switch ( v.getId()) {

		case R.id.ll_jy_type: 
			Intent intent = new Intent(Register4phone.this, CityProvinceActivity.class);
			startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			break;
		case R.id.tv_code:  // ��ȡ��֤��tv_check

			tv_code.setClickable(false);
			getCode();
			break;
		case R.id.tv_check:  // ��ȡ��֤�� 
			System.out.println("vcode"+Config.reg_phoneCode);
			
			if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
				Toast.makeText(getApplicationContext(), "请输入验证码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(login_edit_code.getText().toString().equals(Config.reg_phoneCode)){
				img_check.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
				chenckcode=true;
			}else{
				img_check.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
				chenckcode=false;
			}

			break;
		case R.id.login_linear_signin:  // ��ȡ��֤�� 

			if(check()){
				zhuche();
			}

			break;
		case R.id.login_linear_deletemali:
			login_edit_email.setText("");
			break;
		case R.id.login_linear_deletcode:
			login_edit_code.setText("");
			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		default : 
			break;
		}
	}
	private boolean check() {
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(email.length()==0){
			Toast.makeText(getApplicationContext(), "手机号不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
			Toast.makeText(getApplicationContext(), "请输入验证码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_code.getText().toString().equals(Config.reg_phoneCode)){
			Toast.makeText(getApplicationContext(), "验证码错误",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(pass.length()==0){
			Toast.makeText(getApplicationContext(), "密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(pass.length() < 6){
			Toast.makeText(getApplicationContext(), "密码长度最少6个字符",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_pass2.getText().toString().equals(pass)){
			Toast.makeText(getApplicationContext(), "二次密码不一样",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if( tv_jy_type.getText().toString().equals("请选择地区")){
			Toast.makeText(getApplicationContext(), "请选择地区",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		pass=StringUtil.Md5(pass);
		System.out.println("pass"+pass);
		return true;
	}

	private void getCode() {
		email=StringUtil.replaceBlank(login_edit_email.getText().toString()); 
		registerPhoneCode(email);

	}
	private void initView() {
		tv_check=(TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		img_check=(ImageView) findViewById(R.id.img_check);
		img_check_n=(ImageView) findViewById(R.id.img_check_n);
		tv_code=(TextView) findViewById(R.id.tv_code);
		tv_code.setOnClickListener(this);
		tv_jy_type=(TextView) findViewById(R.id.tv_jy_type);
		ll_jy_type=(LinearLayout) findViewById(R.id.ll_jy_type);
		ll_jy_type.setOnClickListener(this);
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		login_linear_deletemali=(LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
		login_edit_email=(EditText) findViewById(R.id.login_edit_email);
		String s=getIntent().getStringExtra("phone");
		login_edit_email.setText(s);
		Config.reg_phoneCode=getIntent().getStringExtra("vcode");
		login_edit_email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletemali.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemali.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		//	login_linear_deletpass
		login_linear_deletcode=(LinearLayout) findViewById(R.id.login_linear_deletcode);
		login_linear_deletcode.setOnClickListener(this);
		login_edit_code=(EditText) findViewById(R.id.login_edit_code);
		login_edit_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletcode.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletcode.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		//	login_linear_deletpass
		login_linear_deletpass=(LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
		login_edit_pass=(EditText) findViewById(R.id.login_edit_pass);
		login_edit_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletpass.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		login_linear_deletpass2=(LinearLayout) findViewById(R.id.login_linear_deletpass2);
		login_linear_deletpass2.setOnClickListener(this);
		login_edit_pass2=(EditText) findViewById(R.id.login_edit_pass2);
		login_edit_pass2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletpass2.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass2.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	public class MyThread implements Runnable{      // thread  
		@Override  
		public void run(){  

			while(isRun){  
				System.out.println("run``"+Countmun);
				try{  
					Thread.sleep(1000);     // sleep 1000ms  
					Message message = new Message();  
					message.what = 1;  
					handler.sendMessage(message);  
				}catch (Exception e) {  

				}
			}
		}  
	} 
	public void zhuche(){ 
		API.zhuche(Register4phone.this, email,pass,Config.reg_phoneCode, cityid,false,

				new HttpCallback(Register4phone.this) {


			@Override
			public void onSuccess(Object data) {
				Toast.makeText(Register4phone.this, "注册成功", 1000).show();
				Intent i =new Intent(getApplicationContext(), Regist4phoneSucces.class);
				i.putExtra("tel", email);
				startActivity(i);
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
	public void registerPhoneCode(String phonenumber){ 
		API.reg_phoneCode(Register4phone.this, phonenumber,
				new HttpCallback(Register4phone.this) {

			@Override
			public void onSuccess(Object data) {
				handler.postDelayed(runnable, 1000);
				Toast.makeText(Register4phone.this, "验证码发送成功", 1000).show();
				Config.reg_phoneCode= data.toString();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
}
