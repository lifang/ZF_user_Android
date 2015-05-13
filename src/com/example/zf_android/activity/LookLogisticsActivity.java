package com.example.zf_android.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

public class LookLogisticsActivity extends Activity{
	private TextView nameTextView,numTextView;

	private String logistics_name,logistics_number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_look_logistics);
		new TitleMenuUtil(LookLogisticsActivity.this, "查看物流").show();

		logistics_name = getIntent().getExtras().getString("logistics_name");
		logistics_number = getIntent().getExtras().getString("logistics_number");

		nameTextView = (TextView) findViewById(R.id.nameTextView);
		numTextView = (TextView) findViewById(R.id.numTextView);
		nameTextView.setText(logistics_name);
		numTextView.setText(logistics_number);
	}
}
