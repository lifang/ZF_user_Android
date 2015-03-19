package com.example.zf_android.activity;

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
import com.example.zf_android.Config;
import com.example.zf_android.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
				if (check()) {

					// TODO Auto-generated method stub
					AsyncHttpClient client = new AsyncHttpClient(); //  
					RequestParams params = new RequestParams();
					System.out.println(content+"-check---"+name+phone);
					params.put("name", name);
					params.put("phone", phone);
					params.put("content", content);

				 	params.setUseJsonStreamer(true);

					client.post( Config.WANTBUY, params,
							new AsyncHttpResponseHandler() {

								@Override
								public void onSuccess(int statusCode,
										Header[] headers, byte[] responseBody) {

									System.out.println("-onSuccess---");
									String responseMsg = new String(
											responseBody).toString();
									Log.e("LJP", responseMsg);
									Gson gson = new Gson();
									JSONObject jsonobject = null;
									int code = 0;
									try {
										jsonobject = new JSONObject(responseMsg);

										code = jsonobject.getInt("code");

										if (code == -2) {

										} else if (code == 1) {

											Toast.makeText(
													getApplicationContext(),
													"提交成功，请等待客服回复",
													Toast.LENGTH_SHORT).show();
											finish();

										} else {
											Toast.makeText(
													getApplicationContext(),
													jsonobject
															.getString("message"),
													Toast.LENGTH_SHORT).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}

								@Override
								public void onFailure(int statusCode,
										Header[] headers, byte[] responseBody,
										Throwable error) {
									// TODO Auto-generated method stub
									System.out.println("onFailure`` `");
								}
							});

				}

			}

		});

	}

	private boolean check() {
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

}
