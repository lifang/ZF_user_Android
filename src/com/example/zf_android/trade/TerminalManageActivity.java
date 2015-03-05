package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalItem;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.zf_android.trade.Constants.TerminalIntent.REQUEST_ADD;
import static com.example.zf_android.trade.Constants.TerminalIntent.REQUEST_DETAIL;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.example.zf_android.trade.Constants.TerminalStatus.CANCELED;
import static com.example.zf_android.trade.Constants.TerminalStatus.OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.PART_OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.STOPPED;
import static com.example.zf_android.trade.Constants.TerminalStatus.UNOPENED;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalManageActivity extends Activity {

	private LayoutInflater mInflater;
	private ListView mTerminalList;
	private List<TerminalItem> mTerminalItems;
	private TerminalListAdapter mAdapter;

	private View.OnClickListener mSyncListener;
	private View.OnClickListener mOpenListener;
	private View.OnClickListener mPosListener;
	private View.OnClickListener mVideoListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_manage);
		new TitleMenuUtil(this, getString(R.string.title_terminal_management)).show();

		initViews();
		initBtnListeners();
		loadData();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mTerminalList = (ListView) findViewById(R.id.terminal_list);
		mTerminalItems = new ArrayList<TerminalItem>();
		mAdapter = new TerminalListAdapter();
		mTerminalList.setAdapter(mAdapter);

		LinearLayout listHeader = (LinearLayout) mInflater.inflate(R.layout.terminal_list_header, null);
		TextView addTerminal = (TextView) listHeader.findViewById(R.id.terminal_add_others);
		addTerminal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(TerminalManageActivity.this, TerminalAddActivity.class), REQUEST_ADD);
			}
		});
		mTerminalList.addHeaderView(listHeader);
	}

	private void initBtnListeners() {
		mSyncListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.toastShort(TerminalManageActivity.this, "synchronising...");
			}
		};
		mOpenListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(TerminalManageActivity.this, ApplyDetailActivity.class);
				startActivity(intent);
			}
		};
		mPosListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalItem item = (TerminalItem) view.getTag();
				API.findPosPassword(TerminalManageActivity.this, item.getId(), new HttpCallback(TerminalManageActivity.this) {
					@Override
					public void onSuccess(Object data) {

					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
			}
		};
		mVideoListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.toastShort(TerminalManageActivity.this, "not yet completed...");
			}
		};
	}

	private void loadData() {
		API.getTerminalApplyList(this, 80, 1, 10, new HttpCallback<List<TerminalItem>>(this) {
			@Override
			public void onSuccess(List<TerminalItem> data) {
				mTerminalItems.addAll(data);
				mAdapter.notifyDataSetChanged();
			}

			@Override
			public TypeToken<List<TerminalItem>> getTypeToken() {
				return new TypeToken<List<TerminalItem>>() {
				};
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) return;
		switch (requestCode) {
			case REQUEST_ADD:
				mTerminalItems.clear();
				loadData();
				break;
			case REQUEST_DETAIL:

				break;
		}
	}

	class TerminalListAdapter extends BaseAdapter {
		TerminalListAdapter() {
		}

		@Override
		public int getCount() {
			return mTerminalItems.size();
		}

		@Override
		public TerminalItem getItem(int i) {
			return mTerminalItems.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.terminal_list_item, null);
				holder = new ViewHolder();
				holder.tvTerminalNumber = (TextView) convertView.findViewById(R.id.terminal_number);
				holder.tvTerminalStatus = (TextView) convertView.findViewById(R.id.terminal_status);
				holder.llButtonContainer = (LinearLayout) convertView.findViewById(R.id.terminal_button_container);
				holder.llButtons = (LinearLayout) convertView.findViewById(R.id.terminal_buttons);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalItem item = getItem(i);
			holder.tvTerminalNumber.setText(item.getTerminalNumber());
			String[] status = getResources().getStringArray(R.array.terminal_status);
			holder.tvTerminalStatus.setText(status[item.getStatus()]);

			// add buttons according to status
			holder.llButtons.removeAllViews();
			switch (item.getStatus()) {
				case OPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					addButton(holder.llButtons, R.string.terminal_button_video, item, mVideoListener);
					addButton(holder.llButtons, R.string.terminal_button_pos, item, mPosListener);
					break;
				case PART_OPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					addButton(holder.llButtons, R.string.terminal_button_sync, item, mSyncListener);
					addButton(holder.llButtons, R.string.terminal_button_reopen, item, mOpenListener);
					addButton(holder.llButtons, R.string.terminal_button_video, item, mVideoListener);
					addButton(holder.llButtons, R.string.terminal_button_pos, item, mPosListener);
					break;
				case UNOPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					addButton(holder.llButtons, R.string.terminal_button_open, item, mOpenListener);
					addButton(holder.llButtons, R.string.terminal_button_video, item, mVideoListener);
					break;
				case CANCELED:
					holder.llButtonContainer.setVisibility(View.GONE);
					break;
				case STOPPED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					addButton(holder.llButtons, R.string.terminal_button_sync, item, mSyncListener);
					break;
			}
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(TerminalManageActivity.this, TerminalDetailActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					intent.putExtra(TERMINAL_STATUS, item.getStatus());
					startActivityForResult(intent, REQUEST_DETAIL);
				}
			});
			return convertView;
		}

		private void addButton(LinearLayout ll, int res, Object tag, View.OnClickListener listener) {
			Button button = new Button(TerminalManageActivity.this);
			button.setBackgroundDrawable(getResources().getDrawable(R.drawable.blank_button_selector));
			button.setText(res);
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			button.setTextColor(getResources().getColorStateList(R.color.blank_button_selector));
			if (null != tag) {
				button.setTag(tag);
			}
			if (null != listener) {
				button.setOnClickListener(listener);
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					0, CommonUtil.dip2px(TerminalManageActivity.this, 30), 1);
			if (ll.getChildCount() > 0) {
				lp.setMargins(15, 0, 0, 0);
			}
			ll.addView(button, lp);
		}
	}

	static class ViewHolder {
		public TextView tvTerminalNumber;
		public TextView tvTerminalStatus;
		public LinearLayout llButtonContainer;
		public LinearLayout llButtons;
	}
}
