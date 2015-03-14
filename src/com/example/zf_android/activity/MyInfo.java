package com.example.zf_android.activity;
 
 
import java.io.IOException;
import java.net.URI;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
 
import android.widget.TextView;
 
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
/***
 * 
*    
* ����ƣ�MyInfo   
* ��������  �ҵ���Ϣ
* �����ˣ� ljp 
* ����ʱ�䣺2015-1-27 ����7:55:20   
* @version    
*
 */
public class MyInfo extends BaseActivity implements OnClickListener{
	
	private Button btn_exit;
	private LinearLayout mi_r1,mi_r2,mi_r3,mi_r4,mi_r5,mi_r6,mi_r7,mi_r8;
	private TextView tv1,tv2,tv3,tv4,tv5;
	private String url=Config.GRTONE+8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myinfo);
		new TitleMenuUtil(MyInfo.this, "我的信息").show();
		initView();
		System.out.println("11111");
 
	}
	 

	private void initView() {
		// TODO Auto-generated method stub
		btn_exit=(Button) findViewById(R.id.btn_exit);
		btn_exit.setOnClickListener(this);
		mi_r1=(LinearLayout) findViewById(R.id.mi_r1);
		mi_r1.setOnClickListener(this);
		mi_r2=(LinearLayout) findViewById(R.id.mi_r2);
		mi_r2.setOnClickListener(this);
		mi_r3=(LinearLayout) findViewById(R.id.mi_r3);
		mi_r3.setOnClickListener(this);
		mi_r4=(LinearLayout) findViewById(R.id.mi_r4);
		mi_r4.setOnClickListener(this);
		mi_r5=(LinearLayout) findViewById(R.id.mi_r5);
		mi_r5.setOnClickListener(this);
		mi_r6=(LinearLayout) findViewById(R.id.mi_r6);
		mi_r6.setOnClickListener(this);
		mi_r7=(LinearLayout) findViewById(R.id.mi_r7);
		mi_r7.setOnClickListener(this);
		tv1=(TextView) findViewById(R.id.tv1);
		tv2=(TextView) findViewById(R.id.tv2);
		tv3=(TextView) findViewById(R.id.tv3);
		tv4=(TextView) findViewById(R.id.tv4);
		tv5=(TextView) findViewById(R.id.tv5);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_exit:
			// ִ���˳���¼
			exit();
			break;
		case  R.id.mi_r1: // ����
			Intent i =new Intent(MyInfo.this,ChangeText.class);
			i.putExtra("key", 1);
			startActivityForResult(i, 1);
			 
			break;
		case  R.id.mi_r2: // �ֻ�
			Intent i2 =new Intent(MyInfo.this,ChangeText.class);
			i2.putExtra("key", 2);
			startActivityForResult(i2, 2);
			 
			break;
		case  R.id.mi_r3: // ����
			Intent i3 =new Intent(MyInfo.this,ChangeText.class);
			i3.putExtra("key",3);
			startActivityForResult(i3, 3);
			 
			break;
		case  R.id.mi_r4: // ���ڵ�
			 
			 
			break;
		case  R.id.mi_r5: // �ҵĻ��
			 
			 
			break;
		case  R.id.mi_r6: // ��ַ����
			 
			startActivity(new Intent(MyInfo.this,AdressList.class));
			 
			break;
		case  R.id.mi_r7: // �޸�����
			startActivity(new Intent(MyInfo.this,ChangePassword.class));
			 
			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv1.setText(a);
			}
			break;
		case 2:
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv2.setText(a);
			}
			if(data!=null){
				String  a =data.getStringExtra("text");
				tv3.setText(a);
			}
			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	private void exit() {
		// TODO Auto-generated method stub
		startActivity(new Intent(MyInfo.this,LoginActivity.class));
	}

	 
}
