package com.example.zf_android.activity;
 
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
 
import com.examlpe.zf_android.util.Tools;
import com.examlpe.zf_android.util.XListView;
import com.examlpe.zf_android.util.XListView.IXListViewListener;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
 
import com.example.zf_android.entity.NewPoslistEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.PosItem;
import com.example.zf_android.entity.PosSelectEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_android.entity.UserEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.Constants;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalItem;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.example.zf_zandroid.adapter.PosAdapter;
 
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

 
public class PosListActivity extends BaseActivity implements OnClickListener, IXListViewListener{
	private ImageView pos_select,search2,img3;	
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata,ll_xxyx,ll_mr,ll_updown,ll_pj;
	private boolean onRefresh_number = true;
	private PosAdapter myAdapter;
	private String keys=null;
	private int maxPrice=0,minPrice=0;
	private TextView next_sure,tv_mr,tv_2,tv_3,tv_4;
	private Boolean isDown=true;
	private int orderType=0;
	private int has_purchase=0;
	private EditText et_search;
	 List<PosItem> slist=new ArrayList<PosItem>();
	 ArrayList<Integer>  category = new ArrayList<Integer>();
	 ArrayList<Integer>  brands_id = new ArrayList<Integer>();
	 ArrayList<Integer>  pay_channel_id = new ArrayList<Integer>();
	 ArrayList<Integer>  pay_card_id = new ArrayList<Integer>();
	 ArrayList<Integer>  trade_type_id = new ArrayList<Integer>();
	 
	 ArrayList<Integer>  sale_slip_id = new ArrayList<Integer>();
	 ArrayList<Integer>  tDate = new ArrayList<Integer>();
 
 

	 
	 
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
				Toast.makeText(getApplicationContext(),  " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	
	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.poslist_activity);
		// MyApplication.pse=new PosSelectEntity();
 
		initView();
		getData();
	}
	private void initView() {
		// TODO Auto-generated method stub
		ll_mr=(LinearLayout) findViewById(R.id.ll_mr);
		ll_mr.setOnClickListener(this);
		ll_xxyx=(LinearLayout) findViewById(R.id.ll_xxyx);
		ll_xxyx.setOnClickListener(this);
		ll_updown=(LinearLayout) findViewById(R.id.ll_updown);
		ll_updown.setOnClickListener(this);
		ll_pj=(LinearLayout) findViewById(R.id.ll_pj);
		ll_pj.setOnClickListener(this);
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
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i =new Intent (PosListActivity.this,GoodDeatail.class);
				i.putExtra("id", myList.get(position-1).getId());
				System.out.println("-Xlistview--"+id);
				startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
			getData();
			break;	
		case R.id.ll_xxyx:
			orderType=1;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			getData();
			break;	
		case R.id.ll_updown:
			if(isDown){
				orderType=2;
				isDown=false;
				img3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ti_down));
			}else{
				orderType=3;
				isDown=true;
				img3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ti_up));
			}
			 
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_4.setTextColor(getResources().getColor(R.color.bg_575D5F));
			myList.clear();
			getData();
			break;	
		case R.id.ll_pj:
			orderType=4;
			tv_mr.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_2.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_3.setTextColor(getResources().getColor(R.color.bg_575D5F));
			tv_4.setTextColor(getResources().getColor(R.color.bgtitle));
			myList.clear();
			getData();
			break;	
		default:
			break;
		}
	}	
	
	
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		page = 1;
		 System.out.println("onRefresh1");
		myList.clear();
		 System.out.println("onRefresh2");
		getData();
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		if (onRefresh_number) {
			page = page+1;
			
			onRefresh_number = false;
			getData();
			
 
		}
		else {
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
	 
	private void getData() {
		API.postList(this, MyApplication.getInstance().getCityId(), orderType,
				brands_id, null, null, null, null, null, null,
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
			if(data!=null){
				System.out.println("进入条件选择回调···");
				minPrice=data.getIntExtra("minPrice", 0);
				maxPrice=data.getIntExtra("maxPrice", 1000000);
				has_purchase=data.getIntExtra("has_purchase", 1);
				brands_id=data.getIntegerArrayListExtra("brands_id");
				pay_channel_id=data.getIntegerArrayListExtra("pay_channel_id");
				pay_card_id=data.getIntegerArrayListExtra("pay_card_id");
				trade_type_id=data.getIntegerArrayListExtra("trade_type_id");
				sale_slip_id=data.getIntegerArrayListExtra("sale_slip_id");
				tDate=data.getIntegerArrayListExtra("tDate");
			 
//				 ArrayList<Integer>  trade_type_id = new ArrayList<Integer>();
//				 
//				 ArrayList<Integer>  sale_slip_id = new ArrayList<Integer>();
//				 ArrayList<Integer>  tDate = new ArrayList<Integer>();
				
				
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
