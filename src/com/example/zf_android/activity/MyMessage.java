package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.example.zf_zandroid.adapter.OrderAdapter;
/***
 * 
*    
* 类名称：MyMessage   
* 类描述：   我的消息
* 创建人： ljp 
* 创建时间：2015-2-5 下午2:15:03   
* @version    
*
 */
public class MyMessage extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private MessageAdapter myAdapter;
	private TextView next_sure;
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
			setContentView(R.layout.my_message);
			initView();
			getData();
		}

		private void initView() {
			// TODO Auto-generated method stub
			next_sure=(TextView) findViewById(R.id.next_sure);
			next_sure.setVisibility(View.VISIBLE);
			next_sure.setText("编辑");
			new TitleMenuUtil(MyMessage.this, "我的消息").show();
			myAdapter=new MessageAdapter(MyMessage.this, myList);
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
			
			
			
			next_sure.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(MyApplication.getIsSelect()){
						//遍历数组删除操作
						MyApplication.setIsSelect(false);
						myAdapter.notifyDataSetChanged();
						for(int i=0;i<myList.size();i++){
							 
							if(myList.get(i).getIscheck()){
								System.out.println("第---"+i+"条被选中");
							}
						}
					}else{
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
			 te.setContent("---这里是标题1---"+page+page);
			 myList.add(te);
			 TestEntitiy te2=new TestEntitiy();
			 te2.setContent("---这里是标题2---"+page+page);
			 myList.add(te2);
			 TestEntitiy te22=new TestEntitiy();
			 te22.setContent("---标题3---"+page+page);
			 myList.add(te22);
			
			System.out.println("getData");
			handler.sendEmptyMessage(0);
		}
}
