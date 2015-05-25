package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.example.zf_android.trade.widget.WheelView;

/**
 * Created by Leo on 2015/2/11.
 */
public class CityProvinceActivity extends BaseActivity {

    private List<Province> mProvinces;
    private static final int OFFSET = 5;

    private Province mSelectedProvince;
    private City mSelectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSelectedProvince = (Province) getIntent().getSerializableExtra(SELECTED_PROVINCE);
        mSelectedCity = (City) getIntent().getSerializableExtra(SELECTED_CITY);

        LinearLayout contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout titleBack = (RelativeLayout) getLayoutInflater().inflate(R.layout.title_back, null);
        TextView confirm = (TextView) titleBack.findViewById(R.id.next_sure);
        confirm.setVisibility(View.VISIBLE);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (mSelectedCity != null) {
            		Intent intent = new Intent();
            		intent.putExtra(SELECTED_PROVINCE, mSelectedProvince);
            		intent.putExtra(SELECTED_CITY, mSelectedCity);
            		setResult(RESULT_OK, intent);
            		finish();
				}
            }
        });
        contentView.addView(titleBack, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, CommonUtil.dip2px(this, 50)));

        LinearLayout contentWheels = new LinearLayout(this);
        contentWheels.setOrientation(LinearLayout.HORIZONTAL);
        contentView.addView(contentWheels, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                screenWidth / 2, ViewGroup.LayoutParams.WRAP_CONTENT);
        final WheelView provinceWheel = new WheelView(this);
        final WheelView cityWheel = new WheelView(this);
        contentWheels.addView(provinceWheel, lp);
        contentWheels.addView(cityWheel, lp);
        setContentView(contentView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        new TitleMenuUtil(this, getString(R.string.title_city_select)).show();

        provinceWheel.setOffset(OFFSET);
        cityWheel.setOffset(OFFSET);

        provinceWheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, Object item) {
                Province province = mProvinces.get(selectedIndex - OFFSET);
                mSelectedProvince = province;
                mSelectedCity = mSelectedProvince.getCities().get(0);
                cityWheel.setItems(province.getCities());
                cityWheel.invalidate();
            }
        });
        cityWheel.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, Object item) {
                mSelectedCity = mSelectedProvince.getCities().get(selectedIndex - OFFSET);
            }
        });

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Province> provinces = (List<Province>) msg.obj;
                if (null == provinces || provinces.size() == 0)
                    return;
                mProvinces = provinces;
                provinceWheel.setItems(mProvinces);
                if (null != mSelectedProvince && null != mSelectedCity) {
                    provinceWheel.setSeletion(mProvinces.indexOf(mSelectedProvince));
                    cityWheel.setItems(mSelectedProvince.getCities());
                    cityWheel.setSeletion(mSelectedProvince.getCities().indexOf(mSelectedCity));
                } else {
                    mSelectedProvince = mProvinces.get(0);
                    provinceWheel.setSeletion(0);

                    mSelectedCity = mSelectedProvince.getCities().get(0);
                    cityWheel.setItems(mProvinces.get(0).getCities());
                    cityWheel.setSeletion(0);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                List<Province> provinces = CommonUtil.readProvincesAndCities(CityProvinceActivity.this);
                Message message = new Message();
                message.obj = provinces;
                handler.sendMessage(message);
            }
        }.start();


    }
}
