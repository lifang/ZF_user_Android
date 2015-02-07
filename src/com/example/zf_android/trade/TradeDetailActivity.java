package com.example.zf_android.trade;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

import org.w3c.dom.Text;

public class TradeDetailActivity extends Activity {

    private LinearLayout mCommercialKeyContainer;
    private LinearLayout mCommercialValueContainer;

    private LinearLayout mBankKeyContainer;
    private LinearLayout mBankValueContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_item);
        new TitleMenuUtil(this, getString(R.string.title_trade_detail)).show();
        initialViews();
    }

    private void initialViews() {
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

            TextView value = new TextView(this);
            value.setGravity(Gravity.LEFT);
            value.setPadding(0, 5, 0, 5);
            value.setTextColor(resources.getColor(R.color.text6c6c6c6));
            value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            value.setText("测试数据" + i);
            mCommercialValueContainer.addView(value);
        }

        for (int i = 0; i < bankKeys.length; i++) {
            TextView key = new TextView(this);
            key.setGravity(Gravity.RIGHT);
            key.setPadding(0, 5, 0, 5);
            key.setTextColor(resources.getColor(R.color.text6c6c6c6));
            key.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            key.setText(bankKeys[i]);
            mBankKeyContainer.addView(key);

            TextView value = new TextView(this);
            value.setGravity(Gravity.LEFT);
            value.setPadding(0, 5, 0, 5);
            value.setTextColor(resources.getColor(R.color.text6c6c6c6));
            value.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            value.setText("测试数据" + i);
            mBankValueContainer.addView(value);
        }


    }
}
