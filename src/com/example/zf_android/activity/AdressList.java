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
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.AdressAdapter;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AdressList extends BaseActivity  {
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private String Url=Config.ChooseAdress;
	private RelativeLayout mune_rl;
	private boolean onRefresh_number = true;
	private AdressAdapter myAdapter;
	private ListView lv;
	private ImageView search,img_add;
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 
				
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
			case 2:  
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
			setContentView(R.layout.adress_list);
			initView();
			getData();
		}

		private void initView() {
			// TODO Auto-generated method stub
			search=(ImageView) findViewById(R.id.search);
			img_add=(ImageView) findViewById(R.id.img_add);
			img_add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i =new Intent(AdressList.this,AdressEdit.class);
					startActivity(i);
					
				}
			});
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			 
			new TitleMenuUtil(AdressList.this, "地址管理").show();
			myAdapter=new AdressAdapter(AdressList.this, myList);
//			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
//			Xlistview=(XListView) findViewById(R.id.x_listview);
//			// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
//			Xlistview.setPullLoadEnable(true);
//			Xlistview.setXListViewListener(this);
//			Xlistview.setDivider(null);
//			Xlistview.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> parent, View view,
//						int position, long id) {
// 
//				}
//			});
//			Xlistview.setAdapter(myAdapter);
			lv=(ListView) findViewById(R.id.lv);
			lv.setAdapter(myAdapter);
			
			search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MyApplication.getIsSelect()){
					 				 
						MyApplication.setIsSelect(false);
						myAdapter.notifyDataSetChanged();
						mune_rl.setVisibility(View.GONE);
//						for(int i=0;i<myList.size();i++){
//							 
//							if(myList.get(i).getIscheck()){
//								System.out.println("��---"+i+"����ѡ��");
//							}
						//}
					}else{
						mune_rl.setVisibility(View.VISIBLE);
						MyApplication.setIsSelect(true);
						myAdapter.notifyDataSetChanged();
					}
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
					.get(Url, new AsyncHttpResponseHandler() {

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
