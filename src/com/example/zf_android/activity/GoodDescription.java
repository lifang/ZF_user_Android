package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.GoodCommentEntity;
import com.example.zf_android.entity.GoodImgUrlEntity;
import com.example.zf_android.entity.MyinfoEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.GoodCommentAdapter;
import com.example.zf_zandroid.adapter.GoodDesAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;



public class GoodDescription extends BaseActivity{
	private ListView listview;
	private LinearLayout eva_nodata;
	private List<GoodImgUrlEntity> myList = new ArrayList<GoodImgUrlEntity>(); 
	private GoodDesAdapter myAdapter;
	private int goodId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_good_description);
		new TitleMenuUtil(GoodDescription.this, "图文详情").show();
		goodId = getIntent().getIntExtra("goodId", 0);
		
		initView();
		getData();
	}
	private void initView() {

		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		listview=(ListView) findViewById(R.id.listview);
		
		myAdapter=new GoodDesAdapter(GoodDescription.this, myList);
		listview.setAdapter(myAdapter);
	
	}
	private void getData() {
		API.getGoodImgUrl(GoodDescription.this,goodId,
				new HttpCallback<List<GoodImgUrlEntity>> (GoodDescription.this) {

			@Override
			public void onSuccess(List<GoodImgUrlEntity> data) {
				
				if (data.size() != 0) {
					myList = data;
				}
				
				if(data.size()==0){
					listview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					listview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				myAdapter=new GoodDesAdapter(GoodDescription.this, myList);
				listview.setAdapter(myAdapter);
				//myAdapter.notifyDataSetChanged();
			}
			@Override
			public TypeToken<List<GoodImgUrlEntity>> getTypeToken() {
				return  new TypeToken<List<GoodImgUrlEntity>>() {
				};
			};
		});
	}

}
