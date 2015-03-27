package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.Answer;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.ComfirmcarAdapter;
import com.example.zf_zandroid.adapter.OrderDetail_PosAdapter;
import com.example.zf_zandroid.adapter.RecordAdapter;
import com.example.zf_zandroid.adapter.ShopcarAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/***
 * 
*    
* ����ƣ�ConfirmOrder   
* ��������   ����ȷ��
* �����ˣ� ljp 
* ����ʱ�䣺2015-2-9 ����3:49:46   
* @version    
*
 */
public class ConfirmOrder extends BaseActivity implements OnClickListener{
	private  ScrollViewWithListView   pos_lv,his_lv;
	 private  int index=0;
			 private String howMoney,comment,invoice_info;
private EditText et_comment,et_info;
private String Url=Config.ChooseAdress;
List<AdressEntity>  myList = new ArrayList<AdressEntity>();
List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
List<Integer>  inn = new ArrayList<Integer>();
			 private int addressId;
	private ComfirmcarAdapter myAdapter;
	private LinearLayout ll_adress;
	private TextView tv_pop,tv_pay,tv_hj,tv_count,tv_name,tv_tel,tv_adresss;
	private List<Good> comfirmList=new ArrayList<Good>();
	private int  is_need_invoice=0;//否需要发票（1要，0不要
	private int invoice_type=0;//发票类型（0公司  1个人）
	private Button btn_pay;
	 PopupWindow menuWindow;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
		//	 posAdapter.notifyDataSetChanged();
			  
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
			setContentView(R.layout.comfirm_order);
			index=getIntent().getIntExtra("index", 0);
			howMoney=getIntent().getStringExtra("howMoney");
			initView(); 
			getData();
			getData1();
		}
		private void getData() {
			// TODO Auto-generated method stub
			 
			comfirmList=MyApplication.getComfirmList();
			myAdapter=new ComfirmcarAdapter(ConfirmOrder.this, comfirmList);
 			pos_lv.setAdapter(myAdapter);
		}
		private void initView() {
			// TODO Auto-generated method stub
			new TitleMenuUtil(ConfirmOrder.this, "订单确定").show();
 			pos_lv=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
 			btn_pay=(Button) findViewById(R.id.btn_pay);
 			btn_pay.setOnClickListener(this);
 			tv_pop=(TextView) findViewById(R.id.tv_pop);
 			tv_pop.setOnClickListener(this);
 			tv_name=(TextView) findViewById(R.id.tv_name);
 			tv_tel=(TextView) findViewById(R.id.tv_tel);
 			tv_adresss=(TextView) findViewById(R.id.tv_adresss);
 			ll_adress=(LinearLayout) findViewById(R.id.ll_adress);
 			ll_adress.setOnClickListener(this);
 			tv_pay=(TextView) findViewById(R.id.tv_pay);
 			tv_pay.setText(howMoney.replace("合计","实付" ));
 			tv_hj=(TextView) findViewById(R.id.tv_hj);
 			tv_hj.setText(howMoney);
 			tv_count=(TextView) findViewById(R.id.tv_count);
 			tv_count.setText("共计  ： "+index+"件");
 			et_comment=(EditText) findViewById(R.id.et_comment);
 			  et_info=(EditText) findViewById(R.id.et_info); //et_info
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub tv_pop
			switch (v.getId()) {
			case R.id.tv_pop:
			//	 menu_press();
			 pay() ;
				break;
			case R.id.btn_pay:
				getpay11();
				break;
			case R.id.ll_adress:
				 Intent ll_adress=new Intent(ConfirmOrder.this,ChanceAdress.class);
				 startActivityForResult(ll_adress, 11);
				break;
			default:
				break;
			}
		}
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode==11){
				if(data!=null){
 
					addressId=data.getIntExtra("id", addressId);
						tv_adresss.setText("收件地址 ： "+data.getStringExtra("adree"));
						tv_name.setText("收件人 ： "+data.getStringExtra("name"));
						tv_tel.setText( data.getStringExtra("tel"));
				}
			}
		}
		private void getData1() { 

			// TODO Auto-generated method stub
 
 
			Url=Url+"80";
			System.out.println("---getData-"+Url);
			 
			MyApplication.getInstance().getClient()
					.post(Url, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("print", responseMsg);
							System.out.println("----"+responseMsg);
						 
							 
							Gson gson = new Gson();
							
							JSONObject jsonobject = null;
							String code = null;
							try {
								jsonobject = new JSONObject(responseMsg);
								code = jsonobject.getString("code");
								int a =jsonobject.getInt("code");
								if(a==Config.CODE){  
	 								String res =jsonobject.getString("result");
	 								moreList= gson.fromJson(res, new TypeToken<List<AdressEntity>>() {
	 			 					}.getType());
				 				 
	 								for(int i =0;i<moreList.size();i++){
	 									if(moreList.get(i).getIsDefault()==1) {
	 										//tv_name,tv_tel,tv_adresss;
	 										addressId=moreList.get(i).getId();
	 										tv_adresss.setText("收件地址 ： "+moreList.get(i).getAddress());
	 										tv_name.setText("收件人 ： "+moreList.get(i).getReceiver());
	 										tv_tel.setText( moreList.get(i).getMoblephone());
	 									}
	 								}
	 								
	 								
	 								
	 								
//	 							myList.addAll(moreList);
//	 				 				handler.sendEmptyMessage(0);
				 					  
				 				 
				 			 
								}else{
									code = jsonobject.getString("message");
									Toast.makeText(getApplicationContext(  ), code, 1000).show();
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
		public void menu_press() {
			View view = getLayoutInflater().inflate(R.layout.popwindow, null);
			// view.findViewById(R.id.todayorder_ordernumber).setOnClickListener(this); 
			// view.findViewById(R.id.todayorder_mobile).setOnClickListener(this);
			menuWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			TextView tv_new = (TextView) view.findViewById(R.id.tv_new);
			TextView tv_all = (TextView) view.findViewById(R.id.tv_all);
			TextView tv_re = (TextView) view.findViewById(R.id.tv_re);
			if (invoice_type == 0) {
				tv_new.setTextColor(getResources().getColor(R.color.bgtitle));
				 
				tv_re.setTextColor(getResources().getColor(R.color.black));

			}
		 
			if (invoice_type == 1) {
				tv_new.setTextColor(getResources().getColor(R.color.black));
				 
				tv_re.setTextColor(getResources().getColor(R.color.bgtitle));
			}
			tv_new.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					invoice_type = 0;
					tv_pop.setText("公司");
					menuWindow.dismiss();
					 
					 
				}
			});
			 
			tv_re.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					invoice_type = 1;
					tv_pop.setText("个人"); 
					menuWindow.dismiss(); 
				}
			});
			menuWindow.setFocusable(true);
			menuWindow.setOutsideTouchable(true);
			menuWindow.update();
			menuWindow.setBackgroundDrawable(new BitmapDrawable());
			// 设置layout在PopupWindow中显示的位置
			// int hight = findViewById(R.id.main_top).getHeight()
			// + Tools.getStatusBarHeight(getApplicationContext());
			menuWindow.showAsDropDown(this.findViewById(R.id.tv_pop),0,0);
