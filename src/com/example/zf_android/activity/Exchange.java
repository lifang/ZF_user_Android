package com.example.zf_android.activity;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.JifenEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
	private TextView next_sure,tv_xyjf;
	private EditText et_name,et_tel,t_y;
	private String name,phone,price;
	private Button btn_exit;
	private int price1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_exchange);
		initView();
		price1=getIntent().getIntExtra("price", 0);
		tv_xyjf.setText(price1+"");
	}

 

	private void initView() {
		// TODO Auto-generated method stub
		tv_xyjf=(TextView) findViewById(R.id.tv_xyjf);
		et_name=(EditText) findViewById(R.id.et_name1);
		et_tel=(EditText) findViewById(R.id.et_tel);
		t_y=(EditText) findViewById(R.id.et_y1);
		next_sure=(TextView) findViewById(R.id.next_sure);
		next_sure.setOnClickListener(this);
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
		next_sure.setText("提交");
		next_sure.setVisibility(View.VISIBLE);
		new TitleMenuUtil(Exchange.this, "兑换积分").show();
		 
		
	}

	private void getData() {
		name=et_name.getText().toString();
		phone=et_tel.getText().toString();
		price=t_y.getText().toString();
		String url = "http://114.215.149.242:18080/ZFMerchant/api/customers/insertIntegralConvert";
		RequestParams params = new RequestParams();
		
		if(StringUtil.replaceBlank(name).length()==0){
			Toast.makeText(getApplicationContext(), "请输入姓名", 1000).show();
			return;
		}
		if(StringUtil.replaceBlank(phone).length()==0){
			Toast.makeText(getApplicationContext(), "请输入手机号", 1000).show();
			return;
		}
		if(StringUtil.replaceBlank(price).length()==0){
			Toast.makeText(getApplicationContext(), "请输入金额", 1000).show();
			return;
		}
		
		System.out.println(name+"--"+phone+"---"+price);
		params.put("customerId", 80);
		params.put("name",name);
		params.put("phone", phone);
		params.put("price", price);
		params.setUseJsonStreamer(true);

		MyApplication.getInstance().getClient()
				.post(url,params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("print", responseMsg);

					 
						 
						Gson gson = new Gson();
						
						JSONObject jsonobject = null;
						String code = null;
						try {
							jsonobject = new JSONObject(responseMsg);
							code = jsonobject.getString("code");
							int a =jsonobject.getInt("code");
							if(a==Config.CODE){  
//								String res =jsonobject.getString("result");
//								jsonobject = new JSONObject(res);
//								
//								moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<JifenEntity>>() {
//			 					}.getType());
//			 				 
//								myList.addAll(moreList);
//				 				handler.sendEmptyMessage(0);
								Toast.makeText(getApplicationContext(), "兑换成功", 1000).show();
								
			 			 
							}else{
								code = jsonobject.getString("message");
								Toast.makeText(getApplicationContext(), code, 1000).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							 ;	
							e.printStackTrace();
							
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						System.out.println("-onFailure---");
						Log.e("print", "-onFailure---" + error);
					}
				});

	 
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.next_sure:
			getData();
			break;
		case R.id.btn_exit:
			getData();
			break;
		default:
			break;
		}
	}
}
