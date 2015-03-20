package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
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
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
 
import com.example.zf_android.R;
 
import com.example.zf_android.trade.API;
 
import com.example.zf_android.trade.common.HttpCallback;
 
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
 
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class WantBuy extends BaseActivity {
	private EditText login_edit_name1, login_edit_name, et_contetn;
	private Button btn_exit;
	private String name, phone, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.want_buy);
		new TitleMenuUtil(WantBuy.this, "填写购买意向单").show();
		login_edit_name = (EditText) findViewById(R.id.login_edit_name);
		login_edit_name1 = (EditText) findViewById(R.id.login_edit_name1);
		et_contetn = (EditText) findViewById(R.id.et_contetn);
		btn_exit = (Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				name = StringUtil.replaceBlank(login_edit_name.getText()
						.toString());
				phone = StringUtil.replaceBlank(login_edit_name1.getText()
						.toString());
				content = StringUtil.replaceBlank(et_contetn.getText()
						.toString());
				if(check()){
					ggg();
				}
			 	 
//				if (check()) {
//
//					// TODO Auto-generated method stub
//				AsyncHttpClient client = new AsyncHttpClient(); //  
//				RequestParams params = new RequestParams();
//				 System.out.println(content+"-check---"+name+phone);
//				params.put("name",name);
//				params.put("phone", phone.toString());
//				params.put("content", content.toString());
//				params.setUseJsonStreamer(true);
//				   ///ZFMerchant/api/paychannel/intention/add
//				String uuu="http://114.215.149.242:18080/ZFMerchant/api/paychannel/intention/add";
//				System.out.println(params.toString()+"-----");
//				client.post(uuu, params,
//							new TextHttpResponseHandler() {
//
////								@Override
////								public void onSuccess(int statusCode,
////										Header[] headers, byte[] responseBody) {
////
////									System.out.println("-onSuccess---");
////									String responseMsg = new String(
////											responseBody).toString();
////									Log.e("LJP", responseMsg);
////									Gson gson = new Gson();
////									JSONObject jsonobject = null;
////									int code = 0;
////									try {
////										jsonobject = new JSONObject(responseMsg);
////
////										code = jsonobject.getInt("code");
////
////										if (code == -2) {
////
////										} else if (code == 1) {
////
////											Toast.makeText(
////													getApplicationContext(),
////													"提交成功，请等待客服回复",
////													Toast.LENGTH_SHORT).show();
////											finish();
////
////										} else {
////											Toast.makeText(
////													getApplicationContext(),
////													jsonobject
////															.getString("message"),
////													Toast.LENGTH_SHORT).show();
////										}
////									} catch (JSONException e) {
////										// TODO Auto-generated catch block
////										e.printStackTrace();
////									}
////
////								}
//
////								@Override
////								public void onFailure(int statusCode,
////										Header[] headers, byte[] responseBody,
////										Throwable error) {
////									// TODO Auto-generated method stub
////									System.out.println("onFailure`` `");
////									Log.e("print", statusCode+"-onFailure---" + headers.toString()+responseBody.toString());
////								}
//
//								@Override
//								public void onFailure(int statusCode,
//										Header[] headers,
//										String responseString,
//										Throwable throwable) {
//									// TODO Auto-generated method stub
//									System.out.println("-onFailure---" );
//								}
//
//								@Override
//								public void onSuccess(int statusCode,
//										Header[] headers, String responseString) {
//									// TODO Auto-generated method stub
//									System.out.println("-onSuccess---"+responseString);
//									String responseMsg = new String(
//											responseString).toString();
//									Log.e("LJP", responseMsg);
//								}
//							});
//
//				}

			}

		});

	}

	private boolean check () {
		// TODO Auto-generated method stub
		// content
		if (name.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您的称呼",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (phone.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入联系方式",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (content.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您的意向",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	public void ggg(){


        API.ApiWantBug(WantBuy.this, name,phone , content,
        		
                new HttpCallback(WantBuy.this) {
           

					@Override
					public void onSuccess(Object data) {
						// TODO Auto-generated method stub
						Toast.makeText(WantBuy.this, "提交成功", 1000).show();
						finish();
					}

					@Override
					public TypeToken getTypeToken() {
						// TODO Auto-generated method stub
						return null;
					}
                });
	}
}
