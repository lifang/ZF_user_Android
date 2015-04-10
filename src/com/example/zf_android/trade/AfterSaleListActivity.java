package com.example.zf_android.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Pageable;
import com.example.zf_android.trade.entity.AfterSaleRecord;
import com.example.zf_android.trade.widget.XListView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_ID;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_STATUS;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_TYPE;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.REQUEST_DETAIL;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.REQUEST_MARK;
import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.MAINTAIN;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;

/**
 * Created by Leo on 2015/2/26.
 */
public class AfterSaleListActivity extends Activity implements XListView.IXListViewListener {

	private int mRecordType;

	private XListView mListView;
	private RecordListAdapter mAdapter;
	private List<AfterSaleRecord> mEntities;

	private int page = 0;
	private int total = 0;
	private final int rows = 10;

	private LayoutInflater mInflater;

	// cancel apply button listener
	private View.OnClickListener mCancelApplyListener;
	// submit mark button listener
	private View.OnClickListener mSubmitMarkListener;
	// pay maintain button listener;
	private View.OnClickListener mPayMaintainListener;
	// submit cancel button listener
	private View.OnClickListener mSubmitCancelListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordType = getIntent().getIntExtra(RECORD_TYPE, 0);

		setContentView(R.layout.activity_after_sale_list);
		String[] titles = getResources().getStringArray(R.array.title_after_sale_list);
		new TitleMenuUtil(this, titles[mRecordType]).show();


		mInflater = LayoutInflater.from(this);
		mEntities = new ArrayList<AfterSaleRecord>();
		mListView = (XListView) findViewById(R.id.after_sale_list);
		mAdapter = new RecordListAdapter();

		// init the XListView
		mListView.initHeaderAndFooter();
		mListView.setXListViewListener(this);
		mListView.setPullLoadEnable(true);

