package com.example.zf_android.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.DialogUtil;
import com.examlpe.zf_android.util.DialogUtil.CallBackChange;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;

public class ContentUs extends BaseActivity implements OnClickListener{
	private LinearLayout ll_pay_type,ll_kf,ll_wx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_us);
		new TitleMenuUtil(ContentUs.this, "联系我们").show();
		initView();
	}
	
	private void initView() {
		ll_pay_type=(LinearLayout) findViewById(R.id.ll_pay_type);
		ll_pay_type.setOnClickListener(this);
		ll_kf=(LinearLayout) findViewById(R.id.ll_kf);
		ll_kf.setOnClickListener(this);
		ll_wx=(LinearLayout) findViewById(R.id.ll_wx);
		ll_wx.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_pay_type:
			startActivity(new Intent(ContentUs.this,WantBuy.class));

			break;
		case R.id.ll_kf:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000090876"));
			//通知activtity处理传入的call服务
			startActivity(intent);
			break;
		case R.id.ll_wx:
			Dialog dialog = new DialogUtil(ContentUs.this,
					"保存到本地").getCheck(new CallBackChange() {
						@Override
						public void change() {
							
						}

					});
			dialog.show();
			break;
		default:
			break;
		}
	}

}
