package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

public class MerchantItemEdit extends BaseActivity implements OnClickListener{

	private EditText editContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_item_edit);
		String title = getIntent().getStringExtra("title");
		String value = getIntent().getStringExtra("value");

		new TitleMenuUtil(MerchantItemEdit.this, title).show();
		editContent = (EditText)findViewById(R.id.edit_content);
		editContent.setText(value);
		findViewById(R.id.btn_sub).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		intent.putExtra("value", editContent.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
