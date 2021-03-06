package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TerminalIntent.HAVE_VIDEO;
import static com.example.zf_android.trade.Constants.TerminalIntent.REQUEST_DETAIL;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.example.zf_android.trade.Constants.TerminalStatus.PART_OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.UNOPENED;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalItem;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.video.VideoActivity;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/3/5.
 */
public class ApplyListActivity extends BaseActivity implements
		XListView.IXListViewListener {

	private LayoutInflater mInflater;
	private XListView mApplyList;
	private List<TerminalItem> mTerminalItems;
	private ApplyListAdapter mAdapter;

	private int page = 0;
	private boolean noMoreData = false;
	private String pullType = "loadData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_list);
		new TitleMenuUtil(this, getString(R.string.title_apply_open)).show();

		initViews();
		loadData();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mApplyList = (XListView) findViewById(R.id.apply_list);
		mTerminalItems = new ArrayList<TerminalItem>();
		mAdapter = new ApplyListAdapter();

		View header = new View(this);
		mApplyList.addHeaderView(header);
		// init the XListView
		mApplyList.initHeaderAndFooter();
		mApplyList.setXListViewListener(this);
		mApplyList.setPullLoadEnable(true);

		mApplyList.setAdapter(mAdapter);

	}

	private void loadData() {
		API.getApplyList(this, MyApplication.getInstance().getCustomerId(),
				page + 1, Config.ROWS, new HttpCallback<List<TerminalItem>>(
						this) {
					@Override
					public void onSuccess(List<TerminalItem> data) {
						// 没有数据或者数据不够Config.ROWS个时说明后台没有更多数据 不需要上拉加载
						if (null == data || data.size() < Config.ROWS)
							noMoreData = true;

						if (pullType.equals("onRefresh")) {
							mTerminalItems.clear();
						}

						mTerminalItems.addAll(data);
						page++;
						mAdapter.notifyDataSetChanged();
					}

					@Override
					public void preLoad() {
						super.preLoad();
					}

					@Override
					public void postLoad() {
						loadFinished();
						super.postLoad();
					}

					@Override
					public TypeToken<List<TerminalItem>> getTypeToken() {
						return new TypeToken<List<TerminalItem>>() {
						};
					}
				});
	}

	@Override
	public void onRefresh() {
		page = 0;
		pullType = "onRefresh";
		noMoreData = false;
		mApplyList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		pullType = "onLoadMore";
		if (noMoreData) {
			mApplyList.stopLoadMore();
			mApplyList.setPullLoadEnable(false);
			CommonUtil.toastShort(this, "没有更多数据");
		} else {
			loadData();
		}
	}

	private void loadFinished() {
		mApplyList.stopRefresh();
		mApplyList.stopLoadMore();
		mApplyList.setRefreshTime(Tools.getHourAndMin());
	}

	private class ApplyListAdapter extends BaseAdapter {
		ApplyListAdapter() {
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
				convertView = mInflater.inflate(R.layout.apply_list_item, null);
				holder = new ViewHolder();
				holder.tvTerminalNumber = (TextView) convertView
						.findViewById(R.id.apply_terminal_number);
				holder.tvTerminalStatus = (TextView) convertView
						.findViewById(R.id.apply_terminal_status);
				holder.btnOpen = (Button) convertView
						.findViewById(R.id.apply_button_open);
				holder.btnVideo = (Button) convertView
						.findViewById(R.id.apply_button_video);
				holder.terminal_buttons = (LinearLayout) convertView
						.findViewById(R.id.terminal_buttons);
				holder.tvfill1 = (TextView) convertView
						.findViewById(R.id.tv_fill1);
				holder.tvfill2 = (TextView) convertView
						.findViewById(R.id.tv_fill2);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalItem item = getItem(i);
			holder.tvTerminalNumber.setText(item.getTerminalNumber());
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			holder.tvTerminalStatus.setText(status[item.getStatus()]);

			if (item.getStatus() == UNOPENED) {
				holder.btnOpen.setEnabled(true);
				if (!"".equals(item.getAppid())) {
					holder.btnOpen
							.setText(getString(R.string.apply_button_reopen));
				} else {
					holder.btnOpen
							.setText(getString(R.string.apply_button_open));
				}
			} else if (item.getStatus() == PART_OPENED) {
				holder.btnOpen.setEnabled(true);
				holder.btnOpen.setText(getString(R.string.apply_button_reopen));
			} else {
				holder.btnOpen.setEnabled(false);
			}
			holder.btnOpen.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if (item.getStatus() == UNOPENED
							&& "".equals(item.getAppid())) {

						openDialog(item);

					} else {

						Intent intent = new Intent(ApplyListActivity.this,
								ApplyDetailActivity.class);
						intent.putExtra(TERMINAL_ID, item.getId());
						intent.putExtra(TERMINAL_NUMBER,
								item.getTerminalNumber());
						intent.putExtra(TERMINAL_STATUS, item.getStatus());
						startActivityForResult(intent, REQUEST_DETAIL);
					}
				}
			});

			Boolean videoBoolean = 1 == item.getHasVideoVerify();
			if (!videoBoolean) {
				holder.btnVideo.setVisibility(View.GONE);
				holder.tvfill1.setVisibility(View.VISIBLE);
				holder.tvfill2.setVisibility(View.VISIBLE);
			}

			holder.btnVideo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// 添加视频审核
					Intent intent = new Intent(ApplyListActivity.this,
							VideoActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					startActivity(intent);
				}
			});

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(ApplyListActivity.this,
							TerminalDetailActivity.class);
					intent.putExtra(HAVE_VIDEO, item.getHasVideoVerify());
					intent.putExtra(TERMINAL_ID, item.getId());
					intent.putExtra(TERMINAL_NUMBER, item.getTerminalNumber());
					intent.putExtra(TERMINAL_STATUS, item.getStatus());
					intent.putExtra("xieyi", item.getOpeningProtocol());
					
					startActivity(intent);
				}
			});

			return convertView;
		}
	}

	private static class ViewHolder {
		public TextView tvTerminalNumber;
		public TextView tvTerminalStatus;
		public TextView tvfill1;
		public TextView tvfill2;
		public LinearLayout terminal_buttons;
		public Button btnOpen;
		public Button btnVideo;
	}

	private void openDialog(final TerminalItem item) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				ApplyListActivity.this);

		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.protocoldialog, null);
		builder.setView(view);

		final AlertDialog dialog = builder.create();
		dialog.show();
		final CheckBox cb = (CheckBox) view.findViewById(R.id.cb);

		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

		TextView tv_protocol = (TextView) view.findViewById(R.id.tv_protocol);

		tv_protocol.setText(item.getOpeningProtocol());

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!cb.isChecked()) {

					CommonUtil.toastShort(ApplyListActivity.this,
							"请仔细阅读开通协议，并接受协议");

				} else {

					dialog.dismiss();
					Intent intent = new Intent(ApplyListActivity.this,
							ApplyDetailActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					intent.putExtra(TERMINAL_NUMBER, item.getTerminalNumber());
					intent.putExtra(TERMINAL_STATUS, item.getStatus());
					startActivityForResult(intent, REQUEST_DETAIL);
				}

			}
		});
		// dialog.show();

	}

	@Override
	public void onResume() {
		super.onResume();
		page = 0;
		pullType = "onRefresh";
		noMoreData = false;
		mApplyList.setPullLoadEnable(true);
		loadData();
	}
}
