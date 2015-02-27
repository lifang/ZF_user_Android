package com.example.zf_android.trade;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.entity.AfterSaleRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2015/2/26.
 */
public class AfterSaleListActivity extends Activity {

    public static final int RECORD_MAINTAIN = 0;
    public static final int RECORD_RETURN = 1;
    public static final int RECORD_CANCEL = 2;
    public static final int RECORD_CHANGE = 3;
    public static final int RECORD_UPDATE = 4;
    public static final int RECORD_LEASE = 5;

    public static final String RECORD_TYPE = "record_type";
    public static final String RECORD_TITLE = "record_title";
    private int mRecordType;
    private String mRecordTitle;

    private ListView mListView;
    private RecordListAdapter mAdapter;
    private List<AfterSaleRecord> mEntities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecordType = getIntent().getIntExtra(RECORD_TYPE, 0);
        mRecordTitle = getIntent().getStringExtra(RECORD_TITLE);

        setContentView(R.layout.activity_after_sale_list);
        new TitleMenuUtil(this, mRecordTitle).show();

        mEntities = new ArrayList<AfterSaleRecord>();
        for (int i = 0; i < 10; i++) {
            mEntities.add(new AfterSaleRecord());
        }
        mListView = (ListView) findViewById(R.id.after_sale_list);
        mAdapter = new RecordListAdapter();
        mListView.setAdapter(mAdapter);
    }

    class RecordListAdapter extends BaseAdapter {
        RecordListAdapter() {
        }

        @Override
        public int getCount() {
            return mEntities.size();
        }

        @Override
        public Object getItem(int position) {
            return mEntities.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return LayoutInflater.from(AfterSaleListActivity.this).inflate(R.layout.after_sale_record_item, null);
        }
    }
}
