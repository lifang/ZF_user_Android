package com.example.zf_android.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
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
	private TextView moneyTextView,next_sure;
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
		moneyTextView.setText("最高可以兑换金额：" + formateRate(priceMax));
	}
	// 保留小数点后两位  
	public String formateRate(String rateStr){  
		if(rateStr.indexOf(".") != -1){  
			//获取小数点的位置  
			int num = 0;  
			num = rateStr.indexOf(".");  

			//获取小数点后面的数字 是否有两位 不足两位补足两位  
			String dianAfter = rateStr.substring(0,num+1);  
			String afterData = rateStr.replace(dianAfter, "");  
			if(afterData.length() < 2){  
				afterData = afterData + "0" ;  
			}else{  
				afterData = afterData;  
			}  
			return rateStr.substring(0,num) + "." + afterData.substring(0,2);  
		}else{  
			if(rateStr == "1"){  
				return "100";  
			}else{  
				return rateStr;  
			}  
		}  
	}
	private void initView() {
		tv_xyjf=(TextView) findViewById(R.id.tv_xyjf);
		et_name=(EditText) findViewById(R.id.et_name1);
		et_tel=(EditText) findViewById(R.id.et_tel);
		t_y=(EditText) findViewById(R.id.et_y1);
		btn_exit=(Button) findViewById(R.id.btn_exit);
		moneyTextView = (TextView) findViewById(R.id.moneyTextView);
		btn_exit.setOnClickListener(this);
		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setText("提交");
		next_sure.setOnClickListener(this);
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
		case R.id.next_sure:
			getData();
			break;
		default:
			break;
		}
	}
}
