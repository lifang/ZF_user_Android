package com.example.zf_android.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class ChangePassword extends BaseActivity implements OnClickListener{

	private EditText login_edit_name,login_edit_name2,login_edit_name3;
	private Button btn_exit;
	String passwordOld,password,password2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);
		MyApplication.getInstance().addActivity(this);
		new TitleMenuUtil(ChangePassword.this, "修改密码").show();
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);

		login_edit_name2=(EditText) findViewById(R.id.login_edit_name2);
		login_edit_name3=(EditText) findViewById(R.id.login_edit_name3);
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(check ()){
			API.ChangePass(ChangePassword.this,MyApplication.getInstance().getCustomerId(),passwordOld,password,
					new HttpCallback (ChangePassword.this) {

				@Override
				public void onSuccess(Object data) {
					Toast.makeText(getApplicationContext(), "修改成功,请重新登录", 1000).show();

					MyApplication.getInstance().exit();
					
				}

				@Override
				public TypeToken getTypeToken() {
					return   null;
				};
			});


		}
	}

	private boolean check () {
		passwordOld=StringUtil.replaceBlank(login_edit_name.getText().toString());
		password=StringUtil.replaceBlank(login_edit_name2.getText().toString());
		password2=StringUtil.replaceBlank(login_edit_name3.getText().toString());
		if (passwordOld.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您旧密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (password.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入新密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (password2.length() == 0) {
			Toast.makeText(getApplicationContext(), "请再次输入您的密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (password.length() > 5) {
			Toast.makeText(getApplicationContext(), "密码长度不能少于6位",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!password2.equals(password)) {

			Toast.makeText(getApplicationContext(), "二次密码不一样",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passwordOld=StringUtil.Md5(passwordOld);
		password=StringUtil.Md5(password);
		return true;
	}
}
