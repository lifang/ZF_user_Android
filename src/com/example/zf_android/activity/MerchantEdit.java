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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.trinea.android.common.util.StringUtils;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
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
/**
 * 
*    
* 类名称：MerchantEdit   
* 类描述：   编辑新建
* 创建人： ljp 
* 创建时间：2015-3-12 下午3:28:08   
* @version    
*
 */
public class MerchantEdit extends BaseActivity implements OnClickListener{
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
	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tvkhyh,tv8;
	private LinearLayout layout10,layout11,layout12,layout13,layout14,layout15,layout16;
	private Button saveBtn;
	private MerchantEntity merchantEntity;
	private Map<Integer, LinearLayout> linearLayout = new HashMap<Integer, LinearLayout>();
	private String photoPath;
	private int type;
	private boolean needFresh = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_info);
		id=getIntent().getIntExtra("ID", 0);
 
		new TitleMenuUtil(MerchantEdit.this, "编辑商户").show();
 
		if(id==0){
			return;
		}
		
		initView();
		new TitleMenuUtil(MerchantEdit.this, getIntent().getStringExtra("name")).show();
		getData();
	}
	private void initView() {
		saveBtn = (Button)findViewById(R.id.btn_save);
		saveBtn.setOnClickListener(this);
		tv1=(TextView) findViewById(R.id.tv1);
		tv1.setOnClickListener(new ItemOnClickListener(TYPE_1, "商铺名称（商户名）"));
		tv2=(TextView) findViewById(R.id.tv2);
		tv2.setOnClickListener(new ItemOnClickListener(TYPE_2, "商户法人姓名"));
		tv3=(TextView) findViewById(R.id.tv3);
		tv3.setOnClickListener(new ItemOnClickListener(TYPE_3, "商户法人身份证号"));
		tv4=(TextView) findViewById(R.id.tv4);
		tv4.setOnClickListener(new ItemOnClickListener(TYPE_4, "营业执照登记号"));
		tv5=(TextView) findViewById(R.id.tv5);
		tv5.setOnClickListener(new ItemOnClickListener(TYPE_5, "税务证号"));
		tv6=(TextView) findViewById(R.id.tv6);
		tv6.setOnClickListener(new ItemOnClickListener(TYPE_6, "组织机构代码证号"));
		tv7=(TextView) findViewById(R.id.tv7);
		tv7.setOnClickListener(new ItemOnClickListener(TYPE_7, "商户所在地"));
		tvkhyh=(TextView) findViewById(R.id.tvkhyh);
		tvkhyh.setOnClickListener(new ItemOnClickListener(TYPE_KHYH, "开户银行"));
		tv8=(TextView) findViewById(R.id.tv8);
		tv8.setOnClickListener(new ItemOnClickListener(TYPE_8, "银行开户许可证号"));
		layout10=(LinearLayout) findViewById(R.id.layout10);
		layout10.setOnClickListener(new ItemOnClickListener(TYPE_10, ""));
		linearLayout.put(TYPE_10, layout10);
		
		layout11=(LinearLayout) findViewById(R.id.layout11);
		layout11.setOnClickListener(new ItemOnClickListener(TYPE_11, ""));
		linearLayout.put(TYPE_11, layout11);

		layout12=(LinearLayout) findViewById(R.id.layout12);
		layout12.setOnClickListener(new ItemOnClickListener(TYPE_12, ""));
		linearLayout.put(TYPE_12, layout12);

		layout13=(LinearLayout) findViewById(R.id.layout13);
		layout13.setOnClickListener(new ItemOnClickListener(TYPE_13, ""));
		linearLayout.put(TYPE_13, layout13);

		layout14=(LinearLayout) findViewById(R.id.layout14);
		layout14.setOnClickListener(new ItemOnClickListener(TYPE_14, ""));
		linearLayout.put(TYPE_14, layout14);

		layout15=(LinearLayout) findViewById(R.id.layout15);
		layout15.setOnClickListener(new ItemOnClickListener(TYPE_15, ""));
		linearLayout.put(TYPE_15, layout15);

		layout16=(LinearLayout) findViewById(R.id.layout16);
		layout16.setOnClickListener(new ItemOnClickListener(TYPE_16, ""));
		linearLayout.put(TYPE_16, layout16);

	}
	private void getData() {
		getInfo();
	}
	
	private void getInfo() {
		 API.merchantInfo(MerchantEdit.this,id,
	                new HttpCallback<MerchantEntity> (MerchantEdit.this) {
						@Override
						public void onSuccess(MerchantEntity data) {
							merchantEntity = data;
							tv1.setText(data.getTitle());
							tv2.setText(data.getLegal_person_name());
							tv3.setText(data.getLegal_person_card_id());
							tv4.setText(data.getBusiness_license_no());
							tv5.setText(data.getTax_registered_no());
							tv6.setText(data.getOrganization_code_no());
							tvkhyh.setText(data.getAccount_bank_name());
							tv8.setText(data.getBank_open_account());
							if(!StringUtils.isBlank(data.getCard_id_front_photo_path())){
								layout10.findViewById(R.id.textView).setVisibility(View.GONE);
								layout10.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout10.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout10.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							
							if(!StringUtils.isBlank(data.getCard_id_back_photo_path())){
								layout11.findViewById(R.id.textView).setVisibility(View.GONE);
								layout11.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout11.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout11.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(!StringUtils.isBlank(data.getBody_photo_path())){
								layout12.findViewById(R.id.textView).setVisibility(View.GONE);
								layout12.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout12.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout12.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(!StringUtils.isBlank(data.getLicense_no_pic_path())){
								layout13.findViewById(R.id.textView).setVisibility(View.GONE);
								layout13.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout13.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout13.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(!StringUtils.isBlank(data.getTax_no_pic_path())){
								layout14.findViewById(R.id.textView).setVisibility(View.GONE);
								layout14.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout14.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout14.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(!StringUtils.isBlank(data.getOrg_code_no_pic_path())){
								layout15.findViewById(R.id.textView).setVisibility(View.GONE);
								layout15.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout15.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout15.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(!StringUtils.isBlank(data.getAccount_pic_path())){
								layout16.findViewById(R.id.textView).setVisibility(View.GONE);
								layout16.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
							}else {
								layout16.findViewById(R.id.textView).setVisibility(View.VISIBLE);
								layout16.findViewById(R.id.imgView).setVisibility(View.GONE);
							}
							if(data.getCity_id() != 0){
								List<Province> provinces = CommonUtil.readProvincesAndCities(getApplicationContext());
								for (Province province : provinces) {
									List<City> cities = province.getCities();
									for (City city : cities) {
										if(city.getId() == data.getCity_id()){
											tv7.setText(province.getName()+city.getName());
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
	class ItemOnClickListener implements View.OnClickListener{
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
			if(merchantEntity == null){
				merchantEntity = new MerchantEntity();
			}
			switch (type) {
			case TYPE_1:
				intent.putExtra("value", merchantEntity.getTitle());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_2:
				intent.putExtra("value", merchantEntity.getLegal_person_name());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_3:
				intent.putExtra("value", merchantEntity.getLegal_person_card_id());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_4:
				intent.putExtra("value", merchantEntity.getBusiness_license_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_5:
				intent.putExtra("value", merchantEntity.getTax_registered_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_6:
				intent.putExtra("value", merchantEntity.getOrganization_code_no());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_7:
				intent.putExtra("value", merchantEntity.getCity_id());
				intent.setClass(MerchantEdit.this, CityProvinceActivity.class);
				startActivityForResult(intent, type);
				break;
			case TYPE_KHYH:
				intent.putExtra("value", merchantEntity.getAccount_bank_name());
				intent.setClass(MerchantEdit.this, MerchantItemEdit.class);
				startActivityForResult(intent, type);

				break;
			case TYPE_8:
				intent.putExtra("value", merchantEntity.getBank_open_account());
				startActivityForResult(intent, type);
				break;
			case TYPE_10:
			case TYPE_11:
			case TYPE_12:
			case TYPE_13:
			case TYPE_14:
			case TYPE_15:
			case TYPE_16:
				AlertDialog.Builder builder = new AlertDialog.Builder(MerchantEdit.this);
				final String[] items = getResources().getStringArray(R.array.apply_detail_upload);
				
				builder.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MerchantEdit.this.type = type;
						switch (which) {
							case 0: {
								Intent intent = new Intent();
								intent.setType("image/*");
								intent.setAction(Intent.ACTION_GET_CONTENT);
								startActivityForResult(intent, REQUEST_UPLOAD_IMAGE);
								break;
							}
							case 1: {
								String state = Environment.getExternalStorageState();
								if (state.equals(Environment.MEDIA_MOUNTED)) {
									Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
									File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
									if (!outDir.exists()) {
										outDir.mkdirs();
									}
									File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
									photoPath = outFile.getAbsolutePath();
									intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
									intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
									startActivityForResult(intent, REQUEST_TAKE_PHOTO);
								} else {
									CommonUtil.toastShort(MerchantEdit.this, getString(R.string.toast_no_sdcard));
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
	protected void onActivityResult(final int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode!=RESULT_OK) {
			return;
		}
		String value = "";
		if(data != null){
			value = data.getStringExtra("value");
		}
		switch (requestCode) {
		case TYPE_1:
			merchantEntity.setTitle(value);
			tv1.setText(value);
			break;
		case TYPE_2:
			merchantEntity.setLegal_person_name(value);
			tv2.setText(value);
			break;
		case TYPE_3:
			merchantEntity.setLegal_person_card_id(value);
			tv3.setText(value);
			break;
		case TYPE_4:
			merchantEntity.setBusiness_license_no(value);
			tv4.setText(value);
			break;
		case TYPE_5:
			merchantEntity.setTax_registered_no(value);
			tv5.setText(value);
			break;
		case TYPE_6:
			merchantEntity.setOrganization_code_no(value);
			tv6.setText(value);
			break;
		case TYPE_7:
			Province province =  (Province)data.getSerializableExtra(Constants.CityIntent.SELECTED_PROVINCE);
			City city = (City)data.getSerializableExtra(Constants.CityIntent.SELECTED_CITY);
			if(province == null || city == null){
				merchantEntity.setCity_id(0);
				tv7.setText("");
			} else{
				merchantEntity.setCity_id(city.getId());
				tv7.setText(province.getName()+city.getName());
			}
	
			break;
		case TYPE_KHYH:
			merchantEntity.setAccount_bank_name(value);
			tvkhyh.setText(value);
			break;
		case TYPE_8:
			merchantEntity.setBank_open_account(value);
			tv8.setText(value);
			break;
			
		case REQUEST_UPLOAD_IMAGE:
		case REQUEST_TAKE_PHOTO: {
			final LinearLayout layout = linearLayout.get(MerchantEdit.this.type);
			final Handler handler = new Handler() {
				private int type;
				@Override
				public void handleMessage(Message msg) {
					this.type = MerchantEdit.this.type;
					if (msg.what == 1) {
						CommonUtil.toastShort(MerchantEdit.this, (String) msg.obj);
						layout.setClickable(false);
						layout.findViewById(R.id.imgView).setVisibility(View.VISIBLE);
						layout.findViewById(R.id.textView).setVisibility(View.GONE);
						String url = (String) msg.obj;
						layout.setClickable(true);
						switch (type) {
						case TYPE_10:
							merchantEntity.setCard_id_front_photo_path(url);
							break;
						case TYPE_11:
							merchantEntity.setCard_id_back_photo_path(url);
							break;
						case TYPE_12:
							merchantEntity.setBody_photo_path(url);
							break;
						case TYPE_13:
							merchantEntity.setLicense_no_pic_path(url);
							break;
						case TYPE_14:
							merchantEntity.setTax_no_pic_path(url);
							break;
						case TYPE_15:
							merchantEntity.setOrg_code_no_pic_path(url);
							break;
						case TYPE_16:
							merchantEntity.setAccount_pic_path(url);
							break;
						default:
							break;
						}
					} else {
						CommonUtil.toastShort(MerchantEdit.this, getString(R.string.toast_upload_failed));
						layout.setClickable(true);
					}

				}
			};
			if (null != layout) {
				ImageView imgView = (ImageView)layout.findViewById(R.id.imgView);
				TextView textView = (TextView)layout.findViewById(R.id.textView);
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
							realPath = Tools.getRealPathFromURI(MerchantEdit.this, uri);
						}
					}
					if (TextUtils.isEmpty(realPath)) {
						handler.sendEmptyMessage(0);
						return;
					}
					CommonUtil.uploadFile(realPath, "img", new CommonUtil.OnUploadListener() {
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
		API.editMerchant(MerchantEdit.this,merchantEntity,
                new HttpCallback<String> (MerchantEdit.this) {

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
						return  null;
					}
                });
		
	}
	
}
