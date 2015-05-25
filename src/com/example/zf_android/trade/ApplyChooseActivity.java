package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.example.zf_android.trade.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_ID;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_TITLE;

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
import com.example.zf_android.trade.entity.ApplyChooseItem;
import com.umeng.analytics.MobclickAgent;

public class ApplyChooseActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_list);

		String title = getIntent().getStringExtra(CHOOSE_TITLE);
		List<ApplyChooseItem> chooseItems = (List<ApplyChooseItem>) getIntent().getSerializableExtra(CHOOSE_ITEMS);
		int selectedId = getIntent().getIntExtra(SELECTED_ID, 0);

		new TitleMenuUtil(this, title).show();

		final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (ApplyChooseItem chooseItem : chooseItems) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("id", chooseItem.getId());
			item.put("name", chooseItem.getTitle());
			item.put("selected", chooseItem.getId() == selectedId ? R.drawable.icon_selected : null);
		    items.add(item);
        }
		final SimpleAdapter adapter = new SimpleAdapter(
				this, items,
				R.layout.simple_list_item,
				new String[]{"id", "name", "selected"},
				new int[]{R.id.item_id, R.id.item_name, R.id.item_selected});
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		TextView tvId = (TextView) v.findViewById(R.id.item_id);
		TextView tvTitle = (TextView) v.findViewById(R.id.item_name);
		Intent intent = new Intent();
		intent.putExtra(SELECTED_ID, Integer.parseInt(tvId.getText().toString()));
		intent.putExtra(SELECTED_TITLE, tvTitle.getText().toString());
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
