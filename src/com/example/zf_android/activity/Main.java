package com.example.zf_android.activity;
 
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
 
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.trade.TradeFlowActivity;

public class Main extends BaseActivity implements OnClickListener{
	
	private RelativeLayout  main_rl_pos,main_rl_renzhen,main_rl_zdgl,main_rl_jyls,
	main_rl_Forum,main_rl_wylc,main_rl_xtgg,main_rl_lxwm,main_rl_my,main_rl_pos1;
	private ImageView testbutton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		testbutton=(ImageView) findViewById(R.id.testbutton);
		testbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i =new Intent(Main.this,LoginActivity.class);
					 startActivity(i);
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		main_rl_pos=(RelativeLayout) findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen=(RelativeLayout) findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl=(RelativeLayout) findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls=(RelativeLayout) findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum=(RelativeLayout) findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc=(RelativeLayout) findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm=(RelativeLayout) findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg=(RelativeLayout) findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);
		main_rl_my=(RelativeLayout) findViewById(R.id.main_rl_my);
		main_rl_my.setOnClickListener(this);
		main_rl_pos1=(RelativeLayout) findViewById(R.id.main_rl_pos1);
		main_rl_pos1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.main_rl_pos1:  // 买POS机器
			 startActivity(new Intent(Main.this,MyMessage.class));
			 
			break;
		case R.id.main_rl_my:  // 买POS机器
			 startActivity(new Intent(Main.this,MenuMine.class));
			 
			break;
		
		case R.id.main_rl_pos:  // 买POS机器
			 startActivity(new Intent(Main.this,PosListActivity.class));
			 
			break;
			
		case R.id.main_rl_renzhen:  //认证
			 Intent i =new Intent(Main.this,OrderList.class);
			 startActivity(i);
			 
			break;
		case R.id.main_rl_zdgl: //终端管理 
			 
			break;
		case R.id.main_rl_jyls: //交易流水 
			 
			startActivity(new Intent(Main.this, TradeFlowActivity.class));
			break;
		case R.id.main_rl_Forum: //我要贷款   
			 
			break;
		case R.id.main_rl_xtgg: //系统公告   
			 
			 
			break;
		case R.id.main_rl_lxwm: //联系我们
			 
			 
			break;
		default:
			break;
		}
	}
}
