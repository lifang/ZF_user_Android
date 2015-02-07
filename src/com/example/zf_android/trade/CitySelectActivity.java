package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

import java.util.ArrayList;
import java.util.List;

public class CitySelectActivity extends Activity {

    public static final String CITY_SELECTED = "city_selected";
    private String mCitySelected;
    private TextView mCityCurrent;

    private TextView overlay;
    private ListView mListView;
    private LetterListView mLetterListView;
    private CityListAdapter mAdapter;

    private Handler handler;
    private Thread overlayThread;

    private List<String> mCities = new ArrayList<String>();
    private List<String> mLetters = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        mCitySelected = getIntent().getStringExtra(CITY_SELECTED);

        new TitleMenuUtil(this, getString(R.string.title_city_select)).show();
        initViews();
    }

    /**
     * 初始化一些测试数据
     */
    private void initTestData() {
        for (int i = 0; i < LetterListView.b.length; i++) {
            // 随机剔除一些字母
            if (i % 5 != 4) {
                mLetters.add(LetterListView.b[i]);
                mCities.add(LetterListView.b[i]);
                for (int j = 0; j < 20; j++) {
                    mCities.add(LetterListView.b[i].toUpperCase() + " - City");
                }
            }
        }
    }

    private void initViews() {
        initTestData();
        initOverlay();

        mCityCurrent = (TextView) findViewById(R.id.city_current);
        if (!TextUtils.isEmpty(mCitySelected)) {
            mCityCurrent.setText(mCitySelected);
        }

        mListView = (ListView) findViewById(R.id.city_list_view);
        mLetterListView = (LetterListView) findViewById(R.id.letter_list_view);
        mAdapter = new CityListAdapter();
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String cityName = ((TextView) view).getText().toString();
                if (mLetters.contains(cityName)) return;
                Intent intent = new Intent();
                intent.putExtra(CITY_SELECTED, cityName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        handler = new Handler();
        overlayThread = new Thread() {
            @Override
            public void run() {
                overlay.setVisibility(View.GONE);
            }
        };

        mLetterListView = (LetterListView) findViewById(R.id.letter_list_view);
        mLetterListView
                .setOnTouchingLetterChangedListener(new LetterListView.OnTouchingLetterChangedListener() {
                    @Override
                    public void onTouchingLetterChanged(String s) {
                        if (mLetters.contains(s)) {
                            int position = mCities.indexOf(s);
                            mListView.setSelection(position);
                            overlay.setText(s);
                            overlay.setVisibility(View.VISIBLE);
                            handler.removeCallbacks(overlayThread);
                            // 延迟一秒后执行，让overlay为不可见
                            handler.postDelayed(overlayThread, 1500);
                        }
                    }
                });
    }

    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.letter_overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class CityListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCities.size();
        }

        @Override
        public Object getItem(int position) {
            return mCities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Resources resources = getResources();
            ViewHolder holder;
            if (convertView == null) {
                convertView = new TextView(CitySelectActivity.this);
                ((TextView) convertView).setHeight(dip2px(50));
                ((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
                ((TextView) convertView).setTextColor(resources.getColor(R.color.text292929));
                ((TextView) convertView).setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                convertView.setPadding(dip2px(24), 0, 0, 0);
                holder = new ViewHolder();
                holder.tv = (TextView) convertView;
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String city = mCities.get(position);
            if (mLetters.contains(city)) {
                holder.tv.setBackgroundColor(resources.getColor(R.color.F3F2F2));
            } else {
                holder.tv.setBackgroundColor(resources.getColor(R.color.white));
            }
            holder.tv.setText(city.toUpperCase());
            return convertView;
        }

        private class ViewHolder {
            TextView tv;
        }
    }

}
