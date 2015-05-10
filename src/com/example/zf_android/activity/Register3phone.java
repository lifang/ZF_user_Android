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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.MyToast;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;

public class Register3phone extends BaseActivity   implements OnClickListener{
	private TextView tv_code,tv_check;
	private EditText login_edit_email,login_edit_code;
	private LinearLayout login_linear_deletemali,login_linear_deletcode ,login_linear_signin ;
	private int Countmun=120;
	private Boolean isRun=true;
	public  int cityid = 0;
	private String email;
	private Runnable runnable;
	private String s;
	final Handler handler = new Handler(){          // handle  
		public void handleMessage(Message msg){  
			switch (msg.what) {  
			case 1:  
				if(Countmun==0){

					isRun=false;
					tv_code.setClickable(true);

					tv_code.setText("发送验证码");
					System.out.println("destroy`"+Countmun);
				}else{
					Countmun--;  
					tv_code.setText(  Countmun+"秒后重新发送");  
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
		cityid=MyApplication.getInstance().getCityId();
		setContentView(R.layout.register_checkcode);
		new TitleMenuUtil(Register3phone.this, "注册").show();

		initView();

		runnable = new Runnable() {  
			@Override  
			public void run() {  
				if(Countmun==0){

					Countmun=120;
					tv_code.setClickable(true);
					tv_code.setText("发送验证码");
				}else{

					Countmun--;  
					tv_code.setText( Countmun+"秒后重新发送");  

					handler.postDelayed(this, 1000);  
				}
			}  
		};
		getCode();
		tv_code.setClickable(false);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId()) {

		case R.id.tv_code:  
			if(check())
				getCode();
			tv_code.setClickable(false);

			break;

		case R.id.login_linear_signin: 

			if(StringUtil.replaceBlank(login_edit_code.getText().toString()).length()==0){
				MyToast.showToast(getApplicationContext(),"请输入验证码");
				break;
			}
			if(!login_edit_code.getText().toString().trim().endsWith(Config.reg_phoneCode)){
				MyToast.showToast(getApplicationContext(),"验证码错误");
				break;
			}else{
				Intent i = new Intent(getApplicationContext(),
						Register4phone.class);
				i.putExtra("phone",s);
				i.putExtra("vcode",Config.reg_phoneCode);
				startActivity(i);
			}
			break;
		case R.id.login_linear_deletemali:
			login_edit_email.setText("");
			break;
		case R.id.login_linear_deletcode:
			login_edit_code.setText("");
			break;

		default : 
			break;
		}
	}
	private boolean check() {
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
		if(email.length()==0){
			MyToast.showToast(getApplicationContext(),"手机号不能为空");
			return false;
		}
		if (!StringUtil.isMobile(email)) {
			MyToast.showToast(getApplicationContext(),"请输入正确的手机号码");
			return false;
		}
		return true;
	}

	private void getCode() {

		email=StringUtil.replaceBlank(login_edit_email.getText().toString()); 
		registerPhoneCode(email);

	}
	private void initView() {
		tv_check=(TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		tv_code=(TextView) findViewById(R.id.tv_code);
		tv_code.setOnClickListener(this);


		login_linear_signin=(LinearLayout) findViewById(R.id.login_linear_signin);
		login_linear_signin.setOnClickListener(this);

		login_linear_deletemali=(LinearLayout) findViewById(R.id.login_linear_deletemali);
		login_linear_deletemali.setOnClickListener(this);
		login_edit_email=(EditText) findViewById(R.id.login_edit_email);
		s=getIntent().getStringExtra("phone");
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

	public void registerPhoneCode(String phonenumber){ 
		API.reg_phoneCode(Register3phone.this, phonenumber,
				new HttpCallback(Register3phone.this) {		       
			@Override
			public void onSuccess(Object data) {
				Config.reg_phoneCode = data+"";
				handler.postDelayed(runnable, 1000); 
				MyToast.showToast(getApplicationContext(),"验证码发送成功");
			}
			@Override
			public TypeToken getTypeToken() {
				return null;
			}
		});
	}
}
