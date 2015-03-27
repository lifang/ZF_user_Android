package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
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
import com.loopj.android.http.AsyncHttpClient;
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
	private Integer ids[]=new Integer []{};
	private TextView tv_delete;
	List<Integer> as = new ArrayList<Integer>();
	private ImageView search,img_add;
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 
				
				if(myList.size()==0){
				//	norecord_text_to.setText("��û����ص���Ʒ");
					lv.setVisibility(View.GONE);
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
			MyApplication.setIsSelect(false);
			Url=Url+"80";
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
				 
					startActivityForResult(i, 1);
				}
			});
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			 
			new TitleMenuUtil(AdressList.this, "地址管理").show();
			myAdapter=new AdressAdapter(AdressList.this, myList);
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
 
			lv=(ListView) findViewById(R.id.lv);
			lv.setAdapter(myAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					Intent i =new Intent(getApplicationContext(), Adress4Edit.class);
					 
					i.putExtra("id", myList.get(arg2).getId());
					i.putExtra("cityId", myList.get(arg2).getCityId()+"");
					i.putExtra("receiver", myList.get(arg2).getReceiver());
					i.putExtra("moblephone", myList.get(arg2).getMoblephone());
					i.putExtra("zipCode", myList.get(arg2).getZipCode());
					i.putExtra("address", myList.get(arg2).getAddress());
					i.putExtra("isDefault", myList.get(arg2).getIsDefault());
					i.putExtra("cityname", myList.get(arg2).getCity_name());
					startActivityForResult(i, 2);
				}
			});
			search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MyApplication.getIsSelect()){
					 				 
						MyApplication.setIsSelect(false);
						myAdapter.notifyDataSetChanged();
						mune_rl.setVisibility(View.GONE);
 
					}else{
						mune_rl.setVisibility(View.VISIBLE);
						MyApplication.setIsSelect(true);
						myAdapter.notifyDataSetChanged();
					}
				}
			});
			 findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 
						// TODO Auto-generated method stub
						RequestParams params = new RequestParams();
						ids=new Integer[myList.size()];
						for (int i = 0; i < myList.size(); i++) {
							
							if(myList.get(i).getIscheck()){
								ids[i]=myList.get(i).getId();
								as.add( myList.get(i).getId() );
								 System.out.println("delete---"+i);
								 
							}
							
						 
						
						}
						Gson gson = new Gson();
					  
						 
						try {
							params.put("ids", new JSONArray(gson.toJson(as)));
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println("DELETE---"+params);
				  
						params.setUseJsonStreamer(true);
						MyApplication.getInstance().getClient()
								.post(Config.ADRESSDelete, params, new AsyncHttpResponseHandler() {

									@Override
									public void onSuccess(int statusCode, Header[] headers,
											byte[] responseBody) {
										System.out.println("-onSuccess---");
										String responseMsg = new String(responseBody)
												.toString();
										Log.e("LJP", responseMsg);
										 
										JSONObject jsonobject = null;
										int code = 0;
										try {
											jsonobject = new JSONObject(responseMsg);
											 
											 
											code = jsonobject.getInt("code");
											
											if(code==1){
												moreList.clear();
												for (int i = 0; i < myList.size(); i++) {
													 
													if (!myList.get(i).getIscheck()) {
													 	moreList.add(myList.get(i));
														System.out
																.println("删除···"+i);
													}
												}
											 	myList.clear();
											  
											 	System.out
												.println("moreList除···"+	moreList.size());
												 myList.addAll(moreList);
												myAdapter.notifyDataSetChanged();
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
			});
		}

 
		private void getData() { 
			 myList.clear();
  
			 AsyncHttpClient  a= new AsyncHttpClient();
			a.post(Url, new AsyncHttpResponseHandler() {

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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		System.out.println("onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult");
		getData();
	}
}
