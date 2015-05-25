package com.example.zf_android.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.DialogUtil;
import com.examlpe.zf_android.util.DialogUtil.CallBackChange;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.MessageEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.reflect.TypeToken;
/**
 * 
 *    
 * ����ƣ�SystemDetail   
 * ��������    消息详情
 * �����ˣ� ljp 
 * ����ʱ�䣺2015-2-6 ����3:32:14   
 * @version    
 *
 */
public class MymsgDetail extends BaseActivity{
	private TextView tv_titel,tv_time,tv_content,msg_title;
	private LinearLayout titleback_linear_back;
	private String id;
	private ImageView search;
	private TextView titleback_text_title,tv_back;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_detail);
		Config.notificationMsgID = "";
		id=getIntent().getStringExtra("id");
		initView();
		getData();
	}
	private void getData() {
		API.receiverGetById(this, MyApplication.getInstance().getCustomerId(),Integer.valueOf(id), 
				new HttpCallback<MessageEntity>(this) {
			@Override
			public void onSuccess(MessageEntity data) {

				tv_titel.setText(data.getTitle());
				tv_time.setText(data.getCreate_at());
				tv_content.setText(data.getContent());
			}

			@Override
			public TypeToken<MessageEntity> getTypeToken() {
				return new TypeToken<MessageEntity>() {
				};
			}
		});
	}
	private void deleMsg(){
		API.receiverDeleteById(this, MyApplication.getInstance().getCustomerId(),Integer.valueOf(id), 
				new HttpCallback(this) {

			@Override
			public TypeToken getTypeToken() {
				return null;
			}

			@Override
			public void onSuccess(Object data) {
				Toast.makeText(getApplicationContext(), "删除成功",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("flag", "delete");
				intent.putExtra("id", id);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
	}
	private void initView() {
		tv_titel=(TextView) findViewById(R.id.msg_title);
		tv_time=(TextView) findViewById(R.id.msg_time);
		tv_content=(TextView) findViewById(R.id.msg_conten);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("flag", "read");
				intent.putExtra("id", id);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		search=(ImageView) findViewById(R.id.search);
		search.setVisibility(View.VISIBLE);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Dialog dialog = new DialogUtil(MymsgDetail.this,
						"确认删除？").getCheck(new CallBackChange() {

							@Override
							public void change() {
								deleMsg();
							}

						});
				dialog.show();
			}
		});
		
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_text_title = (TextView)findViewById(R.id.titleback_text_title);
		tv_back = (TextView) findViewById(R.id.tv_back);
		titleback_text_title.setText("消息详情");
		tv_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("flag", "read");
				intent.putExtra("id", id);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});

		titleback_linear_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("flag", "read");
				intent.putExtra("id", id);
				setResult(Activity.RESULT_OK, intent);
				finish();
			}
		});
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.putExtra("flag", "read");
			intent.putExtra("id", id);
			setResult(Activity.RESULT_OK, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
