package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
 
public class Regist4phoneSucces extends BaseActivity implements OnClickListener{
	
	private LinearLayout login_linear_signin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.r4ps);
		new TitleMenuUtil(Regist4phoneSucces.this, "注册").show();
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		MyApplication.getInstance().clearHistoryForPay();
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
