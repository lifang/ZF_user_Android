package com.example.zf_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

public class ContentUs extends BaseActivity implements OnClickListener{
	private LinearLayout ll_pay_type;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.content_us);
			new TitleMenuUtil(ContentUs.this, "联系我们").show();
			initView();
		}
		private void initView() {
			// TODO Auto-generated method stub
			ll_pay_type=(LinearLayout) findViewById(R.id.ll_pay_type);
			ll_pay_type.setOnClickListener(this);
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 switch (v.getId()) {
			case R.id.ll_pay_type:
				  startActivity(new Intent(ContentUs.this,WantBuy.class));
				break;

			default:
				break;
			}
		}
}
