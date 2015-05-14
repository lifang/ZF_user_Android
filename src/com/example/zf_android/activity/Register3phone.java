package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.MyToast;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.common.CustomDialog;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class Register3phone extends BaseActivity implements OnClickListener {

	private int countNun = 120;
	public int cityid = 0;
	Handler handler = new Handler() { // handle
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (countNun == 0) {

					countText.setEnabled(true);
					countText.setText("点此重新发送验证码");
					countText.setTextColor(getResources().getColor(
							R.color.bgtitle));
					System.out.println("destroy`" + countNun);
				} else {
					countNun--;
					// countText.setTextColor(getResources().getColor(R.color.hint6C));
					countText.setText("接受短信大约需要" + countNun + "秒");
					System.out.println("countNun`D2`" + countNun);
					Message message = new Message();
					message.what = 1;
					handler.sendMessageDelayed(message, 1000);
				}
			}
			super.handleMessage(msg);
		}
	};

	private EditText edit_code;
	private TextView countText, agreementText, phoneText,register_hint;
	private LinearLayout checkCode;
	private ImageView del;
	private String phoneStr;
	private LinearLayout titleback_linear_back,msgLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		cityid = MyApplication.getInstance().getCityId();
		setContentView(R.layout.register_checkcode);
		new TitleMenuUtil(Register3phone.this, "注册").show();
		phoneStr = this.getIntent().getExtras().getString("phone");
		initView();

	}

	@Override
	public void onClick(View v) {

		Intent intent = null;
		switch (v.getId()) {

		case R.id.countText:

			API.reg_phoneCode(this, phoneStr, new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					Config.reg_phoneCode = data.toString();
					countText.setTextColor(getResources().getColor(
							R.color.hint6C));
					countNun = 120;
					Message message = new Message();
					message.what = 1;
					handler.sendMessageDelayed(message, 1000);
				}

				public TypeToken getTypeToken() {
					return null;
				}
			});

			countText.setEnabled(false);
			break;
		case R.id.agreementText:
			intent = new Intent(this, AgreementActivity.class);
			startActivity(intent);
			break;
		case R.id.checkCode:
			if (edit_code.getText().toString()
					.equalsIgnoreCase(Config.reg_phoneCode)) {
				intent = new Intent(this, Register4phone.class);
				intent.putExtra("phone", phoneStr);
				startActivity(intent);
				finish();
			} else {
				msgLayout.setVisibility(View.VISIBLE);
				register_hint.setText("验证码错误");
			}
			break;
		case R.id.del:
			edit_code.setText("");
			msgLayout.setVisibility(View.GONE);
			break;
		case R.id.titleback_linear_back:
			showCustomDialog();
			break;

		default:
			break;
		}
	}

	private void initView() {
		MyApplication.getInstance().getHistoryList().add(this);
		edit_code = (EditText) this.findViewById(R.id.edit_code);
		phoneText = (TextView) this.findViewById(R.id.phoneText);
		countText = (TextView) this.findViewById(R.id.countText);
		agreementText = (TextView) this.findViewById(R.id.agreementText);
		checkCode = (LinearLayout) this.findViewById(R.id.checkCode);
		del = (ImageView) this.findViewById(R.id.del);
		edit_code.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				if (s.length() > 0) {
					del.setVisibility(View.VISIBLE);
				} else {
					msgLayout.setVisibility(View.GONE);
					del.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				msgLayout.setVisibility(View.GONE);
			}
		});
		phoneText.setText(phoneStr);
		countText.setOnClickListener(this);
		countText.setEnabled(false);
		agreementText.setOnClickListener(this);
		del.setOnClickListener(this);
		checkCode.setOnClickListener(this);
		Message message = new Message();
		message.what = 1;
		handler.sendMessageDelayed(message, 1000);
		
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
		msgLayout=(LinearLayout) findViewById(R.id.msgLayout);
		register_hint=(TextView) findViewById(R.id.register_hint);
	}

	public void registerPhoneCode(String phonenumber) {
		API.reg_phoneCode(Register3phone.this, phonenumber, new HttpCallback(
				Register3phone.this) {
			@Override
			public void onSuccess(Object data) {
				Message message = new Message();
				message.what = 1;
				handler.sendMessageDelayed(message, 1000);
				Toast.makeText(Register3phone.this, "验证码发送成功", 1000).show();
				Config.reg_phoneCode = data.toString();
			}

			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			showCustomDialog();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showCustomDialog() {
		final CustomDialog dialog = new CustomDialog(this);
		dialog.setSoftKeyValue("返回", "等待");
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.setContent("验证短信可能略有延迟，确定返回并重新开始注册？");
		dialog.setLeftListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Register3phone.this.finish();
			}

		});
		dialog.setRightListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		countNun = 0;
		handler.removeMessages(1);
	}

}
