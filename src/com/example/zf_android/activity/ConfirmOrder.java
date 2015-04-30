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
import com.example.zf_android.entity.MyShopCar.Good;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_zandroid.adapter.ComfirmcarAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
/***
 * 购物车订单确认
 * @version    
 *
 */
public class ConfirmOrder extends BaseActivity implements OnClickListener{
	private  ScrollViewWithListView   pos_lv;
	private  int index=0;
	private String howMoney,comment,invoice_info;
	private EditText et_comment,et_info;
	private int customerId;
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private int addressId;
	private ComfirmcarAdapter myAdapter;
	private LinearLayout ll_adress;
	private TextView tv_pop,tv_pay,tv_hj,tv_count,tv_name,tv_tel,tv_adresss;
	private List<Good> comfirmList=new ArrayList<Good>();
	private int  is_need_invoice=0;//否需要发票（1要，0不要
	private int invoice_type=0;//发票类型（0公司  1个人）
	private Button btn_pay;
	private boolean isFirstCreate;
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
		setContentView(R.layout.comfirm_order);
		isFirstCreate=true;
		customerId = MyApplication.getInstance().getCustomerId();
		index=getIntent().getIntExtra("index", 0);
		howMoney=getIntent().getStringExtra("howMoney");
		initView(); 
		getData();
		getData1();
	}
	private void getData() {

		comfirmList=MyApplication.getComfirmList();
		myAdapter=new ComfirmcarAdapter(ConfirmOrder.this, comfirmList);
		pos_lv.setAdapter(myAdapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(!isFirstCreate){
			getData1();
		}else {
			isFirstCreate=false;
		}
		
	}
	private void initView() {
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
		switch (v.getId()) {
		case R.id.tv_pop:
			menu_press();
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
	private void getData1() { 
		String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId+"");
		MyApplication.getInstance().getClient()
		.post(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("responseMsg", responseMsg);
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

						tv_adresss.setText("收件地址 ： ");
						tv_name.setText("收件人 ： ");
						tv_tel.setText("");
						
						for(int i =0;i<moreList.size();i++){
							if(moreList.get(i).getIsDefault()==1) {
								//tv_name,tv_tel,tv_adresss;
								addressId=moreList.get(i).getId();
								tv_adresss.setText("收件地址 ： "+moreList.get(i).getAddress());
								tv_name.setText("收件人 ： "+moreList.get(i).getReceiver());
								tv_tel.setText( moreList.get(i).getMoblephone());
							}
						}

					}else{
						code = jsonobject.getString("message");
						Toast.makeText(getApplicationContext(  ), code, 1000).show();
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
	public void menu_press() {
		View view = getLayoutInflater().inflate(R.layout.popwindow, null);
		menuWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView tv_new = (TextView) view.findViewById(R.id.tv_new);
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
		//			menuWindow.showAtLocation(this.findViewById(R.id.next_sure),
		//					Gravity.TOP | Gravity.RIGHT, 50, 100);
	}

	public void getpay11(){
		int inn[]=new int[comfirmList.size()];
		for(int i=0;i<comfirmList.size();i++){
			inn[i]=comfirmList.get(i).getId();
		}
		comment=et_comment.getText().toString();
		invoice_info =et_info.getText().toString();

		API.CARTFIRM(ConfirmOrder.this, customerId,inn,
				addressId,comment,is_need_invoice,invoice_type,invoice_info,

				new HttpCallback  (ConfirmOrder.this) {

			@Override
			public void onSuccess(Object data) {
				System.out.println(data);
				Intent i1 =new Intent (ConfirmOrder.this,PayFromCar.class);
				i1.putExtra("orderId", data.toString());
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
