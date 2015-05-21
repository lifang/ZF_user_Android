package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_TYPE;
import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.MAINTAIN;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;

/**
 * Created by Leo on 2015/2/26.
 */
public class AfterSaleGridActivity extends BaseActivity implements View.OnClickListener {

    private TextView mMaintain;
    private TextView mReturn;
    private TextView mCancel;
    private TextView mChange;
    private TextView mUpdate;
    private TextView mLease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_grid);
        new TitleMenuUtil(this, getString(R.string.title_after_sale)).show();
        initViews();
    }

    private void initViews() {
        mMaintain = (TextView) findViewById(R.id.after_sale_maintain);
        mReturn = (TextView) findViewById(R.id.after_sale_return);
        mCancel = (TextView) findViewById(R.id.after_sale_cancel);
        mChange = (TextView) findViewById(R.id.after_sale_change);
        mUpdate = (TextView) findViewById(R.id.after_sale_update);
        mLease = (TextView) findViewById(R.id.after_sale_lease);
        mMaintain.setOnClickListener(this);
        mReturn.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mChange.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
        mLease.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int recordType = 0;
        switch (v.getId()) {
            case R.id.after_sale_maintain:
                recordType = MAINTAIN;
                break;
            case R.id.after_sale_return:
                recordType = RETURN;
                break;
            case R.id.after_sale_cancel:
                recordType = CANCEL;
                break;
            case R.id.after_sale_change:
                recordType = CHANGE;
                break;
            case R.id.after_sale_update:
                recordType = UPDATE;
                break;
            case R.id.after_sale_lease:
                recordType = LEASE;
                break;
        }
        Intent intent = new Intent(this, AfterSaleListActivity.class);
        intent.putExtra(RECORD_TYPE, recordType);
        startActivity(intent);
    }
}
