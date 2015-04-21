package com.example.zf_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

public class LeaseAgreementActivity extends Activity{
	private TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_leas_pact);
		new TitleMenuUtil(LeaseAgreementActivity.this, "租赁协议").show();
		
		tv = (TextView) findViewById(R.id.tv);
		
		tv.setText(getIntent().getExtras().getString("leasepact"));
	}
}
