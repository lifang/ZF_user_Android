package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.RegText;

public class MerchantItemEdit extends BaseActivity implements OnClickListener {

	private EditText editContent;

	private String title, value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_item_edit);
		title = getIntent().getStringExtra("title");
		value = getIntent().getStringExtra("value");

		new TitleMenuUtil(MerchantItemEdit.this, title).show();
		editContent = (EditText) findViewById(R.id.edit_content);
		editContent.setText(value);
		findViewById(R.id.btn_sub).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 非空check
		if (editContent.getText().toString() == null
				|| editContent.getText().toString().length() <= 0) {
			CommonUtil.toastShort(MerchantItemEdit.this, "您还没有输入！");
			return;
		}
		// 身份证验证， 营业执照登记号是数字和字母
		if (title.contains("身份证")) {

			if (!RegText.isIdentityCard(editContent.getText().toString())) {
				CommonUtil.toastShort(MerchantItemEdit.this,
						"您输入的身份证号有误，请确认后重新输入！");
				return;
			}

		} else if (title.contains("营业执照")) {

			if (!RegText.isYingYeZhiZhao(editContent.getText().toString())) {
				CommonUtil.toastShort(MerchantItemEdit.this,
						"您输入的营业执照有误，只能包含数字和字母！");
				return;

			}
		}
		Intent intent = new Intent();
		intent.putExtra("value", editContent.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
}
