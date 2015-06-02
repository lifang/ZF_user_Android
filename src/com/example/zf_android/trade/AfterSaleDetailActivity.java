package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_ID;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_STATUS;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.RECORD_TYPE;
import static com.example.zf_android.trade.Constants.AfterSaleIntent.REQUEST_MARK;
import static com.example.zf_android.trade.Constants.AfterSaleType.CANCEL;
import static com.example.zf_android.trade.Constants.AfterSaleType.CHANGE;
import static com.example.zf_android.trade.Constants.AfterSaleType.LEASE;
import static com.example.zf_android.trade.Constants.AfterSaleType.MAINTAIN;
import static com.example.zf_android.trade.Constants.AfterSaleType.RETURN;
import static com.example.zf_android.trade.Constants.AfterSaleType.UPDATE;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.AfterSaleDetail;
import com.example.zf_android.trade.entity.AfterSaleDetailCancel;
import com.example.zf_android.trade.entity.AfterSaleDetailChange;
import com.example.zf_android.trade.entity.AfterSaleDetailLease;
import com.example.zf_android.trade.entity.AfterSaleDetailMaintain;
import com.example.zf_android.trade.entity.AfterSaleDetailReturn;
import com.example.zf_android.trade.entity.AfterSaleDetailUpdate;
import com.example.zf_android.trade.entity.Comment;
import com.example.zf_android.trade.entity.ResourceInfo;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Leo on 2015/2/28.
 */
public class AfterSaleDetailActivity extends BaseActivity {

	public static final String MATERIAL_URL = "material_url";

	private int mRecordType;
	private int mRecordId;
	private int mRecordStatus;

	private LayoutInflater mInflater;
	private TextView mStatus;
	private TextView mTime;
	private Button mButton1;
	private Button mButton2;
	private LinearLayout mCategoryContainer;
	private LinearLayout mCommentContainer;

	// cancel apply button listener
	private View.OnClickListener mCancelApplyListener;
	// submit mark button listener
	private View.OnClickListener mSubmitMarkListener;
	// pay maintain button listener;
	private View.OnClickListener mPayMaintainListener;
	// submit cancel button listener
	private View.OnClickListener mSubmitCancelListener;

