package com.example.zf_android.activity;

 
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.entity.MessageEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 
*    
* ����ƣ�SystemDetail   
* ��������   ϵͳ����
* �����ˣ� ljp 
* ����ʱ�䣺2015-2-6 ����3:32:14   
* @version    
*
 */
public class SystemDetail extends BaseActivity{
	private TextView tv_titel,tv_time,tv_content;
	private int id;
	private String url;
	private ImageView search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_detail);
		new TitleMenuUtil(SystemDetail.this, "公告详情").show();
		id=getIntent().getIntExtra("id", 0);
		initView();
		getData();
	}
	private void getData() {
 

//			RequestParams params = new RequestParams();
//
//			System.out.println("````"+id);
//			params.put("id", id );
//		 
//			params.setUseJsonStreamer(true);
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			JSONObject jsonParams = new JSONObject(params);
			HttpEntity entity;
			try {
				entity = new StringEntity(jsonParams.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return;
			}
			
			
			AsyncHttpClient cl =new AsyncHttpClient();
			//Config.getMSGById Config.webMSGById
			cl.post(getApplicationContext(),Config.webMSGById, null,entity,"application/json", new AsyncHttpResponseHandler() {

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
		 
	}
	
}
