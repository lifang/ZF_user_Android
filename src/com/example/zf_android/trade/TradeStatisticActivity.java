package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TradeStatistic;
import com.google.gson.reflect.TypeToken;

public class TradeStatisticActivity extends Activity {

    public static final String TRADE_TYPE = "trade_type";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";
    public static final String CLIENT_NUMBER = "client_number";

    private int mTradeType;
    private String mStartDate;
    private String mEndDate;
    private String mClientNumber;

    private TextView statisticAmount;
    private TextView statisticCount;
    private TextView statisticTime;
    private TextView statisticClient;
    private TextView statisticChannel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mTradeType = intent.getIntExtra(TRADE_TYPE, 1);
        mStartDate = intent.getStringExtra(START_DATE);
        mEndDate = intent.getStringExtra(END_DATE);
        mClientNumber = intent.getStringExtra(CLIENT_NUMBER);

        setContentView(R.layout.activity_trade_statistic);
        initViews();

        API.getTradeRecordTotal(this, mTradeType, mClientNumber, mStartDate, mEndDate,
                new HttpCallback<TradeStatistic>(this) {
                    @Override
                    public void onSuccess(TradeStatistic data) {
                        statisticAmount.setText("" + data.getAmountTotal());
                        statisticCount.setText("" + data.getTradeTotal());
                        statisticTime.setText(mStartDate.replaceAll("-", "/") + " - " + mEndDate.replaceAll("-", "/"));
                        statisticClient.setText(data.getTerminalNumber());
                        statisticChannel.setText("" + data.getPayChannelId());
                    }

                    @Override
                    public TypeToken<TradeStatistic> getTypeToken() {
                        return new TypeToken<TradeStatistic>() {
                        };
                    }
                });
    }

    private void initViews() {
        new TitleMenuUtil(this, getString(R.string.title_trade_statistic)).show();

        statisticAmount = (TextView) findViewById(R.id.trade_statistic_amount);
        statisticCount = (TextView) findViewById(R.id.trade_statistic_count);
        statisticTime = (TextView) findViewById(R.id.trade_statistic_time);
        statisticClient = (TextView) findViewById(R.id.trade_statistic_client);
        statisticChannel = (TextView) findViewById(R.id.trade_statistic_channel);
    }
}
