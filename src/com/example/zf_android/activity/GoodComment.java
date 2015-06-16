package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.GoodCommentEntity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.GoodCommentAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
/***
 * 
*    
* 类名称：GoodComment   
* 类描述：    商品评论
* 创建人： ljp 
* 创建时间：2015-3-11 上午10:18:58   
* @version    
*
 */
public class GoodComment extends BaseActivity implements  IXListViewListener{
 
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private int goodId;
	private String title;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private int total = 0;
	private GoodCommentAdapter myAdapter;
	List<GoodCommentEntity>  myList = new ArrayList<GoodCommentEntity>();
	List<GoodCommentEntity>  moreList = new ArrayList<GoodCommentEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad( );
				
				if(myList.size()==0){
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					Xlistview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				onRefresh_number = true; 
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
				Toast.makeText(getApplicationContext(),  " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_commet);
		title=getIntent().getStringExtra("commentsCount");
		goodId=getIntent().getIntExtra("goodId", 0);
		initView();
		getData(); 
	}

	private void initView() {
		
		new TitleMenuUtil(GoodComment.this, "评论  ("+title+")").show();
		myAdapter=new GoodCommentAdapter(GoodComment.this, myList);
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
			//	Intent i = new Intent(GoodComment.this, OrderDetail.class);
			//	startActivity(i);
			}
		});
		Xlistview.setAdapter(myAdapter);
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
			CommonUtil.toastShort(this, "没有更多数据");
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
		String url = Config.goodcomment;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodId", goodId);
		params.put("indexPage", page);
	 	params.put("rows", rows);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		
		MyApplication.getInstance().getClient()
		.post(getApplicationContext(),url, null,entity,"application/json", new AsyncHttpResponseHandler(){
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
								
								total = jsonobject.getInt("total");
								moreList= gson.fromJson(jsonobject.getString("list"), new TypeToken<List<GoodCommentEntity>>() {
			 					}.getType());
								page++;
								myList.addAll(moreList);
				 				handler.sendEmptyMessage(0);
							}else{
								code = jsonobject.getString("message");
								Toast.makeText(getApplicationContext(), code, 1000).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
							
						}

					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						Log.e("print", "-onFailure---" + error);
					}
				});
 
	}
}
