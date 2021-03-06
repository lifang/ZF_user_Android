package com.example.zf_android.activity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.DialogUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class GoodConfirm extends BaseActivity implements OnClickListener{
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private TextView tv_sjr,tv_tel,tv_adress;
	private LinearLayout ll_choose;
	private TextView tv_pop,tv_totle,title2,retail_price,showCountText,tv_pay,
					tv_open_price,tv_count,channel_text,content2;
	private Button btn_pay;
	private String comment,invoice_info;
	private ImageView reduce,add,evevt_img;
	PopupWindow menuWindow;
	private int pirce;
	private boolean isFirstCreate;
	private int goodId,paychannelId,quantity,addressId,is_need_invoice=0;
	private EditText buyCountEdit,comment_et,et_titel;
	private CheckBox item_cb;
	private int invoice_type=0;//发票类型（0公司  1个人）
	private int customerId;
	private LinearLayout ll_fp;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_confirm);
		new TitleMenuUtil(GoodConfirm.this, "订单确认").show();
		customerId = MyApplication.getInstance().getCustomerId();

		isFirstCreate=true;
		System.out.println("进入订单确认····");
		initView();
		String image_url = getIntent().getStringExtra("image_url");
		if (!StringUtil.isNull(image_url)) {
			ImageCacheUtil.IMAGE_CACHE.get(image_url,evevt_img);
		}
		title2.setText(getIntent().getStringExtra("getTitle"));
		pirce=getIntent().getIntExtra("price", 0);
		retail_price.setText("￥"+ StringUtil.getMoneyString(pirce));
		goodId=getIntent().getIntExtra("goodId", 1);
		paychannelId=getIntent().getIntExtra("paychannelId", 1);
		tv_pay.setText("实付：￥ "+StringUtil.getMoneyString(pirce)); 
		channel_text.setText(getIntent().getExtras().getString("payChannelName", "")); 
		tv_totle.setText("实付：￥ "+StringUtil.getMoneyString(pirce)); 
		content2.setText(getIntent().getStringExtra("brand")+getIntent().getStringExtra("model"));
		
		tv_open_price.setText("（含开通费￥ "+StringUtil.getMoneyString(getIntent().getIntExtra("open_price", 0))+"）");
		
		System.out.println("=paychannelId=="+paychannelId);
		getData1();

	}
	@Override
	public void onResume() {
		super.onResume();

	}
	private void initView() {
		ll_fp = (LinearLayout)findViewById(R.id.ll_fp);
		tv_open_price = (TextView) findViewById(R.id.tv_open_price);
		
		evevt_img = (ImageView) findViewById(R.id.evevt_img);
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
		content2 = (TextView) findViewById(R.id.content2);
		channel_text = (TextView)findViewById(R.id.channel_text);
		item_cb=(CheckBox) findViewById(R.id.item_cb);
		//过滤前后的空格
		et_titel.setFilters(new InputFilter[]{
				new InputFilter.LengthFilter(16),
				new InputFilter(){    
					public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {   
						if(src.length()<1){
							return null;
						}else{
							char temp [] = (src.toString()).toCharArray();
							char result [] = new char[temp.length];
							for(int i = 0,j=0; i< temp.length; i++){
								if(temp[i] == ' '){
									continue;
								}else{
									result[j++] = temp[i];
								}
							}
							return String.valueOf(result).trim();
						}
					}
				}          
		}); 
		//过滤前后的空格
		comment_et.setFilters(new InputFilter[]{
				new InputFilter.LengthFilter(16),
				new InputFilter(){    
					public CharSequence filter(CharSequence src, int start, int end, Spanned dst, int dstart, int dend) {   
						if(src.length()<1){
							return null;
						}else{
							char temp [] = (src.toString()).toCharArray();
							char result [] = new char[temp.length];
							for(int i = 0,j=0; i< temp.length; i++){
								if(temp[i] == ' '){
									continue;
								}else{
									result[j++] = temp[i];
								}
							}
							return String.valueOf(result).trim();
						}
					}
				}          
		});  

		item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					is_need_invoice=1;
					et_titel.setEnabled(true);
					ll_fp.setVisibility(View.VISIBLE);
				}else{
					is_need_invoice=0;
					et_titel.setText("");
					et_titel.setEnabled(false);
					ll_fp.setVisibility(View.GONE);
				}
			}
		});
		buyCountEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				showCountText.setText("X   "+arg0.toString());
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
	private void getData1() { 
		myList.clear();
		String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId+"");
		loadingDialog = DialogUtil.getLoadingDialg(this);
		loadingDialog.show();

		MyApplication.getInstance().getClient()
		.post(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				loadingDialog.dismiss();
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

						tv_adress.setText("收件地址 ： ");
						tv_sjr.setText("收件人 ： ");
						tv_tel.setText("");
						if (moreList.size() != 0) {
							/*
							 * 判断是否有新增地址
							*/
							if (Config.newAddAddressId != 0) {
								//有新增地址，显示新增地址
								
								int flag = 0;
								for(int i =0;i<moreList.size();i++){
									if (Config.newAddAddressId==moreList.get(i).getId()) {
										addressId=moreList.get(i).getId();
										tv_adress.setText("收件地址 ： "+moreList.get(i).getAddress());
										tv_sjr.setText("收件人 ： "+moreList.get(i).getReceiver());
										tv_tel.setText( moreList.get(i).getMoblephone());
									}else {
										flag ++;
									}
								}
								Config.newAddAddressId = 0;
								//有新增地址，但是新增后又被删除，先选取默认地址，若无默认地址选择第一个地址
								if (flag == moreList.size()) {
									int flag2 = 0;
									for(int i =0;i<moreList.size();i++){
										if(moreList.get(i).getIsDefault()==1) {
											addressId=moreList.get(i).getId();
											tv_adress.setText("收件地址 ： "+moreList.get(i).getAddress());
											tv_sjr.setText("收件人 ： "+moreList.get(i).getReceiver());
											tv_tel.setText( moreList.get(i).getMoblephone());
										}else {
											flag2 ++;
										}
									}
									if (flag2 == moreList.size()) {
										addressId=moreList.get(0).getId();
										tv_adress.setText("收件地址 ： "+moreList.get(0).getAddress());
										tv_sjr.setText("收件人 ： "+moreList.get(0).getReceiver());
										tv_tel.setText( moreList.get(0).getMoblephone());
									}
								}

							}else {
								//无新增地址，先选取默认地址，若无默认地址选择第一个地址
								int mflag = 0;
								for(int i =0;i<moreList.size();i++){
									if(moreList.get(i).getIsDefault()==1) {
										addressId=moreList.get(i).getId();
										tv_adress.setText("收件地址 ： "+moreList.get(i).getAddress());
										tv_sjr.setText("收件人 ： "+moreList.get(i).getReceiver());
										tv_tel.setText( moreList.get(i).getMoblephone());
									}else {
										mflag ++;
									}
								}
								if (mflag == moreList.size()) {
									addressId=moreList.get(0).getId();
									tv_adress.setText("收件地址 ： "+moreList.get(0).getAddress());
									tv_sjr.setText("收件人 ： "+moreList.get(0).getReceiver());
									tv_tel.setText( moreList.get(0).getMoblephone());
								}
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
				loadingDialog.dismiss();
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
			quantity= Integer.parseInt( buyCountEdit.getText().toString() );
			if (addressId != 0) {
				if (quantity > 0) {
					confirmGood();
				}else {
					Toast.makeText(this, "请确认购买数量", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(this, "收件地址不能为空", Toast.LENGTH_SHORT).show();
			}
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
	private void confirmGood() {
		comment=comment_et.getText().toString().trim();
		invoice_info=et_titel.getText().toString().trim();

		if (is_need_invoice == 0) {
			invoice_type = -1;
			invoice_info = "";
		}

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
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==11){
			if(data!=null){
				/*
				 * 点击地址列表返回，id非0
				 * 不是点击地址列表返回的，需要重新访问接口更新数据；目的：防止地址修改，新增，删除后，此activity数据没有更新
				 * 若回调的id不在原地址列表内，也需要重新访问接口，显示数据
				*/
				addressId=data.getIntExtra("id", addressId);
				int mflag = 0;
				for(int i =0;i<moreList.size();i++){
					if(addressId==moreList.get(i).getId()) {
						addressId=moreList.get(i).getId();
						tv_adress.setText("收件地址 ： "+moreList.get(i).getAddress());
						tv_sjr.setText("收件人 ： "+moreList.get(i).getReceiver());
						tv_tel.setText( moreList.get(i).getMoblephone());
					}else {
						mflag ++;
					}
				}
				if (mflag == moreList.size()) {
					getData1();
				}
			}
		}
	}
}
