package com.example.zf_android.trade;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.widget.MyTabWidget;
import com.example.zf_android.trade.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2015/2/6.
 */
public class TradeFlowActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private MyTabWidget mTabWidget;
    private MyViewPager mViewPager;

    private List<TradeFlowFragment> mFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trade_flow);
        initViews();
    }

    private void initViews() {
        new TitleMenuUtil(this, getString(R.string.title_trade_flow)).show();

        mTabWidget = (MyTabWidget) findViewById(R.id.tab_widget);
        mViewPager = (MyViewPager) findViewById(R.id.view_pager);
        mFragments = new ArrayList<TradeFlowFragment>();

        String[] tabs = getResources().getStringArray(R.array.trade_flow_tabs);
        for (int i = 0; i < tabs.length; i++) {
            TradeFlowFragment fragment = TradeFlowFragment.newInstance(i + 1);
            mFragments.add(fragment);
            mTabWidget.addTab(tabs[i]);
        }

        mTabWidget.setViewPager(mViewPager);
        mViewPager.setAdapter(new TradeFlowPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);

        // initial
        mTabWidget.updateTabs(0);
        mViewPager.setCurrentItem(0);

    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
    }

    @Override
    public void onPageSelected(int i) {
        mTabWidget.updateTabs(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    public class TradeFlowPagerAdapter extends FragmentPagerAdapter {

        public TradeFlowPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return mFragments.get(i);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
