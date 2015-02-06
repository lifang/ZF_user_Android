package com.examlpe.zf_android.util;


import com.example.zf_android.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UpdateDialog extends Dialog {
	private TextView quit_message,quit_cancel,quit_ok;
	private LinearLayout ll_hulve,ll_gengxin;
	private String str,note,st_yes,st_no;
	private ICallBack icall;
	public UpdateDialog(Context context,ICallBack icall,String str,String note) {
		super(context, R.style.mydialog);
		this.icall = icall;
		this.str = str;
		this.note = note;
	}
	
//	public UpdateDialog(Context context,ICallBack icall,String str,String note,String s1,String s2) {
//		super(context, R.style.mydialog);
//		this.icall = icall;
//		this.str = str;
//		this.note = note;
//		this.st_yes=s1;
//		this.st_no=s2;
//	}
	
	
	public interface ICallBack{
		void callwitch();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quit_builder);
 		quit_cancel=(TextView)findViewById(R.id.quit_cancel);
 		quit_cancel.setText("Cancle");
 		quit_message=(TextView)findViewById(R.id.quit_message);
 		quit_message.setText(str);
 		quit_ok=(TextView)findViewById(R.id.quit_ok);
 		quit_ok.setText("Download");
 		quit_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				icall.callwitch();
				dismiss();
			}
		});
 		quit_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			} 
 			
 		});
	}

	
}
