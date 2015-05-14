package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.NewPoslistEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.PosItem;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.PosAdapter;
import com.google.gson.reflect.TypeToken;


public class PosListActivity extends BaseActivity implements OnClickListener, IXListViewListener{
	private ImageView pos_select,search2,img3;	
	private TextView countShopCar;
	private XListView Xlistview;
	private LinearLayout titleback_linear_back;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata,ll_xxyx,ll_mr,ll_updown,ll_pj;
	private boolean onRefresh_number = true;
	private PosAdapter myAdapter;
	private String keys= "";
	private int maxPrice=0,minPrice=0;
	private TextView next_sure,tv_mr,tv_2,tv_3,tv_4;
	private Boolean isDown=true;
	private int orderType=0;
	private int has_purchase=1;
	private EditText et_search;
	List<PosItem> slist=new ArrayList<PosItem>();
	private ArrayList<Integer>  category_id = new ArrayList<Integer>();
	private ArrayList<Integer>  brands_id = new ArrayList<Integer>();
	private ArrayList<Integer>  pay_channel_id = new ArrayList<Integer>();
	private ArrayList<Integer>  pay_card_id = new ArrayList<Integer>();
	private ArrayList<Integer>  trade_type_id = new ArrayList<Integer>();

	private ArrayList<Integer>  sale_slip_id = new ArrayList<Integer>();
	private ArrayList<Integer>  tDate = new ArrayList<Integer>();

