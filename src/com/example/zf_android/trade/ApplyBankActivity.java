package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_BANK;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.entity.Bank;
import com.example.zf_android.entity.BankEntity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.widget.XListView;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/3/16.
 */
public class ApplyBankActivity extends BaseActivity implements
View.OnClickListener, XListView.IXListViewListener {

	private String keyword = "";

	private TextView next_sure;
	private EditText mBankInput;
	private ImageButton mBankSearch;
	private LinearLayout mResultContainer;

	private XListView mBankList;
	private BankListAdapter mAdapter;
	private Bank mChosenBank;
	private int page = 0;
	private int pageSize = 20;
	private int mTerminalId;
	private List<Bank> bank;
	private boolean noMoreData = false;
	private String pullType = "loadData";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_bank);
		new TitleMenuUtil(this, getString(R.string.title_apply_choose_bank))
		.show();
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);
		mChosenBank = (Bank) getIntent().getSerializableExtra(SELECTED_BANK);

		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setText("使用我输入的");
		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (StringUtil.isNull(mBankInput.getText().toString().trim())) {
					CommonUtil.toastShort(ApplyBankActivity.this,"请输入银行名称");
				}else {
					Bank bank = new Bank();
					bank.setName(mBankInput.getText().toString());
					bank.setNo("");
					Intent intent = new Intent();
					intent.putExtra(SELECTED_BANK, bank);
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
		mBankInput = (EditText) findViewById(R.id.apply_bank_input);
		mBankSearch = (ImageButton) findViewById(R.id.apply_bank_search);
		mBankSearch.setOnClickListener(this);
		mResultContainer = (LinearLayout) findViewById(R.id.apply_bank_result_container);
		mBankList = (XListView) findViewById(R.id.apply_bank_list);
		bank = new ArrayList<Bank>();
		mAdapter = new BankListAdapter();
		mBankList.initHeaderAndFooter();
		mBankList.setXListViewListener(this);
		mBankList.setPullLoadEnable(true);
		mBankList.setAdapter(mAdapter);

		mBankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int i, long l) {
				Bank bank = (Bank) view.getTag(R.id.item_id);
				Intent intent = new Intent();
				intent.putExtra(SELECTED_BANK, bank);
				setResult(RESULT_OK, intent);
				finish();
			}
		});

		// loadData();
	}

	private void loadData() {

		API.getApplyBankList(this, page + 1, keyword, pageSize,
				String.valueOf(mTerminalId),
				new HttpCallback<BankEntity>(this) {
			@Override
			public void onSuccess(BankEntity data) {
				mResultContainer.setVisibility(View.VISIBLE);

				// 没有数据或者数据不够Config.ROWS个时说明后台没有更多数据 不需要上拉加载
				if (null == data || data.getContent().size() < pageSize)
					noMoreData = true;

				if (pullType.equals("onRefresh")) {
					bank.clear();
				}

				if (null != data && data.getContent().size() > 0)
					bank.addAll(data.getContent());
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
			public TypeToken<BankEntity> getTypeToken() {
				return new TypeToken<BankEntity>() {
				};
			}
		});
	}

	@Override
	public void onClick(View view) {
		keyword = mBankInput.getText().toString();
		bank.clear();
		loadData();
	}

	private class BankListAdapter extends BaseAdapter {
		private BankListAdapter() {
		}

		@Override
		public int getCount() {
			return bank.size();
		}

		@Override
		public Bank getItem(int i) {
			return bank.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
			ViewHolder holder = new ViewHolder();
			if (null == convertView) {
				convertView = LayoutInflater.from(ApplyBankActivity.this)
						.inflate(R.layout.simple_list_item, null);
				holder.icon = (ImageView) convertView
						.findViewById(R.id.item_selected);
				holder.name = (TextView) convertView
						.findViewById(R.id.item_name);
				holder.id = (TextView) convertView.findViewById(R.id.item_id);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Bank bank = getItem(i);

			if (null != bank) {
				holder.id.setText(bank.getNo());
				holder.name.setText(bank.getName());
				if (null != mChosenBank
						&& bank.getNo().equals(mChosenBank.getNo())) {
					holder.icon.setVisibility(View.VISIBLE);
					holder.icon.setImageDrawable(getResources().getDrawable(
							R.drawable.icon_selected));
				} else {
					holder.icon.setVisibility(View.INVISIBLE);
				}
				convertView.setTag(R.id.item_id, bank);
			}

			return convertView;
		}
	}

	private static class ViewHolder {
		ImageView icon;
		TextView name;
		TextView id;
	}

	private void loadFinished() {
		mBankList.stopRefresh();
		mBankList.stopLoadMore();
		mBankList.setRefreshTime(Tools.getHourAndMin());
	}

	@Override
	public void onRefresh() {
		page = 0;
		pullType = "onRefresh";
		noMoreData = false;
		mBankList.setPullLoadEnable(true);
		loadData();
	}

	@Override
	public void onLoadMore() {
		pullType = "onLoadMore";
		if (noMoreData) {
			mBankList.stopLoadMore();
			mBankList.setPullLoadEnable(false);
			CommonUtil.toastShort(this,
					getResources().getString(R.string.no_more_data));
		} else {
			loadData();
		}

	}
}