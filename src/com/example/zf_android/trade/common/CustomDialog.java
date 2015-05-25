package com.example.zf_android.trade.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.epalmpay.userPhone.R;

/**
 * customDialog 对话框
 * 
 * @author wongs
 * 
 */
public class CustomDialog extends Dialog {

	TextView contentTv;
	Button leftButton;
	Button rightButton;
	Context context;

	public CustomDialog(Activity context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.dialog_custom);
		this.context = context;
		contentTv = (TextView) this.findViewById(R.id.custom_dialog_content);
		leftButton = (Button) this.findViewById(R.id.custom_dialog_left);
		rightButton = (Button) this.findViewById(R.id.custom_dialog_right);
		rightButton.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CustomDialog.this.dismiss();
			}

		});
		Window window = this.getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setWindowAnimations(R.style.Dialog);
		WindowManager.LayoutParams lp = window.getAttributes();
		int screenW = DialogUtil.getScreenWidth(context);
		lp.width = (int) (0.7 * screenW);
		
	}

	public void setContent(String content) {
		contentTv.setText(content);
	}

	public void setContent(int resId) {
		String content = context.getResources().getString(resId);
		contentTv.setText(content);
	}

	public void setLeftListener(android.view.View.OnClickListener listener) {
		leftButton.setOnClickListener(listener);
	}

	public void setRightListener(android.view.View.OnClickListener listener) {
		rightButton.setOnClickListener(listener);
	}

	public void setSoftKeyValue(String left, String right) {
		leftButton.setText(left);
		rightButton.setText(right);
	}

	public void setSoftKeyValue(int left_resId, int right_resId) {
		String left = context.getResources().getString(left_resId);
		String right = context.getResources().getString(right_resId);
		leftButton.setText(left);
		rightButton.setText(right);
	}

	/*
	 * final CustomDialog dialog = new CustomDialog(Main.this);
	 * dialog.setSoftKeyValue("left", "right");
	 * dialog.setCanceledOnTouchOutside(false); dialog.setCancelable(false);
	 * dialog.setContent("content"); dialog.setLeftListener(new
	 * android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { dialog.dismiss(); }
	 * 
	 * }); dialog.setRightListener(new android.view.View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { dialog.dismiss(); } });
	 * dialog.show();
	 */

}
