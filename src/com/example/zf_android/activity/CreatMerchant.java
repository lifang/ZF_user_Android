package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_TAKE_PHOTO;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_UPLOAD_IMAGE;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MerchantEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.CityProvinceActivity;
import com.example.zf_android.trade.Constants;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.RegText;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/***
 * 
 * 
 * 类名称：CreatMerchant 类描述： 创建商户 创建人： ljp 创建时间：2015-3-23 上午10:10:30
 * 
 * @version
 * 
 */
public class CreatMerchant extends BaseActivity implements OnClickListener {
	private static final int TYPE_7 = 7;
	private static final int TYPE_10 = 10;
	private static final int TYPE_11 = 11;
	private static final int TYPE_12 = 12;
	private static final int TYPE_13 = 13;
	private static final int TYPE_14 = 14;
	private static final int TYPE_15 = 15;
	private static final int TYPE_16 = 16;
	private TextView tv7;
	private EditText tv1, tv2, tv3, tv4, tv5, tv6, tvkhyh, tv8;
	private LinearLayout layout10, layout11, layout12, layout13, layout14,
			layout15, layout16;
	private Button saveBtn;
	private MerchantEntity merchantEntity = new MerchantEntity();
	private Map<Integer, LinearLayout> linearLayout = new HashMap<Integer, LinearLayout>();
	private String photoPath;
	private int type;
	private boolean needFresh = false;
	private Integer customerId;

	private String mUploadUri;

