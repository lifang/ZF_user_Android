package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.OrderDetail_PosAdapter;
import com.example.zf_zandroid.adapter.RecordAdapter;
/***
 * 
*    
* 类名称：OrderDetail   
* 类描述：   订单详情
* 创建人： ljp 
* 创建时间：2015-2-5 下午3:06:02   
* @version    
*
 */
public class OrderDetail extends BaseActivity{
	private  ScrollViewWithListView   pos_lv,his_lv;
	List<TestEntitiy>  poslist = new ArrayList<TestEntitiy>();
	List<TestEntitiy>  relist = new ArrayList<TestEntitiy>(); 
	private OrderDetail_PosAdapter posAdapter;
	private RecordAdapter reAdapter;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
			//	posAdapter.notifyDataSetChanged();
			 	reAdapter.notifyDataSetChanged();
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
			setContentView(R.layout.order_detail);
			new TitleMenuUtil(OrderDetail.this, "订单详情").show();
			initView();
			getData();
		}
		private void getData() {
			// TODO Auto-generated method stub
			 TestEntitiy te=new TestEntitiy();	
			 te.setContent("泰山旗舰900");
			 poslist.add(te);
			 TestEntitiy te1=new TestEntitiy();	
			 te1.setContent("泰山旗舰1000");
			 poslist.add(te1);
			 
			 
			 TestEntitiy te22=new TestEntitiy();	
			 te22.setContent("这里是一条记录 -001");
			 relist.add(te22);
			 TestEntitiy te21=new TestEntitiy();	
			 te21.setContent("这里是一条记录 -002");
			 relist.add(te21);
			 
			 handler.sendEmptyMessage(0);
		 
		}
		private void initView() {
			// TODO Auto-generated method stub
 			pos_lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
 			posAdapter=new OrderDetail_PosAdapter(OrderDetail.this, poslist);
 			pos_lv.setAdapter(posAdapter);
			
			his_lv=(ScrollViewWithListView) findViewById(R.id.his_lv);
			reAdapter=new RecordAdapter(OrderDetail.this, relist);
			his_lv.setAdapter(reAdapter);
		}
}
