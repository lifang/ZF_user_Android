package com.example.zf_android.trade;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.AfterSaleDetail;
import com.example.zf_android.trade.entity.AfterSaleDetailMaintain;
import com.example.zf_android.trade.entity.AfterSaleDetailReturn;
import com.example.zf_android.trade.entity.Comment;
import com.google.gson.reflect.TypeToken;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetailActivity extends Activity {

	private int mRecordType;
	private int mRecordId;

	private LayoutInflater mInflater;
	private TextView mStatus;
	private TextView mTime;
	private Button mButton1;
	private Button mButton2;
	private LinearLayout mCategoryContainer;
	private LinearLayout mCommentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordType = getIntent().getIntExtra(AfterSaleListActivity.RECORD_TYPE, 0);
		mRecordId = getIntent().getIntExtra(AfterSaleListActivity.RECORD_ID, 0);
		setContentView(R.layout.activity_after_sale_detail);
		String[] titles = getResources().getStringArray(R.array.title_after_sale_detail);
		new TitleMenuUtil(this, titles[mRecordType]).show();

		initViews();

		API.getAfterSaleRecordDetail(this, mRecordType, mRecordId, new HttpCallback<AfterSaleDetail>(this) {
			@Override
			public void onSuccess(AfterSaleDetail data) {

				// render terminal category
				LinkedHashMap<String, String> terminalPairs = new LinkedHashMap<String, String>();
				String[] terminalKeys = getResources().getStringArray(R.array.after_sale_terminal);
				terminalPairs.put(terminalKeys[0], data.getTerminalNum());
				terminalPairs.put(terminalKeys[1], data.getBrandName());
				terminalPairs.put(terminalKeys[2], data.getBrandNumber());
				terminalPairs.put(terminalKeys[3], data.getPayChannel());
				terminalPairs.put(terminalKeys[4], data.getMerchantName());
				terminalPairs.put(terminalKeys[5], data.getMerchantPhone());
				renderCategoryTemplate(R.string.after_sale_terminal_title, terminalPairs);

				mTime.setText(data.getApplyTime());
				// render other categories
				switch (mRecordType) {
					case AfterSaleListActivity.RECORD_MAINTAIN:
						String[] maintainStatus = getResources().getStringArray(R.array.maintain_status);
						mStatus.setText(maintainStatus[data.getStatus()]);
						if (data.getStatus() == 1) {
							mButton1.setVisibility(View.VISIBLE);
							mButton2.setVisibility(View.VISIBLE);
							mButton1.setText(getString(R.string.button_cancel_apply));
							mButton2.setText(getString(R.string.button_pay_maintain));
						} else if (data.getStatus() == 2) {
							mButton1.setVisibility(View.VISIBLE);
							mButton1.setText(getString(R.string.button_submit_flow));
						}

						// render maintain category
						AfterSaleDetailMaintain maintainDetail = (AfterSaleDetailMaintain) data;
						LinkedHashMap<String, String> maintainPairs = new LinkedHashMap<String, String>();
						String[] maintainKeys = getResources().getStringArray(R.array.after_sale_maintian);
						maintainPairs.put(maintainKeys[0], maintainDetail.getReceiverAddr());
						maintainPairs.put(maintainKeys[1], maintainDetail.getRepairPrice() + "");
						maintainPairs.put(maintainKeys[2], maintainDetail.getDescription());
						renderCategoryTemplate(R.string.after_sale_maintain_title, maintainPairs);
						break;
					case AfterSaleListActivity.RECORD_RETURN:
						String[] returnStatus = getResources().getStringArray(R.array.return_status);
						mStatus.setText(returnStatus[data.getStatus()]);
						if (data.getStatus() == 1) {
							mButton1.setVisibility(View.VISIBLE);
							mButton1.setText(getString(R.string.button_cancel_apply));
						} else if (data.getStatus() == 2) {
							mButton1.setVisibility(View.VISIBLE);
							mButton1.setText(getString(R.string.button_submit_flow));
						}

						// render return category
						AfterSaleDetailReturn returnDetail = (AfterSaleDetailReturn) data;
						LinkedHashMap<String, String> returnPairs = new LinkedHashMap<String, String>();
						String[] returnKeys = getResources().getStringArray(R.array.after_sale_return);
						returnPairs.put(returnKeys[0], returnDetail.getReturnPrice() + "");
						returnPairs.put(returnKeys[1], returnDetail.getBankName());
						returnPairs.put(returnKeys[2], returnDetail.getBankAccount());
						returnPairs.put(returnKeys[3], returnDetail.getReason());
						renderCategoryTemplate(R.string.after_sale_return_title, returnPairs);
						break;
					case AfterSaleListActivity.RECORD_CANCEL:
						break;
					case AfterSaleListActivity.RECORD_CHANGE:
						break;
					case AfterSaleListActivity.RECORD_UPDATE:
						break;
					case AfterSaleListActivity.RECORD_LEASE:
						break;
				}

				List<Comment> comments = data.getComments().getContent();
				if (null != comments && comments.size() > 0) {
					for (Comment comment : comments) {
						LinearLayout commentLayout = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_comment, null);
						TextView content = (TextView) commentLayout.findViewById(R.id.comment_content);
						TextView person = (TextView) commentLayout.findViewById(R.id.comment_person);
						TextView time = (TextView) commentLayout.findViewById(R.id.comment_time);
						content.setText(comment.getContent());
						person.setText(comment.getPerson());
						time.setText(comment.getTime());
						mCommentContainer.addView(commentLayout);
					}
				}
			}

			@Override
			public TypeToken getTypeToken() {
				switch (mRecordType) {
					case AfterSaleListActivity.RECORD_MAINTAIN:
						return new TypeToken<AfterSaleDetailMaintain>() {
						};
					case AfterSaleListActivity.RECORD_RETURN:
						return new TypeToken<AfterSaleDetailReturn>() {
						};
					case AfterSaleListActivity.RECORD_CANCEL:
						return new TypeToken<AfterSaleDetailMaintain>() {
						};
					case AfterSaleListActivity.RECORD_CHANGE:
						return new TypeToken<AfterSaleDetailMaintain>() {
						};
					case AfterSaleListActivity.RECORD_UPDATE:
						return new TypeToken<AfterSaleDetailMaintain>() {
						};
					case AfterSaleListActivity.RECORD_LEASE:
						return new TypeToken<AfterSaleDetailMaintain>() {
						};
					default:
						throw new IllegalArgumentException();
				}
			}
		});
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mStatus = (TextView) findViewById(R.id.after_sale_detail_status);
		mTime = (TextView) findViewById(R.id.after_sale_detail_time);
		mButton1 = (Button) findViewById(R.id.after_sale_detail_button_1);
		mButton2 = (Button) findViewById(R.id.after_sale_detail_button_2);
		mCategoryContainer = (LinearLayout) findViewById(R.id.after_sale_detail_category_container);
		mCommentContainer = (LinearLayout) findViewById(R.id.after_sale_comment_container);
	}

	private TextView createKeyText() {
		TextView tv = new TextView(this);
		tv.setGravity(Gravity.RIGHT);
		tv.setPadding(0, 5, 0, 5);
		tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		return tv;
	}

	private TextView createValueText() {
		TextView tv = new TextView(this);
		tv.setGravity(Gravity.LEFT);
		tv.setPadding(0, 5, 0, 5);
		tv.setTextColor(getResources().getColor(R.color.text6c6c6c6));
		tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
		return tv;
	}

	private void renderCategoryTemplate(int titleRes, LinkedHashMap<String, String> pairs) {
		LinearLayout terminalCategory = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_category, null);
		mCategoryContainer.addView(terminalCategory);

		TextView title = (TextView) terminalCategory.findViewById(R.id.category_title);
		LinearLayout keyContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_key_container);
		LinearLayout valueContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_value_container);

		title.setText(getString(titleRes));
		for (Map.Entry<String, String> pair : pairs.entrySet()) {
			TextView key = createKeyText();
			key.setText(pair.getKey());
			keyContainer.addView(key);

			TextView value = createValueText();
			value.setText(pair.getValue());
			valueContainer.addView(value);
		}

	}

}
