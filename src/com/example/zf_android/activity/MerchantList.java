package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.AdressAdapter;
import com.example.zf_zandroid.adapter.MessageAdapter;

public class MerchantList extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private RelativeLayout mune_rl;
	private boolean onRefresh_number = true;
	private AdressAdapter myAdapter;
	private ImageView search;
	List<TestEntitiy>  myList = new ArrayList<TestEntitiy>();
	List<TestEntitiy>  moreList = new ArrayList<TestEntitiy>();
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
			setContentView(R.layout.adress_list);
			initView();
			getData();
		}

		private void initView() {
			// TODO Auto-generated method stub
			search=(ImageView) findViewById(R.id.search);
			mune_rl=(RelativeLayout) findViewById(R.id.mune_rl);
			 
			new TitleMenuUtil(MerchantList.this, "我的地址").show();
			myAdapter=new AdressAdapter(MerchantList.this, myList);
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
//					Intent i = new Intent(MyMessage.this, OrderDetail.class);
//					startActivity(i);
				}
			});
			Xlistview.setAdapter(myAdapter);
			
			
			
			search.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MyApplication.getIsSelect()){
						//遍历数组删除操作					 
						MyApplication.setIsSelect(false);
						myAdapter.notifyDataSetChanged();
						mune_rl.setVisibility(View.GONE);
//						for(int i=0;i<myList.size();i++){
//							 
//							if(myList.get(i).getIscheck()){
//								System.out.println("第---"+i+"条被选中");
//							}
						//}
					}else{
						mune_rl.setVisibility(View.VISIBLE);
						MyApplication.setIsSelect(true);
						myAdapter.notifyDataSetChanged();
					}
				}
			});
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
				
//				if (Tools.isConnect(getApplicationContext())) {
//					onRefresh_number = false;
//					getData();
//				} else {
//					onRefresh_number = true;
//					handler.sendEmptyMessage(2);
//				}
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
			 
		 
			 TestEntitiy te=new TestEntitiy();
			 te.setContent("用户名user"+page+page);
			 myList.add(te);
			 TestEntitiy te2=new TestEntitiy();
			 te2.setContent("用户名user"+page+page);
			 myList.add(te2);
			 TestEntitiy te22=new TestEntitiy();
			 te22.setContent("用户名user"+page+page);
			 myList.add(te22);
			
			System.out.println("getData");
			handler.sendEmptyMessage(0);
		}
}
