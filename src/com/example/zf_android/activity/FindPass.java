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
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class FindPass extends BaseActivity implements OnClickListener{
	//�������������ʼ�
	private EditText login_edit_name;
	private LinearLayout login_linear_deletename,login_linear_in;
	private TextView tv_msg;
	private Boolean isMail=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpass);
		new TitleMenuUtil(FindPass.this,"找回密码").show();
		initView();
	}
	private void initView() {
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
					tv_msg.setText("发送连接到邮箱");
					isMail=true;
				}else{
					tv_msg.setText("发送验证码到手机");
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
		switch (v.getId()) {
		case R.id.login_linear_deletename:
			login_edit_name.setText("");
			break;
		case R.id.login_linear_in:
			if (StringUtil.isNull(login_edit_name.getText().toString().trim())) {
				Toast.makeText(getApplicationContext(), "手机号码或邮箱不能为空",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (!(StringUtil.isMobile(login_edit_name.getText().toString().trim()) || StringUtil
					.checkEmail(login_edit_name.getText().toString().trim()))) {
				Toast.makeText(getApplicationContext(), "请输入正确的手机号码或邮箱",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(isMail){
				System.out.println("-0--"+login_edit_name.getText().toString());

				API.getEmailPass(FindPass.this, login_edit_name.getText().toString(),

						new HttpCallback(FindPass.this) {	           
					@Override
					public void onSuccess(Object data) {

						Intent i = new Intent(getApplicationContext(),
								FindpassmailSucces.class);
						i.putExtra("tel", login_edit_name.getText().toString());
						startActivity(i);
					}
					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				}); 
			}else{
				Intent i = new Intent(getApplicationContext(),
						FindPassword.class);
				i.putExtra("phone", login_edit_name.getText().toString());
				startActivity(i);
			}

			break;
		default:
			break;
		}
	}
}
