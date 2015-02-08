package com.example.zf_android.trade;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zf_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2015/2/6.
 */
public class PagerTabWidget extends TabWidget implements View.OnClickListener,ViewPager.OnPageChangeListener{
	protected ViewPager mViewPager;
	protected final List<TextView> mTabViews = new ArrayList<TextView>();
	protected Context mContext;
	protected OnTabSelectedListener mOnTabSelectedListener;

	protected Resources resources;
	protected int mBaseColor;

	public void setmOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
		this.mOnTabSelectedListener = mOnTabSelectedListener;
	}

	public PagerTabWidget(Context context) {
		super(context);
		init(context);
	}

	public PagerTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PagerTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	protected void init(Context context){
		this.mContext = context;
		resources = mContext.getResources();
		mBaseColor = resources.getColor(R.color.bgtitle);
		setBackgroundDrawable(resources.getDrawable(R.drawable.tab_widget_bg));
		setDividerDrawable(R.drawable.divider);
		setDividerPadding(0);
	}

	public ViewPager getViewPager() {
		return mViewPager;
	}

	public void setViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
		this.mViewPager.setOnPageChangeListener(this);
		if(mTabViews.size() > 0) setCurrentTab(0);
	}

	public void addTab(String tab){

		TextView tv = new TextView(mContext);
		tv.setText(tab);
		tv.setTextColor(mBaseColor);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
		mTabViews.add(tv);
		addView(tv);
		tv.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if (mTabViews.size() == 0 ) return;

		for (TextView tv : mTabViews) {
			tv.setBackgroundColor(Color.TRANSPARENT);
			tv.setTextColor(mBaseColor);
		}

		TextView tv = (TextView) v;
		tv.setTextColor(Color.WHITE);
		if (mTabViews.indexOf(tv) == 0) {
			tv.setBackgroundDrawable(resources.getDrawable(R.drawable.tab_left_bg));
		} else if (mTabViews.indexOf(tv) == mTabViews.size() - 1) {
			tv.setBackgroundDrawable(resources.getDrawable(R.drawable.tab_right_bg));
		} else {
			tv.setBackgroundColor(mBaseColor);
		}


		if(mViewPager != null){
			mViewPager.setCurrentItem(mTabViews.indexOf(v));
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {

		setCurrentTab(position);
		mOnTabSelectedListener.onSelected(mTabViews,position);

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	public static  interface OnTabSelectedListener {

		void onSelected(List<TextView> tabViews, int position);

	}


}