	DisplayImageOptions options = MyApplication.getDisplayOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_new);
		customerId = MyApplication.getInstance().getCustomerId();
		initView();
		new TitleMenuUtil(CreatMerchant.this, "创建商户").show();
	}

	private void initView() {
		saveBtn = (Button) findViewById(R.id.btn_save);
		saveBtn.setOnClickListener(this);
		tv1 = (EditText) findViewById(R.id.tv1);
		tv2 = (EditText) findViewById(R.id.tv2);
		tv3 = (EditText) findViewById(R.id.tv3);
		tv4 = (EditText) findViewById(R.id.tv4);
		tv5 = (EditText) findViewById(R.id.tv5);
		tv6 = (EditText) findViewById(R.id.tv6);
		tv7 = (TextView) findViewById(R.id.tv7);
		tv7.setOnClickListener(this);
		tvkhyh = (EditText) findViewById(R.id.tvkhyh);
		tv8 = (EditText) findViewById(R.id.tv8);

		layout10 = (LinearLayout) findViewById(R.id.layout10);
		layout10.setOnClickListener(new ItemOnClickListener(TYPE_10, ""));
		linearLayout.put(TYPE_10, layout10);

		layout11 = (LinearLayout) findViewById(R.id.layout11);
		layout11.setOnClickListener(new ItemOnClickListener(TYPE_11, ""));
		linearLayout.put(TYPE_11, layout11);

		layout12 = (LinearLayout) findViewById(R.id.layout12);
		layout12.setOnClickListener(new ItemOnClickListener(TYPE_12, ""));
		linearLayout.put(TYPE_12, layout12);

		layout13 = (LinearLayout) findViewById(R.id.layout13);
		layout13.setOnClickListener(new ItemOnClickListener(TYPE_13, ""));
		linearLayout.put(TYPE_13, layout13);

		layout14 = (LinearLayout) findViewById(R.id.layout14);
		layout14.setOnClickListener(new ItemOnClickListener(TYPE_14, ""));
		linearLayout.put(TYPE_14, layout14);

		layout15 = (LinearLayout) findViewById(R.id.layout15);
		layout15.setOnClickListener(new ItemOnClickListener(TYPE_15, ""));
		linearLayout.put(TYPE_15, layout15);

		layout16 = (LinearLayout) findViewById(R.id.layout16);
		layout16.setOnClickListener(new ItemOnClickListener(TYPE_16, ""));
		linearLayout.put(TYPE_16, layout16);

	}

	class ItemOnClickListener implements View.OnClickListener {
		private int type;
		private String title;

		public ItemOnClickListener(int type, String title) {
			super();
			this.type = type;
			this.title = title;
		}

		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.putExtra("title", title);
			if (merchantEntity == null) {
				merchantEntity = new MerchantEntity();
			}
			switch (type) {
			case TYPE_7:
				intent.putExtra("value", merchantEntity.getCityId());
				intent.setClass(CreatMerchant.this, CityProvinceActivity.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_10:
			case TYPE_11:
			case TYPE_12:
			case TYPE_13:
			case TYPE_14:
			case TYPE_15:
			case TYPE_16:
				AlertDialog.Builder builder = new AlertDialog.Builder(
						CreatMerchant.this);
				final String[] items = getResources().getStringArray(
						R.array.apply_detail_upload);

				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						CreatMerchant.this.type = type;
						switch (which) {
						case 0: {
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
						case 1: {
							String state = Environment
									.getExternalStorageState();
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
								intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,
										1);
								startActivityForResult(intent,
										REQUEST_TAKE_PHOTO);
							} else {
								CommonUtil.toastShort(CreatMerchant.this,
										getString(R.string.toast_no_sdcard));
							}
							break;
						}
						}
					}
				});
				builder.show();
				break;
			default:
				break;
			}
		}
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		switch (requestCode) {
		case TYPE_7:
			Province province = (Province) data
					.getSerializableExtra(Constants.CityIntent.SELECTED_PROVINCE);
			City city = (City) data
					.getSerializableExtra(Constants.CityIntent.SELECTED_CITY);
			if (province == null || city == null) {
				merchantEntity.setCityId(0);
				tv7.setText("");
			} else {
				merchantEntity.setCityId(city.getId());
				tv7.setText(province.getName() + city.getName());
				tv7.setTextColor(getResources().getColorStateList(
						R.color.text292929));
			}

			break;
		case REQUEST_UPLOAD_IMAGE:
		case REQUEST_TAKE_PHOTO: {
			final LinearLayout layout = linearLayout
					.get(CreatMerchant.this.type);
			final Handler handler = new Handler() {
				private int type;

				@Override
				public void handleMessage(Message msg) {
					this.type = CreatMerchant.this.type;
					if (msg.what == 1) {
						// CommonUtil.toastShort(CreatMerchant.this,
						// (String) msg.obj);
						mUploadUri = (String) msg.obj;
						layout.setClickable(false);
						layout.findViewById(R.id.imgView).setVisibility(
								View.VISIBLE);
						layout.findViewById(R.id.imgView).setOnClickListener(
								new onWatchListener());
						layout.findViewById(R.id.textView).setVisibility(
								View.GONE);
						String url = (String) msg.obj;
//						layout.setClickable(true);
						switch (type) {
						case TYPE_10:
							merchantEntity.setCardIdFrontPhotoPath(url);
							break;
						case TYPE_11:
							merchantEntity.setCardIdBackPhotoPath(url);
							break;
						case TYPE_12:
							merchantEntity.setBodyPhotoPath(url);
							break;
						case TYPE_13:
							merchantEntity.setLicenseNoPicPath(url);
							break;
						case TYPE_14:
							merchantEntity.setTaxNoPicPath(url);
							break;
						case TYPE_15:
							merchantEntity.setOrgCodeNoPicPath(url);
							break;
						case TYPE_16:
							merchantEntity.setAccountPicPath(url);
							break;
						default:
							break;
						}
					} else {
						CommonUtil.toastShort(CreatMerchant.this,
								getString(R.string.toast_upload_failed));
						layout.setClickable(true);
					}

				}
			};
			if (null != layout) {
				ImageView imgView = (ImageView) layout
						.findViewById(R.id.imgView);
				TextView textView = (TextView) layout
						.findViewById(R.id.textView);
				imgView.setVisibility(View.GONE);
				textView.setVisibility(View.VISIBLE);
				textView.setText(getString(R.string.apply_uploading));
				layout.setClickable(false);
			}

			String realPath = "";
			if (requestCode == REQUEST_TAKE_PHOTO) {
				realPath = photoPath;
			} else {
				Uri uri = data.getData();
				if (uri != null) {
					realPath = Tools
							.getRealPathFromURI(CreatMerchant.this, uri);
				}
			}
			if (TextUtils.isEmpty(realPath)) {
				handler.sendEmptyMessage(0);
				return;
			}

			File file = new File(realPath);
			API.uploadImg(CreatMerchant.this, file, new HttpCallback(
					CreatMerchant.this) {

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
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv7:
			Intent intent = new Intent(CreatMerchant.this,
					CityProvinceActivity.class);
			startActivityForResult(intent, TYPE_7);
			break;
		case R.id.btn_save:
			if (canNext()) {
				merchantEntity.setTitle(tv1.getText().toString());
				merchantEntity.setLegalPersonName(tv2.getText().toString());
				merchantEntity.setLegalPersonCardId(tv3.getText().toString());
				merchantEntity.setBusinessLicenseNo(tv4.getText().toString());
				merchantEntity.setTaxRegisteredNo(tv5.getText().toString());
				merchantEntity.setOrganizationCodeNo(tv6.getText().toString());
				merchantEntity.setAccountBankName(tvkhyh.getText().toString());
				merchantEntity.setBankOpenAccount(tv8.getText().toString());
				needFresh = true;
				API.createMerchant(CreatMerchant.this, customerId,
						merchantEntity, new HttpCallback<String>(
								CreatMerchant.this) {

							@Override
							public void onSuccess(String data) {
								CommonUtil.toastShort(CreatMerchant.this,
										"创建成功！");
								Intent intent = new Intent();
								intent.putExtra("needFresh", needFresh);
								setResult(RESULT_OK, intent);
								finish();
							}

							@Override
							public TypeToken getTypeToken() {
								return null;
							}
						});
			}
			break;
		default:
			break;
		}
	}

	private boolean canNext() {
		if (StringUtil.isNull(tv1.getText().toString())) {
			Toast.makeText(this, "店铺名称（商户名）不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv2.getText().toString())) {
			Toast.makeText(this, "商户法人姓名不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv3.getText().toString())) {
			Toast.makeText(this, "商户法人身份证号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (!RegText.isIdentityCard(tv3.getText().toString())) {
			Toast.makeText(this, "商户法人身份证号格式有误", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv4.getText().toString())) {
			Toast.makeText(this, "营业执照登记号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (!RegText.isYingYeZhiZhao(tv4.getText().toString())) {
			Toast.makeText(this, "营业执照号格式有误", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv5.getText().toString())) {
			Toast.makeText(this, "税务证号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (!RegText.isShuiWuZhengHao(tv5.getText().toString())) {
			Toast.makeText(this, "税务证号格式有误", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv6.getText().toString())) {
			Toast.makeText(this, "组织机构代码证号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv7.getText().toString())) {
			Toast.makeText(this, "商户所在地市不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tvkhyh.getText().toString())) {
			Toast.makeText(this, "开户银行不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(tv8.getText().toString())) {
			Toast.makeText(this, "银行开户许可证号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getCardIdFrontPhotoPath())) {
			Toast.makeText(this, "商户法人身份证照片正面不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getCardIdBackPhotoPath())) {
			Toast.makeText(this, "商户法人身份证照片背面不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getBodyPhotoPath())) {
			Toast.makeText(this, "商户法人上半身照片不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getLicenseNoPicPath())) {
			Toast.makeText(this, "营业执照照片不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getTaxNoPicPath())) {
			Toast.makeText(this, "税务证照片不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getOrgCodeNoPicPath())) {
			Toast.makeText(this, "组织机构代码证照片不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else if (StringUtil.isNull(merchantEntity.getAccountPicPath())) {
			Toast.makeText(this, "银行开户许可证照片不能为空", Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	private class onWatchListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					CreatMerchant.this);
			final String[] items = getResources().getStringArray(
					R.array.apply_detail_view);
			builder.setItems(items, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0: {

						AlertDialog.Builder build = new AlertDialog.Builder(
								CreatMerchant.this);
						LayoutInflater factory = LayoutInflater
								.from(CreatMerchant.this);
						final View textEntryView = factory.inflate(
								R.layout.show_view, null);
						build.setView(textEntryView);
						final ImageView view = (ImageView) textEntryView
								.findViewById(R.id.imag);
						// ImageCacheUtil.IMAGE_CACHE.get(mUploadUri, view);
						ImageLoader.getInstance().displayImage(mUploadUri,
								view, options);
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
							CommonUtil.toastShort(CreatMerchant.this,
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
}
