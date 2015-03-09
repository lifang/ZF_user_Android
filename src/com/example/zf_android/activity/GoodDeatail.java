package com.example.zf_android.activity;
 
import java.util.ArrayList;
 
import java.util.List;

import org.apache.http.Header;
 
 
import org.json.JSONException;
import org.json.JSONObject;
 
import android.content.Intent;
 
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

 
import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.ScrollViewWithListView;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_android.entity.FactoryEntity;
import com.example.zf_android.entity.GoodinfoEntity;
import com.example.zf_android.entity.PicEntity;
import com.example.zf_zandroid.adapter.HuilvAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
 
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
 
public class GoodDeatail extends BaseActivity implements OnClickListener{
	private int id;
	private TextView eventsFinshTime,tv_detail,name,creat_tv,location,tv_time,tv_tel2;
	private LinearLayout titleback_linear_back;
	private ImageView image,search2,fac_img;
	private RelativeLayout ri_tel;
	private ArrayList<String> ma = new ArrayList<String>();
	private ArrayList<String> mal = new ArrayList<String>();
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//存放引到图片数组
	private View item ;
	private LayoutInflater inflater;
	private RelativeLayout rl_imgs,rela_loc;
	private int  index_ima=0;
	GoodinfoEntity gfe;
	FactoryEntity factoryEntity;
	private  TextView tv_title,content1,tv_pp,tv_xh,tv_ys,tv_price,tv_lx,tv_sjhttp
	,tv_spxx,fac_detai,ppxx,wkxx,dcxx,tv_qgd,tv_jm;
	private ScrollViewWithListView  pos_lv1;
	private HuilvAdapter lvAdapter;
	private ArrayList<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	private String phoneNumber,locName;
	private float lat,lng;
	List<View> list = new ArrayList<View>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
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
				tv_price.setText("￥ "+gfe.getPrice());
				tv_lx.setText(gfe.getCategory() );
			 	tv_sjhttp.setText(factoryEntity.getWebsite_url() );
				tv_spxx.setText(gfe.getDescription() );
				fac_detai.setText(factoryEntity.getDescription() );
				ppxx.setText(gfe.getModel_number() );
				wkxx.setText(gfe.getShell_material() );
				dcxx.setText(gfe.getBattery_info());
				tv_qgd.setText(gfe.getSign_order_way());
				tv_jm.setText(gfe.getEncrypt_card_way());
				
				
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.good_detail);
	 
		id=getIntent().getIntExtra("id", 0);
		innitView();
		getdata();
		System.out.println("-Xlistview--"+id);
	 
	}
	private void innitView() {
		// TODO Auto-generated method stub
		rl_imgs=(RelativeLayout) findViewById(R.id.rl_imgs); 
		view_pager = (ViewPager) findViewById(R.id.view_pager); 
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list); 
		view_pager.setAdapter(adapter);
		view_pager.setOnPageChangeListener(new MyListener());
		
		
		pos_lv1=(ScrollViewWithListView) findViewById(R.id.pos_lv1);
		 
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
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
		 
		default:
			break;
		}
	}
	
	private void getdata() {
		RequestParams params = new RequestParams();
		params.put("goodId",id);
		params.put("city_id",1);
		
		params.setUseJsonStreamer(true);
		MyApplication.getInstance().getClient().post(Config.GOODDETAIL, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				// TODO Auto-generated method stub
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
						jsonobject = new JSONObject(res);
							
							List<String> piclist= gson.fromJson(jsonobject.getString("goodPics"), new TypeToken<List<String>>() {
		 					}.getType());
							 gfe=gson.fromJson(jsonobject.getString("goodinfo"), new TypeToken<GoodinfoEntity>() {
		 					}.getType());
							 factoryEntity=gson.fromJson(jsonobject.getString("factory"), new TypeToken<FactoryEntity>() {
			 				}.getType());
//							 ImageCacheUtil.IMAGE_CACHE.get( factoryEntity.getLogo_file_path(),
//						 				fac_img);  图片路径待定
							//  
							 String res2=	 jsonobject.getString("paychannelinfo");
							 jsonobject = new JSONObject(res2);
							 celist=gson.fromJson(jsonobject.getString("standard_rates"), new TypeToken<List<ChanelEntitiy>>() {
				 				}.getType());
							 
						System.out.println("``celist`"+celist.size());
						lvAdapter=new HuilvAdapter(GoodDeatail.this, celist);
						pos_lv1.setAdapter(lvAdapter);
						for(int i=0;i<piclist.size();i++){
							ma.add(piclist.get(i));
						}
						 
						
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
			// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			container.removeView(mList.get(position));
			
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		
		/**
		 * Create the page for the given position.
		 */
		@Override
		public Object instantiateItem(final ViewGroup container, final int position) {
			
 
			View view = mList.get(position);
			image = ((ImageView) view.findViewById(R.id.image));
 
			ImageCacheUtil.IMAGE_CACHE.get(  ma.get(position),
	 				image);
 		
 		
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
			// TODO Auto-generated method stub
			if (state == 0) {
				//new MyAdapter(null).notifyDataSetChanged();
			}
		}

		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
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
