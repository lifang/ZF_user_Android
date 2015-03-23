package com.example.zf_android.activity;

 
import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
 
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
	private TextView tv_code,tv_msg,tv_check;
	private EditText login_edit_email,login_edit_code,login_edit_pass,login_edit_pass2;
	private LinearLayout login_linear_deletemali,login_linear_deletcode,login_linear_deletpass 
	,login_linear_deletpass2,login_linear_signin;
	private int Countmun=120;
	private Thread myThread;
	private Boolean isRun=true;
	private ImageView img_check,img_check_n;
	public  String vcode="";
	private String url,email,pass;
	private Runnable runnable;
    final Handler handler = new Handler(){          // handle  
        public void handleMessage(Message msg){  
            switch (msg.what) {  
            case 1:  
            	if(Countmun==0){
            	  
            		isRun=false;
            		tv_code.setClickable(true);
            	 
            		tv_code.setText("������֤��");
            		System.out.println("destroy`"+Countmun);
            	}else{
                 	Countmun--;  
                 	tv_code.setText(  Countmun+"������·���");  
            		System.out.println("Countmun`D2`"+Countmun);
            	}
    
            }  
            super.handleMessage(msg);  
        }  
    };  
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpass);
		new TitleMenuUtil(FindPassword.this, "找回密码").show();
		initView();
		url=Config.FINDPASS;
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
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// myThread.stop();
		//myThread.destroy();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch ( v.getId()) {
 
			 
		case R.id.tv_code:  // ��ȡ��֤��tv_check
 
		 	tv_code.setClickable(false);
			tv_code.setText("120秒");
			getCode();
			break;
		case R.id.tv_check:  // ��ȡ��֤�� 
			System.out.println("vcode"+vcode);
			 
			if(login_edit_code.getText().toString().equals(vcode)){
				img_check.setVisibility(View.VISIBLE);
				img_check_n.setVisibility(View.GONE);
			}else{
				img_check.setVisibility(View.GONE);
				img_check_n.setVisibility(View.VISIBLE);
			}
			
			break;
		case R.id.login_linear_signin:  // ��ȡ��֤�� 
		//	pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		 
			//pass= StringUtil.Md5(pass);
			 
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
		// TODO Auto-generated method stub
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
		if(!login_edit_code.getText().toString().endsWith(vcode)){
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
		if(!login_edit_pass2.getText().toString().equals(pass)){
			Toast.makeText(getApplicationContext(), "二次密码不一致",
					Toast.LENGTH_SHORT).show();
			return false;
		}
	 	pass=StringUtil.Md5(pass);
		return true;
	}

	private void sure() {
		// TODO Auto-generated method stub
 
		RequestParams params = new RequestParams();
		params.put("password",pass);
		params.put("code",vcode); 
		params.put("username", email); 
		System.out.println(pass+"-------"+email+"----"+vcode);
		pass=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		 API.PhonefindPass(FindPassword.this, pass,vcode,email,
		
        new HttpCallback(FindPassword.this) {	           
			@Override
			public void onSuccess(Object data) {
				// TODO Auto-generated method stub
	 		Toast.makeText(FindPassword.this, "修改密码成功", 1000).show();
	 		Intent i =new Intent(getApplication(),PassSucces.class);
	 		startActivity(i);
			}
			@Override
			public TypeToken getTypeToken() {
				// TODO Auto-generated method stub
				return null;
			}
        });
		
	 
		
//		params.setUseJsonStreamer(true);
//		MyApplication.getInstance().getClient().post(Config.updatePassword, params, new AsyncHttpResponseHandler() {
//
//			@Override
//			public void onSuccess(int statusCode, Header[] headers,
//					byte[] responseBody) {
//				// TODO Auto-generated method stub
//				String responseMsg = new String(responseBody).toString();
//				System.out.println("MSG" + responseMsg);	
//			 
//				Gson gson = new Gson();				
//				JSONObject jsonobject = null;
//				int code = 0;
//				try {
//					jsonobject = new JSONObject(responseMsg);
//					code = jsonobject.getInt("code");
//					
//					if(code==-2){
//						Intent i =new Intent(getApplication(),LoginActivity.class);
//						startActivity(i);
//					}else if(code==0){
//						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
//								Toast.LENGTH_SHORT).show();
//						Intent i =new Intent(getApplication(),PassSucces.class);
//						startActivity(i);
//					}else{
//						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
//								Toast.LENGTH_SHORT).show();
//					}	 
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					System.out.println( "e````"+e.toString());	
//					e.printStackTrace();
//					
//				}
//			}
//
//			@Override
//			public void onFailure(int statusCode, Header[] headers,
//					byte[] responseBody, Throwable error) {
//				// TODO Auto-generated method stub
//				System.out.println("eee" + responseBody.toString());	
//			}
//		});
	
	}

	/**
	 * ��ȡ��֤��
	 */
	private void getCode() {
		// TODO Auto-generated method stub
 		 
		//tv_code.setText("Resent Code");
	//	 handler.postDelayed(runnable, 1000);  
		 
		email=StringUtil.replaceBlank(login_edit_email.getText().toString());
			AsyncHttpClient aaa= new AsyncHttpClient();

		// TODO Auto-generated method stub
		String url = "http://114.215.149.242:18080/ZFMerchant/api/user/sendPhoneVerificationCodeFind";
		RequestParams params = new RequestParams();
		params.put("codeNumber", email);
		params.setUseJsonStreamer(true);
		System.out.println("-codeNumber---"+email);
		
		aaa.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						 
						try {
							vcode=new JSONObject(responseMsg).getString("result");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("-vcode---"+vcode);
						Toast.makeText(getApplicationContext(), "发送成功", 1000).show();

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
		tv_check=(TextView) findViewById(R.id.tv_check);
		tv_check.setOnClickListener(this);
		img_check=(ImageView) findViewById(R.id.img_check);
		img_check_n=(ImageView) findViewById(R.id.img_check_n);
		tv_code=(TextView) findViewById(R.id.tv_code);
		tv_code.setOnClickListener(this);
	 
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
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					 
					login_linear_deletemali.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletemali.setVisibility(View.GONE);
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
		
	//	login_linear_deletpass
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
		//	login_linear_deletpass
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
