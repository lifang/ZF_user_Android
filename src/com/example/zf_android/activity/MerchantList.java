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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Page;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.MerchanAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MerchantList extends BaseActivity implements  IXListViewListener{
	private static final int CODE_EDIT = 1;
	private static final int CODE_CREATE = 2;

	private XListView xListview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private RelativeLayout mune_rl; 
	private MerchanAdapter myAdapter;
	private ImageView search,img_add;
	private int ids[]=new int []{};
	private TextView tv_delete;
	private Integer customerId;
	private int total = 0;
	List<MerchantEntity>  myList = new ArrayList<MerchantEntity>();
	List<MerchantEntity> idList = new ArrayList<MerchantEntity>();
	List<MerchantEntity>  moreList = new ArrayList<MerchantEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				xListview.setVisibility(View.GONE);
				if(myList.size()==0){
					eva_nodata.setVisibility(View.VISIBLE);
				} else{
					myAdapter.notifyDataSetChanged();
					xListview.setVisibility(View.VISIBLE);
				}
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
		new TitleMenuUtil(MerchantList.this, "我的商户").show();
		search=(ImageView) findViewById(R.id.search);
		mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
		img_add=(ImageView) findViewById(R.id.img_add);
		myAdapter=new MerchanAdapter(MerchantList.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		xListview=(XListView) findViewById(R.id.x_listview);
		xListview.setVisibility(View.VISIBLE);
		
		xListview.initHeaderAndFooter();
		xListview.setPullLoadEnable(true);
		xListview.setPullRefreshEnable(true);
		xListview.setXListViewListener(this);
		xListview.setDivider(null);
		tv_delete=(TextView) findViewById(R.id.tv_delete);
		xListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println("```onItemClick``");
				Intent i = new Intent(MerchantList.this, MerchantEdit.class);
				i.putExtra("ID", myList.get(position-1).getId());
				i.putExtra("name", myList.get(position-1).getTitle()+"");
				startActivityForResult(i, CODE_EDIT);
			}

		});
		xListview.setAdapter(myAdapter);

		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
		img_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(MerchantList.this, CreatMerchant.class);
				startActivityForResult(i, CODE_CREATE);

			}
		});
		tv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				for (int i = 0; i < myList.size(); i++) {
					if (myList.get(i).getIscheck()) {
						idList.add(myList.get(i));
					}
				}
				ids=new int[idList.size()];
				for (int i = 0; i < idList.size(); i++) {
					ids[i]=idList.get(i).getId();
				}
				Gson gson = new Gson();
				try {
					API.merchantDelete(MerchantList.this,new JSONArray(gson.toJson(ids)),
							new HttpCallback(MerchantList.this) {
						@Override
						public void onSuccess(Object data) {
							moreList.clear();
							for (int i = 0; i < myList.size(); i++) {
								if (myList.get(i).getIscheck()) {
									moreList.add( myList.get(i));
								}
							}
							myList.removeAll(moreList);
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

	@Override
	public void onRefresh() {
		page = 1;
		xListview.setPullLoadEnable(true);
		myList.clear();
		getData();		
	}

	@Override
	public void onLoadMore() {
		if (myList.size() >= total) {
			xListview.setPullLoadEnable(false);
			xListview.stopLoadMore();
			CommonUtil.toastShort(this, "no more data");
		} else {
			getData();
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
		API.merchantGetList(this,customerId,page,rows,
				new HttpCallback<Page<MerchantEntity>> (this) {

			@Override
			public void onSuccess(Page<MerchantEntity> data) {

				if (null != data.getList()) {
					myList.addAll(data.getList());
				}
				page++;
				total = data.getTotal();
				handler.sendEmptyMessage(0);
			};
			@Override
			public TypeToken<Page<MerchantEntity>> getTypeToken() {
				return new TypeToken<Page<MerchantEntity>>() {
				};
			}
		});

	}
	@Override
	protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode!=RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case CODE_EDIT:
		case CODE_CREATE:
			boolean needFresh = data.getBooleanExtra("needFresh", false);
			if(needFresh){
				page = 1;
				xListview.setPullLoadEnable(true);
				myList.clear();
				getData();
			}
			break;

		default:
			break;
		}

	}
}
