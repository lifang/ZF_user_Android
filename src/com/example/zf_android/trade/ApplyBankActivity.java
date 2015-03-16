package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.entity.ApplyBank;

import java.util.List;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyBankActivity extends Activity {

	private EditText mBankInput;

	private List<ApplyBank> mBanks;
	private ListView mBankList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_bank);
		new TitleMenuUtil(this, getString(R.string.title_apply_choose_bank)).show();


	}
}
