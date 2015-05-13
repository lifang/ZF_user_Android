package com.example.zf_android.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;
/***
 * 
 *    
 * 类名称：FindPassword   
 * 类描述：   手机找回密码
 * 创建人： ljp 
 * 创建时间：2015-3-23 下午3:49:21   
 * @version    
 *
 */
public class FindPassword extends BaseActivity   implements OnClickListener{
	private LinearLayout tv_codeLayout;
	private TextView tv_code,tv_msg,tv_check;
	private EditText login_edit_email,login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletemali,login_linear_deletcode,login_linear_deletpass 
	,login_linear_deletpass2,login_linear_signin;
	private int Countmun=120;
	private Boolean isRun=true;
	private ImageView img_check,img_check_n;
	private String email,pass;
	private Runnable runnable;
	final Handler handler = new Handler(){          // handle  
		public void handleMessage(Message msg){  
			switch (msg.what) {  
			case 1:  
				if(Countmun==0){

					isRun=false;
					tv_codeLayout.setClickable(true);

					tv_code.setText("发送验证码");
					System.out.println("destroy`"+Countmun);
				}else{
					Countmun--;  
					tv_code.setText( Countmun+"秒后重新发送");  
					System.out.println("Countmun`D2`"+Countmun);
				}

			}  
			super.handleMessage(msg);  
		}  
	};  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_findpass);
		new TitleMenuUtil(FindPassword.this, "找回密码").show();
		initView();
		runnable = new Runnable() {  
			@Override  
			public void run() {  
				if(Countmun==0){

					Countmun=120;
					tv_codeLayout.setClickable(true);
					tv_code.setText("发送验证码");
				}else{

					Countmun--;  
					tv_code.setText( Countmun+"秒后重新发送");  

					handler.postDelayed(this, 1000);  
				}

			}  
		};
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	@Override
	public void onClick(View v) {
		switch ( v.getId()) {

		case R.id.tv_codeLayout:  // ��ȡ��֤��tv_check
			getCode();
			break;
		case R.id.tv_check:  // ��ȡ��֤�� 
			System.out.println("Config.find_phoneCode"+Config.find_phoneCode);
			
			if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
				Toast.makeText(getApplicationContext(), "请输入验证码",
						Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(login_edit_code.getText().toString().equals(Config.find_phoneCode)){
				img_check.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
			}else{
				img_check.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
			}

			break;
		case R.id.login_linear_signin:  // ��ȡ��֤�� 
			if(check()){
				sure();
			}

			break;
		case R.id.login_linear_deletemali:
			login_edit_email.setText("");
			break;
		case R.id.login_linear_deletcode:
			login_edit_code.setText("");
			break;
		case R.id.login_linear_deletpass:
			login_edit_pass.setText("");
			break;
		case R.id.login_linear_deletpass2:
			login_edit_pass2.setText("");
			break;
		default : 
			break;
		}
	}
	private boolean check() {
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(email.length()==0){
			Toast.makeText(getApplicationContext(), "手机号不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
			Toast.makeText(getApplicationContext(), "请输入验证码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_code.getText().toString().equals(Config.find_phoneCode)){
			Toast.makeText(getApplicationContext(), "验证码错误",
					Toast.LENGTH_SHORT).show();
			return false;
		}

		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(pass.length()==0){
			Toast.makeText(getApplicationContext(), "请输入密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(pass.length() < 6){
			Toast.makeText(getApplicationContext(), "密码长度最少6个字符",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(!login_edit_pass2.getText().toString().equals(pass)){
			Toast.makeText(getApplicationContext(), "二次密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		pass=StringUtil.Md5(pass);
		return true;
	}

	private void sure() {

		System.out.println(pass+"-------"+email+"----"+login_edit_code.getText().toString());
		API.PhonefindPass(FindPassword.this, pass,login_edit_code.getText().toString(),email,
				new HttpCallback(FindPassword.this) {	           
			@Override
			public void onSuccess(Object data) {
				Toast.makeText(FindPassword.this, "修改密码成功", 1000).show();
				Intent i =new Intent(getApplication(),PassSucces.class);
				i.putExtra("tel", email);
				startActivity(i);
			}
			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}

	/**
	 * ��ȡ��֤��
	 */
	private void getCode() {

		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		API.sendPhoneCode(FindPassword.this,email,
				new HttpCallback(FindPassword.this) {	           
			@Override
			public void onSuccess(Object data) {
				Config.find_phoneCode = data+"";
				tv_codeLayout.setClickable(false);
				tv_code.setText("120秒");
				Toast.makeText(getApplicationContext(), "发送成功", 1000).show();
				handler.postDelayed(runnable, 1000);  
			}
			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}

	private void initView() {
		tv_check=(TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		img_check=(ImageView) findViewById(R.id.img_check);
		img_check_n=(ImageView) findViewById(R.id.img_check_n);
		tv_codeLayout = (LinearLayout) findViewById(R.id.tv_codeLayout);
		tv_code=(TextView) findViewById(R.id.tv_code);
		tv_codeLayout.setOnClickListener(this);

		tv_msg=(TextView) findViewById(R.id.tv_msg);

		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);
		login_linear_deletemali=(LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
		login_edit_email=(EditText) findViewById(R.id.login_edit_email);
		String s=getIntent().getStringExtra("phone");
		login_edit_email.setText(s);
		login_edit_email.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletemali.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemali.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		//	login_linear_deletpass
		login_linear_deletcode=(LinearLayout) findViewById(R.id.login_linear_deletcode);
		login_linear_deletcode.setOnClickListener(this);
		login_edit_code=(EditText) findViewById(R.id.login_edit_code);
		login_edit_code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletcode.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletcode.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		//	login_linear_deletpass
		login_linear_deletpass=(LinearLayout) findViewById(R.id.login_linear_deletpass);
		login_linear_deletpass.setOnClickListener(this);
		login_edit_pass=(EditText) findViewById(R.id.login_edit_pass);
		login_edit_pass.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletpass.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		login_linear_deletpass2=(LinearLayout) findViewById(R.id.login_linear_deletpass2);
		login_linear_deletpass2.setOnClickListener(this);
		login_edit_pass2=(EditText) findViewById(R.id.login_edit_pass2);
		login_edit_pass2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {

					login_linear_deletpass2.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletpass2.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}
	public class MyThread implements Runnable{      // thread  
		@Override  
		public void run(){  
			while(isRun){  
				System.out.println("run``"+Countmun);

				try{  
					Thread.sleep(1000);     // sleep 1000ms  
					Message message = new Message();  
					message.what = 1;  
					handler.sendMessage(message);  
				}catch (Exception e) {  

				}
			}
		}  
	} 

}
