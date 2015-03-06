package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.ApplyChooseItem;
import com.example.zf_android.trade.entity.ApplyMerchantDetail;
import com.example.zf_android.trade.entity.ApplyDetail;
import com.example.zf_android.trade.entity.ApplyMaterial;
import com.example.zf_android.trade.entity.ApplyTerminalDetail;
import com.example.zf_android.trade.widget.MyTabWidget;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;

/**
 * Created by Leo on 2015/3/5.
 */
public class ApplyDetailActivity extends Activity {

	private static final int APPLY_PUBLIC = 1;
	private static final int APPLY_PRIVATE = 2;
	private int mApplyType;

	private static final int ITEM_EDIT = 1;
	private static final int ITEM_CHOOSE = 2;
	private static final int ITEM_UPLOAD = 3;
	private static final int ITEM_VIEW = 4;

	private int mTerminalId;

	private LayoutInflater mInflater;

	private TextView mPosBrand;
	private TextView mPosModel;
	private TextView mSerialNum;
	private TextView mPayChannel;

	private LinearLayout mMerchantContainer;
	private LinearLayout mTerminalContainer;
	private LinearLayout mMaterialContainer;
	private Button mApplySubmit;

	private MyTabWidget mTab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_detail);
		new TitleMenuUtil(this, getString(R.string.title_apply_open)).show();
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);

		initViews();
		loadData(mApplyType);
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);

		mTab = (MyTabWidget) findViewById(R.id.apply_detail_tab);
		mTab.addTab(getString(R.string.apply_public), 17);
		mTab.addTab(getString(R.string.apply_private), 17);
		mTab.setOnTabSelectedListener(new MyTabWidget.OnTabSelectedListener() {
			@Override
			public void onTabSelected(int position) {
				mApplyType = position + 1;
				loadData(mApplyType);
			}
		});
		mTab.updateTabs(0);
		mApplyType = APPLY_PUBLIC;

		mPosBrand = (TextView) findViewById(R.id.apply_detail_brand);
		mPosModel = (TextView) findViewById(R.id.apply_detail_model);
		mSerialNum = (TextView) findViewById(R.id.apply_detail_serial);
		mPayChannel = (TextView) findViewById(R.id.apply_detail_channel);

		mMerchantContainer = (LinearLayout) findViewById(R.id.apply_detail_merchant_container);
		mTerminalContainer = (LinearLayout) findViewById(R.id.apply_detail_terminal_container);
		mMaterialContainer = (LinearLayout) findViewById(R.id.apply_detail_material_container);
		mApplySubmit = (Button) findViewById(R.id.apply_submit);

		initMerchantDetails();
	}

	private void loadData(int applyType) {

		API.getApplyDetail(this, 80, mTerminalId, applyType, new HttpCallback<ApplyDetail>(this) {
			@Override
			public void onSuccess(ApplyDetail data) {
				ApplyTerminalDetail terminalDetail = data.getTerminalDetail();
				List<ApplyChooseItem> merchants = data.getMerchants();
				List<ApplyMaterial> materials = data.getMaterials();
				List<ApplyMerchantDetail> customerDetails = data.getCustomerDetails();

				if (null != terminalDetail) {
					mPosBrand.setText(terminalDetail.getBrandName());
					mPosModel.setText(terminalDetail.getModelNumber());
					mSerialNum.setText(terminalDetail.getSerialNumber());
					mPayChannel.setText(terminalDetail.getChannelName());
				}


			}

			@Override
			public TypeToken<ApplyDetail> getTypeToken() {
				return new TypeToken<ApplyDetail>() {
				};
			}
		});
	}

	private void initMerchantDetails() {
		final String[] keys = getResources().getStringArray(R.array.apply_detail_merchant_keys);
		mMerchantContainer.addView(getDetailItem(ITEM_CHOOSE, keys[0], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[1], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[2], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[3], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_CHOOSE, keys[4], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[5], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[6], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, keys[7], null, null));
		mMerchantContainer.addView(getDetailItem(ITEM_CHOOSE, keys[8], null, new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				CommonUtil.toastShort(ApplyDetailActivity.this, keys[8]);
			}
		}));
	}

	private void setMerchantItem(int itemType, String key, String value) {
		LinearLayout item = (LinearLayout) mMerchantContainer.findViewWithTag(key);
		setupItem(item, itemType, key, value);
	}

	private LinearLayout getDetailItem(int itemType, String key, String value, View.OnClickListener listener) {
		LinearLayout item;
		switch (itemType) {
			case ITEM_EDIT:
				item = (LinearLayout) mInflater.inflate(R.layout.apply_detail_item_edit, null);
				break;
			case ITEM_CHOOSE:
				item = (LinearLayout) mInflater.inflate(R.layout.apply_detail_item_choose, null);
				break;
			case ITEM_UPLOAD:
				item = (LinearLayout) mInflater.inflate(R.layout.apply_detail_item_upload, null);
				break;
			case ITEM_VIEW:
				item = (LinearLayout) mInflater.inflate(R.layout.apply_detail_item_view, null);
				break;
			default:
				item = (LinearLayout) mInflater.inflate(R.layout.apply_detail_item_edit, null);
		}
		item.setTag(key);
		setupItem(item, itemType, key, value);
		if (null != listener) {
			item.setOnClickListener(listener);
		}
		return item;
	}

	private void setupItem(LinearLayout item, int itemType, String key, String value) {
		switch (itemType) {
			case ITEM_EDIT: {
				TextView tvKey = (TextView) item.findViewById(R.id.apply_detail_key);
				EditText etValue = (EditText) item.findViewById(R.id.apply_detail_value);

				if (!TextUtils.isEmpty(key))
					tvKey.setText(key);
				if (!TextUtils.isEmpty(value))
					etValue.setText(value);
				break;
			}
			case ITEM_CHOOSE: {
				TextView tvKey = (TextView) item.findViewById(R.id.apply_detail_key);
				TextView tvValue = (TextView) item.findViewById(R.id.apply_detail_value);

				if (!TextUtils.isEmpty(key))
					tvKey.setText(key);
				if (!TextUtils.isEmpty(value))
					tvValue.setText(value);
				break;
			}
			case ITEM_UPLOAD: {
				TextView tvKey = (TextView) item.findViewById(R.id.apply_detail_key);
				TextView tvValue = (TextView) item.findViewById(R.id.apply_detail_value);

				if (!TextUtils.isEmpty(key))
					tvKey.setText(key);
				tvValue.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						CommonUtil.toastShort(ApplyDetailActivity.this, "upload image");
					}
				});
				break;
			}
			case ITEM_VIEW: {
				TextView tvKey = (TextView) item.findViewById(R.id.apply_detail_key);
				ImageButton ibView = (ImageButton) item.findViewById(R.id.apply_detail_view);

				if (!TextUtils.isEmpty(key))
					tvKey.setText(key);
				ibView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						CommonUtil.toastShort(ApplyDetailActivity.this, "view image");
					}
				});
			}
		}
	}
}
