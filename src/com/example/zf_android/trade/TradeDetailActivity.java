package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TradeIntent.TRADE_RECORD_ID;
import static com.example.zf_android.trade.Constants.TradeIntent.TRADE_TYPE;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TradeDetail;
import com.google.gson.reflect.TypeToken;

public class TradeDetailActivity extends Activity {

    private LinearLayout mCommercialKeyContainer;
    private LinearLayout mCommercialValueContainer;

    private LinearLayout mBankKeyContainer;
    private LinearLayout mBankValueContainer;

    private TextView mTradeStatus;
    private TextView mTradeAmount;
    private TextView mTradePoundage;
    private TextView mTradeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_item);
        new TitleMenuUtil(this, getString(R.string.title_trade_detail)).show();
        initialViews();

        int typeId = getIntent().getIntExtra(TRADE_TYPE, 0);
        int recordId = getIntent().getIntExtra(TRADE_RECORD_ID, 0);
        API.getTradeRecordDetail(this, typeId, recordId, new HttpCallback<TradeDetail>(this) {
            @Override
            public void onSuccess(TradeDetail data) {

                Resources resources = getResources();
                String[] tradeStatuses = resources.getStringArray(R.array.trade_status);
                mTradeStatus.setText(tradeStatuses[data.getTradedStatus()]);
                mTradeAmount.setText(getString(R.string.notation_yuan) + data.getAmount());
                mTradePoundage.setText(getString(R.string.notation_yuan) + data.getPoundage());
                mTradeTime.setText(data.getTradedTimeStr());

                String[] commercialKeys = resources.getStringArray(R.array.trade_item_commercial);
                String[] bankKeys = resources.getStringArray(R.array.trade_item_bank);

                for (int i = 0; i < commercialKeys.length; i++) {
                    TextView value = new TextView(TradeDetailActivity.this);
                    value.setGravity(Gravity.LEFT);
                    value.setPadding(0, 5, 0, 5);
                    value.setTextColor(getResources().getColor(R.color.text6c6c6c6));
                    value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    value.setText(i == 0 ? data.getMerchantNumber()
                            : i == 1 ? data.getAgentId() + ""
                            : i == 2 ? data.getMerchantName()
                            : "");
                    mCommercialValueContainer.addView(value);
                }

                for (int i = 0; i < bankKeys.length; i++) {
                    TextView value = new TextView(TradeDetailActivity.this);
                    value.setGravity(Gravity.LEFT);
                    value.setPadding(0, 5, 0, 5);
                    value.setTextColor(resources.getColor(R.color.text6c6c6c6));
                    value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    value.setText(i == 0 ? data.getTerminalNumber()
                            : i == 1 ? data.getPayFromAccount()
                            : i == 2 ? data.getPayIntoAccount()
                            : i == 3 ? data.getPayChannelName()
                            : i == 4 ? data.getProfitPrice() + ""
                            : i == 5 ? data.getAmount() + ""
                            : i == 6 ? data.getTradedTimeStr()
                            : i == 7 ? tradeStatuses[data.getTradedStatus()]
                            : i == 8 ? data.getBatchNumber()
                            : i == 9 ? data.getTradeNumber()
                            : "");
                    mBankValueContainer.addView(value);
                }
            }

            @Override
            public TypeToken<TradeDetail> getTypeToken() {
                return new TypeToken<TradeDetail>() {
                };
            }
        });
    }

    private void initialViews() {
        mTradeStatus = (TextView) findViewById(R.id.trade_detail_status);
        mTradeAmount = (TextView) findViewById(R.id.trade_detail_amount);
        mTradePoundage = (TextView) findViewById(R.id.trade_detail_poundage);
        mTradeTime = (TextView) findViewById(R.id.trade_detail_time);

        mCommercialKeyContainer = (LinearLayout) findViewById(R.id.trade_commercial_key_container);
        mCommercialValueContainer = (LinearLayout) findViewById(R.id.trade_commercial_value_container);
        mBankKeyContainer = (LinearLayout) findViewById(R.id.trade_bank_key_container);
        mBankValueContainer = (LinearLayout) findViewById(R.id.trade_bank_value_container);

        Resources resources = getResources();
        String[] commercialKeys = resources.getStringArray(R.array.trade_item_commercial);
        String[] bankKeys = resources.getStringArray(R.array.trade_item_bank);

        for (int i = 0; i < commercialKeys.length; i++) {
            TextView key = new TextView(this);
            key.setGravity(Gravity.RIGHT);
            key.setPadding(0, 5, 0, 5);
            key.setTextColor(resources.getColor(R.color.text6c6c6c6));
            key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            key.setText(commercialKeys[i]);
            mCommercialKeyContainer.addView(key);
        }

        for (int i = 0; i < bankKeys.length; i++) {
            TextView key = new TextView(this);
            key.setGravity(Gravity.RIGHT);
            key.setPadding(0, 5, 0, 5);
            key.setTextColor(resources.getColor(R.color.text6c6c6c6));
            key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            key.setText(bankKeys[i]);
            mBankKeyContainer.addView(key);
        }
    }
}
