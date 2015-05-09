package com.example.zf_android.activity;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

import android.app.Activity;
import android.os.Bundle;

public class LoanActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan);
		new TitleMenuUtil(this, "我要贷款").show();
	}
}
