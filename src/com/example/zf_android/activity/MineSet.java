package com.example.zf_android.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.R;

public class MineSet extends BaseActivity implements OnClickListener{
	private ImageView img_on_off;
	private Boolean isOpen_mineset;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private LinearLayout ll_new,ll_clean;
	private TextView tv_clean;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_set);
		new TitleMenuUtil(MineSet.this, "ÉèÖÃ").show();
		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		isOpen_mineset=mySharedPreferences.getBoolean("isOpen_mineset", true);
		img_on_off=(ImageView) findViewById(R.id.img_on_off);
		if(!isOpen_mineset){
			img_on_off.setBackgroundResource(R.drawable.pos_off);
		 
		}
		img_on_off.setOnClickListener(this);
		ll_new=(LinearLayout) findViewById(R.id.ll_new);
		ll_new.setOnClickListener(this);
		ll_clean=(LinearLayout) findViewById(R.id.ll_clean);
		ll_clean.setOnClickListener(this);
		
		tv_clean=(TextView) findViewById(R.id.tv_clean);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.ll_new:
				
				//¼ì²â¸üÐÂ
				
				break;
			case R.id.tv_clean:
				
				tv_clean.setText("");
				
				break;
			case R.id.img_on_off:
				
				if(isOpen_mineset){
					isOpen_mineset=false;
					//img_on_off.setBackgroundDrawable(getResources().getDrawable(R.drawable.pos_off));
					img_on_off.setBackgroundResource(R.drawable.pos_off);
					editor.putBoolean("isOpen_mineset",false);
 					editor.commit();
					
				}else{
					isOpen_mineset=true;
					img_on_off.setBackgroundResource(R.drawable.pos_on);		 
					editor.putBoolean("isOpen_mineset",true);
 					editor.commit();
				}
				
				
				
				break;

			default:
				break;
			}
	}
}
