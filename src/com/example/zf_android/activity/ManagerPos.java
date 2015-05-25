package com.example.zf_android.activity;
 
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
/***
 * 
*    
* �����ƣ�About   
* ��������  �ն˹���
* �����ˣ� ljp 
* ����ʱ�䣺2015-1-27 ����7:55:20   
* @version    
*
 */
public class ManagerPos extends BaseActivity implements OnClickListener{
	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_mine);
		new TitleMenuUtil(ManagerPos.this, "�ն˹���").show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	 
}
