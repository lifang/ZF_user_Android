package com.example.zf_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.UserEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;


/***
 * 
 * @author Lijinpeng
 * 
 *         comdo
 */
public class LoginActivity extends Activity implements OnClickListener {
	private TextView login_text_forget;
	private EditText login_edit_name, login_edit_pass;
	private LinearLayout login_linear_deletename, login_linear_deletepass,zhuche_ll,
	login_linear_login, msg;
	private String usename, passsword;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private Boolean isFirst;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
//		MyApplication.getInstance().addActivity(this);
		initView();
		new TitleMenuUtil(LoginActivity.this, "登录").show();
		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
	}

	private void initView() {

		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		editor = mySharedPreferences.edit();

		login_text_forget = (TextView) findViewById(R.id.login_text_forget);
		login_text_forget.setOnClickListener(this);
		
		msg = (LinearLayout) findViewById(R.id.msg);

		zhuche_ll= (LinearLayout) findViewById(R.id.zhuche_ll);
		zhuche_ll.setOnClickListener(this);

		login_edit_name = (EditText) findViewById(R.id.login_edit_name);
		login_edit_pass = (EditText) findViewById(R.id.login_edit_pass);

		login_linear_deletename = (LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_deletepass = (LinearLayout) findViewById(R.id.login_linear_deletepass);
		login_linear_deletepass.setOnClickListener(this);
		login_linear_deletename.setOnClickListener(this);
		login_edit_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				msg.setVisibility(View.INVISIBLE);
				if (s.length() > 0) {
					login_linear_deletename.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletename.setVisibility(View.GONE);
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
		login_edit_pass.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				msg.setVisibility(View.INVISIBLE);
				if (s.length() > 0)
					login_linear_deletepass.setVisibility(View.VISIBLE);
				else
					login_linear_deletepass.setVisibility(View.GONE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		login_linear_login = (LinearLayout) findViewById(R.id.login_linear_login);
		login_linear_login.setOnClickListener(this);
		isFirst = mySharedPreferences.getBoolean("isRemeber", false);
		if (isFirst) {
			login_edit_pass.setText(mySharedPreferences.getString("password",
					""));
			login_edit_name.setText(mySharedPreferences.getString("username",
					""));
		}else{
			login_edit_name.setText(mySharedPreferences.getString("username",
					""));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_text_forget: 
			startActivity(new Intent(LoginActivity.this,FindPass.class));
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_login: 
			if(check()){
				login();
			}
			break;
		case R.id.zhuche_ll: 
			startActivity(new Intent(LoginActivity.this,Register.class));
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletename:
			login_edit_name.setText("");
			break;
		case R.id.login_linear_deletepass:
			login_edit_pass.setText("");
			break;
		default:
			break;
		}
	}

	private void login() {
		API.Login1(LoginActivity.this,usename,passsword,
				new HttpCallback<UserEntity> (LoginActivity.this) {

			@Override
			public void onSuccess(UserEntity data) {
				MyApplication.setNewUser(data);
				editor.putBoolean("islogin", true);
				editor.putString("name", data.getUsername());
				editor.putInt("id", data.getId());
				editor.commit();
				MyApplication.getInstance().setCustomerId(data.getId());
				Intent i =new Intent(getApplicationContext(), Main.class);
				startActivity(i);
				finish();
				//MyApplication.getInstance().exit();
			}

			@Override
			public TypeToken<UserEntity> getTypeToken() {
				return  new TypeToken<UserEntity>() {
				};
			}
		});

	}
	private boolean check() {
		usename=StringUtil.replaceBlank(login_edit_name.getText().toString());
		if(usename.length()==0){
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passsword=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(passsword.length()==0){
			Toast.makeText(getApplicationContext(), "请输入用户密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passsword=StringUtil.Md5(passsword);
		
		return true;
	}

}
