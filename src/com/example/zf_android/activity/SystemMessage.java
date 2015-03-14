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
import com.example.zf_android.entity.MessageEntity;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.example.zf_zandroid.adapter.OrderAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* ����ƣ�MyMessage   
* ��������   ϵͳ��Ϣ
* �����ˣ� ljp 
* ����ʱ�䣺2015-2-5 ����2:15:03   
* @version    
*
 */
public class SystemMessage extends BaseActivity implements  IXListViewListener{
	private XListView Xlistview;
	private int page=1;
	private int rows=Config.ROWS;
	private LinearLayout eva_nodata;
	private boolean onRefresh_number = true;
	private MessageAdapter myAdapter;
	private TextView next_sure;
	private String Url = Config.getsysmes;
	List<MessageEntity>  myList = new ArrayList<MessageEntity>();
	List<MessageEntity>  moreList = new ArrayList<MessageEntity>();
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
 
			new TitleMenuUtil(SystemMessage.this, "系统公告").show();
			myAdapter=new MessageAdapter(SystemMessage.this, myList);
			eva_nodata=(LinearLayout) findViewById(R.id.eva_nodata);
			Xlistview=(XListView) findViewById(R.id.x_listview);
			// refund_listview.getmFooterView().getmHintView().setText("�Ѿ�û�������");
			Xlistview.setPullLoadEnable(true);
			Xlistview.setXListViewListener(this);
			Xlistview.setDivider(null);

			Xlistview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
 					Intent i = new Intent(SystemMessage.this, SystemDetail.class);
 					startActivity(i);
				}
			});
			Xlistview.setAdapter(myAdapter);
//			next_sure.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					if(MyApplication.getIsSelect()){
//						//��������ɾ�����
//						MyApplication.setIsSelect(false);
//						myAdapter.notifyDataSetChanged();
//						for(int i=0;i<myList.size();i++){
//							 
//							if(myList.get(i).getIscheck()){
//								System.out.println("��---"+i+"����ѡ��");
//							}
//						}
//					}else{
//						MyApplication.setIsSelect(true);
//						myAdapter.notifyDataSetChanged();
//					}
//				}
//			});
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

			RequestParams params = new RequestParams();

		 
			params.put("page", page);
			params.put("rows", rows);
			params.setUseJsonStreamer(true);
			MyApplication.getInstance().getClient()
					.post(Url, params, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							System.out.println("-onSuccess---");
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("LJP", responseMsg);
							Gson gson = new Gson();
							JSONObject jsonobject = null;
							int code = 0;
							try {
								jsonobject = new JSONObject(responseMsg);
								 
								 
								code = jsonobject.getInt("code");
								
								if(code==-2){
								 
								}else if(code==1){
									
									String res =jsonobject.getString("result");
									System.out.println("`res``"+res);
									jsonobject = new JSONObject(res);
									moreList.clear();
									
					 				moreList = gson.fromJson(jsonobject.getString("content") ,
										new TypeToken<List<MessageEntity>>() {
										}.getType());
					 					 	
					 						if (moreList.size()==0) {
					 							Toast.makeText(getApplicationContext(),
					 									"没有更多数据了", Toast.LENGTH_SHORT).show();
					 							Xlistview.getmFooterView().setState2(2);
					 					 
					 						} 
					 						 
					 				myList.addAll(moreList);
					 				handler.sendEmptyMessage(0);
			 
									
									
								}else{
									Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 
						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							error.printStackTrace();
						}
					});
			 
		
		}
}
