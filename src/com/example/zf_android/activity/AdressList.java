package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.AdressAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AdressList extends BaseActivity  {
	private View bottomView;
	private LinearLayout eva_nodata;
	private RelativeLayout mune_rl;
	private AdressAdapter myAdapter;
	private ListView lv;
	private Integer ids[]=new Integer []{};
	private int customerId;
	List<Integer> as = new ArrayList<Integer>();
	private ImageView search,img_add;
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(myList.size()==0){
					lv.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				} else{
					lv.setVisibility(View.VISIBLE);
				}
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
		customerId = MyApplication.getInstance().getCustomerId();
		initView();
		MyApplication.setIsSelect(false);
		getData();
	}

	private void initView() {
		search=(ImageView) findViewById(R.id.search);
		img_add=(ImageView) findViewById(R.id.img_add);
		bottomView = findViewById(R.id.bottomView);
		img_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
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
		lv.setVisibility(View.VISIBLE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
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
				if(MyApplication.getIsSelect()){
					MyApplication.setIsSelect(false);
					myAdapter.notifyDataSetChanged();
					mune_rl.setVisibility(View.GONE);
					bottomView.setVisibility(View.GONE);

				}else{
					mune_rl.setVisibility(View.VISIBLE);
					bottomView.setVisibility(View.VISIBLE);
					MyApplication.setIsSelect(true);
					myAdapter.notifyDataSetChanged();
				}
			}
		});
		findViewById(R.id.tv_delete).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ids=new Integer[myList.size()];
				for (int i = 0; i < myList.size(); i++) {
					if(myList.get(i).getIscheck()){
						ids[i]=myList.get(i).getId();
						as.add( myList.get(i).getId() );
					}
				}
				Gson gson = new Gson();
				try {
					API.deleteAddress(AdressList.this,new JSONArray(gson.toJson(as)),
							new HttpCallback(AdressList.this) {
						@Override
						public void onSuccess(Object data) {
							moreList.clear();
							for (int i = 0; i < myList.size(); i++) {
								if (!myList.get(i).getIscheck()) {
									moreList.add(myList.get(i));
								}
							}
							myList.clear();

							myList.addAll(moreList);
							myAdapter.notifyDataSetChanged();
						}
						@Override
						public TypeToken getTypeToken() {
							return null;
						}

					});
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void getData() { 
		myList.clear();
		API.getAddressList(this,customerId,
				new HttpCallback<List<AdressEntity>> (this) {

			@Override
			public void onSuccess(List<AdressEntity> data) {
				moreList.clear();
				moreList= data;

				if (null != data) {
					myList.addAll(data);
				}
				handler.sendEmptyMessage(0);
			};
			@Override
			public TypeToken<List<AdressEntity>> getTypeToken() {
				return new TypeToken<List<AdressEntity>>() {
				};
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getData();
	}
}
