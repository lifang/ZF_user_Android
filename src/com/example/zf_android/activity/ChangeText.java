package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
 

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
/***
 * 
*    
* ����ƣ�About   
* ��������   ����ҳ��
* �����ˣ� ljp 
* ����ʱ�䣺2015-1-27 ����7:55:20   
* @version    
*
 */
public class ChangeText extends BaseActivity implements OnClickListener{
	private LinearLayout login_linear_deletename;
	private Button btn_sub;
	private EditText login_edit_name;
	private String tv_content;
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changetext);
		new TitleMenuUtil(ChangeText.this, "请输入内容").show();
		login_linear_deletename=(LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_deletename.setOnClickListener(this);
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
		btn_sub=(Button) findViewById(R.id.btn_sub);
		btn_sub.setOnClickListener(this);
		index=getIntent().getIntExtra("key", 1);
		System.out.println("index--"+index);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		 switch (v.getId()) {
		case R.id.btn_sub:
				tv_content=login_edit_name.getText().toString();
			 if(StringUtil.replaceBlank(tv_content).length()>0){
				 
					Intent intent2 = new Intent();
					intent2.putExtra("text", tv_content);
					ChangeText.this.setResult(index, intent2);
					finish();
			 }else{
				 Toast.makeText(ChangeText.this, "������Ҫ�ı������", 1000).show();
			 }
			break;

		default:
			break;
		}
	 
	}

	 
}
