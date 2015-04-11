package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.JifenEntity;
import com.example.zf_android.entity.JifenTotalEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Page;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.JifenAdapter;
import com.google.gson.reflect.TypeToken;

public class JifenActivity extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private JifenAdapter myAdapter;
	private TextView next_sure,tv_jf;
	private String price,jf;
	private int total = 0;
	List<JifenEntity>  myList = new ArrayList<JifenEntity>();
	List<JifenEntity>  moreList = new ArrayList<JifenEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );

				if(myList.size()==0){
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_jifen);
		initView();
		getData();
		getIntegralTotal();
	}
	//我的积分总计
	private void getIntegralTotal() {
		API.getIntegralTotal(this,MyApplication.getInstance().getCustomerId(),
				new HttpCallback<JifenTotalEntity> (this) {

			@Override
			public void onSuccess(JifenTotalEntity data) {
				if (StringUtil.isNull(data.getMoneyTotal())) 
					price= "0";
				else 
					price= data.getMoneyTotal();
				
				if (StringUtil.isNull(data.getQuantityTotal())) 
					jf= "0";
				else 
					jf= data.getQuantityTotal();

				tv_jf.setText(jf+"");
			};
			@Override
			public TypeToken<JifenTotalEntity> getTypeToken() {
				return new TypeToken<JifenTotalEntity>() {
				};
			}
		});

	}
	private void initView() {
		tv_jf=(TextView) findViewById(R.id.tv_jf);
		next_sure=(TextView) findViewById(R.id.next_sure);
		next_sure.setText("兑换");
		next_sure.setVisibility(View.VISIBLE);
		new TitleMenuUtil(JifenActivity.this, "我的积分").show();
		myAdapter=new JifenAdapter(JifenActivity.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview=(XListView) findViewById(R.id.x_listview);
		
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setAdapter(myAdapter);

		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i =new Intent(JifenActivity.this,Exchange.class);
				i.putExtra("price", price);
				i.putExtra("point", jf);
				startActivity(i );
			}
		});
	}

	@Override
	public void onRefresh() {
		page = 1;
		Xlistview.setPullLoadEnable(true);
		myList.clear();
		getData();
	}


	@Override
	public void onLoadMore() {
		if (myList.size() >= total) {
			Xlistview.setPullLoadEnable(false);
			Xlistview.stopLoadMore();
			CommonUtil.toastShort(this, "no more data");
		} else {
			getData();
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
	//我的积分列表
	private void getData() {
		API.getIntegralList(this,MyApplication.getInstance().getCustomerId(),page,rows,
				new HttpCallback<Page<JifenEntity>> (this) {

			@Override
			public void onSuccess(Page<JifenEntity> data) {
				
				if (null != data.getList()) {
					myList.addAll(data.getList());
				}
				page++;
				total = data.getTotal();
				handler.sendEmptyMessage(0);
			};
			@Override
			public TypeToken<Page<JifenEntity>> getTypeToken() {
				return new TypeToken<Page<JifenEntity>>() {
				};
			}
		});
	}
}
