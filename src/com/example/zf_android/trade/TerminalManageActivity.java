package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TerminalIntent.REQUEST_ADD;
import static com.example.zf_android.trade.Constants.TerminalIntent.REQUEST_DETAIL;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.HAVE_VIDEO;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.example.zf_android.trade.Constants.TerminalStatus.CANCELED;
import static com.example.zf_android.trade.Constants.TerminalStatus.OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.PART_OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.STOPPED;
import static com.example.zf_android.trade.Constants.TerminalStatus.UNOPENED;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Page;
import com.example.zf_android.trade.entity.TerminalItem;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.video.VideoActivity;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalManageActivity extends Activity implements
		XListView.IXListViewListener {

	private LayoutInflater mInflater;
	private XListView mTerminalList;
	private List<TerminalItem> mTerminalItems;
	private TerminalListAdapter mAdapter;

	private int page = 0;
	private int total = 0;

	private View.OnClickListener mSyncListener;
	private View.OnClickListener mOpenListener;
	private View.OnClickListener mPosListener;
	private View.OnClickListener mVideoListener;

	private String pullType = "loadData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terminal_manage);
		new TitleMenuUtil(this, getString(R.string.title_terminal_management))
				.show();

		initViews();
		initBtnListeners();
		loadData();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mTerminalList = (XListView) findViewById(R.id.terminal_list);
		mTerminalItems = new ArrayList<TerminalItem>();
		mAdapter = new TerminalListAdapter();

		LinearLayout listHeader = (LinearLayout) mInflater.inflate(
				R.layout.terminal_list_header, null);
		TextView addTerminal = (TextView) listHeader
				.findViewById(R.id.terminal_add_others);
		addTerminal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivityForResult(new Intent(TerminalManageActivity.this,
						TerminalAddActivity.class), REQUEST_ADD);
			}
		});

		// add the custom header view
		// mTerminalList.addHeaderView(listHeader);

		// init the XListView
		mTerminalList.initHeaderAndFooter();
		mTerminalList.setXListViewListener(this);
		mTerminalList.setPullLoadEnable(true);
		mTerminalList.setPullRefreshEnable(true);
		mTerminalList.setAdapter(mAdapter);
	}

	private void initBtnListeners() {
		mSyncListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalItem item = (TerminalItem) view.getTag();
				API.synchronous(TerminalManageActivity.this, item.getId(),
						new HttpCallback(TerminalManageActivity.this) {

							@Override
							public void onSuccess(Object data) {
								CommonUtil.toastShort(
										TerminalManageActivity.this,
										data.toString());
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
			}
		};

		mOpenListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalItem item = (TerminalItem) view.getTag();

				if (item.getStatus() == UNOPENED && "".equals(item.getAppid())) {

					openDialog(item);

				} else {
					if (!"".equals(item.getAppid())
							&& Integer.parseInt(item.getOpenstatus()) == 6) {
						CommonUtil.toastShort(TerminalManageActivity.this,
								"正在第三方审核,请耐心等待...");

					} else {
						Intent intent = new Intent(TerminalManageActivity.this,
								ApplyDetailActivity.class);
						intent.putExtra(TERMINAL_ID, item.getId());
						intent.putExtra(TERMINAL_NUMBER,
								item.getTerminalNumber());
						intent.putExtra(TERMINAL_STATUS, item.getStatus());
						startActivity(intent);
					}
				}
			}
		};
		mPosListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				TerminalItem item = (TerminalItem) view.getTag();
				API.findPosPassword(TerminalManageActivity.this, item.getId(),
						new HttpCallback(TerminalManageActivity.this) {
							@Override
							public void onSuccess(Object data) {
								final String password = data.toString();
								final AlertDialog.Builder builder = new AlertDialog.Builder(
										TerminalManageActivity.this);
								builder.setMessage(password);
								builder.setPositiveButton(
										getString(R.string.button_copy),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												CommonUtil
														.copy(TerminalManageActivity.this,
																password);
												dialogInterface.dismiss();
												CommonUtil
														.toastShort(
																TerminalManageActivity.this,
																getString(R.string.toast_copy_password));
											}
										});
								builder.setNegativeButton(
										getString(R.string.button_cancel),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												dialogInterface.dismiss();
											}
										});
								builder.show();
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
				// 添加视频审核
				TerminalItem item = (TerminalItem) view.getTag();
				if (item.getStatus() == UNOPENED && "".equals(item.getAppid())) {

					CommonUtil
							.toastShort(TerminalManageActivity.this, "请先申请开通");

				} else {

					Intent intent = new Intent(TerminalManageActivity.this,
							VideoActivity.class);
					intent.putExtra(TERMINAL_ID, item.getId());
					startActivity(intent);
				}

			}
		};
	}

	private void loadData() {
		API.getTerminalApplyList(this, MyApplication.getInstance()
				.getCustomerId(), page + 1, Config.ROWS,
				new HttpCallback<Page<TerminalItem>>(this) {
					@Override
					public void onSuccess(Page<TerminalItem> data) {
						loadFinished();

						if (null != data.getList()) {
							if (pullType.equals("onRefresh")) {
								mTerminalItems.clear();
							}
							mTerminalItems.addAll(data.getList());
						}
						total = data.getTotal();
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
					public TypeToken<Page<TerminalItem>> getTypeToken() {
						return new TypeToken<Page<TerminalItem>>() {
						};
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_ADD:
			// mTerminalItems.clear();
			// loadData();
			onRefresh();
			break;
		case REQUEST_DETAIL:

			break;
		}
	}

	@Override
	public void onRefresh() {
		page = 0;
		pullType = "onRefresh";
		// mTerminalItems.clear();
		mTerminalList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {

		pullType = "onLoadMore";

		if (mTerminalItems.size() >= total) {
			mTerminalList.stopLoadMore();
			mTerminalList.setPullLoadEnable(false);
			CommonUtil.toastShort(this, "没有更多数据");
		} else {
			loadData();
		}
	}

	private void loadFinished() {
		mTerminalList.stopRefresh();
		mTerminalList.stopLoadMore();
		mTerminalList.setRefreshTime(Tools.getHourAndMin());
	}

	private class TerminalListAdapter extends BaseAdapter {
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
				convertView = mInflater.inflate(R.layout.terminal_list_item,
						null);
				holder = new ViewHolder();
				holder.tvTerminalNumber = (TextView) convertView
						.findViewById(R.id.terminal_number);
				holder.tvTerminalStatus = (TextView) convertView
						.findViewById(R.id.terminal_status);
				holder.llButtonContainer = (LinearLayout) convertView
						.findViewById(R.id.terminal_button_container);
				// holder.llButtonContainer2 = (LinearLayout) convertView
				// .findViewById(R.id.terminal_button_container_2);
				holder.llButtons = (LinearLayout) convertView
						.findViewById(R.id.terminal_buttons);
				// holder.llButtons2 = (LinearLayout) convertView
				// .findViewById(R.id.terminal_buttons_2);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			final TerminalItem item = getItem(i);
			holder.tvTerminalNumber.setText(item.getTerminalNumber());
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			holder.tvTerminalStatus.setText(status[item.getStatus()]);

			// add buttons according to status

			holder.llButtonContainer.setVisibility(View.GONE);
			holder.llButtons.removeAllViews();
			// holder.llButtons2.removeAllViews();
			// 通过添加其他终端 进来的终端(type=2)，是没有详情，也没有操作按钮
			if (!"2".equals(item.getType())) {

				Boolean appidBoolean = !"".equals(item.getAppid());
				Boolean videoBoolean = 1 == item.getHasVideoVerify();

				switch (item.getStatus()) {
				// 除了已停用，其余状态都有同步功能
				case OPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					// holder.llButtonContainer2.setVisibility(View.GONE);
					if (appidBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_sync, item,
								mSyncListener);
					// addButton(holder.llButtons,
					// R.string.terminal_button_video,
					// item, mVideoListener);
					addButton(holder.llButtons, R.string.terminal_button_pos,
							item, mPosListener);
					if (videoBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_video, item,
								mVideoListener);
					break;
				case PART_OPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					// holder.llButtonContainer2.setVisibility(View.VISIBLE);
					// holder.llButtonContainer2.setVisibility(View.GONE);

					if (appidBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_sync, item,
								mSyncListener);
					addButton(holder.llButtons,
							R.string.terminal_button_reopen, item,
							mOpenListener);
					if (videoBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_video, item,
								mVideoListener);
					addButton(holder.llButtons, R.string.terminal_button_pos,
							item, mPosListener);
					// addButton(holder.llButtons2,
					// R.string.terminal_button_video,
					// item, mVideoListener);
					// addButton(holder.llButtons2,
					// R.string.terminal_button_pos,
					// item, mPosListener);
					break;
				case UNOPENED:
					holder.llButtonContainer.setVisibility(View.VISIBLE);
					// holder.llButtonContainer2.setVisibility(View.VISIBLE);
					// holder.llButtonContainer2.setVisibility(View.GONE);

					if (appidBoolean) {
						addButton(holder.llButtons,
								R.string.terminal_button_sync, item,
								mSyncListener);

						addButton(holder.llButtons,
								R.string.terminal_button_reopen, item,
								mOpenListener);
					} else {

						addButton(holder.llButtons,
								R.string.terminal_button_open, item,
								mOpenListener);
					}

					if (videoBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_video, item,
								mVideoListener);
					// addButton(holder.llButtons2,
					// R.string.terminal_button_video,
					// item, mVideoListener);
					break;
				case CANCELED:

					holder.llButtonContainer.setVisibility(View.VISIBLE);
					// holder.llButtonContainer.setVisibility(View.GONE);
					// holder.llButtonContainer2.setVisibility(View.GONE);
					if (appidBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_sync, item,
								mSyncListener);

					addButton(holder.llButtons,
							R.string.terminal_button_reopen, item,
							mOpenListener);
					if (videoBoolean)
						addButton(holder.llButtons,
								R.string.terminal_button_video, item,
								mVideoListener);
					break;
				case STOPPED:
					// holder.llButtonContainer.setVisibility(View.VISIBLE);
					// addButton(holder.llButtons,
					// R.string.terminal_button_sync,
					// item, mSyncListener);

					holder.llButtonContainer.setVisibility(View.GONE);
					// holder.llButtonContainer2.setVisibility(View.GONE);
					break;
				}
			}
			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if ("2".equals(item.getType())) {
						// 通过添加其他终端 进来的终端，是没有详情，也没有操作按钮
						return;
					} else {

						Intent intent = new Intent(TerminalManageActivity.this,
								TerminalDetailActivity.class);
						intent.putExtra(HAVE_VIDEO, item.getHasVideoVerify());
						intent.putExtra(TERMINAL_ID, item.getId());
						intent.putExtra(TERMINAL_NUMBER,
								item.getTerminalNumber());
						intent.putExtra(TERMINAL_STATUS, item.getStatus());
						startActivityForResult(intent, REQUEST_DETAIL);
					}
				}
			});
			return convertView;
		}

		private void addButton(LinearLayout ll, int res, Object tag,
				View.OnClickListener listener) {
			Button button = new Button(TerminalManageActivity.this);
			button.setBackgroundResource(R.drawable.blank_button_selector);
			button.setText(res);
			button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
			button.setTextColor(getResources().getColorStateList(
					R.color.blank_button_selector));
			if (null != tag) {
				button.setTag(tag);
			}
			if (null != listener) {
				button.setOnClickListener(listener);
			}
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
					CommonUtil.dip2px(TerminalManageActivity.this, 40), 1);
			if (ll.getChildCount() > 0) {
				lp.setMargins(15, 0, 0, 0);
			}
			ll.addView(button, lp);
		}
	}

	private static class ViewHolder {
		public TextView tvTerminalNumber;
		public TextView tvTerminalStatus;
		public LinearLayout llButtonContainer;
		// public LinearLayout llButtonContainer2;
		public LinearLayout llButtons;
		// public LinearLayout llButtons2;
	}

	private void openDialog(final TerminalItem item) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(
				TerminalManageActivity.this);

		LayoutInflater factory = LayoutInflater.from(this);
		View view = factory.inflate(R.layout.protocoldialog, null);
		builder.setView(view);

		final AlertDialog dialog = builder.create();
		dialog.show();
		final CheckBox cb = (CheckBox) view.findViewById(R.id.cb);

		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);

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

					CommonUtil.toastShort(TerminalManageActivity.this,
							"请仔细阅读开通协议，并接受协议");

				} else {

					dialog.dismiss();
					Intent intent = new Intent(TerminalManageActivity.this,
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
}
