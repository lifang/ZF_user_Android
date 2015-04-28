package com.example.zf_android.activity;

import java.util.ArrayList;

import com.example.zf_android.R;
import com.example.zf_android.trade.widget.DepthPageTransformer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class WelcomeActivity extends Activity implements OnPageChangeListener {

	private ImageView img;
	private ViewPager view_pager;
	private int[] imgs = { R.drawable.guide1, R.drawable.guide2,
			R.drawable.guide3, R.drawable.guide4 };
	private ArrayList<View> views;
	private ViewGroup scrollDot;
	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		img = (ImageView) this.findViewById(R.id.img);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SharedPreferences sp = getSharedPreferences("appstatus",
						MODE_PRIVATE);
				if (!sp.getBoolean("isOpen", false)) {
					img.setVisibility(View.GONE);
					initViewPager();
					sp.edit().putBoolean("isOpen", true).commit();
				} else {
					Intent i = new Intent(getApplicationContext(), Main.class);
					startActivity(i);
					finish();
				}
			}
		}, 3000);

	}

	private void initViewPager() {
		views = loadViews(imgs);
		view_pager = (ViewPager) this.findViewById(R.id.view_pager);
		scrollDot = (ViewGroup) this.findViewById(R.id.scroll_dot);
		view_pager.setVisibility(View.VISIBLE);
		scrollDot.setVisibility(View.VISIBLE);
		((ImageView) scrollDot.getChildAt(0)).setEnabled(false);
//		view_pager.setPageTransformer(true, new DepthPageTransformer());
		view_pager.setOnPageChangeListener(this);
		view_pager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return views.size();
			}

			public Object instantiateItem(ViewGroup container, int position) {
				if (position == 3) {
					views.get(position).setOnClickListener(
							new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									//加上PageTransformer
//									if (index == views.size() - 1) {
										Intent i = new Intent(getApplicationContext(),Main.class);
										startActivity(i);
										finish();
//									}
								}
							});
				}
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				((ViewPager) container).removeView(views.get(position));
			}
		});
	}

	public ArrayList<View> loadViews(int[] mLoadPage) {
		ArrayList<View> views = new ArrayList<View>();
		int length = mLoadPage.length;
		for (int index = 0; index < length; index++) {
			RelativeLayout layout = new RelativeLayout(WelcomeActivity.this);
			layout.setBackgroundResource(mLoadPage[index]);
			views.add(layout);
		}
		return views;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {

		index = position;
		// TODO Auto-generated method stub
		if (position == 0) {
			((ImageView) scrollDot.getChildAt(0)).setEnabled(false);
			((ImageView) scrollDot.getChildAt(1)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(2)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(3)).setEnabled(true);
		} else if (position == 1) {
			((ImageView) scrollDot.getChildAt(0)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(1)).setEnabled(false);
			((ImageView) scrollDot.getChildAt(2)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(3)).setEnabled(true);
		} else if (position == 2) {
			((ImageView) scrollDot.getChildAt(0)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(1)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(2)).setEnabled(false);
			((ImageView) scrollDot.getChildAt(3)).setEnabled(true);
		} else if (position == 3) {
			((ImageView) scrollDot.getChildAt(0)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(1)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(2)).setEnabled(true);
			((ImageView) scrollDot.getChildAt(3)).setEnabled(false);
		}
	}

}
