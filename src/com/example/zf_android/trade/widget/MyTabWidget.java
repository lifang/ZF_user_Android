package com.example.zf_android.trade.widget;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
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

import com.epalmpay.user_phone.R;

/**
 * Custom {@link TabWidget} to collaborate with a {@link ViewPager}
 * <p/>
 * Created by Leo on 2015/2/6.
 */
public class MyTabWidget extends TabWidget implements View.OnClickListener {
	protected ViewPager mViewPager;
	protected final List<TextView> mTabViews = new ArrayList<TextView>();
	protected Context mContext;

	protected Resources resources;
	protected int mBaseColor;

	protected OnTabSelectedListener onTabSelectedListener;

	public MyTabWidget(Context context) {
		super(context);
		init(context);
	}

	public MyTabWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MyTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@SuppressLint("NewApi")
	protected void init(Context context) {
		this.mContext = context;
		resources = mContext.getResources();
		mBaseColor = resources.getColor(R.color.bgtitle);
		setBackgroundDrawable(resources.getDrawable(R.drawable.tab_widget_bg));
		setDividerDrawable(R.drawable.divider);
		setDividerPadding(0);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
	}

	/**
	 * set a {@link ViewPager} to the tab content
	 *
	 * @param mViewPager
	 */
	public void setViewPager(ViewPager mViewPager) {
		this.mViewPager = mViewPager;
		if (mTabViews.size() > 0) setCurrentTab(0);
	}

	/**
	 * Add a tab
	 *
	 * @param tab the tab name
	 */
	public void addTab(String tab) {
		addTab(tab, 13);
	}

	/**
	 * Add a tab
	 *
	 * @param tab  the tab name
	 * @param size the tab text size (sp)
	 */
	public void addTab(String tab, int size) {
		TextView tv = new TextView(mContext);
		tv.setWidth((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()));
		tv.setText(tab);
		tv.setTextColor(mBaseColor);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
		mTabViews.add(tv);
		addView(tv);
		tv.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if (mTabViews.size() == 0) return;
		TextView tv = (TextView) v;
		int position = mTabViews.indexOf(tv);
		updateTabs(position);

		if (mViewPager != null) {
			mViewPager.setCurrentItem(position);
		}
		if (null != onTabSelectedListener) {
			onTabSelectedListener.onTabSelected(position);
		}
	}

	/**
	 * update the tab style when selected
	 *
	 * @param position
	 */
	public void updateTabs(int position) {

		for (TextView tv : mTabViews) {
			tv.setBackgroundColor(Color.TRANSPARENT);
			tv.setTextColor(mBaseColor);
		}

		TextView tv = mTabViews.get(position);
		tv.setTextColor(Color.WHITE);
		if (mTabViews.indexOf(tv) == 0) {
			if(mTabViews.size() ==1){
				tv.setBackgroundDrawable(resources.getDrawable(R.drawable.tab_widget_bg_one));
			}else{
				tv.setBackgroundDrawable(resources.getDrawable(R.drawable.tab_left_bg));
			}
		} else if (mTabViews.indexOf(tv) == mTabViews.size() - 1) {
			tv.setBackgroundDrawable(resources.getDrawable(R.drawable.tab_right_bg));
		} else {
			tv.setBackgroundColor(mBaseColor);
		}
	}

	/**
	 * set the listener for tab selection
	 *
	 * @param listener
	 */
	public void setOnTabSelectedListener(OnTabSelectedListener listener) {
		onTabSelectedListener = listener;
	}

	public interface OnTabSelectedListener {
		void onTabSelected(int position);
	}

}
