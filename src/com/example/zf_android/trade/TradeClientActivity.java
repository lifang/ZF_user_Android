package com.example.zf_android.trade;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TradeClient;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeClientActivity extends ListActivity {

    public static final String CLIENT_NUMBER = "client_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_client);
        new TitleMenuUtil(this, getString(R.string.title_trade_client)).show();

        final String selectedNumber = getIntent().getStringExtra(CLIENT_NUMBER);

        final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        final SimpleAdapter adapter = new SimpleAdapter(
                this, items,
                R.layout.trade_client_item,
                new String[]{"name", "selected"},
                new int[]{R.id.trade_client_name, R.id.trade_client_selected});
        setListAdapter(adapter);

        API.getTerminalList(this, 1, new HttpCallback<List<TradeClient>>(this) {

            @Override
            public void onSuccess(List<TradeClient> data) {
                for (TradeClient client : data) {
                    Map<String, Object> item = new HashMap<String, Object>();
                    String clientNumber = client.getSerialNum();
                    item.put("name", clientNumber);
                    item.put("selected", TextUtils.isEmpty(clientNumber)
                            || !clientNumber.equals(selectedNumber) ? null : R.drawable.icon_selected);
                    items.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public TypeToken<List<TradeClient>> getTypeToken() {
                return new TypeToken<List<TradeClient>>() {
                };
            }
        });
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView tv = (TextView) v.findViewById(R.id.trade_client_name);
        Intent intent = new Intent();
        intent.putExtra(CLIENT_NUMBER, tv.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
