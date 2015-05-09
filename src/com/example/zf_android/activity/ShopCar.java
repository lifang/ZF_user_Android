package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_zandroid.adapter.ShopcarAdapter;
import com.google.gson.reflect.TypeToken;

/***
 * 
 * 购物车
 * 
 * @version
 * 
 */
public class ShopCar extends BaseActivity implements OnClickListener {
	private RelativeLayout rl_sy, rl_xx, rl_wd;
	private XListView Xlistview;
	private LinearLayout eva_nodata;
	private ShopcarAdapter myAdapter;
	private int index = 0;
	private TextView howMoney;
	private List<Good> myShopList = new ArrayList<Good>();
	private List<Good> comfirmList = new ArrayList<Good>();
	List<TestEntitiy> moreList = new ArrayList<TestEntitiy>();
	private boolean isFirstCreate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.acv_shopcat);
		isFirstCreate=true;
		Config.countShopCar = 0;
		initView();
		getData();
	}
	@Override
	protected void onResume() {
		super.onResume();
		Config.countShopCar = 0;
		if(!isFirstCreate){
			getData();
		}else {
			isFirstCreate=false;
		}
		
	}
	private void initView() {
		rl_wd = (RelativeLayout) findViewById(R.id.main_rl_my);
		rl_wd.setOnClickListener(this);
		rl_xx = (RelativeLayout) findViewById(R.id.main_rl_pos1);
		rl_xx.setOnClickListener(this);
		rl_sy = (RelativeLayout) findViewById(R.id.main_rl_sy);
		rl_sy.setOnClickListener(this);
		howMoney = (TextView) findViewById(R.id.howMoney);

		findViewById(R.id.confirm).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				index = 0;
				comfirmList.clear();
				for (int i = 0; i < myShopList.size(); i++) {
					if (myShopList.get(i).isChecked()) {
						System.out.println(myShopList.get(i).getQuantity()
								+ "---" + myShopList.get(i).getRetail_price());
						comfirmList.add(myShopList.get(i));
						index = index + myShopList.get(i).getQuantity();
					}
				}
				if (index == 0) {
					Toast.makeText(ShopCar.this, "请选择商品", Toast.LENGTH_SHORT)
					.show();
				} else {
					MyApplication.comfirmList = comfirmList;

					Intent i = new Intent(ShopCar.this, ConfirmOrder.class);
					i.putExtra("howMoney", howMoney.getText().toString());

					i.putExtra("index", index);
					startActivity(i);
				}
			}
		});

		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);

		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(false);
		Xlistview.setPullRefreshEnable(false);
		// Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);
		Xlistview.getmFooterView().setState2(0);
		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			}
		});
		myAdapter = new ShopcarAdapter(ShopCar.this, myShopList);
		Xlistview.setAdapter(myAdapter);
	}

	private void getData() {
		myShopList.clear();
		API.cartList(this, MyApplication.getInstance().getCustomerId(),
				new HttpCallback<List<Good>>(this) {

					@Override
					public void onSuccess(List<Good> data) {

						if (null != data) {
							myShopList.addAll(data);
						}
						if (myShopList.size() == 0) {
							Xlistview.setVisibility(View.GONE);
							eva_nodata.setVisibility(View.VISIBLE);
						}else {
							Xlistview.setVisibility(View.VISIBLE);
							eva_nodata.setVisibility(View.GONE);
						}
						myAdapter.notifyDataSetChanged();
					};

					@Override
					public TypeToken<List<Good>> getTypeToken() {
						return new TypeToken<List<Good>>() {
						};
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.main_rl_pos1:
			startActivity(new Intent(ShopCar.this, MyMessage.class));
			finish();
			break;
		case R.id.main_rl_my:
			startActivity(new Intent(ShopCar.this, MenuMine.class));
			finish();
			break;
		case R.id.main_rl_sy:
			startActivity(new Intent(ShopCar.this, Main.class));
			finish();
			break;
		default:
			break;
		}
	}
}