	List<PosEntity>  myList = new ArrayList<PosEntity>();
	List<PosEntity>  moreList = new ArrayList<PosEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );

				if(myList.size()==0){
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					eva_nodata.setVisibility(View.GONE);
					Xlistview.setVisibility(View.VISIBLE);
				}
				onRefresh_number = true; 
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // 
				Toast.makeText(getApplicationContext(), "no 3g or wifi content",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(),  "no more data",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poslist_activity);
		// MyApplication.pse=new PosSelectEntity();
		SharedPreferences mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		Boolean isOpen_mineset = mySharedPreferences.getBoolean("isOpen_ps", true);
		if (isOpen_mineset) {
			has_purchase = 1;
		}else {
			has_purchase = 0;
		}
		initView();
		getData();
	}
	private void initView() {
		countShopCar = (TextView) findViewById(R.id.countShopCar);
		ll_mr=(LinearLayout) findViewById(R.id.ll_mr);
		ll_mr.setOnClickListener(this);
		ll_xxyx=(LinearLayout) findViewById(R.id.ll_xxyx);
		ll_xxyx.setOnClickListener(this);
		ll_updown=(LinearLayout) findViewById(R.id.ll_updown);
		ll_updown.setOnClickListener(this);
		ll_pj=(LinearLayout) findViewById(R.id.ll_pj);
		ll_pj.setOnClickListener(this);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
		tv_mr=(TextView) findViewById(R.id.tv_mr);
		tv_2=(TextView) findViewById(R.id.tv_2);
		tv_3=(TextView) findViewById(R.id.tv_3);
		tv_4=(TextView) findViewById(R.id.tv_4);
		img3=(ImageView) findViewById(R.id.img3);

		et_search=(EditText) findViewById(R.id.et_search);
		et_search.setOnClickListener(this);
		pos_select=(ImageView) findViewById(R.id.pos_select);
		pos_select.setOnClickListener(this);
		search2=(ImageView) findViewById(R.id.search2);
		search2.setOnClickListener(this);

		myAdapter=new PosAdapter(PosListActivity.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview=(XListView) findViewById(R.id.x_listview);
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i =new Intent (PosListActivity.this,GoodDeatail.class);
				i.putExtra("id", myList.get(position-1).getId());
				startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			finish();
			break;
		case R.id.pos_select:
			Intent i =new Intent(PosListActivity.this,PosSelect.class);
			startActivityForResult(i, 1);
			break;
			//search2
		case R.id.search2:

			startActivity( new Intent(PosListActivity.this,ShopCar.class));
			break;
		case R.id.et_search:
			Intent ii =  new Intent(PosListActivity.this,PosSearch.class);
			startActivityForResult(ii, 2);

			break;	
		case R.id.ll_mr:
			orderType=0;
			tv_mr.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			page = 1;
			getData();
			break;	
		case R.id.ll_xxyx:
			orderType=1;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			page = 1;
			getData();
			break;	
		case R.id.ll_updown:
			if(isDown){
				orderType=2;
				isDown=false;
				tv_3.setText("价格降序");
				img3.setBackgroundResource(R.drawable.ti_down);
			}else{
				orderType=3;
				isDown=true;
				tv_3.setText("价格升序");
				img3.setBackgroundResource(R.drawable.ti_up);
			}

			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			page = 1;
			getData();
			break;	
		case R.id.ll_pj:
			orderType=4;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bgtitle));
			myList.clear();
			page = 1;
			getData();
			break;	
		default:
			break;
		}
	}	
	@Override
	protected void onResume() {
		super.onResume();
		if (Config.countShopCar == 0) {
			countShopCar.setVisibility(View.GONE);
		}else {
			countShopCar.setVisibility(View.VISIBLE);
			countShopCar.setText(Config.countShopCar+"");
		}
	}
	@Override
	public void onRefresh() {
		page = 1;
		myList.clear();
		Xlistview.setPullLoadEnable(true);
		getData();
	}


	@Override
	public void onLoadMore() {
		if (onRefresh_number) {
			page = page+1;

			onRefresh_number = false;
			getData();
		}
		else {
			Xlistview.setPullLoadEnable(false);
			handler.sendEmptyMessage(3);
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

	@SuppressWarnings("null")
	private void getData() {
		int[] arraybrands_id = null,arraycategory_id = null,arraypay_channel_id = null,arraypay_card_id = null,
				arraytrade_type_id = null,arraysale_slip_id = null,arraystDate = null;
		if (brands_id != null){ 
			arraybrands_id = new int[brands_id.size()];  
			if (brands_id.size() == 0) 
				arraybrands_id = null;

			for(int i=0;i<brands_id.size();i++){  
				arraybrands_id[i] = brands_id.get(i);  
			}
		}
		if (category_id != null){
			arraycategory_id = new int[category_id.size()];  
			if (category_id.size() == 0) 
				arraycategory_id = null;
			for(int i=0;i<category_id.size();i++){  
				arraycategory_id[i] = category_id.get(i);  
			}
		}

		if (pay_channel_id != null){
			arraypay_channel_id = new int[pay_channel_id.size()];  
			if (pay_channel_id.size() == 0) 
				arraypay_channel_id = null;
			for(int i=0;i<pay_channel_id.size();i++){  
				arraypay_channel_id[i] = pay_channel_id.get(i);  
			}
		}
		if (pay_card_id != null){
			arraypay_card_id = new int[pay_card_id.size()];
			if (pay_card_id.size() == 0) 
				arraypay_card_id = null;
			for(int i=0;i<pay_card_id.size();i++){  
				arraypay_card_id[i] = pay_card_id.get(i);  
			}
		}
		if (trade_type_id != null){
			arraytrade_type_id = new int[trade_type_id.size()];  
			if (trade_type_id.size() == 0) 
				arraytrade_type_id = null;
			for(int i=0;i<trade_type_id.size();i++){  
				arraytrade_type_id[i] = trade_type_id.get(i);  
			}
		}
		if (sale_slip_id != null){
			arraysale_slip_id = new int[sale_slip_id.size()];  
			if (sale_slip_id.size() == 0) 
				arraysale_slip_id = null;
			for(int i=0;i<sale_slip_id.size();i++){  
				arraysale_slip_id[i] = sale_slip_id.get(i);  
			}
		}
		if (tDate != null){
			arraystDate = new int[tDate.size()];  
			if (tDate.size() == 0) 
				arraystDate = null;
			for(int i=0;i<tDate.size();i++){  
				arraystDate[i] = tDate.get(i);  
			}
		}

		API.postList(this, MyApplication.getInstance().getCityId(), orderType,
				arraybrands_id,arraycategory_id,arraypay_channel_id,arraypay_card_id,
				arraytrade_type_id,arraysale_slip_id,arraystDate,
				has_purchase, minPrice, maxPrice, keys, page, rows,

				new HttpCallback<NewPoslistEntity>(this) {
			@Override
			public void onSuccess(NewPoslistEntity data) {
				moreList=data.getList();
				System.out.println("ssss"+moreList.size());
				myList.addAll(moreList);
				handler.sendEmptyMessage(0);
			}

			@Override
			public TypeToken   getTypeToken() {
				return  new TypeToken<NewPoslistEntity>() {
				};
			}
		});
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 1:
			minPrice = 0;maxPrice = 0;
			has_purchase = 1;
			brands_id = null;
			category_id = null;
			pay_channel_id = null;
			pay_card_id = null;
			trade_type_id = null;
			sale_slip_id = null;
			tDate = null;

			if(data!=null){
				System.out.println("进入条件选择回调···");
				minPrice=data.getIntExtra("minPrice", 0);
				maxPrice=data.getIntExtra("maxPrice", 1000000);
				has_purchase=data.getIntExtra("has_purchase", 1);
				brands_id=data.getIntegerArrayListExtra("brands_id");
				category_id=data.getIntegerArrayListExtra("category_id");
				pay_channel_id=data.getIntegerArrayListExtra("pay_channel_id");
				pay_card_id=data.getIntegerArrayListExtra("pay_card_id");
				trade_type_id=data.getIntegerArrayListExtra("trade_type_id");
				sale_slip_id=data.getIntegerArrayListExtra("sale_slip_id");
				tDate=data.getIntegerArrayListExtra("tDate");

				System.out.println(trade_type_id.toString()+"<trade_type_id--sale_slip_id>"+sale_slip_id.toString()+"tDate-->"+tDate.toString()); 

				System.out.println(pay_channel_id+"<-->"+minPrice+brands_id.toString()+"pay_card_id"+pay_card_id.toString()); 
				myList.clear();
				getData();
			}

			break;
		case 2:
			if(data!=null){
				String  a =data.getStringExtra("text");
				keys=a;
				et_search.setText(a);
				myList.clear();
				getData();
			}

			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}
}
