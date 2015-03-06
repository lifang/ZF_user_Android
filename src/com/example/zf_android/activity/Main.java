package com.example.zf_android.activity;
 
import java.util.ArrayList;
import java.util.List;
 
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;
import static com.example.zf_android.trade.Constants.CityIntent.CITY_ID;
import static com.example.zf_android.trade.Constants.CityIntent.CITY_NAME;
 

import org.apache.http.Header;

import android.content.Intent;
import android.os.Bundle;
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
 
 
import com.examlpe.zf_android.util.ImageCacheUtil;
 
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.CitySelectActivity;
import com.example.zf_android.trade.TradeFlowActivity;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class Main extends BaseActivity implements OnClickListener{

	private RelativeLayout  main_rl_pos,main_rl_renzhen,main_rl_zdgl,main_rl_jyls,
	main_rl_Forum,main_rl_wylc,main_rl_xtgg,main_rl_lxwm,main_rl_my,main_rl_pos1,main_rl_gwc;
	private ImageView testbutton;

    private View citySelect;
    private TextView cityTextView;
    private int cityId;
    private String cityName;

    private Province province;
    private City city;
    public static final int REQUEST_CITY = 1;
    public static final int REQUEST_CITY_WHEEL = 2;
    //vp
    private ArrayList<String> mal = new ArrayList<String>();
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView[] indicator_imgs  ;//存放引到图片数组
	private View item ;
	private LayoutInflater inflater;
	private ImageView image;
	private int  index_ima=0;
	private ArrayList<String> ma = new ArrayList<String>();
	List<View> list = new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		testbutton=(ImageView) findViewById(R.id.testbutton);
		testbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent i =new Intent(Main.this,LoginActivity.class);
					 startActivity(i);
			}
		});
		System.out.println("-----");
		getdata();
	}

	private void getdata() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
	 
		MyApplication.getInstance().getClient().post( "http://114.215.149.242:18080/ZFMerchant/api/index/sysshufflingfigure/getList", new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) { 
				System.out.println("-onSuccess---");
				String responseMsg = new String(responseBody).toString();
				Log.e("LJP", responseMsg);
				
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				// TODO Auto-generated method stub
				 error.printStackTrace();
			}
		});
	
	}

	private void initView() {
		// TODO Auto-generated method stub
        citySelect = findViewById(R.id.titleback_linear_back);
        cityTextView = (TextView) findViewById(R.id.tv_city);
        citySelect.setOnClickListener(this);
        main_rl_gwc=(RelativeLayout) findViewById(R.id.main_rl_gwc);
        main_rl_gwc.setOnClickListener(this);
		main_rl_pos=(RelativeLayout) findViewById(R.id.main_rl_pos);
		main_rl_pos.setOnClickListener(this);
		main_rl_renzhen=(RelativeLayout) findViewById(R.id.main_rl_renzhen);
		main_rl_renzhen.setOnClickListener(this);
		main_rl_zdgl=(RelativeLayout) findViewById(R.id.main_rl_zdgl);
		main_rl_zdgl.setOnClickListener(this);
		main_rl_jyls=(RelativeLayout) findViewById(R.id.main_rl_jyls);
		main_rl_jyls.setOnClickListener(this);
		main_rl_Forum=(RelativeLayout) findViewById(R.id.main_rl_Forum);
		main_rl_Forum.setOnClickListener(this);
		main_rl_wylc=(RelativeLayout) findViewById(R.id.main_rl_wylc);
		main_rl_wylc.setOnClickListener(this);
		main_rl_lxwm=(RelativeLayout) findViewById(R.id.main_rl_lxwm);
		main_rl_lxwm.setOnClickListener(this);
		main_rl_xtgg=(RelativeLayout) findViewById(R.id.main_rl_xtgg);
		main_rl_xtgg.setOnClickListener(this);
		main_rl_my=(RelativeLayout) findViewById(R.id.main_rl_my);
		main_rl_my.setOnClickListener(this);
		main_rl_pos1=(RelativeLayout) findViewById(R.id.main_rl_pos1);
		main_rl_pos1.setOnClickListener(this);
		
		
		view_pager = (ViewPager) findViewById(R.id.view_pager);
		 
		inflater = LayoutInflater.from(this);
		adapter = new MyAdapter(list);
		 
		view_pager.setAdapter(adapter);
		//绑定动作监听器：如翻页的动画
		view_pager.setOnPageChangeListener(new MyListener());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
 
        case R.id.titleback_linear_back:  
 
         
            Intent intent = new Intent(Main.this, CityProvinceActivity.class);
          //  intent.putExtra(CityProvinceActivity.SELECTED_PROVINCE, province);
          //   intent.putExtra(CityProvinceActivity.SELECTED_CITY, city);
            startActivityForResult(intent, REQUEST_CITY_WHEEL);
            break;

		case R.id.main_rl_pos1:  // 锟斤拷POS锟斤拷锟斤拷
			 startActivity(new Intent(Main.this,MyMessage.class));
			 
			break;
 
 
		case R.id.main_rl_my:  // ��POS����
 
			 startActivity(new Intent(Main.this,MenuMine.class));
			 
			break;
 
		case R.id.main_rl_pos:  // ��POS����
 
			 startActivity(new Intent(Main.this,PosListActivity.class));
			 
			break;
 
 
		case R.id.main_rl_renzhen:  //��֤
 
			 Intent i =new Intent(Main.this,OrderList.class);
			 startActivity(i);
			 
			break;
 
		case R.id.main_rl_zdgl: //锟秸端癸拷锟斤拷 
			 
			break;
		case R.id.main_rl_jyls: //锟斤拷锟斤拷锟斤拷水 
			 
			startActivity(new Intent(Main.this, TradeFlowActivity.class));
			break;
		case R.id.main_rl_Forum: //锟斤拷要锟斤拷锟�  
			 
			break;
 
		case R.id.main_rl_xtgg: //ϵͳ����   
 
			  
			 startActivity(new Intent(Main.this,SystemMessage.class));
			 
			break;
 
		case R.id.main_rl_lxwm: //��ϵ����
 
			 startActivity(new Intent(Main.this,ContentUs.class));
			 break;
		case R.id.main_rl_gwc:  
			 startActivity(new Intent(Main.this,ShopCar.class));
			break;
		default:
			break;
		}
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CITY:
                cityId = data.getIntExtra(CITY_ID, 0);
                cityName = data.getStringExtra(CITY_NAME);
                cityTextView.setText(cityName);
                break;
            case REQUEST_CITY_WHEEL:
                province = (Province) data.getSerializableExtra(SELECTED_PROVINCE);
                city = (City) data.getSerializableExtra(SELECTED_CITY);
                cityTextView.setText(city.getName());
                break;
        }
    }
    
private void initIndicator(){
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// 线性水平布局，负责动态调整导航图标
		
		for (int i = 0; i < ma.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(10,10);
			params_linear.setMargins(7, 0, 7, 20);
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
//			image.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//				//	 Toast.makeText(getApplicationContext(), index_ima+"----", 1000).show();
//					 Intent i=new Intent(AroundDetail.this,VPImage.class);
//					// i.putExtra("image_url", ma.get(index_ima));
//					 i.putExtra("index", index_ima);
//					 i.putExtra("mal", mal);
//					 startActivityForResult(i, 9);
//				}
//			});
		  
			
			
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
