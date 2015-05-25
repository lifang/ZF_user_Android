package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TerminalIntent.CHANNEL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.CHANNEL_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalChannel;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Leo on 2015/3/5.
 */
public class TerminalChannelActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list);
		new TitleMenuUtil(this, getString(R.string.title_terminal_choose_channel)).show();

		final int selectedId = getIntent().getIntExtra(CHANNEL_ID, 0);

		final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		final SimpleAdapter adapter = new SimpleAdapter(
				this, items,
				R.layout.simple_list_item,
				new String[]{"id", "name", "selected"},
				new int[]{R.id.item_id, R.id.item_name, R.id.item_selected});
		setListAdapter(adapter);

		API.getChannelList(this, new HttpCallback<List<TerminalChannel>>(this) {

			@Override
			public void onSuccess(List<TerminalChannel> data) {
				for (TerminalChannel channel : data) {
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("id", channel.getId());
					item.put("name", channel.getName());
					item.put("selected", selectedId == channel.getId() ? R.drawable.icon_selected : null);
					items.add(item);
				}
				adapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<List<TerminalChannel>> getTypeToken() {
				return new TypeToken<List<TerminalChannel>>() {
				};
			}
		});
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView idTv = (TextView) v.findViewById(R.id.item_id);
		TextView nameTv = (TextView) v.findViewById(R.id.item_name);
		Intent intent = new Intent();
		intent.putExtra(CHANNEL_ID, Integer.parseInt(idTv.getText().toString()));
		intent.putExtra(CHANNEL_NAME, nameTv.getText().toString());
		setResult(RESULT_OK, intent);
		finish();
	}
	
	 @Override
		protected void onResume() {
			super.onResume();
			MobclickAgent.onPageStart( this.toString() );
			MobclickAgent.onResume(this);
		}

		@Override
		protected void onPause() {
			super.onPause();
			MobclickAgent.onPageEnd(this.toString());
			MobclickAgent.onPause(this);
		}
}
