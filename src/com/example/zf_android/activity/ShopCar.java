package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.example.zf_android.entity.MyShopCar;
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.TradeFlowActivity;
import com.example.zf_zandroid.adapter.OrderAdapter;
import com.example.zf_zandroid.adapter.ShopcarAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***
 * 
 * 
 * 类名称：ShopCar 类描述： 购物车 创建人： ljp 创建时间：2015-2-9 上午11:30:10
 * 
 * @version
 * 
 */
public class ShopCar extends BaseActivity implements IXListViewListener,OnClickListener {
	private RelativeLayout rl_sy,rl_gw,rl_xx,rl_wd;
	private XListView Xlistview;
	private int page = 1;
	private int rows = Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private ShopcarAdapter myAdapter;
	private List<Good> myShopList=new ArrayList<Good>();
	List<TestEntitiy> moreList = new ArrayList<TestEntitiy>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				//
				// if (myShopList.size() == 0) {
				// // norecord_text_to.setText("您没有相关的商品");
				// Xlistview.setVisibility(View.GONE);
				// eva_nodata.setVisibility(View.VISIBLE);
				// }
				// onRefresh_number = true;
				// myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // 网络有问题
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

	// 个体参数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acv_shopcat);
		initView();
		getData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		
		rl_wd=(RelativeLayout) findViewById(R.id.main_rl_my);
		rl_wd.setOnClickListener(this);
		rl_xx=(RelativeLayout) findViewById(R.id.main_rl_pos1);
		rl_xx.setOnClickListener(this);
		rl_sy =(RelativeLayout) findViewById(R.id.main_rl_sy);
		rl_sy.setOnClickListener(this); 
		
		
		
		
		
		findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(ShopCar.this, ConfirmOrder.class);
				startActivity(i);
			}
		});

		myAdapter = new ShopcarAdapter(ShopCar.this, myShopList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("已经没有数据了");
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);
		Xlistview.getmFooterView().setState2(0);
		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// Intent i = new Intent(ShopCar.this, OrderDetail.class);
				// startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		System.out.println("onRefresh1");
		myShopList.clear();
		System.out.println("onRefresh2");
		getData();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
//		if (onRefresh_number) {
//			page = page + 1;
//
//			onRefresh_number = false;
//			getData();
//
//			// if (Tools.isConnect(getApplicationContext())) {
//			// onRefresh_number = false;
//			// getData();
//			// } else {
//			// onRefresh_number = true;
//			// handler.sendEmptyMessage(2);
//			// }
//		} else {
//			handler.sendEmptyMessage(3);
//		}
	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		page = 1;
		myShopList.clear();
		getData();
	}

	/*
	 * 请求数据
	 */
	private void getData() {
		// TODO Auto-generated method stub
		String url = "http://114.215.149.242:18080/ZFMerchant/api/cart/list";
		RequestParams params = new RequestParams("customerId", "80");
		params.setUseJsonStreamer(true);

		MyApplication.getInstance().getClient()
				.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("print", responseMsg);

						MyShopCar myShopCar = MyShopCar.getShopCar(responseMsg);
						if (myShopCar != null) {
							myShopList = myShopCar.getResult();
							if (myShopCar.getResult() != null
									&& myShopCar.getResult().size() != 0) {
								
								onRefresh_number = true;
								myAdapter = new ShopcarAdapter(ShopCar.this, myShopList);
								Xlistview.setAdapter(myAdapter);
								myAdapter.notifyDataSetChanged();
							}
						} else {
							Xlistview.setVisibility(View.GONE);
							eva_nodata.setVisibility(View.VISIBLE);
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
 
		handler.sendEmptyMessage(0);
	}

	@Override
	public void onClick(View v) { 
		switch (v.getId()) {

		case R.id.main_rl_pos1:  // 锟斤拷POS锟斤拷锟斤拷
			 startActivity(new Intent(ShopCar.this,MyMessage.class));
			 finish();
			break;
		case R.id.main_rl_my:  // 锟斤拷POS锟斤拷锟斤拷
			 startActivity(new Intent(ShopCar.this,MenuMine.class));
			 finish();
			break;
		case R.id.main_rl_sy:  // 锟斤拷POS锟斤拷锟斤拷
			 startActivity(new Intent(ShopCar.this,Main.class));
			 finish();
			break;
		default:
			break;
		}
	}
}
