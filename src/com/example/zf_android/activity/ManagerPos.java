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
* 类描述：  终端管理
* 创建人： ljp 
* 创建时间：2015-1-27 下午7:55:20   
* @version    
*
 */
public class ManagerPos extends BaseActivity implements OnClickListener{
	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_mine);
		new TitleMenuUtil(ManagerPos.this, "终端管理").show();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	 
}
