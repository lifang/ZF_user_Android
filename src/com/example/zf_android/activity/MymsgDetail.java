package com.example.zf_android.activity;

 
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

 
import com.examlpe.zf_android.util.DialogUtil;
import com.examlpe.zf_android.util.DialogUtil.CallBackChange;
import com.examlpe.zf_android.util.TitleMenuUtil;
 
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
*    
* 类名称：SystemDetail   
* 类描述：   消息详情
* 创建人： ljp 
* 创建时间：2015-2-6 下午3:32:14   
* @version    
*
 */
public class MymsgDetail extends BaseActivity{
	private TextView tv_titel,tv_time,tv_content,msg_title;
	private String id;
	private String url;
	private ImageView search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_detail);
		new TitleMenuUtil(MymsgDetail.this, "消息详情");
		id=getIntent().getStringExtra("id");
		System.out.println("````"+id);
		initView();
		getData();
	}
	private void getData() {
		// TODO Auto-generated method stub
	 
			// TODO Auto-generated method stub

			RequestParams params = new RequestParams();

		 	params.put("customer_id",80 );
			params.put("id", 1 );
			System.out.println("-onSuccess--id-");
			params.setUseJsonStreamer(true);
			AsyncHttpClient cl =new AsyncHttpClient();
			cl.post(Config.getMSGById, params, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							System.out.println("-onSuccess---");
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("LJP", responseMsg);
							Gson gson = new Gson();
							JSONObject jsonobject = null;
							int code = 0;
							try {
								jsonobject = new JSONObject(responseMsg);
								 
								 
								code = jsonobject.getInt("code");
								
								if(code==-2){
								 
								}else if(code==1){
									
									String res =jsonobject.getString("result");
									System.out.println("`res``"+res);
									jsonobject = new JSONObject(res);
							 
									
									MessageEntity a  = gson.fromJson(res ,
 									new TypeToken <MessageEntity> () {
 									}.getType());
									tv_titel.setText(a.getTitle());
									tv_time.setText(a.getCreate_at());
									tv_content.setText(a.getContent());
					 	 
									
									
								}else{
									Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							error.printStackTrace();
						}
					});
			 
		 
	}
	private void initView() {
		// TODO Auto-generated method stub
		tv_titel=(TextView) findViewById(R.id.msg_title);
		tv_time=(TextView) findViewById(R.id.msg_time);
		tv_content=(TextView) findViewById(R.id.msg_conten);
		search=(ImageView) findViewById(R.id.search);
		search.setVisibility(View.VISIBLE);
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//执行删除操作
				Dialog ddd = new DialogUtil(MymsgDetail.this,
						"Mark all as read").getCheck(new CallBackChange() {

					@Override
					public void change() {
						// TODO Auto-generated method stub
						// Toast.makeText(getApplication(), "DOOOO",
						// 1000).show();
					 
					}

				});
				ddd.show();
			}
		});
	}
	
}
