package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***
 * 
 *    申请进度查询
 * @version    
 *
 */
public class ApplyDetail extends BaseActivity implements OnClickListener{
	private TextView msg_show;
	private LinearLayout login_linear_in;
	private EditText login_edit_name;
	private LinearLayout login_linear_deletename;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_detail);
		new TitleMenuUtil(ApplyDetail.this, "申请开通精度查询").show();
		initView();

	}
	private void getData() {
		msg_show.setText("无忧查询结果，请填写正确的手机号码！");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", MyApplication.getInstance().getCustomerId());
		params.put("phone", login_edit_name.getText().toString());
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}

		//			RequestParams params = new RequestParams();
		//			params.put("id", MyApplication.getInstance().getCustomerId());
		//			params.put("phone", login_edit_name.getText().toString());
		//			System.out.println("login_edit_name.getText().toString()"+login_edit_name.getText().toString());
		//			params.setUseJsonStreamer(true);

		//			MyApplication.getInstance().getClient()
		//					.post(Config.URL_TERMINAL_OPENSTATUS, params, new AsyncHttpResponseHandler() {
		MyApplication.getInstance().getClient()
		.post(getApplicationContext(),Config.URL_TERMINAL_OPENSTATUS, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("print ad", responseMsg);
				System.out.println("adadada"+responseMsg);


				Gson gson = new Gson();

				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					int a =jsonobject.getInt("code");
					if(a==Config.CODE){  
						String res =jsonobject.getString("result");
						jsonobject = new JSONObject(res);
						//									
						//									moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<PosEntity>>() {
						//				 					}.getType());
						//				 				 
						//									myList.addAll(moreList);
						//					 				handler.sendEmptyMessage(0);
						//				 					  


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
	private void initView() {
		// TODO Auto-generated method stub
		msg_show=(TextView) findViewById(R.id.msg_show);
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
		login_edit_name.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				if(s.length()>0){
					login_linear_deletename.setVisibility(View.VISIBLE);
				}else{
					login_linear_deletename.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
		login_linear_deletename=(LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_in=(LinearLayout) findViewById(R.id.login_linear_in);
		login_linear_deletename.setOnClickListener(this);
		login_linear_in.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_linear_in: 
			getData();
			break;
		case R.id.login_linear_deletename: 
			login_edit_name.setText("");
			break;
		default:
			break;
		}
	}
}
