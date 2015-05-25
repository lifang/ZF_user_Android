package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.MyToast;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;
/***
 * 
 *    
 * ����ƣ�RegistMail   
 * ��������   ����ע��
 * �����ˣ� ljp 
 * ����ʱ�䣺2015-2-11 ����2:56:14   
 * @version    
 *
 */
public class RegistMail extends BaseActivity implements OnClickListener{
	private int cityid;
	private TextView tv_jy_type;
	private String  username,password;
	private EditText login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout ll_jy_type,login_linear_deletcode,login_linear_deletpass,login_linear_deletpass2,login_linear_signin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.regist_mail);
		String a=getIntent().getStringExtra("email");
		initView();
		new TitleMenuUtil(RegistMail.this,"注册").show();
		cityid=MyApplication.getInstance().getCityId();
		login_edit_code.setText(a);
	}
	private void initView() {
		ll_jy_type=(LinearLayout) findViewById(R.id.ll_jy_type);
		ll_jy_type.setOnClickListener(this);
		tv_jy_type=(TextView) findViewById(R.id.tv_jy_type);
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
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
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
		switch (v.getId()) {
		case R.id.login_edit_code:
			login_edit_code.setText("");
			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break; 
		case R.id.login_linear_signin:
			if(check()){
				login();
			}
			break;
		case R.id.ll_jy_type: 
			Intent intent = new Intent(RegistMail.this, CityProvinceActivity.class);
			startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			break;
		default:
			break;
		}
	}
	private boolean check() {
		username=StringUtil.replaceBlank(login_edit_code.getText().toString());
		if(username.length()==0){
			MyToast.showToast(getApplicationContext(),"请输入邮箱");
			return false;
		}
		password=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		String password2=StringUtil.replaceBlank(login_edit_pass2.getText().toString());
		if(password.length()<4){
			MyToast.showToast(getApplicationContext(),"密码不能为空");
			return false;
		}
		if(password.length() < 6){
			MyToast.showToast(getApplicationContext(),"密码长度最少6个字符");
			return false;
		}
		if(!password.equals(password2)){
			MyToast.showToast(getApplicationContext(),"二次密码不一样");
			return false;
		}
		password=StringUtil.Md5(password);
		return true;
	}
	private void login() { 
		API.zhuche(RegistMail.this, username,password,"", cityid,true,
				new HttpCallback(RegistMail.this) {

			@Override
			public void onSuccess(Object data) {
				MyToast.showToast(getApplicationContext(),"注册成功");

				Intent i =new Intent(getApplicationContext(), Regist4mailSucces.class);
				i.putExtra("tel", username);
				startActivity(i);
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
}
