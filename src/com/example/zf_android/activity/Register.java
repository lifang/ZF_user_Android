package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.CITY_ID;
import static com.example.zf_android.trade.Constants.CityIntent.CITY_NAME;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CitySelectActivity;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class Register extends BaseActivity implements OnClickListener {

	private LinearLayout cityChoose, login, loginNow, forgetPass, hintLayout;
	private EditText codeEdit;
	private TextView cityText, register_hint;
	private ImageView del;
	public int cityid = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.register);
		new TitleMenuUtil(Register.this, "注册").show();
		initView();
	}

	private void initView() {
		MyApplication.getInstance().getHistoryList().add(this);
		cityChoose = (LinearLayout) this.findViewById(R.id.cityChoose);
		login = (LinearLayout) this.findViewById(R.id.login);
		loginNow = (LinearLayout) this.findViewById(R.id.loginNow);
		forgetPass = (LinearLayout) this.findViewById(R.id.forgetPass);
		hintLayout = (LinearLayout) this.findViewById(R.id.hintLayout);
		codeEdit = (EditText) this.findViewById(R.id.codeEdit);
		cityText = (TextView) this.findViewById(R.id.cityText);
		if(TextUtils.isEmpty(MyApplication.getInstance().getCityName())){
			cityText.setText("上海市");
			cityid = 394;
		}else{
			cityText.setText(MyApplication.getInstance().getCityName());
			cityid = MyApplication.getInstance().getCityId();
		}
		register_hint = (TextView) this.findViewById(R.id.register_hint);
		del = (ImageView) this.findViewById(R.id.del);
		codeEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0) {
					del.setVisibility(View.VISIBLE);
				} else {
					del.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				hintLayout.setVisibility(View.GONE);
				login.setVisibility(View.VISIBLE);
			}
		});

		cityChoose.setOnClickListener(this);
		del.setOnClickListener(this);
		login.setOnClickListener(this);
		loginNow.setOnClickListener(this);
		forgetPass.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {

		case R.id.cityChoose:
			intent = new Intent(this, CitySelectActivity.class);
			intent.putExtra(CITY_NAME, MyApplication.getInstance()
					.getCityName());
			startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			break;
		case R.id.del:
			hintLayout.setVisibility(View.GONE);
			login.setVisibility(View.VISIBLE);
			codeEdit.setText("");
			break;
		case R.id.login:
			error = invalidate();
			if (TextUtils.isEmpty(error)) {
				API.reg_phoneCode(this, codeEdit.getText().toString(),
						new HttpCallback(this) {
							@Override
							public void onSuccess(Object data) {
								if (!data.toString().equalsIgnoreCase(
										"registered")) {
									Config.reg_phoneCode = data.toString();
									Intent i = new Intent(
											getApplicationContext(),
											Register3phone.class);
									i.putExtra("phone", codeEdit.getText()
											.toString());
									startActivity(i);
								} else {
									hintLayout.setVisibility(View.VISIBLE);
									login.setVisibility(View.GONE);
								}
							}

							public TypeToken getTypeToken() {
								return null;
							}
						});
			}
			break;
		case R.id.loginNow:
			intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.forgetPass:
			intent = new Intent(this, FindPass.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	private String invalidate() {
		if (TextUtils.isEmpty(codeEdit.getText().toString())) {

			Toast.makeText(getApplicationContext(), "请输入手机号码",
					Toast.LENGTH_SHORT).show();
			return "error";

		} else if (!TextUtils.isEmpty(codeEdit.getText().toString())
				&& !(StringUtil.isMobile(codeEdit.getText().toString().trim()))) {
			Toast.makeText(getApplicationContext(), "请输入正确的手机号码",
					Toast.LENGTH_SHORT).show();
			return "error";
		} else if (cityid == -1) {
			Toast.makeText(getApplicationContext(), "请选择所在城市",
					Toast.LENGTH_SHORT).show();
			return "error";
		}

		return "";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CHOOSE_CITY:
			if (data != null) {
				cityid = data.getIntExtra(CITY_ID, 0);
				cityText.setText(data.getStringExtra(CITY_NAME));
				MyApplication.getInstance().setReg_cityId(cityid);
			}
			break;

		default:
			break;
		}
	}
}