//			menuWindow.showAtLocation(this.findViewById(R.id.next_sure),
//					Gravity.TOP | Gravity.RIGHT, 50, 100);
		}
		private void pay() {
			// TODO Auto-generated method stub
			int [] cartid=new int[comfirmList.size()];
			for(int i=0;i<comfirmList.size();i++){
				 
				cartid[i]=comfirmList.get(i).getId();
			}
			comment=et_comment.getText().toString();
			invoice_info =et_info.getText().toString();
			
			// TODO Auto-generated method stub
	 
			RequestParams params = new RequestParams();
			Gson gson = new Gson();
			try {
				System.out.println("id```"+cartid.length); 
				params.put("cartid", new JSONArray(gson.toJson(cartid)));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
 
			params.put("customerId", 80);
		 
			params.put("addressId", 108);
			params.put("comment", "1231");
			params.put("is_need_invoice", 0);
			params.put("invoice_type", 1);
 			params.put("invoice_info","tttt");
 
			params.setUseJsonStreamer(true);
			System.out.println("参数-lee-"+params);
			MyApplication.getInstance().getClient()
					.post(Config.JWB, params, new AsyncHttpResponseHandler() {

						@Override
						public void onSuccess(int statusCode, Header[] headers,
								byte[] responseBody) {
							String responseMsg = new String(responseBody)
									.toString();
							Log.e("print", responseMsg);

							Toast.makeText(getApplicationContext(), responseMsg.toString(), 1000).show();
							 
							Gson gson = new Gson();
							
							JSONObject jsonobject = null;
							String code = null;
							try {
								jsonobject = new JSONObject(responseMsg);
								code = jsonobject.getString("code");
								int a =jsonobject.getInt("code");
								if(a==Config.CODE){ 
								 Toast.makeText(getApplicationContext(), "订单确认成功，请支付", 1000).show();
								 
									Intent i=new Intent(ConfirmOrder.this,PayFromCar.class);
									i.putExtra("key", tv_pay.getText().toString());
									startActivity(i);
								}else{ 
									
									Toast.makeText(getApplicationContext(), "创立订单失败", 1000).show();
									 
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								Toast.makeText(getApplicationContext(), "创立订单失败", 1000).show();
								e.printStackTrace();
								 
							}

						}

						@Override
						public void onFailure(int statusCode, Header[] headers,
								byte[] responseBody, Throwable error) {
							// TODO Auto-generated method stub
							System.out.println("-onFailure---");
						 
						}
					});
	 
			 
		
		
		}
		
		public void getpay11(){
 
			int [] cartid=new int[comfirmList.size()];
			for(int i=0;i<comfirmList.size();i++){
				inn.add(comfirmList.get(i).getId());
				//cartid[i]=comfirmList.get(i).getId();
			}
			comment=et_comment.getText().toString();
			invoice_info =et_info.getText().toString();
			
			API.CARTFIRM(ConfirmOrder.this, MyApplication.NewUser.getId(),inn,
					addressId,comment,is_need_invoice,invoice_type,invoice_info,
	        		
	                new HttpCallback  (ConfirmOrder.this) {

						@Override
						public void onSuccess(Object data) {
							// TODO Auto-generated method stub
						 
						}

						@Override
						public TypeToken getTypeToken() {
							// TODO Auto-generated method stub
							return  null;
						}
	                });
			
			
			
		}
}
