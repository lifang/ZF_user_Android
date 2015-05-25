package com.example.zf_android.activity;
 
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.epalmpay.userPhone.R;
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
public class About extends BaseActivity implements OnClickListener{
	private int a;
	private int index;
	private String text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new TitleMenuUtil(About.this, "关于").show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	 
}
