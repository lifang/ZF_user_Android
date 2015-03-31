package com.examlpe.zf_android.util;

 

import com.example.zf_android.R;

import android.app.Activity;
 
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
 
 
public class TitleMenuUtil {
	private Activity activity;
	private String title;
	private LinearLayout titleback_linear_back;
	private TextView titleback_text_title,tv_back;
	public TitleMenuUtil(Activity activity,String title) {
		this.activity = activity;
		this.title = title;
	}
	
	public void show(){
		titleback_linear_back = (LinearLayout) activity.findViewById(R.id.titleback_linear_back);
		tv_back = (TextView) activity.findViewById(R.id.tv_back);
		titleback_text_title = (TextView) activity.findViewById(R.id.titleback_text_title);
		titleback_text_title.setText(title);
		tv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}
	public void show(View v){
		titleback_linear_back = (LinearLayout) v.findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView) v.findViewById(R.id.titleback_text_title);
		titleback_text_title.setText(title);
		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}
	
}
