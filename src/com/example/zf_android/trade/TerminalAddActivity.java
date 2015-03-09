package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.TextWatcherAdapter;
import com.google.gson.reflect.TypeToken;

import static com.example.zf_android.trade.Constants.TerminalIntent.CHANNEL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.CHANNEL_NAME;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalAddActivity extends Activity implements View.OnClickListener {

	private View mChooseChannel;
	private TextView mPayChannel;
	private int mChannelId;
	private String mChannelName;

	private EditText mTerminalNumber;
	private EditText mMerchantName;
	private Button mSubmitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_add);
		new TitleMenuUtil(this, getString(R.string.terminal_add_others)).show();

		mChooseChannel = findViewById(R.id.terminal_choose_channel);
		mChooseChannel.setOnClickListener(this);
		mPayChannel = (TextView) findViewById(R.id.terminal_pay_channel);

		mTerminalNumber = (EditText) findViewById(R.id.terminal_number);
		mMerchantName = (EditText) findViewById(R.id.terminal_merchant_name);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);

		mTerminalNumber.addTextChangedListener(mTextWatcher);
		mMerchantName.addTextChangedListener(mTextWatcher);
		mSubmitBtn.setOnClickListener(this);
	}


	private final TextWatcher mTextWatcher = new TextWatcherAdapter() {

		public void afterTextChanged(final Editable gitDirEditText) {
			updateUIWithValidation();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		updateUIWithValidation();
	}

	private void updateUIWithValidation() {
		final boolean enabled = mChannelId > 0 && mTerminalNumber.length() > 0 && mMerchantName.length() > 0;
		mSubmitBtn.setEnabled(enabled);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			mChannelId = data.getIntExtra(CHANNEL_ID, 0);
			mChannelName = data.getStringExtra(CHANNEL_NAME);
			mPayChannel.setText(mChannelName);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.terminal_choose_channel:
				Intent intent = new Intent(this, TerminalChannelActivity.class);
				intent.putExtra(CHANNEL_ID, mChannelId);
				startActivityForResult(intent, 1);
				break;
			case R.id.terminal_submit:
				API.addTerminal(TerminalAddActivity.this, 80, mChannelId,
						mTerminalNumber.getText().toString(), mMerchantName.getText().toString(),
						new HttpCallback(TerminalAddActivity.this) {
							@Override
							public void onSuccess(Object data) {
								setResult(RESULT_OK);
								finish();
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
				break;
		}
	}
}
