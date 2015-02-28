package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleMarkActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_mark);
        new TitleMenuUtil(this, getString(R.string.title_after_sale_mark)).show();
    }
}
