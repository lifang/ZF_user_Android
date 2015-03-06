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
 
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.example.zf_zandroid.adapter.PosAdapter;
 
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/***
 * 
*    
* 类名称：PosListActivity   
* 类描述：   买POS机器列表
* 创建人： ljp 
* 创建时间：2015-1-27 下午7:51:36   
* @version    
*
 */
public class PosListActivity extends BaseActivity implements OnClickListener, IXListViewListener{
	private ImageView pos_select,search2;	
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private PosAdapter myAdapter;
	private TextView next_sure;
	List<PosEntity>  myList = new ArrayList<PosEntity>();
	List<PosEntity>  moreList = new ArrayList<PosEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );
				
				if(myList.size()==0){
				//	norecord_text_to.setText("您没有相关的商品");
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
			case 2: // 网络有问题
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
		initView();
		getData();
	}
	private void initView() {
		// TODO Auto-generated method stub
		pos_select=(ImageView) findViewById(R.id.pos_select);
		pos_select.setOnClickListener(this);
		search2=(ImageView) findViewById(R.id.search2);
		search2.setOnClickListener(this);
		
		myAdapter=new PosAdapter(PosListActivity.this, myList);
		eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview=(XListView) findViewById(R.id.x_listview);
		// refund_listview.getmFooterView().getmHintView().setText("已经没有数据了");
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
 
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
			startActivity(i);
			break;
			//search2
		case R.id.search2:
		 
			startActivity( new Intent(PosListActivity.this,ShopCar.class));
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
			
//			if (Tools.isConnect(getApplicationContext())) {
//				onRefresh_number = false;
//				getData();
//			} else {
//				onRefresh_number = true;
//				handler.sendEmptyMessage(2);
//			}
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
	/*
	 * 请求数据
	 */
	private void getData() {
		// TODO Auto-generated method stub
		String url = "http://114.215.149.242:18080/ZFMerchant/api/good/list";
		RequestParams params = new RequestParams("city_id", 1);
		params.put("city_id", 1);
		params.put("orderType", 0);
		 
		params.setUseJsonStreamer(true);

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
							if(a==Config.CODE){ //判断返回结果是否合法
							//	"code":0,"message":"用户登录成功","token":"14188706016196","result":{"studentEmail":"475813996@qq.com","studentId":6,"studentStatus":2,"studentMobilePhone":"18862243513"}}
								String res =jsonobject.getString("result");
								jsonobject = new JSONObject(res);
								
								moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<PosEntity>>() {
			 					}.getType());
			 				 
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
