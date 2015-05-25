package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;

public class LookTerminalsActivity extends BaseActivity{
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_leas_pact);
		new TitleMenuUtil(LookTerminalsActivity.this, "终端号").show();
		
		tv = (TextView) findViewById(R.id.tv);
		
		tv.setText(getIntent().getExtras().getString("terminals"));
	}
}
