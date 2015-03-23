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
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.entity.MessageEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.AdressAdapter;
import com.example.zf_zandroid.adapter.MerchanAdapter;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MerchantList extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private RelativeLayout mune_rl; 
	private boolean onRefresh_number = true;
	private MerchanAdapter myAdapter;
	private ImageView search,img_add;
	private ListView lv;
	private int ids[]=new int []{};
	private TextView tv_delete;
	private int customerId=80;
	List<MerchantEntity>  myList = new ArrayList<MerchantEntity>();
	List<MerchantEntity> idList = new ArrayList<MerchantEntity>();
	List<MerchantEntity>  moreList = new ArrayList<MerchantEntity>();
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
			setContentView(R.layout.adress_list);
			initView();
			getData();
		}

		private void initView() {
			// TODO Auto-generated method stub
			search=(ImageView) findViewById(R.id.search);
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			img_add=(ImageView) findViewById(R.id.img_add);
			new TitleMenuUtil(MerchantList.this, "我的商户").show();
			myAdapter=new MerchanAdapter(MerchantList.this, myList);
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
			Xlistview=(XListView) findViewById(R.id.x_listview);
			// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
			Xlistview.setPullLoadEnable(true);
			Xlistview.setXListViewListener(this);
			Xlistview.setDivider(null);
			tv_delete=(TextView) findViewById(R.id.tv_delete);
			Xlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
			 
				}
			});
			Xlistview.setAdapter(myAdapter);
			
			lv=(ListView) findViewById(R.id.lv);
			lv.setAdapter(myAdapter);
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					System.out.println("```onItemClick``");
					Intent i = new Intent(MerchantList.this, MerchantEdit.class);
					i.putExtra("ID", myList.get(position).getId());
					i.putExtra("name", myList.get(position).getTitle()+"");
					startActivity(i);
				}
				
			});
			search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MyApplication.getIsSelect()){
						//��������ɾ�����					 
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
			img_add.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(MerchantList.this, CreatMerchant.class);
					 
					startActivity(i);
				}
			});
			tv_delete.setOnClickListener(new OnClickListener() {
				String UUU="http://114.215.149.242:18080/ZFMerchant/api/merchant/delete";
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					for (int i = 0; i < myList.size(); i++) {

						if (myList.get(i).getIscheck()) {
							idList.add(myList.get(i));
						}
					}
					
					
					
					RequestParams params = new RequestParams();
					ids=new int[idList.size()];
					for (int i = 0; i < idList.size(); i++) {
						ids[i]=idList.get(i).getId();
						 
					
					}
					Gson gson = new Gson();
				  
				//	params.put("customer_id", MyApplication.currentUser.getId());
					try {
						params.put("ids", new JSONArray(gson.toJson(ids)));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

			  
					params.setUseJsonStreamer(true);
					MyApplication.getInstance().getClient()
							.post(UUU, params, new AsyncHttpResponseHandler() {

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
											for (int i = 0; i < myList.size(); i++) {

												if (myList.get(i).getIscheck()) {
													 
													myList.remove(i);
													 
													System.out
															.println("删除"+i);
													
												}
												myList.get(i).setIscheck(false);
											}
									 
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
			String url = "http://114.215.149.242:18080/ZFMerchant/api/merchant/getList/";
		//	RequestParams params = new RequestParams();
			 
			url=url+customerId+"/"+page+"/"+rows;
			//params.setUseJsonStreamer(true);

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
//									
									moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<MerchantEntity>>() {
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
