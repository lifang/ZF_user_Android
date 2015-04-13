package com.example.zf_android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;
/***
 * 
 *    
 * 类名称：Exchange   
 * 类描述：   兑换积分
 * 创建人： ljp 
 * 创建时间：2015-3-16 下午2:36:19   
 * @version    
 *
 */
public class Exchange extends BaseActivity implements OnClickListener{
	private TextView tv_xyjf;
	private TextView moneyTextView;
	private EditText et_name,et_tel,t_y;
	private String name,phone,price;
	private Button btn_exit;
	private String priceMax,point;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_exchange);
		initView();
		priceMax=getIntent().getStringExtra("price");
		point = getIntent().getStringExtra("point");
		tv_xyjf.setText(point);
		moneyTextView.setText("最高可以兑换金额：" + priceMax);
	}


	private void initView() {
		tv_xyjf=(TextView) findViewById(R.id.tv_xyjf);
		et_name=(EditText) findViewById(R.id.et_name1);
		et_tel=(EditText) findViewById(R.id.et_tel);
		t_y=(EditText) findViewById(R.id.et_y1);
		btn_exit=(Button) findViewById(R.id.btn_exit);
		moneyTextView = (TextView) findViewById(R.id.moneyTextView);
		btn_exit.setOnClickListener(this);
		new TitleMenuUtil(Exchange.this, "兑换积分").show();

	}

	private void getData() {
		name=et_name.getText().toString();
		phone=et_tel.getText().toString();
		price=t_y.getText().toString();

		if(StringUtil.replaceBlank(name).length()==0){
			Toast.makeText(getApplicationContext(), "请输入姓名", 1000).show();
			return;
		}
		if(StringUtil.replaceBlank(phone).length()==0){
			Toast.makeText(getApplicationContext(), "请输入手机号", 1000).show();
			return;
		}
		if (!StringUtil.isMobile(phone)) {
			Toast.makeText(getApplicationContext(), "请输入正确的手机号", 1000).show();
			return;
		}
		if(StringUtil.replaceBlank(price).length()==0){
			Toast.makeText(getApplicationContext(), "请输入金额", 1000).show();
			return;
		}

		if (Double.parseDouble(price) > Double.parseDouble(priceMax)) {
			Toast.makeText(getApplicationContext(), "您要兑换的金额大于最高兑换金额", 1000).show();
			return;
		}
		API.insertIntegralConvert(this,MyApplication.getInstance().getCustomerId(),name,phone,price, 
				new HttpCallback(this) {

			@Override
			public void onSuccess(Object data) {
				Toast.makeText(getApplicationContext(), "兑换成功", 1000).show();
				finish();
			}
			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_exit:
			getData();
			break;
		default:
			break;
		}
	}
}
