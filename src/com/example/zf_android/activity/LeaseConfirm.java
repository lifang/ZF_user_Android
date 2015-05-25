package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.trade.common.DialogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class LeaseConfirm extends BaseActivity implements OnClickListener{
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private TextView tv_sjr,tv_tel,tv_adress;
	private LinearLayout ll_choose,llll;
	private TextView tv_yajin,tv_lkl,tv_totle,title2,retail_price,showCountText,tv_pay,tv_count,channel_text,content2;
	private TextView leasepact;
	private Button btn_pay;
	private String comment;
	private ImageView reduce,add,evevt_img;
	private int price;
	private int quantity = 1;
	private int goodId,paychannelId,addressId,is_need_invoice=0;
	private EditText buyCountEdit,comment_et,et_titel;
	private CheckBox item_cb;
	private int yajin;
	private int invoice_type=1;//发票类型（0公司  1个人）
	private int customerId;
	private GoodinfoEntity good;
	private boolean isFirstCreate;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lea_confirm);
		new TitleMenuUtil(LeaseConfirm.this, "租赁订单确认").show();
		customerId = MyApplication.getInstance().getCustomerId();
		initView();
		isFirstCreate=true;
		good = (GoodinfoEntity)getIntent().getSerializableExtra("good");
		if(good == null){
			finish();
		}
		String image_url = getIntent().getStringExtra("image_url");
		if (!StringUtil.isNull(image_url)) {
			ImageCacheUtil.IMAGE_CACHE.get(image_url,evevt_img);
		}

		title2.setText(good.getTitle());
		content2.setText(good.getGood_brand()+good.getModel_number());
		channel_text.setText(getIntent().getExtras().getString("payChannelName", ""));
		//buyCountEdit.setText(good.getLease_time()+"");
		//price=good.getLease_price();
		price = getIntent().getIntExtra("price", 0);
		retail_price.setText("￥"+ StringUtil.getMoneyString(price));
		goodId=good.getId();
		paychannelId=getIntent().getIntExtra("paychannelId", 0);
		yajin=good.getLease_deposit();
		tv_yajin.setText("￥   "+StringUtil.getMoneyString(yajin));
		computeMoney();
		getData();
	}
	@Override
	public void onResume() {
		super.onResume();

	}
	private void computeMoney(){
		tv_pay.setText("实付：￥ "+StringUtil.getMoneyString(price*quantity)); 
		tv_totle.setText("合计：￥ "+StringUtil.getMoneyString(price*quantity)); 
	}

	private void initView() {
		evevt_img = (ImageView) findViewById(R.id.evevt_img);
		add=(ImageView) findViewById(R.id.add);
		reduce=(ImageView) findViewById(R.id.reduce);
		reduce.setOnClickListener(this);
		add.setOnClickListener(this);
		leasepact = (TextView) findViewById(R.id.leasepact);
		leasepact.setOnClickListener(this);
		tv_totle=(TextView) findViewById(R.id.tv_totle);
		showCountText=(TextView) findViewById(R.id.showCountText);
		tv_sjr=(TextView) findViewById(R.id.tv_sjr);
		tv_count=(TextView) findViewById(R.id.tv_count);
		tv_tel=(TextView) findViewById(R.id.tv_tel);
		tv_adress=(TextView) findViewById(R.id.tv_adress);
		content2 = (TextView) findViewById(R.id.content2);
		channel_text = (TextView)findViewById(R.id.channel_text);
		tv_lkl=(TextView) findViewById(R.id.tv_lkl);
		tv_lkl.setOnClickListener(this);
		tv_yajin=(TextView) findViewById(R.id.tv_yajin);
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
					btn_pay.setTextColor(getResources().getColor(R.color.ffffff));
					btn_pay.setBackgroundResource(R.drawable.bg_blue_shape);
					btn_pay.setEnabled(true);
				}else{
					btn_pay.setTextColor(getResources().getColor(R.color.text292929));
					btn_pay.setBackgroundResource(R.drawable.send_out_goods_shape);
					btn_pay.setEnabled(false);
				}
			}
		});
		buyCountEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				showCountText.setText("X   "+arg0.toString());
				tv_count.setText("共计:   "+arg0+"件");
				if( buyCountEdit.getText().toString().trim().equals("")){
					quantity=1;
				}else{
					quantity= Integer.parseInt( buyCountEdit.getText().toString().trim() );
				}
				computeMoney();

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

	private void getData() {
		myList.clear();
		String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId+"");
		System.out.println("---getData-");
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
						//									jsonobject = new JSONObject(res);

						moreList= gson.fromJson(res, new TypeToken<List<AdressEntity>>() {
						}.getType());

						myList.addAll(moreList);

						tv_sjr.setText("收件人： ");
						tv_tel.setText("");
						tv_adress.setText("地址：");

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
		case R.id.leasepact:
