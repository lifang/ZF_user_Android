package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.OrderEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Pageable;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.OrderAdapter;
import com.google.gson.reflect.TypeToken;
/***
 * 
 *    
 * ����ƣ�OrderList   
 * ��������   �����б�
 * �����ˣ� ljp 
 * ����ʱ�䣺2015-2-4 ����3:04:31   
 * @version    
 *
 */
public class OrderList extends BaseActivity implements  IXListViewListener{
	private PopupWindow menuWindow;
	private int type;
	private XListView Xlistview;
	private int page=1;
	private String p=null;
	private TextView tv_tyyp;
	private LinearLayout type_Layout;
	private LinearLayout eva_nodata;

	private int total = 0;
	private OrderAdapter myAdapter;
	private List<OrderEntity>  myList = new ArrayList<OrderEntity>();
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();
				if(myList.size()==0){
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					Xlistview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
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
				Toast.makeText(getApplicationContext(),  "no more data",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		initView();
		getData();
	}
	private void initView() {
		type_Layout = (LinearLayout) findViewById(R.id.type_Layout);
		tv_tyyp=(TextView) findViewById(R.id.tv_tyyp);
		type_Layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				menu_press();
			}
		});

		new TitleMenuUtil(OrderList.this, "我的订单").show();
		myAdapter=new OrderAdapter(OrderList.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview=(XListView) findViewById(R.id.x_listview);
		
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setAdapter(myAdapter);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(OrderList.this, OrderDetail.class);
				i.putExtra("status", myList.get(position-1).getOrder_status());
				i.putExtra("id", myList.get(position-1).getOrder_id());
				startActivityForResult(i, 101);
			}
		});
	}
	public void menu_press() {
		View view = getLayoutInflater().inflate(R.layout.pop_more, null);
		menuWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		LinearLayout ll_new = (LinearLayout) view.findViewById(R.id.ll_new);
		LinearLayout ll_all = (LinearLayout) view.findViewById(R.id.ll_all);
		LinearLayout ll_re = (LinearLayout) view.findViewById(R.id.ll_re);

		TextView tv_new = (TextView) view.findViewById(R.id.tv_new);
		TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
		TextView tv_re = (TextView) view.findViewById(R.id.tv_re);
		if (type == 1) {
			tv_new.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.black));

		}
		if (type == 2) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_re.setTextColor(getResources().getColor(R.color.black));
		}
		if (type == 3) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.bgtitle));
		}
		ll_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				p=null;
				tv_tyyp.setText("全部");
				type = 1;
				page = 1;
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		ll_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				type = 2;
				p="2";
				tv_tyyp.setText("租赁");
				page = 1;
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		ll_re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				type = 3;
				p="1";
				tv_tyyp.setText("购买");
				page = 1;
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		menuWindow.setFocusable(true);
		menuWindow.setOutsideTouchable(true);
		menuWindow.update();
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置layout在PopupWindow中显示的位置
		// int hight = findViewById(R.id.main_top).getHeight()
		// + Tools.getStatusBarHeight(getApplicationContext());
		menuWindow.showAsDropDown(this.findViewById(R.id.tv_type),0,10);

	}
	@Override
	public void onRefresh() {
		page = 1;
		Xlistview.setPullLoadEnable(true);
		myList.clear();
		myAdapter.notifyDataSetChanged();
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

	private void getData() {
		API.getMyOrderAll(this, MyApplication.getInstance().getCustomerId(),p, page, 5, 
				new HttpCallback<Pageable<OrderEntity>>(this) {
			@Override
			public void onSuccess(Pageable<OrderEntity> data) {

				if (null != data.getContent()) {
					myList.addAll(data.getContent());
				}
				page++;
				total = data.getTotal();
				handler.sendEmptyMessage(0);
			}

			@Override
			public TypeToken<Pageable<OrderEntity>> getTypeToken() {
				return new TypeToken<Pageable<OrderEntity>>() {
				};
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 101) {
				page = 1;
				myList.clear();
				getData();
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if(MyApplication.getInstance().isHasOrderPaid()){
			onRefresh();
			MyApplication.getInstance().setHasOrderPaid(false);
		}
		super.onResume();
	}
	
}
