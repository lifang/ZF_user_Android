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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.JSONUtils;

import com.examlpe.zf_android.util.FlowLayout;
import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.ScrollViewWithGView;
import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.ApplyneedEntity;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_android.entity.FactoryEntity;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.entity.PosEntity;
import com.example.zf_android.trade.common.HttpRequest;
import com.example.zf_android.trade.entity.GriviewEntity;
import com.example.zf_zandroid.adapter.GridviewAdapter;
import com.example.zf_zandroid.adapter.HuilvAdapter;
import com.example.zf_zandroid.adapter.HuilvAdapter1;
import com.example.zf_zandroid.adapter.HuilvAdapter2;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GoodDeatail extends BaseActivity implements OnClickListener{
	private Button setting_btn_clear1,setting_btn_clear;
	private int id;
	private LinearLayout titleback_linear_back;
	private ImageView image,search2,fac_img;
	private List<String> ma = new ArrayList<String>();
	List<PosEntity>  myList = new ArrayList<PosEntity>();
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//存放引到图片数组
	private View item ;
	private LayoutInflater inflater;
	private int  index_ima=0;
	GoodinfoEntity gfe;
	private String chanel="";
	private int  	commentsCount;
	FactoryEntity factoryEntity;
	FactoryEntity factory;
	private  TextView tvc_zx,tvc_qy,tv_sqkt,tv_bug,tv_lea,tv_title,content1,tv_pp,tv_xh,tv_ys,tv_price,tv_lx,tv_sjhttp
	,tv_spxx,fac_detai,ppxx,wkxx,dcxx,tv_qgd,tv_jm,tv_comment,tv_appneed,tv_ins,tv_huilv;
	private ScrollViewWithListView  pos_lv1,pos_lv2,pos_lv3;
	private HuilvAdapter lvAdapter;
	private HuilvAdapter1 lvAdapter2;
	private HuilvAdapter2 lvAdapter3;
	List<ApplyneedEntity>  publist = new ArrayList<ApplyneedEntity>();
	List<ApplyneedEntity>  singlelist = new ArrayList<ApplyneedEntity>();
	private ArrayList<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	private ArrayList<String> arelist = new ArrayList<String>();
	private ArrayList<ChanelEntitiy> celist2 = new ArrayList<ChanelEntitiy>();
	private ArrayList<ChanelEntitiy> celist3 = new ArrayList<ChanelEntitiy>();
	private GridviewAdapter gadapter;
	//private ButtonGridviewAdapter buttonAdapter;
	private Boolean islea=false;
	private ScrollViewWithGView gview;
	private FlowLayout flowLayout;
	List<GriviewEntity>  User_button = new ArrayList<GriviewEntity>();
	private int paychannelId ,goodId,quantity;
	private String payChannelName = "";
	private int opening_cost;
	private int all_price;
	private ImageView img_see;
	List<View> list = new ArrayList<View>();

	private SharedPreferences mySharedPreferences;
	private Boolean islogin;
	private int customerId;

	private List<Integer> labelIds = new ArrayList<Integer>();
	private List<Integer> labelSelectIds = new ArrayList<Integer>();

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				list.clear();
				for (int i = 0; i <ma.size(); i++) {			 
					item = inflater.inflate(R.layout.item, null);
					list.add(item);
				}
				indicator_imgs	= new ImageView[ma.size()];
				initIndicator();
				adapter.notifyDataSetChanged();
				//刷新列表数据
				tv_title.setText(gfe.getTitle());
				content1.setText(gfe.getSecond_title());
				tv_pp.setText(gfe.getGood_brand());
				tv_xh.setText(gfe.getModel_number());
				tv_ys.setText("已售"+gfe.getVolume_number());
				all_price = gfe.getRetail_price()+opening_cost;
				tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
				tv_lx.setText(gfe.getCategory() );
				if(factoryEntity != null){
					ImageCacheUtil.IMAGE_CACHE.get(factoryEntity.getLogo_file_path(),
							fac_img);
					tv_sjhttp.setText(factoryEntity.getWebsite_url() );
					fac_detai.setText(factoryEntity.getDescription() );
				}
				tv_spxx.setText(gfe.getDescription() );
				ppxx.setText(gfe.getModel_number() );
				wkxx.setText(gfe.getShell_material() );
				dcxx.setText(gfe.getBattery_info());
				tv_qgd.setText(gfe.getSign_order_way());
				tv_jm.setText(gfe.getEncrypt_card_way());

				if(gfe.isHas_lease()){
					tv_lea.setVisibility(View.VISIBLE);
				}else{
					tv_lea.setVisibility(View.INVISIBLE);
				}
				//	lvAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(), "网络未连接",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:

				break;
			case 4:

				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_detail);

		mySharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
		islogin=mySharedPreferences.getBoolean("islogin", false);
		customerId = mySharedPreferences.getInt("id", 0);
		MyApplication.getInstance().setCustomerId(customerId);

		id=getIntent().getIntExtra("id", 0);
		innitView();

		view_pager.setFocusable(true);
		view_pager.setFocusableInTouchMode(true);
		view_pager.requestFocus();

		gview=(ScrollViewWithGView) findViewById(R.id.gview);
		getdata();

	}
	private void innitView() {
		tvc_zx=(TextView) findViewById(R.id.tvc_zx);
		tvc_qy=(TextView) findViewById(R.id.tvc_qy);
		tv_lea=(TextView) findViewById(R.id.tv_lea);
		tv_lea.setOnClickListener(this);
		tv_bug=(TextView) findViewById(R.id.tv_bug);
		tv_bug.setOnClickListener(this);
		tv_sqkt=(TextView) findViewById(R.id.tv_sqkt);
		tv_huilv=(TextView) findViewById(R.id.tv_huilv);
		tv_huilv.setOnClickListener(this);
		tv_ins=(TextView) findViewById(R.id.tv_ins);
		tv_ins.setOnClickListener(this);
		tv_appneed=(TextView) findViewById(R.id.tv_appneed);
		tv_appneed.setOnClickListener(this);
		tv_comment=(TextView) findViewById(R.id.tv_comment);
		tv_comment.setOnClickListener(this);
		img_see = (ImageView)findViewById(R.id.img_see);
		img_see.setOnClickListener(this);
		view_pager = (ViewPager) findViewById(R.id.view_pager); 
		inflater = LayoutInflater.from(this); 
		flowLayout = (FlowLayout) findViewById(R.id.flowLayout);
		adapter = new MyAdapter(list); 
		view_pager.setAdapter(adapter);
		view_pager.setOnPageChangeListener(new MyListener());
		setting_btn_clear1=(Button) findViewById(R.id.setting_btn_clear1);
		setting_btn_clear1.setOnClickListener(this);
		setting_btn_clear=(Button) findViewById(R.id.setting_btn_clear);
		setting_btn_clear.setOnClickListener(this);
		pos_lv1=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		pos_lv2=(ScrollViewWithListView) findViewById(R.id.pos_lv2);
		pos_lv3=(ScrollViewWithListView) findViewById(R.id.pos_lv3);

		//页面刷新数据
		titleback_linear_back=(LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(this);
		search2=(ImageView) findViewById(R.id.search2);
		search2.setOnClickListener(this);
		tv_ys=(TextView) findViewById(R.id.tv_y1s);
		tv_xh=(TextView) findViewById(R.id.tv_xh);
		tv_title=(TextView) findViewById(R.id.tv_title);
		content1=(TextView) findViewById(R.id.content1);
		tv_pp=(TextView) findViewById(R.id.tv_PP);
		tv_price=(TextView) findViewById(R.id.tv_price);
		tv_lx=(TextView) findViewById(R.id.tv_lx);
		tv_sjhttp=(TextView) findViewById(R.id.tv_sjhttp);
		tv_sjhttp.setOnClickListener(this);
		tv_spxx=(TextView) findViewById(R.id.tv_spxx);
		fac_detai=(TextView) findViewById(R.id.fac_detai);
		fac_img=(ImageView) findViewById(R.id.fac_img);
		ppxx=(TextView) findViewById(R.id.ppxx);
		wkxx=(TextView) findViewById(R.id.wkxx);
		dcxx=(TextView) findViewById(R.id.dcxx);
		tv_qgd=(TextView) findViewById(R.id.tv_qgd);
		tv_jm=(TextView) findViewById(R.id.tv_jm);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_huilv:
			//tv_bug  
			Intent tv_huilv =new Intent(GoodDeatail.this, TradeRate.class);
			startActivity(tv_huilv);
			break;

		case R.id.tv_bug:
			all_price = gfe.getRetail_price()+opening_cost;
			tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
			islea=false;
			setting_btn_clear1.setClickable(true);
			setting_btn_clear.setText("立即购买");
			setting_btn_clear1.setBackgroundResource(R.drawable.bg_shape);
			setting_btn_clear1.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_bug.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_bug.setBackgroundResource(R.drawable.bg_shape);
			tv_lea.setTextColor(getResources().getColor(R.color.text292929));
			tv_lea.setBackgroundResource(R.drawable.send_out_goods_shape);
			break;
		case R.id.tv_lea:
			//tv_bug 
			all_price = gfe.getLease_deposit()+opening_cost;
			tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
			islea=true;
			setting_btn_clear1.setClickable(false);
			setting_btn_clear.setText("立即租赁");
			setting_btn_clear1.setTextColor(getResources().getColor(R.color.bg0etitle));
			setting_btn_clear1.setBackgroundResource(R.drawable.bg0e_shape);
			tv_bug.setTextColor(getResources().getColor(R.color.text292929));
			tv_bug.setBackgroundResource(R.drawable.send_out_goods_shape);
			tv_lea.setTextColor(getResources().getColor(R.color.bgtitle));
			tv_lea.setBackgroundResource(R.drawable.bg_shape);
			break;

		case R.id.tv_ins:
			//tv_appneed tv_huilv
			Intent tv_ins =new Intent(GoodDeatail.this, LeaseInstruction.class);
			startActivity(tv_ins);
			break;
		case R.id.tv_appneed:  
			//tv_appneed
			Intent tv_appneed =new Intent(GoodDeatail.this, ApplyNeed.class);
			startActivity(tv_appneed);
			break;
		case R.id.tv_comment:   
			//tv_appneed
			Intent tv_comment =new Intent(GoodDeatail.this, GoodComment.class);
			tv_comment.putExtra("goodId", gfe.getId());
			tv_comment.putExtra("commentsCount",commentsCount+"");
			startActivity(tv_comment);
			break;
		case R.id.setting_btn_clear:  
			if(paychannelId == 0){
				Toast.makeText(getApplicationContext(), "请选择通道！",
						Toast.LENGTH_SHORT).show();				
			}else if(islea){ 
				if (islogin && id != 0) {
					Intent i21 =new Intent(GoodDeatail.this, LeaseConfirm.class);
					i21.putExtra("good", gfe);
					i21.putExtra("chanel", chanel);
					i21.putExtra("price", all_price);
					i21.putExtra("payChannelName", payChannelName);
					i21.putExtra("paychannelId", paychannelId);
					if (ma.size() != 0) {
						if (!StringUtil.isNull(ma.get(0))) {
							i21.putExtra("image_url", ma.get(0));
						}else {
							i21.putExtra("image_url", "");
						}
					}else {
						i21.putExtra("image_url", "");
					}
					startActivity(i21);
				}else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this,LoginActivity.class));
				}

			}else{
				if (islogin && id != 0) {
					Intent i2 =new Intent(GoodDeatail.this, GoodConfirm.class);
					i2.putExtra("getTitle", gfe.getTitle());
					i2.putExtra("price", all_price);
					i2.putExtra("model", gfe.getModel_number());
					i2.putExtra("brand", gfe.getGood_brand());
					if (ma.size() != 0) {
						if (!StringUtil.isNull(ma.get(0))) {
							i2.putExtra("image_url", ma.get(0));
						}else {
							i2.putExtra("image_url", "");
						}
					}else {
						i2.putExtra("image_url", "");
					}
					i2.putExtra("chanel", chanel);
					i2.putExtra("payChannelName", payChannelName);
					i2.putExtra("paychannelId", paychannelId);
					i2.putExtra("goodId", gfe.getId());
					startActivity(i2);
				}else {
					Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
					startActivity(new Intent(this,LoginActivity.class));
				}
			}

			break;

		case R.id.setting_btn_clear1:  
			if(paychannelId == 0){
				Toast.makeText(getApplicationContext(), "请选择通道！",
						Toast.LENGTH_SHORT).show();				
			}else {
				addGood();
			}
			break;
		case R.id.titleback_linear_back:  
			finish();
			break;
		case R.id.search2: 
			Intent i =new Intent(GoodDeatail.this, ShopCar.class);
			startActivityForResult(i, 44);
			break;
		case R.id.tv_sjhttp: 
			Intent intent = new Intent();        
			intent.setAction("android.intent.action.VIEW");    
			Uri content_url = Uri.parse(tv_sjhttp.getText().toString());   
			intent.setData(content_url);  
			startActivity(intent);
			break;
		case R.id.img_see:
			FactoryInfo.setFactory(factory);
			Intent i1 =new Intent(GoodDeatail.this,FactoryInfo.class);
			//i1.putExtra("factory", factory);
			startActivity(i1);
			break;

		default:
			break;
		}

		if (labelIds.contains(Integer.valueOf(v.getId()))) {
			setFlowLayout(v.getId());
			for (int i = 0; i < User_button.size(); i++) {
				if (v.getId() == User_button.get(i).getId()) {
					payChannelName = User_button.get(i).getName();
					paychannelId = User_button.get(i).getId();
					getdataByChanel(User_button.get(i).getId());
				}
			}
		}
	}
	private void setFlowLayout(int id) {
		flowLayout.removeAllViews();
		labelIds.clear();

		for (int i = 0; i < User_button.size(); i++) {
			TextView textView = (TextView) inflater.inflate(
					R.layout.textview_label, flowLayout, false);
			textView.setText(User_button.get(i).getName());

			if (id == User_button.get(i).getId()) {
				textView.setBackgroundResource(R.drawable.bg_shape);
				textView.setTextColor(getResources().getColor(R.color.bgtitle));
			}else {
				textView.setBackgroundResource(R.drawable.send_out_goods_shape);
				textView.setTextColor(getResources().getColor(R.color.text292929));
			}
			textView.setId(User_button.get(i).getId());
			textView.setOnClickListener(this);
			labelIds.add(User_button.get(i).getId());
			flowLayout.addView(textView);
		}
	}
	private void getdata() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("goodId",id);
		params.put("city_id",MyApplication.getInstance().getCityId());
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}

		//		RequestParams params = new RequestParams();
		//		params.put("goodId",id);
		//		params.put("city_id",MyApplication.getInstance().getCityId());
		//	MyApplication.getInstance().getClient().post(Config.URL_GOOD_INFO, params, new AsyncHttpResponseHandler() {
		MyApplication.getInstance().getClient()
		.post(getApplicationContext(),Config.URL_GOOD_INFO, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String userMsg = new String(responseBody).toString();
				Log.d("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==1){
						String res =jsonobject.getString("result");
						jsonobject = new JSONObject(res);
						commentsCount=jsonobject.getInt("commentsCount");
						tv_comment.setText("查看评论"+" ("+commentsCount+") ");
						List<String> piclist= gson.fromJson(jsonobject.getString("goodPics"), new TypeToken<List<String>>() {
						}.getType());
						ma = piclist;
						gfe=gson.fromJson(jsonobject.getString("goodinfo"), new TypeToken<GoodinfoEntity>() {
						}.getType());
						MyApplication.setGfe(gfe);
						goodId=gfe.getId();
						factory=gson.fromJson(jsonobject.getString("factory"), new TypeToken<FactoryEntity>() {
						}.getType());
						String payChannelListStr = JSONUtils.getString(jsonobject, "payChannelList", "[]");
						User_button = gson.fromJson(payChannelListStr, 
								new TypeToken<List<GriviewEntity>>() {}.getType());

						payChannelName = User_button.get(0).getName();

						setFlowLayout(User_button.get(0).getId());//支付通道

						myList=gson.fromJson(JSONUtils.getString(jsonobject,"relativeShopList","[]"), new TypeToken<List<PosEntity>>() {
						}.getType());
						System.out.println(myList.size()+"````");
						gadapter=new GridviewAdapter(GoodDeatail.this, myList);
						gview.setAdapter(gadapter);
						gview.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								System.out.println("点击ITEM"+myList.get(arg2).getId());

								Intent i =new Intent (GoodDeatail.this,GoodDeatail.class);
								i.putExtra("id", myList.get(arg2).getId());

								startActivity(i);
							}
						});

						String res2=	 JSONUtils.getString(jsonobject, "paychannelinfo", null);
						if(res2 != null){
							jsonobject = new JSONObject(res2);
							paychannelId=jsonobject.getInt("id");
							factoryEntity=gson.fromJson(jsonobject.getString("pcfactory"), new TypeToken<FactoryEntity>() {
							}.getType());
							if(jsonobject.getBoolean("support_type")){
								arelist=   gson.fromJson(jsonobject.getString("supportArea"), new TypeToken<List<String>>() {
								}.getType());
								String a="";
								for(int i=0;i<arelist.size();i++){
									if (!StringUtil.isNull(arelist.get(i))) {
										a=a+arelist.get(i)+"/";
									}
								}
								if (a.length()>1) {
									tvc_qy.setText(a.substring(0, a.length()-1));
								}
							}else{
								tvc_qy.setText("不支持");
							}
							if(jsonobject.getBoolean("support_cancel_flag")){

								tvc_zx.setText("支持");
							}else{
								tvc_zx.setText("不支持");
							}
							opening_cost = jsonobject.getInt("opening_cost");
							tv_sqkt.setText(jsonobject.getString("opening_requirement"));
							publist=gson.fromJson(jsonobject.getString("requireMaterial_pub"), new TypeToken<List<ApplyneedEntity>>() {
							}.getType());
							MyApplication.pub=publist;
							System.out.println("publist"+publist.size());
							singlelist=gson.fromJson(jsonobject.getString("requireMaterial_pra"), new TypeToken<List<ApplyneedEntity>>() {
							}.getType());
							MyApplication.single=singlelist;
							celist=gson.fromJson(jsonobject.getString("standard_rates"), new TypeToken<List<ChanelEntitiy>>() {
							}.getType());
							celist2=gson.fromJson(jsonobject.getString("tDates"), new TypeToken<List<ChanelEntitiy>>() {
							}.getType());
							MyApplication.celist=celist2;
							celist3=gson.fromJson(jsonobject.getString("other_rate"), new TypeToken<List<ChanelEntitiy>>() {
							}.getType());
							System.out.println("``celist`"+celist.size());
							lvAdapter=new HuilvAdapter(GoodDeatail.this, celist);
							pos_lv1.setAdapter(lvAdapter);
							lvAdapter2=new HuilvAdapter1(GoodDeatail.this, celist2);
							pos_lv2.setAdapter(lvAdapter2);
							lvAdapter3=new HuilvAdapter2(GoodDeatail.this, celist3);
							pos_lv3.setAdapter(lvAdapter3);
						}
						handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				System.out.println("");
			}
		});
	}
	private void getdataByChanel(int pcid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pcid",pcid);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
//		RequestParams params = new RequestParams();
//		params.put("pcid",pcid);
//		System.out.println("---支付通道ID--"+pcid);

//		params.setUseJsonStreamer(true);
//		MyApplication.getInstance().getClient().post(Config.URL_PAYCHANNEL_INFO, params, new AsyncHttpResponseHandler() {
			MyApplication.getInstance().getClient()
			.post(getApplicationContext(),Config.URL_PAYCHANNEL_INFO, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==1){
						String res =jsonobject.getString("result");
						System.out.println("pay--"+res);
						jsonobject = new JSONObject(res);
						opening_cost = jsonobject.getInt("opening_cost");
						tv_sqkt.setText(jsonobject.getString("opening_requirement"));
						publist=gson.fromJson(jsonobject.getString("requireMaterial_pub"), new TypeToken<List<ApplyneedEntity>>() {
						}.getType());
						MyApplication.pub=publist;
						factoryEntity = gson.fromJson(jsonobject.getString("pcfactory"), new TypeToken<FactoryEntity>() {
						}.getType());
						if(factoryEntity.getLogo_file_path() != null){
							ImageCacheUtil.IMAGE_CACHE.get(factoryEntity.getLogo_file_path(),
									fac_img);
						}
						tv_sjhttp.setText(factoryEntity.getWebsite_url() );
						fac_detai.setText(factoryEntity.getDescription() );

						singlelist=gson.fromJson(jsonobject.getString("requireMaterial_pra"), new TypeToken<List<ApplyneedEntity>>() {
						}.getType());
						MyApplication.single=singlelist;
						celist=gson.fromJson(jsonobject.getString("standard_rates"), new TypeToken<List<ChanelEntitiy>>() {
						}.getType());
						celist2=gson.fromJson(jsonobject.getString("tDates"), new TypeToken<List<ChanelEntitiy>>() {
						}.getType());
						MyApplication.celist=celist2;
						celist3=gson.fromJson(jsonobject.getString("other_rate"), new TypeToken<List<ChanelEntitiy>>() {
						}.getType());
						System.out.println("``celist`"+celist.size());
						lvAdapter=new HuilvAdapter(GoodDeatail.this, celist);
						pos_lv1.setAdapter(lvAdapter);
						lvAdapter2=new HuilvAdapter1(GoodDeatail.this, celist2);
						pos_lv2.setAdapter(lvAdapter2);
						lvAdapter3=new HuilvAdapter2(GoodDeatail.this, celist3);
						pos_lv3.setAdapter(lvAdapter3);
						if(jsonobject.getBoolean("support_type")){
							arelist=   gson.fromJson(jsonobject.getString("supportArea"), new TypeToken<List<String>>() {
							}.getType());
							String a="";
							for(int i=0;i<arelist.size();i++){
								if (!StringUtil.isNull(arelist.get(i))) {
									a=a+arelist.get(i)+"/";
								}
							}
							if (a.length()>1) {
								tvc_qy.setText(a.substring(0, a.length()-1));
							}
						}else{
							tvc_qy.setText("不支持");
						}
						if(jsonobject.getBoolean("support_cancel_flag")){

							tvc_zx.setText("支持");
						}else{
							tvc_zx.setText("不支持");
						}
						if (islea == false) {
							//购买
							all_price = gfe.getRetail_price()+opening_cost;
							tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getRetail_price()+opening_cost));
						}else {
							//租赁
							all_price = gfe.getLease_deposit()+opening_cost;
							tv_price.setText("￥ "+StringUtil.getMoneyString(gfe.getLease_deposit()+opening_cost));
						}

						//  					    handler.sendEmptyMessage(0);
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {

			}
		});
	}
	private void addGood(){

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId",MyApplication.getInstance().getCustomerId());
		params.put("goodId",goodId);
		params.put("paychannelId",paychannelId);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		
//		RequestParams params = new RequestParams();
//		params.put("customerId",MyApplication.getInstance().getCustomerId());
//		params.put("goodId",goodId);
//		//paychannelId
//		params.put("paychannelId",paychannelId);
//		params.setUseJsonStreamer(true);
//		MyApplication.getInstance().getClient().post(Config.URL_CART_ADD, params, new AsyncHttpResponseHandler() {
			MyApplication.getInstance().getClient()
			.post(getApplicationContext(),Config.URL_CART_ADD, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String userMsg = new String(responseBody).toString();

				Log.i("ljp", userMsg);
				Gson gson = new Gson();
				//EventEntity
				JSONObject jsonobject = null;
				int code = 0;
				try {
					jsonobject = new JSONObject(userMsg);
					code = jsonobject.getInt("code");
					if(code==-2){
						Intent i =new Intent(getApplication(),LoginActivity.class);
						startActivity(i);
					}else if(code==1){
						Config.countShopCar = Config.countShopCar + 1;
						Toast.makeText(getApplicationContext(), "添加商品成功",
								Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), jsonobject.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}		
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
			}
		});
	}
	private void initIndicator(){

		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标

		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;

			if (i == 0) { // 初始化第一个为选中状态

				indicator_imgs[i].setBackgroundResource(R.drawable.indicator_focused);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}
			((ViewGroup)v).addView(indicator_imgs[i]);
		}

	}

	/**
	 * 适配器，负责装配 、销毁  数据  和  组件 。
	 */
	private class MyAdapter extends PagerAdapter {

		private List<View> mList;
		private int index ;


		public MyAdapter(List<View> list) {
			mList = list;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			return mList.size();
		}

		/**
		 * Remove a page for the given position.
		 * 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
		 * instantiateItem(View container, int position)
		 * This method was deprecated in API level . Use instantiateItem(ViewGroup, int)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mList.get(position));

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}

		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container, final int position) {
			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));

			ImageCacheUtil.IMAGE_CACHE.get(ma.get(position),
					image);
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					//			 
					//					 Intent i=new Intent(AroundDetail.this,VPImage.class);
					//				 
					//					 i.putExtra("index", index_ima);
					//					 i.putExtra("mal", mal);
					//					 startActivityForResult(i, 9);
				}
			});
			return mList.get(position);
		}
	}

	/**
	 * 动作监听器，可异步加载图片
	 *
	 */
	private class MyListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int state) {
			if (state == 0) {
				//new MyAdapter(null).notifyDataSetChanged();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {

			// 改变所有导航的背景图片为：未选中
			for (int i = 0; i < indicator_imgs.length; i++) {
				indicator_imgs[i].setBackgroundResource(R.drawable.indicator);
			}

			// 改变当前背景图片为：选中
			index_ima=position;
			indicator_imgs[position].setBackgroundResource(R.drawable.indicator_focused);
		}
	}
}
