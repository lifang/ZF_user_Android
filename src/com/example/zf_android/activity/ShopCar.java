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
 * ����ƣ�ShopCar �������� ���ﳵ �����ˣ� ljp ����ʱ�䣺2015-2-9 ����11:30:10
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
	private int index=0;
	private TextView howMoney;
	private List<Good> myShopList=new ArrayList<Good>();
	private List<Good> comfirmList=new ArrayList<Good>();
	List<TestEntitiy> moreList = new ArrayList<TestEntitiy>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				//
				// if (myShopList.size() == 0) {
				// // norecord_text_to.setText("��û����ص���Ʒ");
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

	// �������
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
		howMoney=(TextView) findViewById(R.id.howMoney);
		
		
		
		
		findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comfirmList.clear();
				for(int i =0;i<myShopList.size();i++){
					 if(myShopList.get(i).isChecked()){
						 System.out.println(myShopList.get(i).getQuantity()+"---"+myShopList.get(i).getRetail_price());
						 comfirmList.add(myShopList.get(i));
						 index=index+myShopList.get(i).getQuantity();
					 }
				}
				MyApplication.comfirmList=comfirmList;
				
				 Intent i = new Intent(ShopCar.this, ConfirmOrder.class);
				 i.putExtra("howMoney", howMoney.getText().toString());
				 
				 i.putExtra("index", index);
				 startActivity(i);
			}
		});

		myAdapter = new ShopcarAdapter(ShopCar.this, myShopList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
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
	 * �������
	 */
	private void getData() {
		// TODO Auto-generated method stub
		String url = "http://114.215.149.242:18080/ZFMerchant/api/cart/list";
		RequestParams params = new RequestParams("customerId", "80");
		params.setUseJsonStreamer(true);
		System.out.println("params---"+ params.toString());
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

		case R.id.main_rl_pos1:  // ��POS����
			 startActivity(new Intent(ShopCar.this,MyMessage.class));
			 finish();
			break;
		case R.id.main_rl_my:  // ��POS����
			 startActivity(new Intent(ShopCar.this,MenuMine.class));
			 finish();
			break;
		case R.id.main_rl_sy:  // ��POS����
			 startActivity(new Intent(ShopCar.this,Main.class));
			 finish();
			break;
		default:
			break;
		}
	}
}
