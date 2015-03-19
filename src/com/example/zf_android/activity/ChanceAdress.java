package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_zandroid.adapter.ChooseAdressAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
*    
* 类名称：ChanceAdress   
* 类描述：   选择地址
* 创建人： ljp 
* 创建时间：2015-3-9 下午3:13:17   
* @version    
*
 */
public class ChanceAdress extends BaseActivity{
	private ScrollViewWithListView   lv;
	private String Url=Config.ChooseAdress;
	private int customerId;
	private ChooseAdressAdapter myAdapter;
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
 
			 	myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
			 
				break;
			case 2: // 
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),  " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chance_adress);
		 new TitleMenuUtil(ChanceAdress.this, "选择地址").show();
		initView();
		getData();
	}
	private void initView() {
		// TODO Auto-generated method stub
		lv=(ScrollViewWithListView) findViewById(R.id.lv);
		myAdapter=new ChooseAdressAdapter(ChanceAdress.this, myList);
		lv.setAdapter(myAdapter);
		lv.setOnItemClickListener( new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.putExtra("id", myList.get(arg2).getId());
				intent2.putExtra("tel", myList.get(arg2).getMoblephone());
				intent2.putExtra("adree", myList.get(arg2).getAddress());
				intent2.putExtra("name", myList.get(arg2).getReceiver());
				setResult(11, intent2);
				finish();
			}
		});
		findViewById(R.id.adresslist).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i =new Intent(ChanceAdress.this,AdressList.class);
				startActivity(i);
			}
		});
	}
	private void getData() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
	 
		RequestParams params = new RequestParams();
		params.put("customerId", 80);
		 
		params.setUseJsonStreamer(true);
		System.out.println("---getData-");
		Url=Url+"80";
		MyApplication.getInstance().getClient()
				.post(Url, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("print", responseMsg);
						System.out.println("----"+responseMsg);
					 
						 
						Gson gson = new Gson();
						
						JSONObject jsonobject = null;
						String code = null;
						try {
							jsonobject = new JSONObject(responseMsg);
							code = jsonobject.getString("code");
							int a =jsonobject.getInt("code");
							if(a==Config.CODE){  
 								String res =jsonobject.getString("result");
//								jsonobject = new JSONObject(res);
								
 								moreList= gson.fromJson(res, new TypeToken<List<AdressEntity>>() {
 			 					}.getType());
			 				 
 							myList.addAll(moreList);
 				 				handler.sendEmptyMessage(0);
			 					  
			 				 
			 			 
							}else{
								code = jsonobject.getString("message");
								Toast.makeText(getApplicationContext(), code, 1000).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							 ;	
							e.printStackTrace();
							
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						// TODO Auto-generated method stub
						System.out.println("-onFailure---");
						Log.e("print", "-onFailure---" + error);
					}
				});
 
		 
	
	}
}
