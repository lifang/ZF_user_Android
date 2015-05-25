package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.entity.GoodImgUrlEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.GoodDesAdapter;
import com.google.gson.reflect.TypeToken;



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
