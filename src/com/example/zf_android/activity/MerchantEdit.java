package com.example.zf_android.activity;

import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_TAKE_PHOTO;
import static com.example.zf_android.trade.Constants.ApplyIntent.REQUEST_UPLOAD_IMAGE;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.common.util.StringUtils;

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
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * 
 * 类名称：MerchantEdit 类描述： 编辑新建 创建人： ljp 创建时间：2015-3-12 下午3:28:08
 * 
 * @version
 * 
 */
public class MerchantEdit extends BaseActivity implements OnClickListener {
	private static final int TYPE_1 = 1;
	private static final int TYPE_2 = 2;
	private static final int TYPE_3 = 3;
	private static final int TYPE_4 = 4;
	private static final int TYPE_5 = 5;
	private static final int TYPE_6 = 6;
	private static final int TYPE_7 = 7;
	private static final int TYPE_8 = 8;
	private static final int TYPE_KHYH = 9;
	private static final int TYPE_10 = 10;
	private static final int TYPE_11 = 11;
	private static final int TYPE_12 = 12;
	private static final int TYPE_13 = 13;
	private static final int TYPE_14 = 14;
	private static final int TYPE_15 = 15;
	private static final int TYPE_16 = 16;
	private int id;
	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tvkhyh, tv8, next_sure;
	private LinearLayout layout10, layout11, layout12, layout13, layout14,
			layout15, layout16;
	private Button saveBtn;
	private MerchantEntity merchantEntity;
	private Map<Integer, LinearLayout> linearLayout = new HashMap<Integer, LinearLayout>();
	private String photoPath;
	private int type;
	private boolean needFresh = false;
	private String path1, path2, path3, path4, path5, path6, path7;

	DisplayImageOptions options = MyApplication.getDisplayOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id = getIntent().getIntExtra("ID", 0);

		new TitleMenuUtil(MerchantEdit.this, "编辑商户").show();

		if (id == 0) {
			return;
		}

