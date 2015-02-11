package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
 
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
/***
 * 
*    
* 类名称：About   
* 类描述：   关于页面
* 创建人： ljp 
* 创建时间：2015-1-27 下午7:55:20   
* @version    
*
 */
public class ChangePassword extends BaseActivity implements OnClickListener{
	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);
		new TitleMenuUtil(ChangePassword.this, "关于").show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	 
}
