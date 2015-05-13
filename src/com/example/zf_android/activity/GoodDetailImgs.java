package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodDetailImgs extends BaseActivity{
	private ViewPager view_pager;
	private MyAdapter adapter ;
	private ImageView image;
	private LayoutInflater inflater;
	List<View> list = new ArrayList<View>();
	private List<String> ma = new ArrayList<String>();
	private int position_detail;
	DisplayImageOptions options = MyApplication.getDisplayOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gooddetailimgs);
		ma = getIntent().getExtras().getStringArrayList("ma");
		position_detail = getIntent().getIntExtra("position_detail", 0);
		initView();
	}
	private void initView() {
		view_pager = (ViewPager) findViewById(R.id.view_pager); 
		inflater = LayoutInflater.from(this); 

		view_pager.setFocusable(true);
		view_pager.setFocusableInTouchMode(true);
		view_pager.requestFocus();

		list.clear();
		for (int i = 0; i <ma.size(); i++) {			 
			View item = inflater.inflate(R.layout.item_gooddetailimgs, null);
			list.add(item);
		}

		adapter = new MyAdapter(list); 
		view_pager.setAdapter(adapter);
		view_pager.setCurrentItem(position_detail);
		view_pager.setOnPageChangeListener(new MyListener());		
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

			ImageLoader.getInstance().displayImage(ma.get(position), image, options);
			container.removeView(mList.get(position));
			container.addView(mList.get(position));
			setIndex(position);
			image.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

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

			//			// 改变所有导航的背景图片为：未选中
			//			for (int i = 0; i < indicator_imgs.length; i++) {
			//				indicator_imgs[i].setBackgroundResource(R.drawable.white_hollow_point);
			//			}
			//
			//			// 改变当前背景图片为：选中
			//			index_ima=position;
			//			indicator_imgs[position].setBackgroundResource(R.drawable.white_solid_point);
			//			View v = list.get(position);
			//			v.setOnClickListener(new OnClickListener() {
			//
			//				@Override
			//				public void onClick(View arg0) {
			//					Intent intent = new Intent(GoodDeatail.this,GoodDetailImgs.class);
			//					intent.putStringArrayListExtra("ma", (ArrayList<String>) ma);
			//					startActivity(intent);
			//				}
			//			});
		}
	}
}
