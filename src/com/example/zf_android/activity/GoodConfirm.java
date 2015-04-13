package com.example.zf_android.activity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GoodConfirm extends BaseActivity implements OnClickListener{
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private TextView tv_sjr,tv_tel,tv_adress;
	private LinearLayout ll_choose;
	private TextView tv_pop,tv_totle,title2,retail_price,showCountText,tv_pay,tv_count;
	private Button btn_pay;
	private String comment,invoice_info;
	private ImageView reduce,add;
	PopupWindow menuWindow;
	private int pirce;
	private int goodId,paychannelId,quantity,addressId,is_need_invoice=0;
	private EditText buyCountEdit,comment_et,et_titel;
	private CheckBox item_cb;
	private int invoice_type=0;//发票类型（0公司  1个人）
	private int customerId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_confirm);
		new TitleMenuUtil(GoodConfirm.this, "订单确认").show();
		customerId = MyApplication.getInstance().getCustomerId();
		
		System.out.println("进入订单确认····");
		initView();
		title2.setText(getIntent().getStringExtra("getTitle"));
		pirce=getIntent().getIntExtra("price", 0);
		retail_price.setText("￥"+ StringUtil.getMoneyString(pirce));
		goodId=getIntent().getIntExtra("goodId", 1);
		paychannelId=getIntent().getIntExtra("paychannelId", 1);
		tv_pay.setText("实付：￥ "+StringUtil.getMoneyString(pirce)); 
		tv_totle.setText("实付：￥ "+StringUtil.getMoneyString(pirce)); 
		System.out.println("=paychannelId=="+paychannelId);
		 getData1();
	}

	private void initView() {
		add=(ImageView) findViewById(R.id.add);
		reduce=(ImageView) findViewById(R.id.reduce);
		reduce.setOnClickListener(this);
		add.setOnClickListener(this);
		tv_pop=(TextView) findViewById(R.id.tv_pop);
		tv_pop.setOnClickListener(this);
		tv_totle=(TextView) findViewById(R.id.tv_totle);
		showCountText=(TextView) findViewById(R.id.showCountText);
		tv_sjr=(TextView) findViewById(R.id.tv_sjr);
		tv_count=(TextView) findViewById(R.id.tv_count);
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		tv_adress=(TextView) findViewById(R.id.tv_adress);
		ll_choose=(LinearLayout) findViewById(R.id.ll_choose);
		ll_choose.setOnClickListener(this);
		title2=(TextView) findViewById(R.id.title2);
		retail_price=(TextView) findViewById(R.id.retail_price);
		btn_pay=(Button) findViewById(R.id.btn_pay);
		btn_pay.setOnClickListener(this);
		tv_pay=(TextView) findViewById(R.id.tv_pay);
		et_titel=(EditText) findViewById(R.id.et_titel);
		buyCountEdit=(EditText) findViewById(R.id.buyCountEdit);
		comment_et=(EditText) findViewById(R.id.comment_et);
		item_cb=(CheckBox) findViewById(R.id.item_cb);
		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					is_need_invoice=1;
				}else{
					is_need_invoice=0;
				}
			}
		});
		buyCountEdit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				showCountText.setText(arg0.toString());
				tv_count.setText("共计:   "+arg0+"件");
				 if( buyCountEdit.getText().toString().equals("")){
					 quantity=0;
				 }else{
					 quantity= Integer.parseInt( buyCountEdit.getText().toString() );
				 }
			 
				 tv_totle.setText("实付：￥ "+StringUtil.getMoneyString(pirce*quantity)); 
				tv_pay.setText("实付：￥ "+StringUtil.getMoneyString(pirce*quantity)); 
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
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
				invoice_type = 0;
				tv_pop.setText("公司");
				menuWindow.dismiss();
				 
				 
			}
		});
		 
		tv_re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
//		menuWindow.showAtLocation(this.findViewById(R.id.next_sure),
//				Gravity.TOP | Gravity.RIGHT, 50, 100);
	}
	private void getData() {
		 String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId);

			MyApplication.getInstance().getClient()
					.get(url, new AsyncHttpResponseHandler() {

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
//									jsonobject = new JSONObject(res);
									
	 								moreList= gson.fromJson(res, new TypeToken<List<AdressEntity>>() {
	 			 					}.getType());
				 				 
	 								myList.addAll(moreList);
	 				 			 for(int i=0;i<myList.size();i++){
	 				 				 if(myList.get(i).getIsDefault()==1){
	 				 					tv_sjr.setText("收件人： "+myList.get(i).getReceiver());
	 				 					tv_tel.setText(myList.get(i).getMoblephone());
	 				 					tv_adress.setText("地址："+myList.get(i).getAddress());
	 				 					addressId=myList.get(i).getId();
	 				 				 }
	 				 			 }
				 					  
				 				 
				 			 
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
							System.out.println("-onFailure---");
							Log.e("print", "-onFailure---" + error);
						}
					});
	 
			 
	 
	}
	private void getData1() { 
		 String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId);
		MyApplication.getInstance().getClient()
				.post(url, new AsyncHttpResponseHandler() {
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
 										tv_adress.setText("收件地址 ： "+moreList.get(i).getAddress());
 										tv_sjr.setText("收件人 ： "+moreList.get(i).getReceiver());
 										tv_tel.setText( moreList.get(i).getMoblephone());
 									}
 								}
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
						System.out.println("-onFailure---");
						Log.e("print", "-onFailure---" + error);
					}
				});
	}
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_pop:
			menu_press();
			break;
		case R.id.ll_choose:
			Intent i =new Intent(GoodConfirm.this,ChanceAdress.class);
			startActivityForResult(i, 11);
			break;
		case R.id.btn_pay:
		   confirmGood();
			break;
		case R.id.add:
			quantity= Integer.parseInt( buyCountEdit.getText().toString() )+1;
			buyCountEdit.setText(quantity+"");
				break;
		case R.id.reduce:
			if(quantity==0){
				break;
			}
			quantity= Integer.parseInt( buyCountEdit.getText().toString() )-1;
			buyCountEdit.setText(quantity+"");
				break;
		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==11){
			if(data!=null){

				addressId=data.getIntExtra("id", addressId);
				tv_adress.setText("收件地址 ： "+data.getStringExtra("adree"));
				tv_sjr.setText("收件人 ： "+data.getStringExtra("name"));
				tv_tel.setText( data.getStringExtra("tel"));
			}
		}
	}
	private void confirmGood() {
		 
		 //quantity addressId comment is_need_invoice et_titel  
		quantity= Integer.parseInt( buyCountEdit.getText().toString() );
		comment=comment_et.getText().toString();
		RequestParams params = new RequestParams();
		params.put("customerId", customerId);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("addressId", addressId);
		params.put("quantity", quantity);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		params.put("invoice_type", invoice_type);
		params.put("invoice_info", et_titel.getText().toString());
		params.setUseJsonStreamer(true);
 
		invoice_info=et_titel.getText().toString();
		API.goodConfirm(GoodConfirm.this,customerId,goodId,paychannelId,
				quantity,addressId,comment,is_need_invoice,invoice_type,invoice_info,
        		
                new HttpCallback  (GoodConfirm.this) {
					@Override
					public void onSuccess(Object data) {
						Intent i1 =new Intent (GoodConfirm.this,PayFromCar.class);
						String orderId = data.toString();
						i1.putExtra("orderId", orderId);
						startActivity(i1);	
						finish();
					}

					@Override
					public TypeToken getTypeToken() {
						return  null;
					}
                });
	}
}
