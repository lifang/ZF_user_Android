package com.example.zf_android.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
 
import com.examlpe.zf_android.util.StringUtil;
 
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* 类名称：RegistMail   
* 类描述：   邮箱注册
* 创建人： ljp 
* 创建时间：2015-2-11 下午2:56:14   
* @version    
*
 */
public class RegistMail extends BaseActivity implements OnClickListener{
	 
	private String  username,password;
	private EditText login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletcode,login_linear_deletpass,login_linear_deletpass2,login_linear_signin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist_mail);
		String a=getIntent().getStringExtra("email");
		initView();
		login_edit_code.setText(a);
	}
	private void initView() {
		// TODO Auto-generated method stub
		login_linear_deletcode=(LinearLayout) findViewById(R.id.login_linear_deletcode);
		login_linear_deletcode.setOnClickListener(this);
		login_edit_code=(EditText) findViewById(R.id.login_edit_code);
		login_edit_code.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletcode.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletcode.setVisibility(View.GONE);
				}
			 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		login_linear_deletpass=(LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
		login_edit_pass=(EditText) findViewById(R.id.login_edit_pass);
		login_edit_pass.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletpass.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass.setVisibility(View.GONE);
				}
			 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		login_linear_deletpass2=(LinearLayout) findViewById(R.id.login_linear_deletpass2);
		login_linear_deletpass2.setOnClickListener(this);
		login_edit_pass2=(EditText) findViewById(R.id.login_edit_pass2);
		login_edit_pass2.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletpass2.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass2.setVisibility(View.GONE);
				}
			 
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_edit_code:
			login_edit_code.setText("");
			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break; 
		case R.id.login_linear_signin:
			if(check()){
				login();
			}
			break;
		default:
			break;
		}
	}
	private boolean check() {
		// TODO Auto-generated method stub
		//username,password
		username=StringUtil.replaceBlank(login_edit_code.getText().toString());
		if(username.length()==0){
			Toast.makeText(getApplicationContext(), "用户密码不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		password=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		String password2=StringUtil.replaceBlank(login_edit_pass2.getText().toString());
		if(password.length()<4){
			Toast.makeText(getApplicationContext(), "用户密码必须大于4位",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!password.equals(password2)){
			Toast.makeText(getApplicationContext(), "密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		password=StringUtil.Md5(password);
		return true;
	}
	private void login() {
		// TODO Auto-generated method stub
		 AsyncHttpClient client = new AsyncHttpClient();  
	 
		RequestParams params = new RequestParams();
		 
		params.put("username",username); 
		params.put("studentPassword", password); 
		params.put("cityId", Config.CITY_ID); 
		params.put("accountType", true); 	 
		params.setUseJsonStreamer(true);
  
		MyApplication.getInstance().getClient().post(Config.UserRegistration, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
 
				// TODO Auto-generated method stub
				String responseMsg = new String(responseBody).toString();
				System.out.println("MSG" + responseMsg);	
				System.out.println("headers" + headers.toString());	
				Gson gson = new Gson();
				
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getInt("code");
					if(code==1){ //判断返回结果是否合法
						Toast.makeText(getApplicationContext(),  jsonobject.getString("result"), 1000).show();
//						User current = gson.fromJson(jsonobject.getString("result"), new TypeToken<User>() {
//	 					}.getType());
//	 					 MyApplication.currentUser = current;
//	 					 
//	 					editor.putString("name", current.getStudentEmail());
//	 					editor.putString("pass", login_edit_pass.getText().toString());
//	 					editor.putString("tag",mySharedPreferences.getString("tag", "tag")+","+current.getStudentId());
//	 					editor.commit();
//	 					 
//	 					 
//	 					 MyApplication.setToken(jsonobject.getString("token"));
//	 					 Intent i =new Intent(LoginActivity.this,MainActivity.class);
//	 					 startActivity(i);
	 				 
	 					 finish();
					}else{
						 
						Toast.makeText(getApplicationContext(),  jsonobject.getString("result"), 1000).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					System.out.println("MSG" + "e````"+e.toString());	
					e.printStackTrace();
					
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				login_linear_signin.setClickable(true);
				Toast.makeText(getApplicationContext(), "请检查网络问题",
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}
