package com.examlpe.zf_android.util;



import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zf_android.R;


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
				hideSoftKeyboard(activity);
			}
		});

		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
				hideSoftKeyboard(activity);
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
				hideSoftKeyboard(activity);
			}
		});
	}
	public static void hideSoftKeyboard(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		if(inputMethodManager.isActive()&&activity.getCurrentFocus()!=null){
			if (activity.getCurrentFocus().getWindowToken()!=null) {
				inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
			}
		}
	}
}
