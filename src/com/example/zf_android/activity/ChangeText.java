package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changetext);
		new TitleMenuUtil(ChangeText.this, "请输入内容").show();
		login_linear_deletename=(LinearLayout) findViewById(R.id.login_linear_deletename);
		login_linear_deletename.setOnClickListener(this);
		login_edit_name=(EditText) findViewById(R.id.login_edit_name);
		btn_sub=(Button) findViewById(R.id.btn_sub);
		btn_sub.setOnClickListener(this);
		index=getIntent().getIntExtra("key", 1);
		String name =getIntent().getStringExtra("name");
		login_edit_name.setText(name);
		login_edit_name.setSelection(name.length());
		System.out.println("index--"+index);
	}

	@Override
	public void onClick(View v) {
		
		 switch (v.getId()) {
		case R.id.btn_sub:
				tv_content=login_edit_name.getText().toString();
				 
			 if(StringUtil.replaceBlank(tv_content).length()>0){
				 if (index == 2) {
					if (!StringUtil.isMobile(tv_content)) {
						Toast.makeText(getApplicationContext(), "请输入正确的手机号", 
								Toast.LENGTH_SHORT).show();
					}else {
						Intent intent2 = new Intent();
						intent2.putExtra("text", tv_content);
						ChangeText.this.setResult(index, intent2);
						finish();
					}
				}else if (index == 3) {
					if (!StringUtil.checkEmail(tv_content)) {
						Toast.makeText(getApplicationContext(), "请输入正确的邮箱", 
								Toast.LENGTH_SHORT).show();
					}else {
						Intent intent2 = new Intent();
						intent2.putExtra("text", tv_content);
						ChangeText.this.setResult(index, intent2);
						finish();
					}
				}else {
					Intent intent2 = new Intent();
					intent2.putExtra("text", tv_content);
					ChangeText.this.setResult(index, intent2);
					finish();
				}
			 }else{
				 Toast.makeText(ChangeText.this, "请输入要改变的内容", 1000).show();
			 }
			break;

		default:
			break;
		}
	}
}
