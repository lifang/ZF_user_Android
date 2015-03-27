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
import android.widget.LinearLayout;
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
import com.example.zf_android.entity.MessageEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.example.zf_zandroid.adapter.OrderAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***
 * 
 * 
 * � 我的消息
 * 
 * @version
 * 
 */
public class MyMessage extends BaseActivity implements IXListViewListener,
		OnClickListener {
	private XListView Xlistview;
	private int page = 1;
	private RelativeLayout rl_sy, main_rl_gwc, rl_xx, rl_wd;
	private int rows = Config.ROWS;
	private String Url = Config.getmes;
	private LinearLayout eva_nodata;
	private String ids[]=new String []{};
	List<Integer> as = new ArrayList<Integer>();
	JSONArray a;
	private boolean onRefresh_number = true;
	private MessageAdapter myAdapter;
	private TextView next_sure,tv_all,tv_dle;
	private Boolean isEdit = false;
	private RelativeLayout rl_edit, rl_editno;
	List<MessageEntity> idList = new ArrayList<MessageEntity>();
	List<MessageEntity> myList = new ArrayList<MessageEntity>();
	List<MessageEntity> moreList = new ArrayList<MessageEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();

				if (myList.size() == 0) {
					// norecord_text_to.setText("��û����ص���Ʒ");
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
				Toast.makeText(getApplicationContext(),
						"no 3g or wifi content", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_message);
		MyApplication.setIsSelect(false);
		initView();
		getData();
	 
	}

	private void initView() {
		// TODO Auto-generated method stub
		rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);
		rl_editno = (RelativeLayout) findViewById(R.id.rl_editno);
		tv_all=(TextView) findViewById(R.id.tv_all);
		tv_dle=(TextView) findViewById(R.id.tv_dle);
		tv_all.setOnClickListener(this);
		tv_dle.setOnClickListener(this);
		rl_wd = (RelativeLayout) findViewById(R.id.main_rl_my);
		rl_wd.setOnClickListener(this);
		main_rl_gwc = (RelativeLayout) findViewById(R.id.main_rl_gwc);
		main_rl_gwc.setOnClickListener(this);
		rl_sy = (RelativeLayout) findViewById(R.id.main_rl_sy);
		rl_sy.setOnClickListener(this);

		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setText("删除");
		new TitleMenuUtil(MyMessage.this, "我的消息").show();
		myAdapter = new MessageAdapter(MyMessage.this, myList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				  Intent i = new Intent(MyMessage.this, MymsgDetail.class);
				  i.putExtra("id", myList.get(position-1).getId()+"");
				  startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);

		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (MyApplication.getIsSelect()) {
					// ��������ɾ�����
					next_sure.setText("删除");
					MyApplication.setIsSelect(false);
					myAdapter.notifyDataSetChanged();
				 
					
					rl_editno.setVisibility(View.VISIBLE);
					rl_edit.setVisibility(View.GONE);
		 
				} else {
					next_sure.setText("取消");
					MyApplication.setIsSelect(true);
					rl_edit.setVisibility(View.VISIBLE);
					rl_editno.setVisibility(View.GONE);
					myAdapter.notifyDataSetChanged();
				}
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
			page = page + 1;

			onRefresh_number = false;
			getData();

			// if (Tools.isConnect(getApplicationContext())) {
			// onRefresh_number = false;
			// getData();
			// } else {
			// onRefresh_number = true;
			// handler.sendEmptyMessage(2);
			// }
		} else {
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

		RequestParams params = new RequestParams();

		params.put("customer_id", 80);
		params.put("page", page);
		params.put("pageSize", rows);
		params.setUseJsonStreamer(true);
		
		 
		MyApplication.getInstance().getClient()
				.post(Url, params, new AsyncHttpResponseHandler() {

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
								moreList.clear();
								
				 				moreList = gson.fromJson(jsonobject.getString("content") ,
									new TypeToken<List<MessageEntity>>() {
									}.getType());
				 					 	
				 						if (moreList.size()==0) {
				 							Toast.makeText(getApplicationContext(),
				 									"暂时还没有您的消息", Toast.LENGTH_SHORT).show();
				 							Xlistview.getmFooterView().setState2(2);
				 					 
				 						} 
				 						 
				 				myList.addAll(moreList);
				 				handler.sendEmptyMessage(0);
		 
								
								
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		
		case R.id.tv_all: 
			for (int i = 0; i < myList.size(); i++) {

				if (myList.get(i).getIscheck()) {
					idList.add(myList.get(i));
				}
			}
			
 
		  	 Msgdelete();
			break;
		case R.id.tv_dle: 
			 
			for (int i = 0; i < myList.size(); i++) {

				if (myList.get(i).getIscheck()) {
					idList.add(myList.get(i));
				}
			}
			
 
		  	 Msgdelete1();
			
			
			
			
			break;
		
		case R.id.main_rl_gwc: 
			startActivity(new Intent(MyMessage.this, ShopCar.class));
			finish();
			break;
		case R.id.main_rl_my: 
			startActivity(new Intent(MyMessage.this, MenuMine.class));
			finish();
			break;
		case R.id.main_rl_sy:  
			startActivity(new Intent(MyMessage.this, Main.class));
			finish();
			break;
		default:
			break;
		}
	}

	private void Msgdelete() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		ids=new String[idList.size()];
		for (int i = 0; i < idList.size(); i++) {
			ids[i]=idList.get(i).getId();
			 
		
		}
		Gson gson = new Gson();
	  
		params.put("customer_id", MyApplication.currentUser.getId());
		try {
			params.put("ids", new JSONArray(gson.toJson(ids)));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

  
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient()
				.post(Config.batchRead, params, new AsyncHttpResponseHandler() {

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
							
							if(code==-2){
							 
							}else if(code==1){
								
								 
								
								
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
	private void Msgdelete1() {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		ids=new String[idList.size()];
		for (int i = 0; i < idList.size(); i++) {
			ids[i]=idList.get(i).getId();
			 
		
		}
		Gson gson = new Gson();
	  
		params.put("customer_id", MyApplication.currentUser.getId());
		try {
			params.put("ids", new JSONArray(gson.toJson(ids)));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

  
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient()
				.post(Config.batchDelete, params, new AsyncHttpResponseHandler() {

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
									}
								}
							//	myAdapter.notify();
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
}