		mListView.setAdapter(mAdapter);
		initButtonListeners();
		loadData();
	}

	private void loadData() {
		API.getAfterSaleRecordList(this, mRecordType, Constants.CUSTOMER_ID, page + 1, rows, new HttpCallback<Pageable<AfterSaleRecord>>(this) {
			@Override
			public void onSuccess(Pageable<AfterSaleRecord> data) {
				if (null != data.getContent()) {
					mEntities.addAll(data.getContent());
				}
				page++;
				total = data.getTotal();
				mAdapter.notifyDataSetChanged();
				
				loadFinished();
			}

			@Override
			public TypeToken<Pageable<AfterSaleRecord>> getTypeToken() {
				return new TypeToken<Pageable<AfterSaleRecord>>() {
				};
			}
		});

	}

	private void initButtonListeners() {
		mCancelApplyListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AfterSaleRecord record = (AfterSaleRecord) v.getTag();
				API.cancelAfterSaleApply(AfterSaleListActivity.this, mRecordType, record.getId(), 
						new HttpCallback(AfterSaleListActivity.this) {
					@Override
					public void onSuccess(Object data) {
						record.setStatus(5);
						mAdapter.notifyDataSetChanged();
						CommonUtil.toastShort(AfterSaleListActivity.this, getString(R.string.toast_cancel_apply_success));
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
			}
		};

		mSubmitMarkListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AfterSaleRecord record = (AfterSaleRecord) v.getTag();
				Intent intent = new Intent(AfterSaleListActivity.this, AfterSaleMarkActivity.class);
				intent.putExtra(RECORD_TYPE, mRecordType);
				intent.putExtra(RECORD_ID, record.getId());
				startActivityForResult(intent, REQUEST_MARK);
			}
		};

		mPayMaintainListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(AfterSaleListActivity.this, AfterSalePayActivity.class));
			}
		};

		mSubmitCancelListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AfterSaleRecord record = (AfterSaleRecord) v.getTag();
				API.resubmitCancel(AfterSaleListActivity.this, record.getId(), new HttpCallback(AfterSaleListActivity.this) {
					@Override
					public void onSuccess(Object obj) {
						record.setStatus(1);
						mAdapter.notifyDataSetChanged();
						CommonUtil.toastShort(AfterSaleListActivity.this, getString(R.string.toast_resubmit_cancel_success));
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
			}
		};
	}

	@Override
	public void onRefresh() {
		page = 0;
		mEntities.clear();
		loadData();
	}

	@Override
	public void onLoadMore() {
		if (mEntities.size() >= total) {
			mListView.stopLoadMore();
			CommonUtil.toastShort(this, "no more data");
		} else {
			loadData();
		}
	}

	private void loadFinished() {
		mListView.stopRefresh();
		mListView.stopLoadMore();
		mListView.setRefreshTime(Tools.getHourAndMin());
	}

	private class RecordListAdapter extends BaseAdapter {
		RecordListAdapter() {
		}

		@Override
		public int getCount() {
			return mEntities.size();
		}

		@Override
		public AfterSaleRecord getItem(int position) {
			return mEntities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.after_sale_record_item, null);
				holder = new ViewHolder();
				holder.tvNumberTitle = (TextView) convertView.findViewById(R.id.after_sale_number_title);
				holder.tvNumber = (TextView) convertView.findViewById(R.id.after_sale_number);
				holder.tvTime = (TextView) convertView.findViewById(R.id.after_sale_time);
				holder.tvTerminal = (TextView) convertView.findViewById(R.id.after_sale_terminal);
				holder.tvStatus = (TextView) convertView.findViewById(R.id.after_sale_status);
				holder.llButtonContainer = (LinearLayout) convertView.findViewById(R.id.after_sale_button_container);
				holder.btnLeft = (Button) convertView.findViewById(R.id.after_sale_button_left);
				holder.btnRight = (Button) convertView.findViewById(R.id.after_sale_button_right);
				holder.btnCenter = (Button) convertView.findViewById(R.id.after_sale_button_center);
				holder.btnCenterBlank = (Button) convertView.findViewById(R.id.after_sale_button_center_blank);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final AfterSaleRecord data = getItem(position);
			String[] numberTitles = getResources().getStringArray(R.array.after_sale_number);
			holder.tvNumberTitle.setText(numberTitles[mRecordType]);
			holder.tvNumber.setText(data.getApplyNum());
			holder.tvTime.setText(data.getCreateTime());
			holder.tvTerminal.setText(data.getTerminalNum());
			String[] status = getResources().getStringArray(
					mRecordType == MAINTAIN ? R.array.maintain_status
							: mRecordType == RETURN ? R.array.return_status
							: mRecordType == CANCEL ? R.array.cancel_status
							: mRecordType == CHANGE ? R.array.change_status
							: mRecordType == UPDATE ? R.array.update_status
							: R.array.lease_status
			);
			holder.tvStatus.setText(status[data.getStatus()]);

			switch (mRecordType) {
				case MAINTAIN:
					if (data.getStatus() == 1) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.VISIBLE);
						holder.btnRight.setVisibility(View.VISIBLE);
						holder.btnCenter.setVisibility(View.GONE);
						holder.btnCenterBlank.setVisibility(View.GONE);

						holder.btnLeft.setText(getString(R.string.button_cancel_apply));
						holder.btnLeft.setTag(data);
						holder.btnLeft.setOnClickListener(mCancelApplyListener);

						holder.btnRight.setText(getString(R.string.button_pay));
						holder.btnRight.setOnClickListener(mPayMaintainListener);
					} else if (data.getStatus() == 2) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.VISIBLE);
						holder.btnCenterBlank.setVisibility(View.GONE);

						holder.btnCenter.setText(getString(R.string.button_submit_flow));
						holder.btnCenter.setTag(data);
						holder.btnCenter.setOnClickListener(mSubmitMarkListener);
					} else {
						holder.llButtonContainer.setVisibility(View.GONE);
					}
					break;
				case CANCEL:
					if (data.getStatus() == 1) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.GONE);
						holder.btnCenterBlank.setVisibility(View.VISIBLE);

						holder.btnCenterBlank.setText(R.string.button_cancel_apply);
						holder.btnCenterBlank.setTag(data);
						holder.btnCenterBlank.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 5) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.VISIBLE);
						holder.btnCenterBlank.setVisibility(View.GONE);

						holder.btnCenter.setText(R.string.button_submit_cancel);
						holder.btnCenter.setTag(data);
						holder.btnCenter.setOnClickListener(mSubmitCancelListener);
					} else {
						holder.llButtonContainer.setVisibility(View.GONE);
					}
					break;
				case UPDATE:
					if (data.getStatus() == 1) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.GONE);
						holder.btnCenterBlank.setVisibility(View.VISIBLE);

						holder.btnCenterBlank.setText(getString(R.string.button_cancel_apply));
						holder.btnCenterBlank.setTag(data);
						holder.btnCenterBlank.setOnClickListener(mCancelApplyListener);
					} else {
						holder.llButtonContainer.setVisibility(View.GONE);
					}
					break;
				case RETURN:
				case CHANGE:
				case LEASE:
					if (data.getStatus() == 1) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.GONE);
						holder.btnCenterBlank.setVisibility(View.VISIBLE);

						holder.btnCenterBlank.setText(R.string.button_cancel_apply);
						holder.btnCenterBlank.setTag(data);
						holder.btnCenterBlank.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 2) {
						holder.llButtonContainer.setVisibility(View.VISIBLE);
						holder.btnLeft.setVisibility(View.GONE);
						holder.btnRight.setVisibility(View.GONE);
						holder.btnCenter.setVisibility(View.VISIBLE);
						holder.btnCenterBlank.setVisibility(View.GONE);

						holder.btnCenter.setText(R.string.button_submit_flow);
						holder.btnCenter.setTag(data);
						holder.btnCenter.setOnClickListener(mSubmitMarkListener);
					} else {
						holder.llButtonContainer.setVisibility(View.GONE);
					}
					break;
			}

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(AfterSaleListActivity.this, AfterSaleDetailActivity.class);
					intent.putExtra(RECORD_TYPE, mRecordType);
					intent.putExtra(RECORD_ID, data.getId());
					startActivityForResult(intent, REQUEST_DETAIL);
				}
			});

			return convertView;
		}
	}

	private static class ViewHolder {
		public TextView tvNumberTitle;
		public TextView tvNumber;
		public TextView tvTime;
		public TextView tvTerminal;
		public TextView tvStatus;
		public LinearLayout llButtonContainer;
		public Button btnLeft;
		public Button btnRight;
		public Button btnCenter;
		public Button btnCenterBlank;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_DETAIL:
					int id = data.getIntExtra(RECORD_ID, 0);
					int status = data.getIntExtra(RECORD_STATUS, 0);
					if (id > 0 && status > 0) {
						for (AfterSaleRecord record : mEntities) {
							if (record.getId() == id) {
								record.setStatus(status);
								mAdapter.notifyDataSetChanged();
							}
						}
					}
					break;
				case REQUEST_MARK:
					CommonUtil.toastShort(this, getString(R.string.toast_add_mark_success));
					break;
			}

		}
	}
}
