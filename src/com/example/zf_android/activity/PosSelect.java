package com.example.zf_android.activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.PosItem;
import com.example.zf_android.entity.PosSelectEntity;
import com.example.zf_android.entity.PrePosItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PosSelect extends BaseActivity implements  OnClickListener{
	private ImageView img_on_off;
	private Boolean isOpen_mineset;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private EditText et1,et2;
	private int 	has_purchase=0;
	private List<PrePosItem> sonlist=new ArrayList<PrePosItem>();

	private ArrayList<Integer>  brands_id = new ArrayList<Integer>();
	private ArrayList<Integer>  category_id = new ArrayList<Integer>();
	private ArrayList<Integer>  pay_channel_id = new ArrayList<Integer>();

	private ArrayList<Integer>  pay_card_id = new ArrayList<Integer>();
	private ArrayList<Integer>  trade_type_id = new ArrayList<Integer>();
	private ArrayList<Integer>  sale_slip_id = new ArrayList<Integer>();
	private ArrayList<Integer>  tDate = new ArrayList<Integer>();

	private PosSelectEntity pse;
	private List<PosItem> mylist=new ArrayList<PosItem>();
	private LinearLayout ll_pp,ll_type, ll_pay_type,ll_paycard_type,ll_jy_type,ll_qgd_type,ll_time,ll_zdj,ll_zgj;
	private TextView next_sure,tv_back,tv_pp,tv_type,tv_pay_type,tv_paycard_type,tv_jy_type,tv_qgd_type,tv_time,tv_zdj,tv_zgj;
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posselect);
		initView();
		gateData();
	}
	private void gateData() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("city_id",MyApplication.getInstance().getCityId());
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}

		MyApplication.getInstance().getClient()
		.post(getApplicationContext(),Config.URL_GOOD_SEARCH, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("print", responseMsg);

				Gson gson = new Gson();

				JSONObject jsonobject = null;
				String code = null;
				try {
					jsonobject = new JSONObject(responseMsg);
					code = jsonobject.getString("code");
					int a =jsonobject.getInt("code");
					if(a==Config.CODE){  

						String res =jsonobject.getString("result");

						pse= gson.fromJson(res, new TypeToken <PosSelectEntity> () {
						}.getType());
						MyApplication.pse = pse;
						System.out.println(MyApplication.pse.getBrands().size()+"---"+pse.getBrands().size());

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
	private void initView() {

		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		isOpen_mineset=mySharedPreferences.getBoolean("isOpen_ps", true);
		img_on_off=(ImageView) findViewById(R.id.img_on_off);
		if(!isOpen_mineset){
			img_on_off.setBackgroundResource(R.drawable.pos_off);

		}
		img_on_off.setOnClickListener(this);

		et1=(EditText) findViewById(R.id.et_zdj);
		et2=(EditText) findViewById(R.id.et_zgj);

		tv_time=(TextView) findViewById(R.id.tv_time);
		tv_qgd_type=(TextView) findViewById(R.id.tv_qgd_type);
		tv_jy_type=(TextView) findViewById(R.id.tv_jy_type);
		tv_paycard_type=(TextView) findViewById(R.id.tv_paycard_type);
		tv_pay_type=(TextView) findViewById(R.id.tv_pay_type);
		tv_type=(TextView) findViewById(R.id.tv_type);
		tv_pp=(TextView) findViewById(R.id.tv_pp);
		img_on_off=(ImageView) findViewById(R.id.img_on_off);
		img_on_off.setOnClickListener(this);
		ll_pp=(LinearLayout) findViewById(R.id.ll_pp);
		ll_pp.setOnClickListener(this);
		ll_type=(LinearLayout) findViewById(R.id.ll_type);
		ll_type.setOnClickListener(this);
		ll_pay_type=(LinearLayout) findViewById(R.id.ll_pay_type);
		ll_pay_type.setOnClickListener(this);
		ll_paycard_type=(LinearLayout) findViewById(R.id.ll_paycard_type);
		ll_paycard_type.setOnClickListener(this);
		ll_jy_type=(LinearLayout) findViewById(R.id.ll_jy_type);
		ll_jy_type.setOnClickListener(this);
		ll_qgd_type=(LinearLayout) findViewById(R.id.ll_qgd_type);
		ll_qgd_type.setOnClickListener(this);
		ll_time=(LinearLayout) findViewById(R.id.ll_time);
		ll_time.setOnClickListener(this);
		next_sure=(TextView) findViewById(R.id.next_sure);
		next_sure.setOnClickListener(this);
		tv_back=(TextView) findViewById(R.id.tv_back);
		tv_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next_sure:  // 
			int a=0 ;int b=0;
			if(StringUtil.isNull(et1.getText().toString())){
				a=0;
			}else{
				a=Integer.parseInt(  et1.getText().toString());
			}
			if(StringUtil.isNull(et2.getText().toString())){
				b=0;
			}else{
				b=Integer.parseInt(  et2.getText().toString());
			}

			if(a==0||b==0){
				Intent intent2 = new Intent();
				intent2.putExtra("minPrice", a);
				intent2.putExtra("maxPrice", b);
				intent2.putExtra("has_purchase", has_purchase);
				intent2.putIntegerArrayListExtra("brands_id", brands_id);
				intent2.putIntegerArrayListExtra("category_id", category_id);
				intent2.putIntegerArrayListExtra("pay_channel_id", pay_channel_id);

				intent2.putIntegerArrayListExtra("pay_card_id", pay_card_id);
				intent2.putIntegerArrayListExtra("trade_type_id", trade_type_id);
				intent2.putIntegerArrayListExtra("sale_slip_id", sale_slip_id);
				intent2.putIntegerArrayListExtra("tDate", tDate);
				PosSelect.this.setResult(1, intent2);
				finish();
			}else{
				if(a>b){
					Toast.makeText(getApplicationContext(), "最低价必须比最高价低", 1000).show();
					break;
				}else{
					Intent intent2 = new Intent();
					intent2.putExtra("minPrice", a);
					intent2.putExtra("maxPrice", b);
					intent2.putExtra("has_purchase", has_purchase);
					intent2.putIntegerArrayListExtra("brands_id", brands_id);
					intent2.putIntegerArrayListExtra("category_id", category_id);
					intent2.putIntegerArrayListExtra("pay_channel_id", pay_channel_id);

					intent2.putIntegerArrayListExtra("pay_card_id", pay_card_id);
					intent2.putIntegerArrayListExtra("trade_type_id", trade_type_id);
					intent2.putIntegerArrayListExtra("sale_slip_id", sale_slip_id);
					intent2.putIntegerArrayListExtra("tDate", tDate);
					PosSelect.this.setResult(1, intent2);
					finish();
				}
			}
			break;
		case R.id.tv_back:  // next_sure
			finish();
			break;
		case R.id.ll_pp:  // 
			Intent ll_pp=new Intent(PosSelect.this, PosSelecList.class);
			ll_pp.putExtra("key", "选择POS品牌");
			ll_pp.putExtra("index", 100);

			startActivityForResult(ll_pp, 100);
			break;
		case R.id.ll_type:  //  
			Intent ll_type=new Intent(PosSelect.this, PosSelecSon.class);
			ll_type.putExtra("key", "选择POS类型");
			ll_type.putExtra("index", 101);

			startActivityForResult(ll_type, 101);
			break;
		case R.id.ll_pay_type:  //  
			Intent ll_pay_type=new Intent(PosSelect.this, PosSelecList.class);
			ll_pay_type.putExtra("key", "选择支付通道");
			ll_pay_type.putExtra("index", 102);

			startActivityForResult(ll_pay_type, 102);
			break;
		case R.id.ll_paycard_type:  
			Intent ll_paycard_type=new Intent(PosSelect.this, PosSelecList.class);
			ll_paycard_type.putExtra("key", "选择支付卡类型");
			ll_paycard_type.putExtra("index", 103);

			startActivityForResult(ll_paycard_type, 103);
			break;
		case R.id.ll_jy_type:  
			Intent ll_jy_type=new Intent(PosSelect.this, PosSelecList.class);
			ll_jy_type.putExtra("key", "选择支付交易类型");
			ll_jy_type.putExtra("index", 104);

			startActivityForResult(ll_jy_type, 104);
			break;	

		case R.id.ll_qgd_type:   
			Intent ll_qgd_type=new Intent(PosSelect.this, PosSelecList.class);
			ll_qgd_type.putExtra("key", "选择签购单方式");
			ll_qgd_type.putExtra("index", 105);

			startActivityForResult(ll_qgd_type, 105 	);

			break;
		case R.id.ll_time:   
			Intent ll_time=new Intent(PosSelect.this, PosSelecList.class);
			ll_time.putExtra("key", "选择对账日期");
			ll_time.putExtra("index", 106);

			startActivityForResult(ll_time, 106);
			break;	

		case R.id.img_on_off:

			if(isOpen_mineset){
				isOpen_mineset=false;
				//img_on_off.setBackgroundDrawable(getResources().getDrawable(R.drawable.pos_off));
				img_on_off.setBackgroundResource(R.drawable.pos_off);
				editor.putBoolean("isOpen_ps",false);
				editor.commit();
				has_purchase=0;

			}else{
				isOpen_mineset=true;
				img_on_off.setBackgroundResource(R.drawable.pos_on);		 
				editor.putBoolean("isOpen_ps",true);
				editor.commit();
				has_purchase=1;
			}

			break;
		default:
			break;
		}
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case 100:
			if(data!=null){

				tv_pp.setText(data.getStringExtra("text"));
				brands_id=data.getIntegerArrayListExtra("ids");
				System.out.println("brands_id"+brands_id.toString());
			}
			break;
		case 101:
			if(data!=null){
				category_id=data.getIntegerArrayListExtra("ids");
				tv_type.setText(data.getStringExtra("text"));
			}

			break;
		case 102: 
			if(data!=null){
				pay_channel_id=data.getIntegerArrayListExtra("ids");
				tv_pay_type.setText(data.getStringExtra("text"));
				System.out.println("pay_channel_id"+pay_channel_id.toString());
			}

			break;
		case 103: 
			if(data!=null){

				tv_paycard_type.setText(data.getStringExtra("text"));

				pay_card_id=data.getIntegerArrayListExtra("ids");
				System.out.println("pay_card_id"+pay_card_id.toString());
			}

			break;
		case 104: 
			if(data!=null){

				tv_jy_type.setText(data.getStringExtra("text"));

				trade_type_id=data.getIntegerArrayListExtra("ids");
				System.out.println("trade_type_id"+trade_type_id.toString());
			}

			break;
		case 105: 
			if(data!=null){

				tv_qgd_type.setText(data.getStringExtra("text"));

				sale_slip_id=data.getIntegerArrayListExtra("ids");
				System.out.println("sale_slip_id"+sale_slip_id.toString());
			}

			break;
		case 106: 
			if(data!=null){

				tv_time.setText(data.getStringExtra("text"));

				tDate=data.getIntegerArrayListExtra("ids");
				System.out.println("tDate"+tDate.toString());
			}

			break;
		default:
			break;

		}
		super.onActivityResult(requestCode, resultCode, data);

	}
}
