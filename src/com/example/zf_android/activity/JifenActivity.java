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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.examlpe.zf_android.util.XListView;
import com.examlpe.zf_android.util.XListView.IXListViewListener;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.JifenEntity;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.AdressAdapter;
import com.example.zf_zandroid.adapter.JifenAdapter;
import com.example.zf_zandroid.adapter.MerchanAdapter;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class JifenActivity extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private RelativeLayout mune_rl; 
	private boolean onRefresh_number = true;
	private JifenAdapter myAdapter;
	private JifenEntity  jjjff;
	private TextView next_sure,tv_jf;
	private int customerId=80;
	 private int price;
	List<JifenEntity>  myList = new ArrayList<JifenEntity>();
	List<JifenEntity>  moreList = new ArrayList<JifenEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );
				
				if(myList.size()==0){
				//	norecord_text_to.setText("��û����ص���Ʒ");
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true; 
			 	myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
			 
				break;
			case 2: // ����������
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
			setContentView(R.layout.act_jifen);
			initView();
			getData();
			getIntegralTotal();
		}
		private void getIntegralTotal() {
			// TODO Auto-generated method stub
	 
			String url = "http://114.215.149.242:18080/ZFMerchant/api/customers/getIntegralTotal/"+MyApplication.currentUser.getId();
	 
			MyApplication.getInstance().getClient()
					.post(url, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("print", responseMsg);

						 
							 
							Gson gson = new Gson();
							
							JSONObject jsonobject = null;
							String code = null;
							try {
								jsonobject = new JSONObject(responseMsg);
								code = jsonobject.getString("code");
								int a =jsonobject.getInt("code");
								if(a==Config.CODE){  
									String res =jsonobject.getString("result");
	 								jsonobject = new JSONObject(res);
	 								
									jjjff	 = gson.fromJson(jsonobject.toString(), new TypeToken< JifenEntity >() {
					 					}.getType());
									price=jjjff.getMoneyTotal()/100;
									tv_jf.setText( price+"");
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
		private void initView() {
			// TODO Auto-generated method stub
			tv_jf=(TextView) findViewById(R.id.tv_jf);
			next_sure=(TextView) findViewById(R.id.next_sure);
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			next_sure.setText("兑换");
			next_sure.setVisibility(View.VISIBLE);
			new TitleMenuUtil(JifenActivity.this, "我的积分").show();
			myAdapter=new JifenAdapter(JifenActivity.this, myList);
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
			Xlistview=(XListView) findViewById(R.id.x_listview);
			// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
			Xlistview.setPullLoadEnable(true);
			Xlistview.setXListViewListener(this);
			Xlistview.setDivider(null);

			Xlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
			 
				}
			});
			Xlistview.setAdapter(myAdapter);
			
		 
			Xlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					System.out.println("```onItemClick``");
 
				}
				
			});
			next_sure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent i =new Intent(JifenActivity.this,Exchange.class);
							i.putExtra("price", price);
				 startActivity(i );
				 
				}
			});
			 
		}

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			page = 1;
			 System.out.println("onRefresh1");
			myList.clear();
			 System.out.println("onRefresh2");
			getData();
		}


		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			if (onRefresh_number) {
				page = page+1;
				
			 
				
				if (Tools.isConnect(getApplicationContext())) {
					onRefresh_number = false;
					getData();
				} else {
					onRefresh_number = true;
					handler.sendEmptyMessage(2);
				}
			}
			else {
				handler.sendEmptyMessage(3);
			}
		}
		private void onLoad() {
			Xlistview.stopRefresh();
			Xlistview.stopLoadMore();
			Xlistview.setRefreshTime(Tools.getHourAndMin());
		}

		public void buttonClick() {
			page = 1;
			myList.clear();
			getData();
		}
		/*
		 * �������
		 */
		private void getData() {
			// TODO Auto-generated method stub

			// TODO Auto-generated method stub
			String url = "http://114.215.149.242:18080/ZFMerchant/api/customers/getIntegralList/";
			RequestParams params = new RequestParams();
			 
			url=url+customerId+"/"+page+"/"+rows;
			params.put("customerId", 8);
			params.put("page", page);
			params.put("rows", rows);
			params.setUseJsonStreamer(true);

			MyApplication.getInstance().getClient()
					.post(url,null, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("print", responseMsg);

						 
							 
							Gson gson = new Gson();
							
							JSONObject jsonobject = null;
							String code = null;
							try {
								jsonobject = new JSONObject(responseMsg);
								code = jsonobject.getString("code");
								int a =jsonobject.getInt("code");
								if(a==Config.CODE){  
 								String res =jsonobject.getString("result");
 								jsonobject = new JSONObject(res);
 								
									moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<JifenEntity>>() {
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
