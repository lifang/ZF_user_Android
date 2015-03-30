package com.example.zf_android.activity;

import java.text.MessageFormat;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.examlpe.zf_android.util.XListView;
import com.examlpe.zf_android.util.XListView.IXListViewListener;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_zandroid.adapter.MerchanAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MerchantList extends BaseActivity implements  IXListViewListener{
	private XListView xListview;
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
				onLoad();
				if(myList.size()==0){
					xListview.setVisibility(View.GONE);
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
			super.onCreate(savedInstanceState);
			setContentView(R.layout.adress_list);
			initView();
			MyApplication.setIsSelect(false);
			getData();
		}

		private void initView() {
			search=(ImageView) findViewById(R.id.search);
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			img_add=(ImageView) findViewById(R.id.img_add);
			myAdapter=new MerchanAdapter(MerchantList.this, myList);
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
			xListview=(XListView) findViewById(R.id.x_listview);
			xListview.setVisibility(View.VISIBLE);
			xListview.setPullLoadEnable(true);
			xListview.setXListViewListener(this);
			xListview.setDivider(null);
			tv_delete=(TextView) findViewById(R.id.tv_delete);
			xListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
				}
			});
			xListview.setAdapter(myAdapter);
		}

		@Override
		public void onRefresh() {
			page = 1;
			myList.clear();
			getData();
		}


		@Override
		public void onLoadMore() {
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
			xListview.stopRefresh();
			xListview.stopLoadMore();
			xListview.setRefreshTime(Tools.getHourAndMin());
		}

		public void buttonClick() {
			page = 1;
			myList.clear();
			getData();
		}
		private void getData() {

			String url = MessageFormat.format(Config.URL_MERCHANT_LIST, customerId, page, rows);
			MyApplication.getInstance().getClient()
					.post(url, new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String responseMsg = new String(responseBody)
									.toString();
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
									moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<MerchantEntity>>() {
				 					}.getType());
									myList.addAll(moreList);
					 				handler.sendEmptyMessage(0);
								}else{
									code = jsonobject.getString("message");
									Toast.makeText(getApplicationContext(), code, Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							Log.e("print", "-onFailure---" + error);
						}
					});
	 
			 
		
		 
		 
		}
}
