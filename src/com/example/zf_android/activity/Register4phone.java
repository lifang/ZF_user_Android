package com.example.zf_android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.MyToast;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CustomDialog;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class Register4phone extends BaseActivity   implements OnClickListener{
	
	private TextView phoneText,register_hint;
	private EditText login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletpass ,login_linear_deletpass2,login_linear_signin,msgLayout;
	private String phoneStr,pass;
	private LinearLayout titleback_linear_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.register_phone);
		new TitleMenuUtil(Register4phone.this, "注册").show();
		System.out.println("Register4phone---");

		initView();
	}
	
	@Override
	public void onClick(View v) {
		switch ( v.getId()) {
		case R.id.login_linear_signin: 

			if(check()){
				zhuche();
			}

			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		case R.id.titleback_linear_back:
			showCustomDialog();
			break;
		default : 
			break;
		}
	}
	private boolean check() {

		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(pass.length()==0){
			msgLayout.setVisibility(View.VISIBLE);
			register_hint.setText("密码不能为空");
			return false;
		}
		if(pass.length() < 6){
			msgLayout.setVisibility(View.VISIBLE);
			register_hint.setText("密码长度最少6个字符");
			return false;
		}
		if(!login_edit_pass2.getText().toString().equals(pass)){
			msgLayout.setVisibility(View.VISIBLE);
			register_hint.setText("密码输入不一致，请重新填写");
			return false;
		}
		msgLayout.setVisibility(View.GONE);
		pass=StringUtil.Md5(pass);
		System.out.println("pass"+pass);
		return true;
	}

	private void initView() {
		MyApplication.getInstance().getHistoryList().add(this);
		phoneText=(TextView) findViewById(R.id.phoneText);
		register_hint=(TextView) findViewById(R.id.register_hint);
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		msgLayout=(LinearLayout) findViewById(R.id.msgLayout);
		login_linear_signin.setOnClickListener(this);
		phoneStr = getIntent().getStringExtra("phone");
		phoneText.setText(phoneStr);
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
					msgLayout.setVisibility(View.GONE);
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
		
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
	}
	public void zhuche(){ 
		API.zhuche(Register4phone.this, phoneStr,pass,Config.reg_phoneCode, MyApplication.getInstance().getReg_cityId(),false,
				new HttpCallback(Register4phone.this) {

			@Override
			public void onSuccess(Object data) {
				MyToast.showToast(getApplicationContext(),"注册成功");
				Intent i =new Intent(getApplicationContext(), Regist4phoneSucces.class);
				startActivity(i);
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			showCustomDialog();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showCustomDialog() {
		final CustomDialog dialog = new CustomDialog(this);
		dialog.setSoftKeyValue("取消注册", "继续填写");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContent("不填写登录密码将无法在APP上登录，是否取消注册？");
		dialog.setLeftListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Register4phone.this.finish();
			}

		});
		dialog.setRightListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
}
