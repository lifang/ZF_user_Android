package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TradeType.CONSUME;
import static com.example.zf_android.trade.Constants.TradeType.LIFE_PAY;
import static com.example.zf_android.trade.Constants.TradeType.PHONE_PAY;
import static com.example.zf_android.trade.Constants.TradeType.REPAYMENT;
import static com.example.zf_android.trade.Constants.TradeType.TRANSFER;

import java.util.ArrayList;
import java.util.List;

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

        // add tabs to the TabWidget
        String[] tabs = getResources().getStringArray(R.array.trade_flow_tabs);
        for (int i = 0; i < tabs.length; i++) {
            mTabWidget.addTab(tabs[i]);
        }
        // add fragments according to the order
        TradeFlowFragment transferFragment = TradeFlowFragment.newInstance(2);//2
        TradeFlowFragment consumeFragment = TradeFlowFragment.newInstance(1);//1
        TradeFlowFragment repaymentFragment = TradeFlowFragment.newInstance(3);//3
        TradeFlowFragment lifePayFragment = TradeFlowFragment.newInstance(5);//5
        TradeFlowFragment phonePayFragment = TradeFlowFragment.newInstance(4);//4
        mFragments.add(transferFragment);
        mFragments.add(consumeFragment);
        mFragments.add(repaymentFragment);
        mFragments.add(lifePayFragment);
        mFragments.add(phonePayFragment);

        mTabWidget.setViewPager(mViewPager);
        mViewPager.setAdapter(new TradeFlowPagerAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(this);

        // init to select the first tab and fragment
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
