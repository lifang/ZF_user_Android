package com.example.zf_android.trade;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.DialogUtil;
import com.examlpe.zf_android.util.DialogUtil.CallBackChange;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

/**
 * Created by Leo on 2015/3/2.
 */
public class AfterSalePayActivity extends BaseActivity implements OnClickListener {

	private TextView tv_pay;
	private LinearLayout titleback_linear_back;
	private String orderId = "";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay);

		orderId = getIntent().getExtras().getString("orderId", "");
		new TitleMenuUtil(this, getString(R.string.title_after_sale_pay)).show();

		if (orderId.equals("")) {
			Toast.makeText(this, "没有传订单id", Toast.LENGTH_SHORT).show();
		}

		tv_pay=(TextView) findViewById(R.id.tv_pay);

		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
				dialogIntent();
			break;

		default:
			break;
		}
	}

	private void dialogIntent() {
		Dialog dialog = new DialogUtil(this,
				"是否放弃付款？").getCheck(new CallBackChange() {

					@Override
					public void change() {
						finish();
					}
				});
		dialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
				dialogIntent();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
