package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.ApplyIntent.CHOOSE_ITEMS;
import static com.example.zf_android.trade.Constants.ApplyIntent.CHOOSE_TITLE;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_BANK;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CHANNEL;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_CITY;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_CHOOSE_MERCHANT;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_TAKE_PHOTO;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_UPLOAD_IMAGE;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_BANK;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_BILLING;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_CHANNEL;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_CHANNEL_ID;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_ID;
import static com.example.zf_android.trade.Constants.ApplyIntent.SELECTED_TITLE;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_CITY;
import static com.example.zf_android.trade.Constants.CityIntent.SELECTED_PROVINCE;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_STATUS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.BankEntity.Bank;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.RegText;
import com.example.zf_android.trade.common.StringUtil;
import com.example.zf_android.trade.common.TextWatcherAdapter;
import com.example.zf_android.trade.entity.ApplyChannel;
import com.example.zf_android.trade.entity.ApplyChooseItem;
import com.example.zf_android.trade.entity.ApplyCustomerDetail;
import com.example.zf_android.trade.entity.ApplyDetail;
import com.example.zf_android.trade.entity.ApplyMaterial;
import com.example.zf_android.trade.entity.ApplyTerminalDetail;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Merchant;
import com.example.zf_android.trade.entity.OpeningInfos;
import com.example.zf_android.trade.entity.Province;
import com.example.zf_android.trade.widget.MyTabWidget;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Leo on 2015/3/5.
 */
public class ApplyDetailActivity extends FragmentActivity {

	private static final int TYPE_TEXT = 1;
	private static final int TYPE_IMAGE = 2;
	private static final int TYPE_BANK = 3;

	private static final int APPLY_PUBLIC = 1;
	private int mApplyType = 1;
	private boolean isLoaded = false;
	private static final int ITEM_EDIT = 1;
	private static final int ITEM_CHOOSE = 2;
	private static final int ITEM_UPLOAD = 3;
	private static final int ITEM_VIEW = 4;

	private int mTerminalId;
	private String mTerminalNumber;
	private int mTerminalStatus;
	private int mPayChannelID = 0;
	private String bankCode;
	private Merchant mMerchant;

	private LayoutInflater mInflater;

	private TextView mPosBrand;
	private TextView mPosModel;
	private TextView mSerialNum;
	private TextView mPayChannel;

	private String[] mMerchantKeys;
	private String[] mBankKeys;

	private LinearLayout mContainer;
	private LinearLayout mMerchantContainer;
	private LinearLayout mCustomerContainer;
	private LinearLayout mMaterialContainer;
	private Button mApplySubmit;

	private MyTabWidget mTab;

	private int mMerchantId;
	private Province mMerchantProvince;
	private City mMerchantCity;
	private String mMerchantBirthday;
	private int mMerchantGender = 3;
	private ApplyChannel mChosenChannel;
	private ApplyChannel.Billing mChosenBilling;
	private Bank mChosenBank;
	private String mBankKey;
	private String mUploadKey;

	private String photoPath;
	private TextView uploadingTextView;
	private ImageButton uploadingImageButton;
	private View clickView;
	private ImageView rightview;
	private ArrayList<ApplyChooseItem> mChannelItems = new ArrayList<ApplyChooseItem>();

	private List<String> mImageUrls = new ArrayList<String>();
	private List<String> mImageNames = new ArrayList<String>();

	private LinkedHashMap<Integer, ApplyMaterial> mMaterials = new LinkedHashMap<Integer, ApplyMaterial>();

	private Boolean isBankName = false;
	private Boolean isShopName = false;
	private String shopName;

