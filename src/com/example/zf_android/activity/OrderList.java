package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.example.zf_android.entity.OrderEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.OrderAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
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
	//���²��� Xlist
	private PopupWindow menuWindow;
	private int type;
	private XListView Xlistview;
	private int page=1;
	private String p=null;
	private TextView tv_type,tv_tyyp;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private OrderAdapter myAdapter;
	List<OrderEntity>  myList = new ArrayList<OrderEntity>();
	List<OrderEntity>  moreList = new ArrayList<OrderEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );
				
				if(myList.size()==0){
				//	norecord_text_to.setText("��û����ص���Ʒ");
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
	//�������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		initView();
		getData();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_tyyp=(TextView) findViewById(R.id.tv_tyyp);
		tv_type=(TextView) findViewById(R.id.tv_type);
		tv_type.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				menu_press();
			}
		});
		
		new TitleMenuUtil(OrderList.this, "我的订单").show();
		myAdapter=new OrderAdapter(OrderList.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview=(XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

//		Xlistview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				// TODO Auto-generated method stub status
//				Intent i = new Intent(OrderList.this, OrderDetail.class);
//				i.putExtra("status", myList.get(position-1).getOrder_status());
//				System.out.println("onItemClick----"+myList.get(position-1).getOrder_status());
//				i.putExtra("id", myList.get(position-1).getOrder_id());
//				startActivity(i);
//			}
//		});
		Xlistview.setAdapter(myAdapter);
	}
	public void menu_press() {
		View view = getLayoutInflater().inflate(R.layout.pop_more, null);
		// view.findViewById(R.id.todayorder_ordernumber).setOnClickListener(this);
		// view.findViewById(R.id.todayorder_mobile).setOnClickListener(this);
		menuWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView tv_new = (TextView) view.findViewById(R.id.tv_new);
		TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
		TextView tv_re = (TextView) view.findViewById(R.id.tv_re);
		if (type == 1) {
			tv_new.setTextColor(getResources().getColor(R.color.C0075FF));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.black));
			 
		}
		if (type == 3) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_re.setTextColor(getResources().getColor(R.color.black));
			//titleback_text_title.setText("");
		}
		if (type == 2) {
			tv_new.setTextColor(getResources().getColor(R.color.black));
			tv_all.setTextColor(getResources().getColor(R.color.black));
			tv_re.setTextColor(getResources().getColor(R.color.bgtitle));
		}
		tv_new.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				p=null;
				tv_tyyp.setText("全部");

				menuWindow.dismiss();
				myList.clear();
				getData();
			//	titleback_text_title.setText("");
			}
		});
		tv_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 2;
				p="2";
				tv_tyyp.setText("租赁");

			//	titleback_text_title.setText("");
				menuWindow.dismiss();
				myList.clear();
				getData();
			}
		});
		tv_re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				type = 1;
				p="1";
				tv_tyyp.setText("购买");

			//	titleback_text_title.setText("");
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
 
			
			if (Tools.isConnect(getApplicationContext())) {
				onRefresh_number = false;
				getData();
			} else {
				onRefresh_number = true;
				handler.sendEmptyMessage(2);
			}
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
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		String url = "http://114.215.149.242:18080/ZFMerchant/api/order/getMyOrderAll";
		RequestParams params = new RequestParams();
		params.put("customer_id", 80);
		params.put("p", p);
		params.put("page", page);
		params.put("pageSize", 5);
		 
		params.setUseJsonStreamer(true);
		 System.out.println("-params--"+params.toString());
		MyApplication.getInstance().getClient()
				.post(url, params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String responseMsg = new String(responseBody)
								.toString();
						Log.e("print", responseMsg);

					 
						 
						Gson gson = new Gson();
						
						JSONObject jsonobject = null;
						String code = null;
						try {
							jsonobject = new JSONObject(responseMsg);
							code = jsonobject.getString("code");
							int a =jsonobject.getInt("code");
							if(a==Config.CODE){  
								String res =jsonobject.getString("result");
								jsonobject = new JSONObject(res);
								moreList.clear();
								System.out.println("-jsonobject String()--"+jsonobject.getString("content").toString());
								moreList= gson.fromJson(jsonobject.getString("content").toString(), new TypeToken<List<OrderEntity>>() {
			 					}.getType());
								System.out.println("-sendEmptyMessage String()--");
								myList.addAll(moreList);
				 				handler.sendEmptyMessage(0);
 	 					  
			 				 
			 			 
							}else{
								code = jsonobject.getString("message");
								Toast.makeText(getApplicationContext(), code, 1000).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							 ;	
							e.printStackTrace();
							
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
 
		 
	
	}
}
