package com.example.zf_android.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
 
 
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/***
 *   ��¼ҳ��
 * 
 * @author Lijinpeng
 * 
 *         comdo
 */
public class LoginActivity extends Activity implements OnClickListener {
	private String name,pass,url,deviceToken;
	private ImageView loginImage;
	private CheckBox isremeber_cb;
	private Boolean isRemeber = true;
	private TextView login_text_forget, login_info;
	private EditText login_edit_name, login_edit_pass;
	private LinearLayout login_linear_deletename, login_linear_deletepass,zhuche_ll,
			login_linear_login, msg;
	private String sign, pass1, usename, passsword;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private Boolean isFirst;
	private String sessionId;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				// showDialog();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(), R.string.no_internet,
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),
						R.string.refresh_toomuch, Toast.LENGTH_SHORT).show();
				break;
			case 4:
				 
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		initView();
		new TitleMenuUtil(LoginActivity.this, "��¼").show();
		//new ClientUpdate(LoginActivity.this).checkSetting();
	}

	private void initView() {
		// TODO Auto-generated method stub

		// ��ʼ��
		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		editor = mySharedPreferences.edit();
 
		login_text_forget = (TextView) findViewById(R.id.login_text_forget);
		//login_text_forget.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		login_text_forget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(),
						FindPass.class);
				startActivity(i);
			}
		});
		msg = (LinearLayout) findViewById(R.id.msg);
		login_info = (TextView) findViewById(R.id.login_info);
		 
		zhuche_ll= (LinearLayout) findViewById(R.id.zhuche_ll);
		zhuche_ll.setOnClickListener(this);

		login_edit_name = (EditText) findViewById(R.id.login_edit_name);
		login_edit_pass = (EditText) findViewById(R.id.login_edit_pass);
		
		login_linear_deletename = (LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_deletepass = (LinearLayout) findViewById(R.id.login_linear_deletepass);
		login_linear_deletepass.setOnClickListener(this);
		login_linear_deletename.setOnClickListener(this);
		login_edit_name.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				msg.setVisibility(View.INVISIBLE);
				if (s.length() > 0) {
					login_linear_deletename.setVisibility(View.VISIBLE);
				} else {
					login_linear_deletename.setVisibility(View.GONE);
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
		login_edit_pass.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				msg.setVisibility(View.INVISIBLE);
				if (s.length() > 0)
					login_linear_deletepass.setVisibility(View.VISIBLE);
				else
					login_linear_deletepass.setVisibility(View.GONE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		 
		login_linear_login = (LinearLayout) findViewById(R.id.login_linear_login);
		login_linear_login.setOnClickListener(this);
		isFirst = mySharedPreferences.getBoolean("isRemeber", false);
		if (isFirst) {
			login_edit_pass.setText(mySharedPreferences.getString("password",
					""));
			login_edit_name.setText(mySharedPreferences.getString("username",
					""));
		}else{
			login_edit_name.setText(mySharedPreferences.getString("username",
					""));
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_linear_login: 
			// ��¼
			if(check()){
				login();
			}
		 

			break;
		case R.id.zhuche_ll: 
		// ��¼
		startActivity(new Intent(LoginActivity.this,Register.class));
	 

		break;
		case R.id.login_linear_deletename:
			login_edit_name.setText("");
			break;
		case R.id.login_linear_deletepass:
			login_edit_pass.setText("");
			break;
		default:
			break;
		}
	}

	private void login() {
		// TODO Auto-generated method stub
		AsyncHttpClient client = new AsyncHttpClient(); // �����첽����Ŀͻ��˶���
		RequestParams params = new RequestParams();
	 

		params.put("username", usename);
		params.put("password", passsword);
		params.setUseJsonStreamer(true);
		
		
		String url=Config.LOGIN;
		System.out.println("usename`` `" + usename);
		System.out.println("passsword`` `" + passsword);
		client.post(Config.LOGIN, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
				String userMsg = new String(responseBody).toString();
				System.out.println("userMsg`` `" + userMsg);
			 
				Gson gson = new Gson();

// 
//				ResultCode rc = gson.fromJson(userMsg.toString(), new TypeToken<ResultCode>() {
//					}.getType());
//				 
//				 if(rc.getSolution()!=null&& rc.getSolution().length()>0){
//					 // �жϴ��󷵻أ�����ʾ��Ϣ
//						msg.setVisibility(View.VISIBLE);
//						login_info.setText(rc.getMessage().toString());
//				 }else{
//					 
//					 
// 						User current = gson.fromJson(userMsg.toString(), new TypeToken<User>() {
// 					}.getType());
// 						MyApplication.currentUser = current;
// 			 
// 					
// 					
// 					Intent i =new Intent(getApplicationContext(),Main.class);
// 					startActivity(i);
// 					finish();
//					System.out.println("�û���¼�ɹ�--���ֵ�¼��Ϣ"+mySharedPreferences.getBoolean("isRemeber", false));
//				 }
 			 
 			 
 
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				System.out.println("onFailure`` `"  );
			}
		});

	}
	private boolean check() {
		// TODO Auto-generated method stub
		usename=StringUtil.replaceBlank(login_edit_name.getText().toString());
		if(usename.length()==0){
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passsword=StringUtil.replaceBlank(login_edit_pass.getText().toString());
		if(passsword.length()==0){
			Toast.makeText(getApplicationContext(), "请输入用户密码",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		passsword=StringUtil.Md5(passsword);
		return true;
	}

}
