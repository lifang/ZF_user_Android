package com.example.zf_android.trade;

import static com.example.zf_android.trade.Constants.TerminalIntent.HAVE_VIDEO;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_ID;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_NUMBER;
import static com.example.zf_android.trade.Constants.TerminalIntent.TERMINAL_STATUS;
import static com.example.zf_android.trade.Constants.TerminalStatus.CANCELED;
import static com.example.zf_android.trade.Constants.TerminalStatus.OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.PART_OPENED;
import static com.example.zf_android.trade.Constants.TerminalStatus.STOPPED;
import static com.example.zf_android.trade.Constants.TerminalStatus.UNOPENED;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.entity.TerminalApply;
import com.example.zf_android.trade.entity.TerminalComment;
import com.example.zf_android.trade.entity.TerminalDetail;
import com.example.zf_android.trade.entity.TerminalOpen;
import com.example.zf_android.trade.entity.TerminalRate;
import com.example.zf_android.video.VideoActivity;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by Leo on 2015/3/4.
 */
public class TerminalDetailActivity extends BaseActivity {

	private int mTerminalStatus;
	private String mTerminalNumber;
	private int mTerminalId;

	private LayoutInflater mInflater;
	private TextView mStatus;
	private Button mBtnLeftTop;
	private Button mBtnLeftBottom;
	private Button mBtnRightTop;
	private Button mBtnRightBottom;
	private LinearLayout mCategoryContainer;
	private LinearLayout mCommentContainer;

	private View.OnClickListener mSyncListener;
	private View.OnClickListener mOpenListener;
	private View.OnClickListener mPosListener;
	private View.OnClickListener mVideoListener;

	private int isVideo, status;
	private Boolean appidBoolean, videoBoolean;
	DisplayImageOptions options = MyApplication.getDisplayOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isVideo = getIntent().getIntExtra(HAVE_VIDEO, 0);
		mTerminalId = getIntent().getIntExtra(TERMINAL_ID, 0);
		mTerminalNumber = getIntent().getStringExtra(TERMINAL_NUMBER);
		mTerminalStatus = getIntent().getIntExtra(TERMINAL_STATUS, 0);
		setContentView(R.layout.activity_terminal_detail);
		new TitleMenuUtil(this, getString(R.string.title_terminal_detail))
				.show();