//			Intent intent1 = new Intent(this,LeaseAgreementActivity.class);
//			intent1.putExtra("leasepact", good.getLease_agreement());
//			startActivity(intent1);
			Intent tv_ins =new Intent(LeaseConfirm.this, LeaseInstruction.class); 
			startActivity(tv_ins);
			break;
		case R.id.ll_choose:
			Intent i =new Intent(LeaseConfirm.this,ChanceAdress.class);
			startActivityForResult(i, 11);
			break;
		case R.id.tv_lkl:
//			Intent tv_ins =new Intent(LeaseConfirm.this, LeaseInstruction.class); 
//			startActivity(tv_ins);
			break;
		case R.id.btn_pay:
			quantity= Integer.parseInt( buyCountEdit.getText().toString().trim() );
			if (addressId != 0) {
				if (quantity > 0) {
					confirmGood();
				}else {
					Toast.makeText(this, "请确认租赁数量", Toast.LENGTH_SHORT).show();
				}
			}else {
				Toast.makeText(this, "收件地址不能为空", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.add:
			//	if(good.getReturn_time() > quantity){
			quantity= Integer.parseInt( buyCountEdit.getText().toString().trim() )+1;
			buyCountEdit.setText(quantity+"");
			//}
			break;
		case R.id.reduce:
			//	if(good.getLease_time() < quantity){
			if(quantity==0){
				break;
			}
			quantity= Integer.parseInt( buyCountEdit.getText().toString().trim() )-1;
			buyCountEdit.setText(quantity+"");
			//}
			break;
		default:
			break;
		}
	}
	private void confirmGood() {

		comment=comment_et.getText().toString().trim();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("addressId", addressId);
		params.put("quantity", quantity);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}

		//		RequestParams params = new RequestParams();
		//		params.put("customerId", customerId);
		//		params.put("goodId", goodId);
		//		params.put("paychannelId", paychannelId);
		//		params.put("addressId", addressId);
		//		params.put("quantity", quantity);
		//		params.put("comment", comment);
		//		params.put("is_need_invoice", is_need_invoice);
		//		params.setUseJsonStreamer(true);

		String Urla=Config.URL_ORDER_LEASE;
		//		MyApplication.getInstance().getClient()
		//		.post(Urla, params, new AsyncHttpResponseHandler() {
		loadingDialog = DialogUtil.getLoadingDialg(this);
		loadingDialog.show();
		MyApplication.getInstance().getClient()
		.post(getApplicationContext(),Urla, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				loadingDialog.dismiss();
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("print", responseMsg);
				System.out.println("----"+responseMsg);

				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					int a =jsonobject.getInt("code");
					if(a==Config.CODE){  
						Intent i1 =new Intent (LeaseConfirm.this,PayFromCar.class);
						i1.putExtra("orderId", jsonobject.getString("result"));
						startActivity(i1);
						finish();
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
				// TODO Auto-generated method stub
				loadingDialog.dismiss();
				System.out.println("-onFailure---");
				Log.e("print", "-onFailure---" + error);
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
					getData();
				}
			}
		}
	}
}
