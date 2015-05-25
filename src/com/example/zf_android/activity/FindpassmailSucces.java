package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
 
public class FindpassmailSucces extends BaseActivity implements OnClickListener{
	private TextView tv_tel;
	private String tel;
	private LinearLayout login_linear_signin,titleback_linear_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().clearHistory();
		setContentView(R.layout.findmailsuccess);
		new TitleMenuUtil(FindpassmailSucces.this, "找回密码").show();
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		tel=getIntent().getStringExtra("tel");
		tv_tel.setText(tel);
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setVisibility(View.GONE);
		login_linear_signin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_linear_signin:
			Intent i =new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);
			finish();
			break;

		default:
			break;
		}
	}

	 
}
