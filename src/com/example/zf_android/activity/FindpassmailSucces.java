package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
 
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
 
public class FindpassmailSucces extends BaseActivity implements OnClickListener{
	private int a;
	private int index;
	private TextView tv_tel;
	private String tel;
	private LinearLayout login_linear_signin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findmailsuccess);
		new TitleMenuUtil(FindpassmailSucces.this, "找回密码").show();
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		tel=getIntent().getStringExtra("tel");
		tv_tel.setText(tel);
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