	DisplayImageOptions options = MyApplication.getDisplayOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply_detail);
		new TitleMenuUtil(this, getString(R.string.title_apply_open)).show();
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);
		mTerminalNumber = getIntent().getStringExtra(TERMINAL_NUMBER);
		mTerminalStatus = getIntent().getIntExtra(TERMINAL_STATUS, 0);

		initViews();
		loadData(mApplyType);
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);

		mTab = (MyTabWidget) findViewById(R.id.apply_detail_tab);

		mPosBrand = (TextView) findViewById(R.id.apply_detail_brand);
		mPosModel = (TextView) findViewById(R.id.apply_detail_model);
		mSerialNum = (TextView) findViewById(R.id.apply_detail_serial);
		mPayChannel = (TextView) findViewById(R.id.apply_detail_channel);

		mContainer = (LinearLayout) findViewById(R.id.apply_detail_container);
		mMerchantContainer = (LinearLayout) findViewById(R.id.apply_detail_merchant_container);
		mCustomerContainer = (LinearLayout) findViewById(R.id.apply_detail_customer_container);
		mMaterialContainer = (LinearLayout) findViewById(R.id.apply_detail_material_container);
		mApplySubmit = (Button) findViewById(R.id.apply_submit);

		mApplySubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				List<Map<String, Object>> totalParams = new ArrayList<Map<String, Object>>();

				Map<String, Object> params = new HashMap<String, Object>();
				params.put("terminalId", mTerminalId);
				params.put("status", mTerminalStatus == 2 ? 2 : 1);
				params.put("applyCustomerId", MyApplication.getNewUser()
						.getId());
				params.put("publicPrivateStatus", mApplyType);

				params.put("merchantId", mMerchantId);
				params.put("name", getItemValue(mMerchantKeys[1]));
				params.put("merchantName", getItemValue(mMerchantKeys[2]));
				params.put("sex", mMerchantGender);
				params.put("birthday", getItemValue(mMerchantKeys[4]));
				if (!RegText.isIdentityCard(getItemValue(mMerchantKeys[5]))) {
					CommonUtil
							.toastShort(ApplyDetailActivity.this, "身份证号格式不正确");
					return;
				}
				params.put("cardId", getItemValue(mMerchantKeys[5]));
				if (!RegText.isMobileNO(getItemValue(mMerchantKeys[6]))) {
					CommonUtil
							.toastShort(ApplyDetailActivity.this, "手机号码格式不正确");
					return;
				}
				params.put("phone", getItemValue(mMerchantKeys[6]));
				if (!RegText.isEmail(getItemValue(mMerchantKeys[7]))) {
					CommonUtil.toastShort(ApplyDetailActivity.this, "邮箱格式不正确");
					return;
				}
				params.put("email", getItemValue(mMerchantKeys[7]));
				params.put("cityId",
						null != mMerchantCity ? mMerchantCity.getId() : 0);

				params.put("bankNum", getItemValue(mBankKeys[0]));
				params.put("bankName", getItemValue(mBankKeys[1]));

				params.put("bank_name", getItemValue(mBankKeys[2]));
				if (mChosenBank != null)
					params.put("bankCode", mChosenBank.getNo());
				else
					params.put("bankCode", bankCode);
				if (mApplyType == 1) {
					params.put("registeredNo", getItemValue(mBankKeys[3]));
					params.put("organizationNo", getItemValue(mBankKeys[4]));
				}
				if (null != mChosenChannel)
					params.put("channel", mChosenChannel.getId());
				if (null != mChosenBilling)
					params.put("billingId", mChosenBilling.id);

				totalParams.add(params);

				for (ApplyMaterial material : mMaterials.values()) {
					// text type
					if (material.getTypes() == TYPE_TEXT) {
						String key = material.getName();
						String value = getItemValue(key);
						material.setValue(value);
					}
					// bank type
					else if (material.getTypes() == TYPE_BANK) {
						String key = material.getName();
						LinearLayout item = (LinearLayout) mContainer
								.findViewWithTag(key);
						String value = (String) item
								.getTag(R.id.apply_detail_key);
						material.setValue(value);
					}
					// image type
					else if (material.getTypes() == TYPE_IMAGE) {
						String key = material.getName();
						LinearLayout item = (LinearLayout) mContainer
								.findViewWithTag(key);
						// if (item.findViewById(R.id.apply_detail_value) !=
						// null) {
						// material.setValue((String) item.findViewById(
						// R.id.apply_detail_value).getTag());
						// } else
						if (item.findViewById(R.id.apply_detail_view) != null) {
							material.setValue((String) item.findViewById(
									R.id.apply_detail_view).getTag());
						} else {
							System.out.println(item);
						}
						// String value = (String) item
						// .getTag(R.id.apply_detail_key);
						// material.setValue(value);
					}
					if (TextUtils.isEmpty(material.getValue()))
						continue;
					// image types' value have been set in advance
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("key", material.getName());
					param.put("value", material.getValue());
					param.put("types", material.getTypes());
					param.put("openingRequirementId",
							material.getOpeningRequirementId());
					param.put("targetId", material.getId());

					totalParams.add(param);
				}

				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("paramMap", totalParams);
				API.submitApply(ApplyDetailActivity.this, paramMap,
						new HttpCallback(ApplyDetailActivity.this) {
							@Override
							public void onSuccess(Object data) {
								CommonUtil.toastShort(ApplyDetailActivity.this,
										data.toString());

								finish();
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
			}
		});
	}

	private void loadData(int applyType) {
		// mMerchantContainer.removeAllViews();
		// mCustomerContainer.removeAllViews();
		// mMaterialContainer.removeAllViews();
		// initMerchantDetailKeys();

		API.getApplyDetail(this, MyApplication.getInstance().getCustomerId(),
				mTerminalId, applyType, new HttpCallback<ApplyDetail>(this) {
					@Override
					public void onSuccess(ApplyDetail data) {
						ApplyTerminalDetail terminalDetail = data
								.getTerminalDetail();
						final List<ApplyChooseItem> merchants = data
								.getMerchants();
						List<ApplyMaterial> materials = data.getMaterials();
						List<ApplyCustomerDetail> customerDetails = data
								.getCustomerDetails();
						final OpeningInfos openingInfos = data
								.getOpeningInfos();

						if (null != terminalDetail) {
							mPosBrand.setText(terminalDetail.getBrandName());
							mPosModel.setText(terminalDetail.getModelNumber());
							mSerialNum.setText(terminalDetail.getSerialNumber());
							mPayChannel.setText(terminalDetail.getChannelName());
							mPayChannelID = terminalDetail.getChannelId();
							// terminalDetail.setSupportRequirementType(1);

							// mApplyType =
							// terminalDetail.getSupportRequirementType();

							if (terminalDetail.getSupportRequirementType() == 1) {

								mApplyType = 1;
								mTab.addTab(getString(R.string.apply_public),
										17);
								mTab.setOnTabSelectedListener(new MyTabWidget.OnTabSelectedListener() {
									@Override
									public void onTabSelected(int position) {
									}
								});
								mTab.updateTabs(0);
							} else if (terminalDetail
									.getSupportRequirementType() == 2) {
								mApplyType = 2;
								mTab.addTab(getString(R.string.apply_private),
										17);
								mTab.setOnTabSelectedListener(new MyTabWidget.OnTabSelectedListener() {
									@Override
									public void onTabSelected(int position) {
									}
								});
								mTab.updateTabs(0);
							} else if (terminalDetail
									.getSupportRequirementType() == 3) {

								if (!isLoaded) {
									isLoaded = true;
									// mTab.removeAllViews();
									mTab.addTab(
											getString(R.string.apply_public),
											17);
									mTab.addTab(
											getString(R.string.apply_private),
											17);
									mTab.setOnTabSelectedListener(new MyTabWidget.OnTabSelectedListener() {
										@Override
										public void onTabSelected(int position) {
											mApplyType = position + 1;
											loadData(mApplyType);
										}
									});
									mTab.updateTabs(0);
								}
							}

						}

						mMerchantContainer.removeAllViews();
						mCustomerContainer.removeAllViews();
						mMaterialContainer.removeAllViews();
						initMerchantDetailKeys();

						// set the choosing merchant listener
						View merchantChoose = mMerchantContainer
								.findViewWithTag(mMerchantKeys[0]);
						merchantChoose
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										startChooseItemActivity(
												REQUEST_CHOOSE_MERCHANT,
												getString(R.string.title_apply_choose_merchant),
												mMerchantId,
												(ArrayList<ApplyChooseItem>) merchants);
									}
								});
						// set the customer details
						setCustomerDetail(materials, customerDetails);
						if (openingInfos != null) {

							mMerchantId = openingInfos.getMerchant_id();
							bankCode = openingInfos.getAccount_bank_code();
							setData(openingInfos);
						}
						updateUIWithValidation();
					}

					@Override
					public TypeToken<ApplyDetail> getTypeToken() {
						return new TypeToken<ApplyDetail>() {
						};
					}
				});
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CHOOSE_MERCHANT: {
			mMerchantId = data.getIntExtra(SELECTED_ID, 0);
			setItemValue(mMerchantKeys[0], data.getStringExtra(SELECTED_TITLE));
			API.getApplyMerchantDetail(ApplyDetailActivity.this, mMerchantId,
					new HttpCallback<Merchant>(ApplyDetailActivity.this) {
						@Override
						public void onSuccess(Merchant data) {
							mMerchant = data;
							setMerchantDetailValues(data);
						}

						@Override
						public TypeToken<Merchant> getTypeToken() {
							return new TypeToken<Merchant>() {
							};
						}
					});
			break;
		}
		case REQUEST_CHOOSE_CITY: {
			mMerchantProvince = (Province) data
					.getSerializableExtra(SELECTED_PROVINCE);
			mMerchantCity = (City) data.getSerializableExtra(SELECTED_CITY);
			setItemValue(mMerchantKeys[8], mMerchantCity.getName());
			break;
		}
		case REQUEST_CHOOSE_CHANNEL: {
			mChosenChannel = (ApplyChannel) data
					.getSerializableExtra(SELECTED_CHANNEL);
			mChosenBilling = (ApplyChannel.Billing) data
					.getSerializableExtra(SELECTED_BILLING);
			Log.e("", mChosenChannel.getName() + mChosenBilling);
			setItemValue(getString(R.string.apply_detail_channel),
					mChosenChannel.getName() + mChosenBilling.name);
			break;
		}
		case REQUEST_CHOOSE_BANK: {
			mChosenBank = (Bank) data.getSerializableExtra(SELECTED_BANK);
			if (null != mChosenBank) {
				LinearLayout item = (LinearLayout) mContainer
						.findViewWithTag(mBankKeys[2]);
				item.setTag(R.id.apply_detail_key, mChosenBank.getNo());
				setItemValue(mBankKeys[2], mChosenBank.getName());
			}

			break;
		}
		case REQUEST_UPLOAD_IMAGE:
		case REQUEST_TAKE_PHOTO: {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					if (msg.what == 1) {

						// CommonUtil.toastShort(ApplyDetailActivity.this,
						// (String) msg.obj);
						String uri = (String) msg.obj;
						if (null != uploadingTextView) {
							// uploadingTextView
							// .setText(getString(R.string.apply_upload_success));
							// uploadingTextView.setClickable(false);
							// uploadingTextView
							// .setOnClickListener(new onWatchListener());

							uploadingTextView.setVisibility(View.GONE);
							uploadingImageButton.setVisibility(View.VISIBLE);
							rightview.setVisibility(View.VISIBLE);
							uploadingImageButton.setTag(uri);
							uploadingImageButton
									.setOnClickListener(new onWatchListener());
						} else {

							clickView.setTag(uri);
							clickView.setOnClickListener(new onWatchListener());
						}
						// String url = (String) msg.obj;
						for (ApplyMaterial material : mMaterials.values()) {
							if (material.getTypes() == TYPE_IMAGE
									&& material.getName().equals(mUploadKey)) {
								// material.setValue(url);
								material.setValue(uri);
								break;
							}
						}

					} else {
						CommonUtil.toastShort(ApplyDetailActivity.this,
								getString(R.string.toast_upload_failed));
						if (null != uploadingTextView) {
							uploadingTextView
									.setText(getString(R.string.apply_upload_again));
							uploadingTextView.setClickable(true);
						}
					}

				}
			};
			if (null != uploadingTextView) {
				uploadingTextView.setText(getString(R.string.apply_uploading));
				uploadingTextView.setClickable(false);
			}

			String realPath = "";
			if (requestCode == REQUEST_TAKE_PHOTO) {
				realPath = photoPath;
			} else {
				Uri uri = data.getData();
				if (uri != null) {
					realPath = getRealPathFromURI(uri);
				}
			}
			if (TextUtils.isEmpty(realPath)) {
				handler.sendEmptyMessage(0);
				return;
			}

			File file = new File(realPath);
			API.uploadPic(ApplyDetailActivity.this, file, mTerminalId,
					new HttpCallback(ApplyDetailActivity.this) {

						@Override
						public void onSuccess(Object data) {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = data.toString();
							handler.sendMessage(msg);
						}

						@Override
						public void onFailure(String message) {
							handler.sendEmptyMessage(0);
						}

						@Override
						public TypeToken getTypeToken() {
							return null;
						}
					});
			// CommonUtil.uploadFile(realPath, "img",
			// new CommonUtil.OnUploadListener() {
			// @Override
			// public void onSuccess(String result) {
			// try {
			// JSONObject jo = new JSONObject(result);
			// String url = jo.getString("result");
			// Message msg = new Message();
			// msg.what = 1;
			// msg.obj = url;
			// handler.sendMessage(msg);
			// } catch (JSONException e) {
			// handler.sendEmptyMessage(0);
			// }
			// }
			//
			// @Override
			// public void onFailed(String message) {
			// handler.sendEmptyMessage(0);
			// }
			// });

			break;
		}
		}

		updateUIWithValidation();
	}

	private void updateUIWithValidation() {
		final boolean enabled = !TextUtils
				.isEmpty(getItemValue(mMerchantKeys[1]))
				&& !TextUtils.isEmpty(getItemValue(mMerchantKeys[2]))
				&& (mMerchantGender == 0 || mMerchantGender == 1)
				&& !TextUtils.isEmpty(getItemValue(mMerchantKeys[4]))
				&& !TextUtils.isEmpty(getItemValue(mMerchantKeys[5]))
				&& !TextUtils.isEmpty(getItemValue(mMerchantKeys[6]))
				&& !TextUtils.isEmpty(getItemValue(mMerchantKeys[7]))
				&& null != mMerchantCity
				&& !TextUtils.isEmpty(getItemValue(mBankKeys[0]))
				&& !TextUtils.isEmpty(getItemValue(mBankKeys[1]))
				&& !TextUtils.isEmpty(getItemValue(mBankKeys[2]))
				&& ((mApplyType == 2) || ((mApplyType == 1) && !TextUtils
						.isEmpty(getItemValue(mBankKeys[3]))))
				&& ((mApplyType == 2) || ((mApplyType == 1) && !TextUtils
						.isEmpty(getItemValue(mBankKeys[4]))))
				&& ((null != mChosenChannel && null != mChosenChannel.getName()) && (null != mChosenBilling && null != mChosenBilling.name));
		mApplySubmit.setEnabled(enabled);
	}

	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = this.managedQuery(contentUri, proj, null, null,
					null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	/**
	 * set the item value by key
	 * 
	 * @param key
	 * @param value
	 */
	private void setItemValue(String key, String value) {
		LinearLayout item = (LinearLayout) mContainer.findViewWithTag(key);
		TextView tvValue = (TextView) item
				.findViewById(R.id.apply_detail_value);
		tvValue.setText(value);
	}

	/**
	 * get the item value by key
	 * 
	 * @param key
	 * @return
	 */
	private String getItemValue(String key) {
		LinearLayout item = (LinearLayout) mContainer.findViewWithTag(key);
		TextView tvValue = (TextView) item
				.findViewById(R.id.apply_detail_value);
		return tvValue.getText().toString();
	}

	/**
	 * firstly init the merchant category with item keys, and after user select
	 * the merchant the values will be set
	 */
	private void initMerchantDetailKeys() {
		// the first category
		mMerchantKeys = getResources().getStringArray(
				R.array.apply_detail_merchant_keys);

		mMerchantContainer.addView(getDetailItem(ITEM_CHOOSE, mMerchantKeys[0],
				null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, mMerchantKeys[1],
				null));
		isShopName = true;
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, mMerchantKeys[2],
				null));

		final View merchantGender = getDetailItem(ITEM_CHOOSE,
				mMerchantKeys[3], null);
		merchantGender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ApplyDetailActivity.this);
				final String[] items = getResources().getStringArray(
						R.array.apply_detail_gender);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setItemValue(mMerchantKeys[3], items[which]);
						mMerchantGender = which;
						Log.e("", "---" + mMerchantGender);
						updateUIWithValidation();
					}
				});
				builder.show();
			}
		});
		mMerchantContainer.addView(merchantGender);

		View merchantBirthday = getDetailItem(ITEM_CHOOSE, mMerchantKeys[4],
				null);
		merchantBirthday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				CommonUtil.showDatePicker(ApplyDetailActivity.this,
						mMerchantBirthday, new CommonUtil.OnDateSetListener() {
							@Override
							public void onDateSet(String date) {
								setItemValue(mMerchantKeys[4], date);
								updateUIWithValidation();
							}
						});
			}
		});
		mMerchantContainer.addView(merchantBirthday);
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, mMerchantKeys[5],
				null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, mMerchantKeys[6],
				null));
		mMerchantContainer.addView(getDetailItem(ITEM_EDIT, mMerchantKeys[7],
				null));

		View merchantCity = getDetailItem(ITEM_CHOOSE, mMerchantKeys[8], null);
		merchantCity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ApplyDetailActivity.this,
						CityProvinceActivity.class);
				intent.putExtra(SELECTED_PROVINCE, mMerchantProvince);
				intent.putExtra(SELECTED_CITY, mMerchantCity);
				startActivityForResult(intent, REQUEST_CHOOSE_CITY);
			}
		});
		mMerchantContainer.addView(merchantCity);

		// the second category 开通申请第二段
		if (mApplyType == 1) {

			mBankKeys = getResources().getStringArray(
					R.array.apply_detail_bank_keys_public);
		} else {
			mBankKeys = getResources().getStringArray(
					R.array.apply_detail_bank_keys_private);
		}
		mCustomerContainer
				.addView(getDetailItem(ITEM_EDIT, mBankKeys[0], null));
		isBankName = true;
		mCustomerContainer
				.addView(getDetailItem(ITEM_EDIT, mBankKeys[1], null));
		View chooseBank = getDetailItem(ITEM_CHOOSE, mBankKeys[2], null);
		chooseBank.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(ApplyDetailActivity.this,
						ApplyBankActivity.class);
				intent.putExtra(TERMINAL_ID, mTerminalId);
				intent.putExtra(SELECTED_BANK, mChosenBank);
				startActivityForResult(intent, REQUEST_CHOOSE_BANK);
			}
		});
		mCustomerContainer.addView(chooseBank);
		// mCustomerContainer
		// .addView(getDetailItem(ITEM_EDIT, mBankKeys[2], null));

		if (mApplyType == 1) {
			mCustomerContainer.addView(getDetailItem(ITEM_EDIT, mBankKeys[3],
					null));
			mCustomerContainer.addView(getDetailItem(ITEM_EDIT, mBankKeys[4],
					null));
		}

		View chooseChannel = getDetailItem(ITEM_CHOOSE,
				getString(R.string.apply_detail_channel), null);
		chooseChannel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// mChosenChannel = new ApplyChannel();
				// mChosenBilling = mChosenChannel.new Billing();
				Intent intent = new Intent(ApplyDetailActivity.this,
						ApplyChannelActivity.class);
				intent.putExtra(SELECTED_CHANNEL_ID, mPayChannelID);
				intent.putExtra(SELECTED_CHANNEL, mChosenChannel);
				intent.putExtra(SELECTED_BILLING, mChosenBilling);
				startActivityForResult(intent, REQUEST_CHOOSE_CHANNEL);
				// if (mChannelItems.size() > 0) {
				// startChooseItemActivity(REQUEST_CHOOSE_CHANNEL,
				// getString(R.string.title_apply_choose_channel), mChannelId,
				// mChannelItems);
				// } else {
				// API.getApplyChannelList(ApplyDetailActivity.this, new
				// HttpCallback<List>(ApplyDetailActivity.this) {
				// @Override
				// public void onSuccess(List data) {
				// for (Object obj : data) {
				// LinkedTreeMap map = (LinkedTreeMap) obj;
				// ApplyChooseItem item = new ApplyChooseItem();
				// item.setId((int) Math.floor((Double) map.get("id")));
				// item.setTitle((String) map.get("name"));
				// mChannelItems.add(item);
				// }
				// startChooseItemActivity(REQUEST_CHOOSE_CHANNEL,
				// getString(R.string.title_apply_choose_channel), mChannelId,
				// mChannelItems);
				// }
				//
				// @Override
				// public TypeToken<List> getTypeToken() {
				// return new TypeToken<List>() {
				// };
				// }
				// });
				// }
			}
		});
		mCustomerContainer.addView(chooseChannel);
	}

	/**
	 * set the item values after user select a merchant
	 * 
	 * @param merchant
	 */
	private void setMerchantDetailValues(final Merchant merchant) {
		setItemValue(mMerchantKeys[1], merchant.getLegalPersonName());
		setItemValue(mMerchantKeys[2], merchant.getTitle());
		setItemValue(mMerchantKeys[5], merchant.getLegalPersonCardId());
		setItemValue(mMerchantKeys[6], merchant.getPhone());
		CommonUtil.findCityById(this, merchant.getCityId(),
				new CommonUtil.OnCityFoundListener() {
					@Override
					public void onCityFound(Province province, City city) {
						mMerchantProvince = province;
						mMerchantCity = city;
						Log.e("--mMerchantCity--", mMerchantCity.getName());
						setItemValue(mMerchantKeys[8], city.getName());
						updateUIWithValidation();
					}
				});

		setItemValue(mBankKeys[0], merchant.getAccountBankNum());
		setItemValue(mBankKeys[1], merchant.getAccountBankName());
		setItemValue(mBankKeys[2], merchant.getBank_name());
		if (mApplyType == 1) {
			setItemValue(mBankKeys[3], merchant.getTaxRegisteredNo());
			setItemValue(mBankKeys[4], merchant.getOrganizationCodeNo());
		}

	}

	private void setData(final OpeningInfos openingInfos) {
		final String[] items = getResources().getStringArray(
				R.array.apply_detail_gender);
		setItemValue(mMerchantKeys[0], openingInfos.getMerchant_name());
		setItemValue(mMerchantKeys[1], openingInfos.getName());
		setItemValue(mMerchantKeys[2], openingInfos.getMerchant_name());
		setItemValue(mMerchantKeys[3], items[openingInfos.getSex() % 2]);
		mMerchantGender = openingInfos.getSex() % 2;
		setItemValue(mMerchantKeys[4], openingInfos.getBirthdays());
		setItemValue(mMerchantKeys[5], openingInfos.getCard_id());
		setItemValue(mMerchantKeys[6], openingInfos.getPhone());
		setItemValue(mMerchantKeys[7], openingInfos.getEmail());
		CommonUtil.findCityById(this, openingInfos.getCity_id(),
				new CommonUtil.OnCityFoundListener() {
					@Override
					public void onCityFound(Province province, City city) {
						mMerchantProvince = province;
						mMerchantCity = city;
						Log.e("--mMerchantCity--", mMerchantCity.getName());
						setItemValue(mMerchantKeys[8], city.getName());
						updateUIWithValidation();
					}
				});

		setItemValue(mBankKeys[0], openingInfos.getAccount_bank_num());
		setItemValue(mBankKeys[1], openingInfos.getAccount_bank_name());
		setItemValue(mBankKeys[2], openingInfos.getBank_name());
		if (mApplyType == 1) {
			setItemValue(mBankKeys[3], openingInfos.getTax_registered_no());
			setItemValue(mBankKeys[4], openingInfos.getOrganization_code_no());
			setItemValue(
					mBankKeys[5],
					StringUtil.formatNull(openingInfos.getChannelname())
							+ StringUtil.formatNull(openingInfos
									.getBillingname()));
		} else {
			setItemValue(
					mBankKeys[3],
					StringUtil.formatNull(openingInfos.getChannelname())
							+ StringUtil.formatNull(openingInfos
									.getBillingname()));
		}
		mChosenChannel = new ApplyChannel();
		mChosenChannel.setId(openingInfos.getPay_channel_id());
		mChosenChannel.setName(openingInfos.getChannelname());
		mChosenBilling = mChosenChannel.new Billing();
		mChosenBilling.id = openingInfos.getBilling_cyde_id();
		mChosenBilling.name = openingInfos.getBillingname();
	}

	/**
	 * start the {@link ApplyChooseActivity} to choose item
	 * 
	 * @param requestCode
	 *            handle the return item according to request code
	 * @param title
	 *            the started activity title
	 * @param selectedId
	 *            the id of the selected item
	 * @param items
	 *            the items to choose
	 */
	private void startChooseItemActivity(int requestCode, String title,
			int selectedId, ArrayList<ApplyChooseItem> items) {
		Intent intent = new Intent(ApplyDetailActivity.this,
				ApplyChooseActivity.class);
		intent.putExtra(CHOOSE_TITLE, title);
		intent.putExtra(SELECTED_ID, selectedId);
		intent.putExtra(CHOOSE_ITEMS, items);
		startActivityForResult(intent, requestCode);
	}

	/**
	 * set the customer's details after the first request returned
	 * 
	 * @param customerDetails
	 */
	private void setCustomerDetail(List<ApplyMaterial> materials,
			List<ApplyCustomerDetail> customerDetails) {
		if (null == materials || materials.size() <= 0)
			return;
		mMaterials.clear();
		for (ApplyMaterial material : materials) {
			mMaterials.put(material.getId(), material);
		}
		if (null != customerDetails && customerDetails.size() > 0) {
			for (ApplyCustomerDetail customerDetail : customerDetails) {
				ApplyMaterial material = mMaterials.get(customerDetail
						.getTargetId());
				if (null != material) {
					material.setValue(customerDetail.getValue());
				}
			}
		}
		for (final ApplyMaterial material : mMaterials.values()) {
			switch (material.getTypes()) {
			case TYPE_TEXT:
				mMaterialContainer.addView(getDetailItem(ITEM_EDIT,
						material.getName(), material.getValue()));
				break;
			case TYPE_IMAGE:
				String imageName = material.getName();
				String imageUrl = material.getValue();
				if (!TextUtils.isEmpty(imageUrl)) {
					mImageNames.add(imageName);
					mImageUrls.add(imageUrl);
					mMaterialContainer.addView(getDetailItem(ITEM_VIEW,
							imageName, imageUrl));
				} else {
					mMaterialContainer.addView(getDetailItem(ITEM_UPLOAD,
							imageName, imageUrl));
				}
				break;
			case TYPE_BANK:
				View chooseBank = getDetailItem(ITEM_CHOOSE,
						material.getName(), null);
				chooseBank.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						mBankKey = material.getName();
						Intent intent = new Intent(ApplyDetailActivity.this,
								ApplyBankActivity.class);
						intent.putExtra(TERMINAL_ID, mTerminalId);
						intent.putExtra(SELECTED_BANK, mChosenBank);
						startActivityForResult(intent, REQUEST_CHOOSE_BANK);
					}
				});
				mMaterialContainer.addView(chooseBank);
				break;
			}
		}

	}

	private LinearLayout getDetailItem(int itemType, String key, String value) {
		LinearLayout item;
		switch (itemType) {
		case ITEM_EDIT:
			item = (LinearLayout) mInflater.inflate(
					R.layout.apply_detail_item_edit, null);
			break;
		case ITEM_CHOOSE:
			item = (LinearLayout) mInflater.inflate(
					R.layout.apply_detail_item_choose, null);
			break;
		case ITEM_UPLOAD:
			item = (LinearLayout) mInflater.inflate(
					R.layout.apply_detail_item_upload, null);
			break;
		case ITEM_VIEW:
			item = (LinearLayout) mInflater.inflate(
					R.layout.apply_detail_item_view, null);
			break;
		default:
			item = (LinearLayout) mInflater.inflate(
					R.layout.apply_detail_item_edit, null);
		}
		item.setTag(key);
		setupItem(item, itemType, key, value);
		return item;
	}

	private void setupItem(LinearLayout item, int itemType, final String key,
			final String value) {
		switch (itemType) {
		case ITEM_EDIT: {
			TextView tvKey = (TextView) item
					.findViewById(R.id.apply_detail_key);
			EditText etValue = (EditText) item
					.findViewById(R.id.apply_detail_value);
			if (isBankName) {
				isBankName = false;
				etValue.setFocusable(false);
				etValue.setEnabled(false);

			}
			if (isShopName) {
				shopName = value;
				isShopName = false;
				etValue.addTextChangedListener(new TextWatcherAdapter() {
					public void afterTextChanged(final Editable gitDirEditText) {

						updateUIWithValidation();

						LinearLayout item = (LinearLayout) mContainer
								.findViewWithTag(mBankKeys[1]);
						EditText etBankName = (EditText) item
								.findViewById(R.id.apply_detail_value);
						etBankName.setText(gitDirEditText.toString());

					}
				});
			} else {
				etValue.addTextChangedListener(new TextWatcherAdapter() {
					public void afterTextChanged(final Editable gitDirEditText) {

						updateUIWithValidation();
					}
				});
			}
			if (!TextUtils.isEmpty(key))
				tvKey.setText(key);
			if (!TextUtils.isEmpty(value))

				if (isBankName) {
					etValue.setText(shopName);
				} else {
					etValue.setText(value);
				}
			break;
		}
		case ITEM_CHOOSE: {
			TextView tvKey = (TextView) item
					.findViewById(R.id.apply_detail_key);
			TextView tvValue = (TextView) item
					.findViewById(R.id.apply_detail_value);

			if (!TextUtils.isEmpty(key))
				tvKey.setText(key);
			if (!TextUtils.isEmpty(value))
				tvValue.setText(value);
			break;
		}
		case ITEM_UPLOAD: {
			TextView tvKey = (TextView) item
					.findViewById(R.id.apply_detail_key);
			final TextView tvValue = (TextView) item
					.findViewById(R.id.apply_detail_value);
			final ImageButton uploadingSuccess = (ImageButton) item
					.findViewById(R.id.apply_detail_view);
			final ImageView right_view = (ImageView) item
					.findViewById(R.id.right_view);
			if (!TextUtils.isEmpty(key))
				tvKey.setText(key);
			tvValue.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					uploadingTextView = tvValue;
					uploadingImageButton = uploadingSuccess;
					rightview = right_view;
					AlertDialog.Builder builder = new AlertDialog.Builder(
							ApplyDetailActivity.this);
					final String[] items = getResources().getStringArray(
							R.array.apply_detail_upload);
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									mUploadKey = key;
									switch (which) {
									case 0: {
										Intent intent;
										if (Build.VERSION.SDK_INT < 19) {
											intent = new Intent(
													Intent.ACTION_GET_CONTENT);
											intent.setType("image/*");
										} else {
											intent = new Intent(
													Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
										}
										startActivityForResult(intent,
												REQUEST_UPLOAD_IMAGE);
										break;
									}
									case 1: {
										String state = Environment
												.getExternalStorageState();
										if (state
												.equals(Environment.MEDIA_MOUNTED)) {
											Intent intent = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);
											File outDir = Environment
													.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
											if (!outDir.exists()) {
												outDir.mkdirs();
											}
											File outFile = new File(outDir,
													System.currentTimeMillis()
															+ ".jpg");
											photoPath = outFile
													.getAbsolutePath();
											intent.putExtra(
													MediaStore.EXTRA_OUTPUT,
													Uri.fromFile(outFile));
											intent.putExtra(
													MediaStore.EXTRA_VIDEO_QUALITY,
													1);
											startActivityForResult(intent,
													REQUEST_TAKE_PHOTO);
										} else {
											CommonUtil
													.toastShort(
															ApplyDetailActivity.this,
															getString(R.string.toast_no_sdcard));
										}
										break;
									}
									}
								}
							});
					builder.show();

				}
			});
			break;
		}
		case ITEM_VIEW: {
			TextView tvKey = (TextView) item
					.findViewById(R.id.apply_detail_key);
			ImageButton ibView = (ImageButton) item
					.findViewById(R.id.apply_detail_view);
			ibView.setTag(value);
			if (!TextUtils.isEmpty(key))
				tvKey.setText(key);
			ibView.setOnClickListener(new onWatchListener());
			// new View.OnClickListener() {
			// @Override
			// public void onClick(View view) {
			// int position = mImageNames.indexOf(key);
			// Intent intent = new Intent(ApplyDetailActivity.this,
			// ShowWebImageActivity.class);
			// intent.putExtra(IMAGE_NAMES,
			// StringUtil.join(mImageNames, ","));
			// intent.putExtra(IMAGE_URLS,
			// StringUtil.join(mImageUrls, ","));
			// intent.putExtra(POSITION, position);
			// startActivity(intent);

			// }
			// });

		}
		}
	}

	private class onWatchListener implements View.OnClickListener {

		@Override
		public void onClick(View view) {
			clickView = view;
			final String uri = (String) view.getTag();
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ApplyDetailActivity.this);
			final String[] items = getResources().getStringArray(
					R.array.apply_detail_view);
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: {

						AlertDialog.Builder build = new AlertDialog.Builder(
								ApplyDetailActivity.this);
						LayoutInflater factory = LayoutInflater
								.from(ApplyDetailActivity.this);
						final View textEntryView = factory.inflate(
								R.layout.show_view, null);
						build.setView(textEntryView);
						final ImageView view = (ImageView) textEntryView
								.findViewById(R.id.imag);
						Log.e("onWatchListener-----------", uri);
						ImageLoader.getInstance().displayImage(uri, view,
								options);

						// ImageCacheUtil.IMAGE_CACHE.get(uri, view);
						build.create().show();
						break;
					}
					case 1: {
						Intent intent = new Intent();
						if (Build.VERSION.SDK_INT < 19) {
							intent = new Intent(Intent.ACTION_GET_CONTENT);
							intent.setType("image/*");
						} else {
							intent = new Intent(
									Intent.ACTION_PICK,
									android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						}
						startActivityForResult(intent, REQUEST_UPLOAD_IMAGE);
						break;
					}
					case 2: {
						String state = Environment.getExternalStorageState();
						if (state.equals(Environment.MEDIA_MOUNTED)) {
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							File outDir = Environment
									.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
							if (!outDir.exists()) {
								outDir.mkdirs();
							}
							File outFile = new File(outDir, System
									.currentTimeMillis() + ".jpg");
							photoPath = outFile.getAbsolutePath();
							intent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(outFile));
							intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
							startActivityForResult(intent, REQUEST_TAKE_PHOTO);
						} else {
							CommonUtil.toastShort(ApplyDetailActivity.this,
									getString(R.string.toast_no_sdcard));
						}
						break;
					}
					}
				}
			});
			builder.show();

		}

	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageStart( this.toString() );
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( this.toString() );
		MobclickAgent.onResume(this);
	}
}