		initView();
		new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name"))
				.show();
		getData();
	}

	private void initView() {
		// 右上角增加保存按钮
		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setText("保存");
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setOnClickListener(this);

		saveBtn = (Button) findViewById(R.id.btn_save);
		saveBtn.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setOnClickListener(new ItemOnClickListener(TYPE_1, "商铺名称（商户名）"));
		tv2 = (TextView) findViewById(R.id.tv2);
		tv2.setOnClickListener(new ItemOnClickListener(TYPE_2, "商户法人姓名"));
		tv3 = (TextView) findViewById(R.id.tv3);
		tv3.setOnClickListener(new ItemOnClickListener(TYPE_3, "商户法人身份证号"));
		tv4 = (TextView) findViewById(R.id.tv4);
		tv4.setOnClickListener(new ItemOnClickListener(TYPE_4, "营业执照登记号"));
		tv5 = (TextView) findViewById(R.id.tv5);
		tv5.setOnClickListener(new ItemOnClickListener(TYPE_5, "税务证号"));
		tv6 = (TextView) findViewById(R.id.tv6);
		tv6.setOnClickListener(new ItemOnClickListener(TYPE_6, "组织机构代码证号"));
		tv7 = (TextView) findViewById(R.id.tv7);
		tv7.setOnClickListener(new ItemOnClickListener(TYPE_7, "商户所在地"));
		tvkhyh = (TextView) findViewById(R.id.tvkhyh);
		tvkhyh.setOnClickListener(new ItemOnClickListener(TYPE_KHYH, "开户银行"));
		tv8 = (TextView) findViewById(R.id.tv8);
		tv8.setOnClickListener(new ItemOnClickListener(TYPE_8, "银行开户许可证号"));
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

	private void getData() {
		getInfo();
	}

	private void getInfo() {
		API.merchantInfo(MerchantEdit.this, id,
				new HttpCallback<MerchantEntity>(MerchantEdit.this) {
					@Override
					public void onSuccess(MerchantEntity data) {
						merchantEntity = data;
						tv1.setText(data.getTitle());
						tv2.setText(data.getLegalPersonName());
						tv3.setText(data.getLegalPersonCardId());
						tv4.setText(data.getBusinessLicenseNo());
						tv5.setText(data.getTaxRegisteredNo());
						tv6.setText(data.getOrganizationCodeNo());
						tvkhyh.setText(data.getAccountBankName());
						tv8.setText(data.getBankOpenAccount());
						if (!StringUtils.isBlank(data.getCardIdFrontPhotoPath())) {
							layout10.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout10.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path1 = data.getCardIdFrontPhotoPath();
						} else {
							layout10.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout10.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}

						if (!StringUtils.isBlank(data.getCardIdBackPhotoPath())) {
							layout11.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout11.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path2 = data.getCardIdBackPhotoPath();
						} else {
							layout11.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout11.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (!StringUtils.isBlank(data.getBodyPhotoPath())) {
							layout12.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout12.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path3 = data.getBodyPhotoPath();
						} else {
							layout12.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout12.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (!StringUtils.isBlank(data.getLicenseNoPicPath())) {
							layout13.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout13.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path4 = data.getLicenseNoPicPath();
						} else {
							layout13.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout13.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (!StringUtils.isBlank(data.getTaxNoPicPath())) {
							layout14.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout14.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path5 = data.getTaxNoPicPath();
						} else {
							layout14.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout14.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (!StringUtils.isBlank(data.getOrgCodeNoPicPath())) {
							layout15.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout15.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path6 = data.getOrgCodeNoPicPath();
						} else {
							layout15.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout15.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (!StringUtils.isBlank(data.getAccountPicPath())) {
							layout16.findViewById(R.id.textView).setVisibility(
									View.GONE);
							layout16.findViewById(R.id.imgView).setVisibility(
									View.VISIBLE);
							path7 = data.getAccountPicPath();
						} else {
							layout16.findViewById(R.id.textView).setVisibility(
									View.VISIBLE);
							layout16.findViewById(R.id.imgView).setVisibility(
									View.GONE);
						}
						if (data.getCityId() != 0) {
							List<Province> provinces = CommonUtil
									.readProvincesAndCities(getApplicationContext());
							for (Province province : provinces) {
								List<City> cities = province.getCities();
								for (City city : cities) {
									if (city.getId() == data.getCityId()) {
										tv7.setText(province.getName()
												+ city.getName());
										return;
									}
								}
							}
						}

					}

					@Override
					public TypeToken<MerchantEntity> getTypeToken() {
						return new TypeToken<MerchantEntity>() {
						};
					}
				});

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
			case TYPE_1:
				intent.putExtra("value", merchantEntity.getTitle());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_2:
				intent.putExtra("value", merchantEntity.getLegalPersonName());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_3:
				intent.putExtra("value", merchantEntity.getLegalPersonCardId());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_4:
				intent.putExtra("value", merchantEntity.getBusinessLicenseNo());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_5:
				intent.putExtra("value", merchantEntity.getTaxRegisteredNo());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_6:
				intent.putExtra("value", merchantEntity.getOrganizationCodeNo());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_7:
				intent.putExtra("value", merchantEntity.getCityId());
				intent.setClass(MerchantEdit.this, CityProvinceActivity.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_KHYH:
				intent.putExtra("value", merchantEntity.getAccountBankName());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_8:
				intent.putExtra("value", merchantEntity.getBankOpenAccount());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_10:
				if ("".equals(path1)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path1);
				}
				break;
			case TYPE_11:
				if ("".equals(path2)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path2);
				}
				break;
			case TYPE_12:
				if ("".equals(path3)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path3);
				}
				break;
			case TYPE_13:
				if ("".equals(path4)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path4);
				}
				break;
			case TYPE_14:
				if ("".equals(path5)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path5);
				}
				break;
			case TYPE_15:
				if ("".equals(path6)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path6);
				}
				break;
			case TYPE_16:

				if ("".equals(path7)) {
					show2Dialog(type);
				} else {
					show3Dialog(type, path7);
				}
				break;
			default:
				break;
			}
		}
	}

	private void show2Dialog(int type) {

		AlertDialog.Builder builder = new AlertDialog.Builder(MerchantEdit.this);
		final String[] items = getResources().getStringArray(
				R.array.apply_detail_upload);

		MerchantEdit.this.type = type;

		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {

				case 0: {

					Intent intent;
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
						CommonUtil.toastShort(MerchantEdit.this,
								getString(R.string.toast_no_sdcard));
					}
					break;
				}
				}
			}
		});

		builder.show();
	}

	private void show3Dialog(int type, final String uri) {
		AlertDialog.Builder builder = new AlertDialog.Builder(MerchantEdit.this);
		final String[] items = getResources().getStringArray(
				R.array.apply_detail_view);

		MerchantEdit.this.type = type;
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case 0: {

					AlertDialog.Builder build = new AlertDialog.Builder(
							MerchantEdit.this);
					LayoutInflater factory = LayoutInflater
							.from(MerchantEdit.this);
					final View textEntryView = factory.inflate(
							R.layout.show_view, null);
					build.setView(textEntryView);
					final ImageView view = (ImageView) textEntryView
							.findViewById(R.id.imag);
//					ImageCacheUtil.IMAGE_CACHE.get(uri, view);
					ImageLoader.getInstance().displayImage(uri, view, options);
					build.create().show();
					break;
				}

				case 1: {

					Intent intent;
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
						CommonUtil.toastShort(MerchantEdit.this,
								getString(R.string.toast_no_sdcard));
					}
					break;
				}
				}
			}
		});

		builder.show();
	}

	@Override
	protected void onActivityResult(final int requestCode, int resultCode,
			final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) {
			return;
		}
		String value = "";
		if (data != null) {
			value = data.getStringExtra("value");
		}
		switch (requestCode) {
		case TYPE_1:
			merchantEntity.setTitle(value);
			tv1.setText(value);
			break;
		case TYPE_2:
			merchantEntity.setLegalPersonName(value);
			tv2.setText(value);
			break;
		case TYPE_3:
			merchantEntity.setLegalPersonCardId(value);
			tv3.setText(value);
			break;
		case TYPE_4:
			merchantEntity.setBusinessLicenseNo(value);
			tv4.setText(value);
			break;
		case TYPE_5:
			merchantEntity.setTaxRegisteredNo(value);
			tv5.setText(value);
			break;
		case TYPE_6:
			merchantEntity.setOrganizationCodeNo(value);
			tv6.setText(value);
			break;
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
			}

			break;
		case TYPE_KHYH:
			merchantEntity.setAccountBankName(value);
			tvkhyh.setText(value);
			break;
		case TYPE_8:
			merchantEntity.setBankOpenAccount(value);
			tv8.setText(value);
			break;

		case REQUEST_UPLOAD_IMAGE:
		case REQUEST_TAKE_PHOTO: {
			final LinearLayout layout = linearLayout
					.get(MerchantEdit.this.type);
			final Handler handler = new Handler() {
				private int type;

				@Override
				public void handleMessage(Message msg) {
					this.type = MerchantEdit.this.type;
					if (msg.what == 1) {
						// CommonUtil.toastShort(MerchantEdit.this,
						// (String) msg.obj);
						layout.setClickable(false);
						layout.findViewById(R.id.imgView).setVisibility(
								View.VISIBLE);
						layout.findViewById(R.id.textView).setVisibility(
								View.GONE);
						String url = (String) msg.obj;
						layout.setClickable(true);
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
						CommonUtil.toastShort(MerchantEdit.this,
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
			new Thread() {
				@Override
				public void run() {
					String realPath = "";
					if (requestCode == REQUEST_TAKE_PHOTO) {
						realPath = photoPath;
					} else {
						Uri uri = data.getData();
						if (uri != null) {
							realPath = Tools.getRealPathFromURI(
									MerchantEdit.this, uri);
						}
					}
					if (TextUtils.isEmpty(realPath)) {
						handler.sendEmptyMessage(0);
						return;
					}
					CommonUtil.uploadFile(realPath, "img",
							new CommonUtil.OnUploadListener() {
								@Override
								public void onSuccess(String result) {
									try {
										JSONObject jo = new JSONObject(result);
										String url = jo.getString("result");
										Message msg = new Message();
										msg.what = 1;
										msg.obj = url;
										handler.sendMessage(msg);
									} catch (JSONException e) {
										handler.sendEmptyMessage(0);
									}
								}

								@Override
								public void onFailed(String message) {
									handler.sendEmptyMessage(0);
								}
							});
				}
			}.start();
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		needFresh = true;
		if (StringUtil.isNull(tv1.getText().toString())) {
			Toast.makeText(this, "商户名不能为空", Toast.LENGTH_SHORT).show();
		} else {

			API.editMerchant(MerchantEdit.this, merchantEntity,
					new HttpCallback<String>(MerchantEdit.this) {

						@Override
						public void onSuccess(String data) {
							CommonUtil.toastShort(MerchantEdit.this, "修改成功！");
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
	}

}
