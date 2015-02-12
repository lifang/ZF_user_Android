package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

public class Register extends BaseActivity implements OnClickListener{
	//发送重置密码邮件
	private EditText login_edit_name;
	private LinearLayout login_linear_deletename,login_linear_in;
	private TextView tv_msg;
	private Boolean isMail=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		new TitleMenuUtil(Register.this,"找回密码").show();
		initView();
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv_msg=(TextView) findViewById(R.id.tv_msg);
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
		login_linear_deletename=(LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_in=(LinearLayout) findViewById(R.id.login_linear_in);
		login_linear_deletename.setOnClickListener(this);
		login_linear_in.setOnClickListener(this);
		login_edit_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			 
				if (s.length() > 0) {
					login_linear_deletename.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletename.setVisibility(View.GONE);
				}
				if(s.toString().contains("@")){
					tv_msg.setText("发送重置密码邮件");
					isMail=true;
				}else{
					tv_msg.setText("发送验证码");
					isMail=false;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_linear_deletename:
			login_edit_name.setText("");
			break;
		case R.id.login_linear_in:
			if(isMail){
				//发送邮件请求
				Intent i = new Intent(getApplicationContext(),
						RegistMail.class);
				i.putExtra("email", login_edit_name.getText().toString());
				startActivity(i);
			}else{
				//发送手机请求
				Intent i = new Intent(getApplicationContext(),
						Register4phone.class);
				i.putExtra("phone", login_edit_name.getText().toString());
				startActivity(i);
			}
		 
			
			
			break;
		default:
			break;
		}
	}
}