		initViews();
		initBtnListeners();
		loadData();
	}

	private void initViews() {
		mInflater = LayoutInflater.from(this);
		mStatus = (TextView) findViewById(R.id.terminal_detail_status);
		mBtnLeftTop = (Button) findViewById(R.id.terminal_button_left_top);
		mBtnLeftBottom = (Button) findViewById(R.id.terminal_button_left_bottom);
		mBtnRightTop = (Button) findViewById(R.id.terminal_button_right_top);
		mBtnRightBottom = (Button) findViewById(R.id.terminal_button_right_bottom);
		mCategoryContainer = (LinearLayout) findViewById(R.id.terminal_category_container);
		mCommentContainer = (LinearLayout) findViewById(R.id.terminal_comment_container);
	}

	private void initBtnListeners() {
		mSyncListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				API.synchronous(TerminalDetailActivity.this, mTerminalId,
						new HttpCallback(TerminalDetailActivity.this) {

							@Override
							public void onSuccess(Object data) {
								CommonUtil.toastShort(
										TerminalDetailActivity.this,
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
				Intent intent = new Intent(TerminalDetailActivity.this,
						ApplyDetailActivity.class);
				intent.putExtra(TERMINAL_ID, mTerminalId);
				intent.putExtra(TERMINAL_NUMBER, mTerminalNumber);
				intent.putExtra(TERMINAL_STATUS, mTerminalStatus);
				startActivity(intent);
			}
		};
		mPosListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				API.findPosPassword(TerminalDetailActivity.this, mTerminalId,
						new HttpCallback(TerminalDetailActivity.this) {
							@Override
							public void onSuccess(Object data) {
								final String password = data.toString();
								final AlertDialog.Builder builder = new AlertDialog.Builder(
										TerminalDetailActivity.this);
								builder.setMessage(password);
								builder.setPositiveButton(
										getString(R.string.button_copy),
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialogInterface,
													int i) {
												CommonUtil
														.copy(TerminalDetailActivity.this,
																password);
												dialogInterface.dismiss();
												CommonUtil
														.toastShort(
																TerminalDetailActivity.this,
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
				if (status == UNOPENED && !appidBoolean) {

					CommonUtil
							.toastShort(TerminalDetailActivity.this, "请先申请开通");

				} else {
					// 添加视频审核
					Intent intent = new Intent(TerminalDetailActivity.this,
							VideoActivity.class);
					intent.putExtra(TERMINAL_ID, mTerminalId);
					startActivity(intent);
				}
			}
		};
	}

	private void loadData() {
		API.getTerminalDetail(this, mTerminalId, MyApplication.getInstance()
				.getCustomerId(), new HttpCallback<TerminalDetail>(this) {
			@Override
			public void onSuccess(TerminalDetail data) {
				TerminalApply apply = data.getApplyDetails();
				List<TerminalComment> comments = data.getTrackRecord();
				List<TerminalRate> rates = data.getRates();
				List<TerminalOpen> openDetails = data.getOpeningDetails();

				// set the status and buttons
				setStatusAndButtons(apply);
				// render terminal info
				LinearLayout terminalCategory = setTerminalInfo(apply);
				// add rate's table
				addRatesTable(terminalCategory, rates);
				// render terminal open info
				setOpenInfo(openDetails);
				// add terminal trace records
				addComments(comments);
			}

			@Override
			public TypeToken<TerminalDetail> getTypeToken() {
				return new TypeToken<TerminalDetail>() {
				};
			}
		});
	}

	private void setStatusAndButtons(TerminalApply apply) {
		status = null == apply ? mTerminalStatus : apply.getStatus();
		String[] terminalStatus = getResources().getStringArray(
				R.array.terminal_status);
		mStatus.setText(terminalStatus[status]);

		appidBoolean = !"".equals(apply.getAppId()) && apply.getAppId() != 0;
		videoBoolean = 1 == isVideo;

		switch (status) {
		case OPENED:
			mBtnRightBottom.setVisibility(View.VISIBLE);
			mBtnRightBottom.setText(getString(R.string.terminal_button_pos));
			mBtnRightBottom.setOnClickListener(mPosListener);
			if (appidBoolean) {
				if (videoBoolean) {

					mBtnLeftBottom.setVisibility(View.INVISIBLE);
					mBtnLeftTop.setVisibility(View.VISIBLE);
					mBtnLeftTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnLeftTop.setOnClickListener(mSyncListener);
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_video));
					mBtnRightTop.setOnClickListener(mVideoListener);

				} else {
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnRightTop.setOnClickListener(mSyncListener);
				}

			} else {
				if (videoBoolean) {
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_video));
					mBtnRightTop.setOnClickListener(mVideoListener);

				}
			}
			break;
		case PART_OPENED:
			if (appidBoolean) {

				mBtnLeftTop.setVisibility(View.VISIBLE);
				mBtnLeftTop.setText(getString(R.string.terminal_button_sync));
				mBtnLeftTop.setOnClickListener(mSyncListener);
				if (videoBoolean) {

					mBtnLeftBottom.setVisibility(View.VISIBLE);
					mBtnLeftBottom
							.setText(getString(R.string.terminal_button_video));
					mBtnLeftBottom.setOnClickListener(mVideoListener);
				}

				mBtnLeftBottom.setVisibility(View.INVISIBLE);
			} else {
				if (videoBoolean) {
					mBtnLeftTop.setVisibility(View.VISIBLE);
					mBtnLeftTop
							.setText(getString(R.string.terminal_button_video));
					mBtnLeftTop.setOnClickListener(mVideoListener);

					mBtnLeftBottom.setVisibility(View.INVISIBLE);
				}
			}
			mBtnRightTop.setVisibility(View.VISIBLE);
			mBtnRightTop.setText(getString(R.string.terminal_button_reopen));
			mBtnRightTop.setOnClickListener(mOpenListener);
			mBtnRightBottom.setVisibility(View.VISIBLE);
			mBtnRightBottom.setText(getString(R.string.terminal_button_pos));
			mBtnRightBottom.setOnClickListener(mPosListener);
			break;
		case UNOPENED:
			if (appidBoolean) {
				if (videoBoolean) {

					mBtnLeftBottom.setVisibility(View.INVISIBLE);
					mBtnLeftTop.setVisibility(View.VISIBLE);
					mBtnLeftTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnLeftTop.setOnClickListener(mSyncListener);
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_reopen));
					mBtnRightTop.setOnClickListener(mOpenListener);
					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_video));
					mBtnRightBottom.setOnClickListener(mVideoListener);
				} else {
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnRightTop.setOnClickListener(mSyncListener);
					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_reopen));
					mBtnRightBottom.setOnClickListener(mOpenListener);
				}
			} else {

				mBtnRightTop.setVisibility(View.VISIBLE);
				mBtnRightTop.setText(getString(R.string.terminal_button_open));
				mBtnRightTop.setOnClickListener(mOpenListener);

				if (videoBoolean) {
					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_video));
					mBtnRightBottom.setOnClickListener(mVideoListener);
				} else {

					mBtnRightBottom.setVisibility(View.INVISIBLE);
				}
			}

			break;
		case CANCELED:
			if (appidBoolean) {
				if (videoBoolean) {
					mBtnLeftBottom.setVisibility(View.INVISIBLE);
					mBtnLeftTop.setVisibility(View.VISIBLE);
					mBtnLeftTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnLeftTop.setOnClickListener(mSyncListener);
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_video));
					mBtnRightTop.setOnClickListener(mVideoListener);
					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_reopen));
					mBtnRightBottom.setOnClickListener(mOpenListener);
				} else {
					mBtnRightTop.setVisibility(View.VISIBLE);
					mBtnRightTop
							.setText(getString(R.string.terminal_button_sync));
					mBtnRightTop.setOnClickListener(mSyncListener);
					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_reopen));
					mBtnRightBottom.setOnClickListener(mOpenListener);
				}
			} else {
				if (videoBoolean) {

					mBtnRightBottom.setVisibility(View.VISIBLE);
					mBtnRightBottom
							.setText(getString(R.string.terminal_button_video));
					mBtnRightBottom.setOnClickListener(mVideoListener);
				}
				mBtnRightTop.setVisibility(View.VISIBLE);
				mBtnRightTop
						.setText(getString(R.string.terminal_button_reopen));
				mBtnRightTop.setOnClickListener(mOpenListener);
			}
			break;
		case STOPPED:

			break;
		}
	}

	private LinearLayout setTerminalInfo(TerminalApply apply) {
		if (null == apply) {
			LinkedHashMap<String, String> pairs = new LinkedHashMap<String, String>();
			pairs.put(getString(R.string.terminal_no_detail), "");
			return renderCategoryTemplate(R.string.terminal_category_apply,
					pairs);
		}
		LinkedHashMap<String, String> pairs = new LinkedHashMap<String, String>();
		String[] keys = getResources().getStringArray(
				R.array.terminal_apply_keys);
		pairs.put(keys[0], apply.getTerminalNum());
		pairs.put(keys[1], apply.getBrandName());
		pairs.put(keys[2], apply.getModelNumber());
		pairs.put(keys[3], apply.getFactorName());
		pairs.put(keys[4], apply.getTitle());
		pairs.put(keys[5], apply.getPhone());
		pairs.put(keys[6], apply.getOrderNumber());
		pairs.put(keys[7], apply.getCreatedAt());
		return renderCategoryTemplate(R.string.terminal_category_apply, pairs);
	}

	private void addRatesTable(LinearLayout category, List<TerminalRate> rates) {
		if (null == category || null == rates || rates.size() <= 0)
			return;
		LinearLayout header = (LinearLayout) mInflater.inflate(
				R.layout.terminal_rates_header, null);
		category.addView(header);
		for (TerminalRate rate : rates) {
			LinearLayout column = (LinearLayout) mInflater.inflate(
					R.layout.terminal_rates_column, null);
			TextView typeTv = (TextView) column
					.findViewById(R.id.terminal_column_type);
			TextView rateTv = (TextView) column
					.findViewById(R.id.terminal_column_rate);
			TextView statusTv = (TextView) column
					.findViewById(R.id.terminal_column_status);
			String[] status = getResources().getStringArray(
					R.array.terminal_status);
			typeTv.setText(rate.getType());

			DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
			df.applyPattern("0.0");

			if (rate.getType().equals("消费/消费撤销")) {
				rateTv.setText(df.format((rate.getTerminalRate() + rate
						.getServiceRate()) / 10)
						+ getString(R.string.notation_percent));
			} else {
				rateTv.setText(df.format(rate.getTerminalRate() / 10)
						+ getString(R.string.notation_percent));
			}
			statusTv.setText(status[rate.getStatus()]);
			category.addView(column);
		}
	}

	private void setOpenInfo(List<TerminalOpen> openDetails) {
		LinkedHashMap<String, String> pairs = new LinkedHashMap<String, String>();
		if (openDetails == null || openDetails.size() <= 0) {
			renderCategoryTemplate(R.string.terminal_category_open, pairs);
			return;
		}

		List<TerminalOpen> photoOpens = new ArrayList<TerminalOpen>();
		final List<String> imageUrls = new ArrayList<String>();
		final List<String> imageNames = new ArrayList<String>();
		for (TerminalOpen openDetail : openDetails) {
			if (null == openDetail)
				continue;
			if (openDetail.getTypes() == 1) {
				pairs.put(openDetail.getKey(), openDetail.getValue());
			} else {
				photoOpens.add(openDetail);
				imageNames.add(openDetail.getKey());
				imageUrls.add(openDetail.getValue());
			}
		}
		LinearLayout category = renderCategoryTemplate(
				R.string.terminal_category_open, pairs);

		View.OnClickListener onViewPhotoListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final int position = (Integer) view.getTag();
				// Intent intent = new Intent(TerminalDetailActivity.this,
				// ShowWebImageActivity.class);
				// intent.putExtra(IMAGE_NAMES, StringUtil.join(imageNames,
				// ","));
				// intent.putExtra(IMAGE_URLS, StringUtil.join(imageUrls, ","));
				// intent.putExtra(POSITION, position);
				// startActivity(intent);

				AlertDialog.Builder builder = new AlertDialog.Builder(
						TerminalDetailActivity.this);
				final String[] items = getResources().getStringArray(
						R.array.terminal_detail_view);
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0: {

							AlertDialog.Builder build = new AlertDialog.Builder(
									TerminalDetailActivity.this);
							LayoutInflater factory = LayoutInflater
									.from(TerminalDetailActivity.this);
							final View textEntryView = factory.inflate(
									R.layout.show_view, null);
							build.setView(textEntryView);
							final ImageView view = (ImageView) textEntryView
									.findViewById(R.id.imag);
							System.out.println((Integer) view.getTag() + "");
							// ImageCacheUtil.IMAGE_CACHE.get(
							// imageUrls.get(position), view);
							ImageLoader.getInstance().displayImage(
									imageUrls.get(position), view, options);
							build.create().show();
							break;
						}

						}
					}
				});
				builder.show();

			}
		};

		LinearLayout column = null;
		for (int i = 0; i < photoOpens.size(); i++) {
			TerminalOpen photoOpen = photoOpens.get(i);
			if (i % 2 == 0) {
				column = (LinearLayout) mInflater.inflate(
						R.layout.terminal_open_column, null);
				TextView key = (TextView) column
						.findViewById(R.id.terminal_open_key_left);
				ImageButton icon = (ImageButton) column
						.findViewById(R.id.terminal_open_icon_left);
				icon.setTag(i);
				icon.setOnClickListener(onViewPhotoListener);
				key.setText(photoOpen.getKey());
				if (i == photoOpens.size() - 1) {
					category.addView(column);
					column.findViewById(R.id.terminal_open_right)
							.setVisibility(View.INVISIBLE);
				}
			} else {
				TextView key = (TextView) column
						.findViewById(R.id.terminal_open_key_right);
				ImageButton icon = (ImageButton) column
						.findViewById(R.id.terminal_open_icon_right);
				icon.setTag(i);
				icon.setOnClickListener(onViewPhotoListener);
				key.setText(photoOpen.getKey());
				category.addView(column);
			}
		}
	}

	private void addComments(List<TerminalComment> comments) {
		if (null != comments && comments.size() > 0) {
			for (TerminalComment comment : comments) {
				if (null == comment)
					continue;
				LinearLayout commentLayout = (LinearLayout) mInflater.inflate(
						R.layout.after_sale_detail_comment, null);
				TextView content = (TextView) commentLayout
						.findViewById(R.id.comment_content);
				TextView person = (TextView) commentLayout
						.findViewById(R.id.comment_person);
				TextView time = (TextView) commentLayout
						.findViewById(R.id.comment_time);
				content.setText(comment.getContent());
				person.setText(comment.getName());
				time.setText(comment.getCreateAt());
				mCommentContainer.addView(commentLayout);
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

	private LinearLayout renderCategoryTemplate(int titleRes,
			LinkedHashMap<String, String> pairs) {
		LinearLayout terminalCategory = (LinearLayout) mInflater.inflate(
				R.layout.after_sale_detail_category, null);
		mCategoryContainer.addView(terminalCategory);

		TextView title = (TextView) terminalCategory
				.findViewById(R.id.category_title);
		LinearLayout keyContainer = (LinearLayout) terminalCategory
				.findViewById(R.id.category_key_container);
		LinearLayout valueContainer = (LinearLayout) terminalCategory
				.findViewById(R.id.category_value_container);

		title.setText(getString(titleRes));
		for (Map.Entry<String, String> pair : pairs.entrySet()) {
			TextView key = createKeyText();
			key.setText(pair.getKey());
			keyContainer.addView(key);

			TextView value = createValueText();
			value.setText(pair.getValue());
			valueContainer.addView(value);
		}
		return terminalCategory;
	}

}
