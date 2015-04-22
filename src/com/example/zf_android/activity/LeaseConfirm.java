package com.example.zf_android.activity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
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
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.GoodinfoEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LeaseConfirm extends BaseActivity implements OnClickListener{
	List<AdressEntity>  myList = new ArrayList<AdressEntity>();
	List<AdressEntity>  moreList = new ArrayList<AdressEntity>();
	private TextView tv_sjr,tv_tel,tv_adress;
	private LinearLayout ll_choose,llll;
	private TextView tv_yajin,tv_lkl,tv_totle,title2,retail_price,showCountText,tv_pay,tv_count,channel_text,content2;
	private TextView leasepact;
	private Button btn_pay;
	private String comment;
	private ImageView reduce,add;
	private int price;
	private int goodId,paychannelId,quantity,addressId,is_need_invoice=0;
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
		title2.setText(good.getTitle());
		content2.setText(good.getGood_brand()+good.getModel_number());
		channel_text.setText(getIntent().getExtras().getString("payChannelName", ""));
		buyCountEdit.setText(good.getLease_time()+"");
		price=good.getLease_price();
		retail_price.setText("￥"+ StringUtil.getMoneyString(price));
		goodId=good.getId();
		paychannelId=getIntent().getIntExtra("paychannelId", 0);
		yajin=good.getLease_deposit();
		tv_yajin.setText("￥   "+StringUtil.getMoneyString(yajin));
		computeMoney();
		getData();
	}
	@Override
	protected void onResume() {
		super.onResume();
//		if(!isFirstCreate){
//			getData();
//		}else {
//			isFirstCreate=false;
//		}

	}
	private void computeMoney(){
		tv_pay.setText("实付：￥ "+StringUtil.getMoneyString((price*quantity+yajin))); 
		tv_totle.setText("合计：￥ "+StringUtil.getMoneyString((price*quantity+yajin))); 
	}

	private void initView() {
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
					btn_pay.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_blue_shape));
					btn_pay.setEnabled(true);
				}else{
					btn_pay.setTextColor(getResources().getColor(R.color.text292929));
					btn_pay.setBackgroundDrawable(getResources().getDrawable(R.drawable.send_out_goods_shape));
					btn_pay.setEnabled(false);
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
		String url = MessageFormat.format(Config.URL_ADDRESS_LIST, customerId);
		System.out.println("---getData-");
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
						//									jsonobject = new JSONObject(res);

						moreList= gson.fromJson(res, new TypeToken<List<AdressEntity>>() {
						}.getType());

						myList.addAll(moreList);
						
						tv_sjr.setText("收件人： ");
						tv_tel.setText("");
						tv_adress.setText("地址：");
						
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
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.leasepact:
			Intent intent1 = new Intent(this,LeaseAgreementActivity.class);
			intent1.putExtra("leasepact", good.getLease_agreement());
			startActivity(intent1);
			break;
		case R.id.ll_choose:
			Intent i =new Intent(LeaseConfirm.this,ChanceAdress.class);
			startActivityForResult(i, 11);
			break;
		case R.id.tv_lkl:
			Intent tv_ins =new Intent(LeaseConfirm.this, LeaseInstruction.class); 
			startActivity(tv_ins);
			break;
		case R.id.btn_pay:
			quantity= Integer.parseInt( buyCountEdit.getText().toString() );
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
			quantity= Integer.parseInt( buyCountEdit.getText().toString() )+1;
			buyCountEdit.setText(quantity+"");
			//}
			break;
		case R.id.reduce:
			//	if(good.getLease_time() < quantity){
			if(quantity==0){
				break;
			}
			quantity= Integer.parseInt( buyCountEdit.getText().toString() )-1;
			buyCountEdit.setText(quantity+"");
			//}
			break;
		default:
			break;
		}
	}
	private void confirmGood() {
		
		comment=comment_et.getText().toString();
		RequestParams params = new RequestParams();
		params.put("customerId", customerId);
		params.put("goodId", goodId);
		params.put("paychannelId", paychannelId);
		params.put("addressId", addressId);
		params.put("quantity", quantity);
		params.put("comment", comment);
		params.put("is_need_invoice", is_need_invoice);
		//		params.put("invoice_type", invoice_type);
		//		params.put("invoice_info", et_titel.getText().toString());
		params.setUseJsonStreamer(true);

		String Urla=Config.URL_ORDER_LEASE;
		MyApplication.getInstance().getClient()
		.post(Urla, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
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

				addressId=data.getIntExtra("id", addressId);
				tv_adress.setText("收件地址 ： "+data.getStringExtra("adree"));
				tv_sjr.setText("收件人 ： "+data.getStringExtra("name"));
				tv_tel.setText( data.getStringExtra("tel"));
			}
		}
	}
}
