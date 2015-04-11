package com.example.zf_android.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class WantBuy extends BaseActivity {
	private EditText login_edit_name1, login_edit_name, et_contetn;
	private TextView maxCountTextView;
	private Button btn_exit;
	private String name, phone, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.want_buy);
		new TitleMenuUtil(WantBuy.this, "填写购买意向单").show();
		login_edit_name = (EditText) findViewById(R.id.login_edit_name);
		login_edit_name1 = (EditText) findViewById(R.id.login_edit_name1);
		maxCountTextView = (TextView) findViewById(R.id.maxCountTextView);
		et_contetn = (EditText) findViewById(R.id.et_contetn);
		et_contetn.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					maxCountTextView.setText("还可填写"+(200-s.length())+"个汉字");
				}else if (s.length() == 0) {
					maxCountTextView.setText("最多填写200个汉字");
				}else if (s.length() > 200) {
					maxCountTextView.setText("已超出允许最多字数");
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

		btn_exit = (Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				name = StringUtil.replaceBlank(login_edit_name.getText()
						.toString());
				phone = StringUtil.replaceBlank(login_edit_name1.getText()
						.toString());
				content = StringUtil.replaceBlank(et_contetn.getText()
						.toString());
				if(check()){
					save();
				}
			}
		});

	}

	private boolean check () {
		// content
		if (name.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您的称呼",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (phone.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入联系方式",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!StringUtil.isMobile(phone)) {
			Toast.makeText(getApplicationContext(), "请输入正确的联系方式",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (content.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您的意向",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (content.length() > 200) {
			Toast.makeText(getApplicationContext(), "您填写的意向超出所允许的最多字数",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	public void save(){

		API.ApiWantBug(WantBuy.this, name,phone , content,
				new HttpCallback(WantBuy.this) {

			@Override
			public void onSuccess(Object data) {
				Toast.makeText(WantBuy.this, "提交成功", 1000).show();
				finish();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
}
