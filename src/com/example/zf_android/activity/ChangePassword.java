package com.example.zf_android.activity;
 
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
 
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.OrderEntity;
import com.example.zf_android.entity.UserEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
public class ChangePassword extends BaseActivity implements OnClickListener{
	
	private EditText login_edit_name,login_edit_name2,login_edit_name3;
	private String URL="http://114.215.149.242:18080/ZFMerchant/api/customers/updatePassword/";
	private Button btn_exit;
//	id
//	passwordOld
//	password
	String passwordOld,password,password2;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);
		new TitleMenuUtil(ChangePassword.this, "修改密码").show();
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
		
		login_edit_name2=(EditText) findViewById(R.id.login_edit_name2);
		login_edit_name3=(EditText) findViewById(R.id.login_edit_name3);
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(check ()){
		  

		 
//			RequestParams params = new RequestParams();
//			params.put("id", MyApplication.currentUser.getId());
//			params.put("passwordOld", passwordOld);
//			params.put("password", password);
//			params.setUseJsonStreamer(true);
			 API.ChangePass(ChangePassword.this,MyApplication.NewUser.getId(),passwordOld,passwordOld,
		        		
		                new HttpCallback (ChangePassword.this) {

							@Override
							public void onSuccess(Object data) {
								// TODO Auto-generated method stub
								Toast.makeText(getApplicationContext(), "修改成功,请重新登录", 1000).show();
							 
			 					editor.putBoolean("islogin", false);
				 		 
				 				editor.commit();
				 				
				 				Intent i= new Intent(getApplicationContext(), LoginActivity.class);
				 				startActivity(i);
				 				finish();
							}

							@Override
							public TypeToken getTypeToken() {
								// TODO Auto-generated method stub
								return   null;
							};
		                });
	 
		
		}
	}

	private boolean check () {
		passwordOld=StringUtil.replaceBlank(login_edit_name.getText().toString());
		password=StringUtil.replaceBlank(login_edit_name2.getText().toString());
		password2=StringUtil.replaceBlank(login_edit_name3.getText().toString());
		if (passwordOld.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入您旧密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if (password.length() == 0) {
			Toast.makeText(getApplicationContext(), "请输入新密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (password2.length() == 0) {
			Toast.makeText(getApplicationContext(), "请再次输入您的密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!password2.equals(password)) {
			 
			Toast.makeText(getApplicationContext(), "二次密码不一样",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passwordOld=StringUtil.Md5(passwordOld);
		password=StringUtil.Md5(password);
		return true;
	}
}
