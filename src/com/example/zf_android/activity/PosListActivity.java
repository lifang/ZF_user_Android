package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

/***
 * 
*    
* 类名称：PosListActivity   
* 类描述：   买POS机器列表
* 创建人： ljp 
* 创建时间：2015-1-27 下午7:51:36   
* @version    
*
 */
public class PosListActivity extends BaseActivity implements OnClickListener{
	private ImageView pos_select;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poslist_activity);
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		pos_select=(ImageView) findViewById(R.id.pos_select);
		pos_select.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.pos_select:
			Intent i =new Intent(PosListActivity.this,PosSelect.class);
			startActivity(i);
			break;

		default:
			break;
		}
	}
}
