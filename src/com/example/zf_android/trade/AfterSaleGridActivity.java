package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;

/**
 * Created by Leo on 2015/2/26.
 */
public class AfterSaleGridActivity extends Activity implements View.OnClickListener {

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
        String recordTitle = "";
        switch (v.getId()) {
            case R.id.after_sale_maintain:
                recordType = AfterSaleListActivity.RECORD_MAINTAIN;
                break;
            case R.id.after_sale_return:
                recordType = AfterSaleListActivity.RECORD_RETURN;
                break;
            case R.id.after_sale_cancel:
                recordType = AfterSaleListActivity.RECORD_CANCEL;
                break;
            case R.id.after_sale_change:
                recordType = AfterSaleListActivity.RECORD_CHANGE;
                break;
            case R.id.after_sale_update:
                recordType = AfterSaleListActivity.RECORD_UPDATE;
                break;
            case R.id.after_sale_lease:
                recordType = AfterSaleListActivity.RECORD_LEASE;
                break;
        }
        Intent intent = new Intent(this, AfterSaleListActivity.class);
        intent.putExtra(AfterSaleListActivity.RECORD_TYPE, recordType);
        startActivity(intent);
    }
}