	private LinearLayout titleback_linear_back;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRecordType = getIntent().getIntExtra(RECORD_TYPE, 0);
		mRecordId = getIntent().getIntExtra(RECORD_ID, 0);
		setContentView(R.layout.activity_after_sale_detail);
		String[] titles = getResources().getStringArray(R.array.title_after_sale_detail);
		new TitleMenuUtil(this, titles[mRecordType]).show();
		initViews();
		initButtonListeners();
		getData();
	}


	@Override
	public void finish() {
		Intent intent = new Intent();
		intent.putExtra(RECORD_ID, mRecordId);
		intent.putExtra(RECORD_STATUS, mRecordStatus);
		setResult(RESULT_OK, intent);
		super.finish();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mStatus = (TextView) findViewById(R.id.after_sale_detail_status);
		mTime = (TextView) findViewById(R.id.after_sale_detail_time);
		mButton1 = (Button) findViewById(R.id.after_sale_detail_button_1);
		mButton2 = (Button) findViewById(R.id.after_sale_detail_button_2);
		mCategoryContainer = (LinearLayout) findViewById(R.id.after_sale_detail_category_container);
		mCommentContainer = (LinearLayout) findViewById(R.id.after_sale_comment_container);
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initButtonListeners() {
		mCancelApplyListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						AfterSaleDetailActivity.this);
				final AlertDialog dialog = builder.create();
				builder.setTitle("提示");
				builder.setMessage("确定要取消吗？");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {

						API.cancelAfterSaleApply(AfterSaleDetailActivity.this, mRecordType, mRecordId, new HttpCallback(AfterSaleDetailActivity.this) {
							@Override
							public void onSuccess(Object data) {
								mRecordStatus = 5;
								String status = getResources().getStringArray(R.array.maintain_status)[5];
								mStatus.setText(status);
								if (mRecordType == CANCEL) {
									mButton1.setText(getString(R.string.button_submit_cancel));
									mButton1.setOnClickListener(mSubmitCancelListener);
								} else {
									mButton1.setVisibility(View.GONE);
								}
								CommonUtil.toastShort(AfterSaleDetailActivity.this, getString(R.string.toast_cancel_apply_success));
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
					}
				});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						dialog.dismiss();
					}

				});

				builder.create().show();

			}
		};

		mSubmitMarkListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AfterSaleDetailActivity.this, AfterSaleMarkActivity.class);
				intent.putExtra(RECORD_TYPE, mRecordType);
				intent.putExtra(RECORD_ID, mRecordId);
				startActivityForResult(intent, REQUEST_MARK);
			}
		};

		mPayMaintainListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) { 
				Intent i1 =new Intent (AfterSaleDetailActivity.this,AfterSalePayActivity.class);
				i1.putExtra(RECORD_TYPE, mRecordType);
				i1.putExtra("orderId", mRecordId+"");
				startActivity(i1);
				AfterSaleDetailActivity.this.finish();
			}
		};

		mSubmitCancelListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				API.resubmitCancel(AfterSaleDetailActivity.this, mRecordId, new HttpCallback(AfterSaleDetailActivity.this) {
					@Override
					public void onSuccess(Object obj) {
						mRecordStatus = 1;
						String status = getResources().getStringArray(R.array.cancel_status)[1];
						mStatus.setText(status);
						mButton1.setText(getString(R.string.button_cancel_apply));
						mButton1.setOnClickListener(mCancelApplyListener);
						CommonUtil.toastShort(AfterSaleDetailActivity.this, getString(R.string.toast_resubmit_cancel_success));
					}

					@Override
					public TypeToken getTypeToken() {
						return null;
					}
				});
			}
		};
	}

	private void getData() {
		API.getAfterSaleRecordDetail(this, mRecordType, mRecordId, new HttpCallback<AfterSaleDetail>(this) {
			@Override
			public void onSuccess(AfterSaleDetail data) {
				//startActivityForResul成功后回调getDATA前要移除之前加载的
				mCategoryContainer.removeAllViews();
				mCommentContainer.removeAllViews();

				mRecordStatus = data.getStatus();

				// render terminal category
				LinkedHashMap<String, String> terminalPairs = new LinkedHashMap<String, String>();
				String[] terminalKeys = getResources().getStringArray(R.array.after_sale_terminal);
				terminalPairs.put(terminalKeys[0], data.getTerminalNum());
				terminalPairs.put(terminalKeys[1], data.getBrandName());
				terminalPairs.put(terminalKeys[2], data.getBrandNumber());
				terminalPairs.put(terminalKeys[3], data.getPayChannel());
				terminalPairs.put(terminalKeys[4], data.getMerchantName());
				terminalPairs.put(terminalKeys[5], data.getMerchantPhone());
				if (mRecordType == LEASE) {
					AfterSaleDetailLease lease = (AfterSaleDetailLease) data;
					if (!StringUtil.isNull(lease.getLeasePrice()+"")) {
						terminalPairs.put(terminalKeys[6], getString(R.string.notation_yuan) + 
								String.format("%.2f",Integer.valueOf(lease.getLeasePrice())/100f));
					}else {
						terminalPairs.put(terminalKeys[6], "");
					}
					if (!StringUtil.isNull(lease.getLeaseDeposit()+"")) {
						terminalPairs.put(terminalKeys[7], getString(R.string.notation_yuan) + 
								String.format("%.2f",Integer.valueOf(lease.getLeaseDeposit())/100f));
					}else {
						terminalPairs.put(terminalKeys[7], "");
					}
					terminalPairs.put(terminalKeys[8], lease.getLeaseLength() + getString(R.string.notation_mouth));
					terminalPairs.put(terminalKeys[9], lease.getLeaseMaxTime() + getString(R.string.notation_mouth));
					terminalPairs.put(terminalKeys[10], lease.getLeaseMinTime() + getString(R.string.notation_mouth));
				}
				renderCategoryTemplate(R.string.after_sale_terminal_title, terminalPairs);

				mTime.setText(data.getApplyTime());
				// render other categories
				switch (mRecordType) {
				case MAINTAIN:
					String[] maintainStatus = getResources().getStringArray(R.array.maintain_status);
					mStatus.setText(maintainStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton2.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));
						mButton2.setText(getString(R.string.button_pay_maintain));

						mButton1.setOnClickListener(mCancelApplyListener);
						mButton2.setOnClickListener(mPayMaintainListener);
					} else if (data.getStatus() == 2) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_submit_flow));

						mButton1.setOnClickListener(mSubmitMarkListener);
					}

					// render maintain category
					AfterSaleDetailMaintain maintainDetail = (AfterSaleDetailMaintain) data;
					LinkedHashMap<String, String> maintainPairs = new LinkedHashMap<String, String>();
					String[] maintainKeys = getResources().getStringArray(R.array.after_sale_maintian);
					maintainPairs.put(maintainKeys[0], maintainDetail.getReceiverAddr());
					if (!StringUtil.isNull(maintainDetail.getRepairPrice())) {
						maintainPairs.put(maintainKeys[1], getString(R.string.notation_yuan) + 
								String.format("%.2f",Integer.valueOf(maintainDetail.getRepairPrice())/100f));
					}else {
						maintainPairs.put(maintainKeys[1], "");
					}
					//maintainPairs.put(maintainKeys[1], maintainDetail.getRepairPrice() + "");
					maintainPairs.put(maintainKeys[2], maintainDetail.getChange_reason());
					renderCategoryTemplate(R.string.after_sale_maintain_title, maintainPairs);
					break;
				case RETURN:
					String[] returnStatus = getResources().getStringArray(R.array.return_status);
					mStatus.setText(returnStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));

						mButton1.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 2) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_submit_flow));

						mButton1.setOnClickListener(mSubmitMarkListener);
					}

					// render return category
					AfterSaleDetailReturn returnDetail = (AfterSaleDetailReturn) data;
					LinkedHashMap<String, String> returnPairs = new LinkedHashMap<String, String>();
					String[] returnKeys = getResources().getStringArray(R.array.after_sale_return);
						if (!StringUtil.isNull(returnDetail.getReturnPrice())) 
							returnPairs.put(returnKeys[0], getString(R.string.notation_yuan) + 
									String.format("%.2f",Integer.valueOf(returnDetail.getReturnPrice())/100f));
						else 
							returnPairs.put(returnKeys[0], "");

					//returnPairs.put(returnKeys[0], returnDetail.getReturnPrice() + "");
					returnPairs.put(returnKeys[1], returnDetail.getBankName());
					returnPairs.put(returnKeys[2], returnDetail.getBankAccount());
					returnPairs.put(returnKeys[3], returnDetail.getReason());
					renderCategoryTemplate(R.string.after_sale_return_title, returnPairs);

					// render return material category
					renderMaterialTemplate(R.string.after_sale_return_material_title, data.getResource_info());
					break;
				case CANCEL:
					String[] cancelStatus = getResources().getStringArray(R.array.cancel_status);
					mStatus.setText(cancelStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));

						mButton1.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 5) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_submit_cancel));

						mButton1.setOnClickListener(mSubmitCancelListener);
					}

					// render cancel material category
					renderMaterialTemplate(R.string.after_sale_cancel_material_title, data.getResource_info());
					break;
				case CHANGE:
					String[] changeStatus = getResources().getStringArray(R.array.change_status);
					mStatus.setText(changeStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));

						mButton1.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 2) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_submit_flow));

						mButton1.setOnClickListener(mSubmitMarkListener);
					}

					// render change category
					AfterSaleDetailChange changeDetail = (AfterSaleDetailChange) data;
					LinkedHashMap<String, String> changePairs = new LinkedHashMap<String, String>();
					String[] changeKeys = getResources().getStringArray(R.array.after_sale_change);
					changePairs.put(changeKeys[0], changeDetail.getReceiverAddr());
					changePairs.put(changeKeys[1], changeDetail.getChangeReason());
					renderCategoryTemplate(R.string.after_sale_change_title, changePairs);

					// render change material category
					renderMaterialTemplate(R.string.after_sale_change_material_title, data.getResource_info());
					break;
				case UPDATE:
					String[] updateStatus = getResources().getStringArray(R.array.update_status);
					mStatus.setText(updateStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));

						mButton1.setOnClickListener(mCancelApplyListener);
					}

					// render update material category
					renderMaterialTemplate(R.string.after_sale_update_material_title, data.getResource_info());
					break;
				case LEASE:
					String[] leaseStatus = getResources().getStringArray(R.array.lease_status);
					mStatus.setText(leaseStatus[data.getStatus()]);
					if (data.getStatus() == 1) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_cancel_apply));

						mButton1.setOnClickListener(mCancelApplyListener);
					} else if (data.getStatus() == 2) {
						mButton1.setVisibility(View.VISIBLE);
						mButton1.setText(getString(R.string.button_submit_flow));

						mButton1.setOnClickListener(mSubmitMarkListener);
					}

					// render lease category
					AfterSaleDetailLease leaseDetail = (AfterSaleDetailLease) data;
					LinkedHashMap<String, String> leasePairs = new LinkedHashMap<String, String>();
					String[] leaseKeys = getResources().getStringArray(R.array.after_sale_lease);
					
					if (!StringUtil.isNull(leaseDetail.getCrf_retrun_price())) {
						if (Integer.valueOf(leaseDetail.getCrf_retrun_price()) > 0) {
							leasePairs.put(leaseKeys[0], getString(R.string.notation_yuan) + 
									String.format("%.2f",Integer.valueOf(leaseDetail.getCrf_retrun_price())/100f));
						}else {
							if (!StringUtil.isNull(leaseDetail.getReturn_price()+"")) {
								leasePairs.put(leaseKeys[0], getString(R.string.notation_yuan) + 
										String.format("%.2f",Integer.valueOf(leaseDetail.getReturn_price())/100f));
							}else {
								leasePairs.put(leaseKeys[0], "");
							}
						}
					}else {
						if (!StringUtil.isNull(leaseDetail.getReturn_price()+"")) {
							leasePairs.put(leaseKeys[0], getString(R.string.notation_yuan) + 
									String.format("%.2f",Integer.valueOf(leaseDetail.getReturn_price())/100f));
						}else {
							leasePairs.put(leaseKeys[0], "");
						}
					}
				
					leasePairs.put(leaseKeys[1], leaseDetail.getReceiverName());
					leasePairs.put(leaseKeys[2], leaseDetail.getReceiverPhone());
					renderCategoryTemplate(R.string.after_sale_lease_title, leasePairs);

					// render lease material category
					renderMaterialTemplate(R.string.after_sale_lease_material_title, data.getResource_info());
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
				case MAINTAIN:
					return new TypeToken<AfterSaleDetailMaintain>() {
					};
				case RETURN:
					return new TypeToken<AfterSaleDetailReturn>() {
					};
				case CANCEL:
					return new TypeToken<AfterSaleDetailCancel>() {
					};
				case CHANGE:
					return new TypeToken<AfterSaleDetailChange>() {
					};
				case UPDATE:
					return new TypeToken<AfterSaleDetailUpdate>() {
					};
				case LEASE:
					return new TypeToken<AfterSaleDetailLease>() {
					};
				default:
					throw new IllegalArgumentException();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_MARK:
				CommonUtil.toastShort(this, getString(R.string.toast_add_mark_success));
				getData();
				break;
			}
		}
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

	private void renderMaterialTemplate(int titleRes, List<ResourceInfo> resourceInfos) {
		LinearLayout terminalCategory = (LinearLayout) mInflater.inflate(R.layout.after_sale_detail_category, null);
		mCategoryContainer.addView(terminalCategory);

		TextView title = (TextView) terminalCategory.findViewById(R.id.category_title);
		LinearLayout keyContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_key_container);
		LinearLayout valueContainer = (LinearLayout) terminalCategory.findViewById(R.id.category_value_container);

		title.setText(getString(titleRes));
		for (final ResourceInfo resourceInfo : resourceInfos) {
			TextView key = createKeyText();
			key.setText(resourceInfo.getTitle());
			keyContainer.addView(key);

			TextView value = createValueText();
			if (null == resourceInfo.getUpload_path()) {
				value.setText(getString(R.string.after_sale_material_unsubmit));
			} else {
				value.setTextColor(getResources().getColor(R.color.blank_button_selector));
				value.setText(getString(R.string.after_sale_material_view));
				value.setClickable(true);
				value.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Uri uri = Uri.parse(resourceInfo.getUpload_path());  
						Intent it = new Intent(Intent.ACTION_VIEW, uri);  
						startActivity(it);

						//						Intent intent = new Intent(AfterSaleDetailActivity.this, AfterSaleMaterialActivity.class);
						//						intent.putExtra(RECORD_TYPE, mRecordType);
						//						intent.putExtra(MATERIAL_URL, resourceInfo.getUpload_path());
						//						startActivity(intent);
					}
				});
			}
			valueContainer.addView(value);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
