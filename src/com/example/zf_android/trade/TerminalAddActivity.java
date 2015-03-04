package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.TextWatcherAdapter;
import com.google.gson.reflect.TypeToken;

import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_ID;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_TYPE;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalAddActivity extends Activity {

	private EditText mTerminalNumber;
	private EditText mMerchantName;
	private Button mSubmitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_add);
		new TitleMenuUtil(this, getString(R.string.terminal_add_others)).show();

		mTerminalNumber = (EditText) findViewById(R.id.terminal_number);
		mMerchantName = (EditText) findViewById(R.id.terminal_merchant_name);
		mSubmitBtn = (Button) findViewById(R.id.terminal_submit);

		mTerminalNumber.addTextChangedListener(mTextWatcher);
		mMerchantName.addTextChangedListener(mTextWatcher);
		mSubmitBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				API.addTerminal(TerminalAddActivity.this, 80,
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
			}
		});
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
		final boolean enabled = mTerminalNumber.length() > 0 && mMerchantName.length() > 0;
		mSubmitBtn.setEnabled(enabled);
	}
}
