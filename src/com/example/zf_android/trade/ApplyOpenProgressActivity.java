package com.example.zf_android.trade;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.ApplyOpenProgress;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.TextWatcherAdapter;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/3/13.
 */
public class ApplyOpenProgressActivity extends BaseActivity {

	private LayoutInflater mInflater;
	private EditText mPhone;
	private Button mQuery;
	private TextView mTips;
	private LinearLayout mContent;
	private ListView mListView;
	private ApplyProgressListAdapter mAdapter;
	private List<ApplyOpenProgress> mEntities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_open_progress);

		new TitleMenuUtil(this, getString(R.string.title_apply_open_progress)).show();

		initViews();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);

		mTips = (TextView) findViewById(R.id.apply_progress_tips);
		mContent = (LinearLayout) findViewById(R.id.apply_progress_content);

		mPhone = (EditText) findViewById(R.id.apply_progress_phone);
		mPhone.requestFocus();
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mPhone, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
		mPhone.addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void afterTextChanged(Editable s) {
				mQuery.setEnabled(mQuery.length() > 0);
			}
		});

		mQuery = (Button) findViewById(R.id.apply_progress_query);
		mQuery.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				API.queryApplyProgress(ApplyOpenProgressActivity.this,
						MyApplication.getInstance().getCustomerId(), mPhone.getText().toString().trim(),
						new HttpCallback<List<ApplyOpenProgress>>(ApplyOpenProgressActivity.this) {
					@Override
					public void onSuccess(List<ApplyOpenProgress> data) {
						mEntities.clear();
						if (null != data && data.size() > 0) {
							mTips.setVisibility(View.GONE);
							mContent.setVisibility(View.VISIBLE);
							mEntities.addAll(data);
							mAdapter.notifyDataSetChanged();
						} else {
							mTips.setVisibility(View.VISIBLE);
							mContent.setVisibility(View.GONE);
						}
					}

					@Override
					public TypeToken<List<ApplyOpenProgress>> getTypeToken() {
						return new TypeToken<List<ApplyOpenProgress>>() {
						};
					}
				});
			}
		});

		mEntities = new ArrayList<ApplyOpenProgress>();
		mListView = (ListView) findViewById(R.id.apply_progress_list);
		mAdapter = new ApplyProgressListAdapter();
		mListView.setAdapter(mAdapter);
	}

	private TextView createKeyText() {
		TextView tv = new TextView(this);
		tv.setGravity(Gravity.RIGHT);
		tv.setPadding(0, 10, 0, 10);
		tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		tv.setSingleLine(true);
		return tv;
	}

	private TextView createValueText() {
		TextView tv = new TextView(this);
		tv.setGravity(Gravity.LEFT);
		tv.setPadding(0, 10, 0, 10);
		tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		tv.setSingleLine(true);
		return tv;
	}

	private class ApplyProgressListAdapter extends BaseAdapter {
		ApplyProgressListAdapter() {
		}

		@Override
		public int getCount() {
			return mEntities.size();
		}

		@Override
		public ApplyOpenProgress getItem(int i) {
			return mEntities.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.apply_open_progress_item, null);
				holder = new ViewHolder();
				holder.left = (LinearLayout) convertView.findViewById(R.id.apply_progress_key);
				holder.right = (LinearLayout) convertView.findViewById(R.id.apply_progress_value);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.left.removeAllViews();
			holder.right.removeAllViews();
			final ApplyOpenProgress item = getItem(i);
			String[] status = getResources().getStringArray(R.array.terminal_open_status);

			TextView terminalKey = createKeyText();
			terminalKey.setText(getString(R.string.apply_progress_terminal));
			holder.left.addView(terminalKey);

			TextView terminalValue = createValueText();
			terminalValue.setText(item.getTerminalNumber());
			holder.right.addView(terminalValue);

			if (item.getOpenStatus() != null) {
				for (ApplyOpenProgress.OpenStatus openStatus : item.getOpenStatus()) {
					TextView key = createKeyText();
					key.setText(openStatus.tradeValue);
					holder.left.addView(key);

					TextView value = createValueText();
					value.setText(status[openStatus.status]);
					holder.right.addView(value);
				}
			}
			return convertView;
		}
	}

	private static class ViewHolder {
		public LinearLayout left;
		public LinearLayout right;
	}
}
