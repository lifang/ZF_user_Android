package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.ScreenUtils;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.GoodImgUrlEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.GoodDesAdapter;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;



public class GoodDescription extends BaseActivity{
//	private ListView listview;
	private LinearLayout eva_nodata;
	private List<GoodImgUrlEntity> myList = new ArrayList<GoodImgUrlEntity>(); 
//	private GoodDesAdapter myAdapter;
	private int goodId;
	private LinearLayout picture_list;
	DisplayImageOptions options = MyApplication.getDisplayOption();
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
		picture_list=(LinearLayout) findViewById(R.id.picture_list);
//		listview=(ListView) findViewById(R.id.listview);

//		myAdapter=new GoodDesAdapter(GoodDescription.this, myList);
//		listview.setAdapter(myAdapter);

	}
	private void getData() {
		API.getGoodImgUrl(GoodDescription.this,goodId,
				new HttpCallback<List<GoodImgUrlEntity>> (GoodDescription.this) {

			@Override
			public void onSuccess(List<GoodImgUrlEntity> data) {

				for (int i = 0; i < data.size(); i++) {
					Log.e("", ""+data.get(i).getId());
				}
				if (data.size() != 0) {
					myList = data;
				}

				if(data.size()==0){
					picture_list.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					picture_list.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
//				myAdapter=new GoodDesAdapter(GoodDescription.this, myList);
//				listview.setAdapter(myAdapter);
				//myAdapter.notifyDataSetChanged();
				
		        int width = ScreenUtils.getScreenWidth(GoodDescription.this);
		        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width,
		                LinearLayout.LayoutParams.WRAP_CONTENT);
		        
		        for (GoodImgUrlEntity entity: data) {
		            View view = getLayoutInflater().inflate(R.layout.item_goodimgurls, null);
		            ImageView iv = (ImageView)view.findViewById(R.id.image);
		            iv.setLayoutParams(lp);
		            iv.setMaxWidth(width);
		            ImageLoader.getInstance().displayImage(entity.getUrlPath(),
		            		iv, options);
		            picture_list.addView(view);
		        }
				
			}
			@Override
			public TypeToken<List<GoodImgUrlEntity>> getTypeToken() {
				return  new TypeToken<List<GoodImgUrlEntity>>() {
				};
			};
		});
	}

}
