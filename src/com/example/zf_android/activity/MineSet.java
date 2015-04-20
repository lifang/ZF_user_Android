package com.example.zf_android.activity;

import java.io.File;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.examlpe.zf_android.util.DataCleanManager;
import com.examlpe.zf_android.util.DownloadUtils;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.R;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.NetworkUtil;
import com.google.gson.reflect.TypeToken;

public class MineSet extends BaseActivity implements OnClickListener{
	private static final String LOG_TAG = MineSet.class.getName();

	private ImageView img_on_off;
	private Boolean isOpen_mineset;
	private SharedPreferences mySharedPreferences;
	private Editor editor;
	private LinearLayout ll_new,ll_clean;
	private TextView tv_type, tv_clean;
	private Dialog versionCheckingDialog;

	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mine_set);
		new TitleMenuUtil(MineSet.this, "设置").show();
		mySharedPreferences = getSharedPreferences(Config.SHARED, MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		isOpen_mineset=mySharedPreferences.getBoolean("isOpen_mineset", true);
		img_on_off=(ImageView) findViewById(R.id.img_on_off);
		if(!isOpen_mineset){
			img_on_off.setBackgroundResource(R.drawable.pos_off);
		}
		img_on_off.setOnClickListener(this);
		ll_new=(LinearLayout) findViewById(R.id.ll_new);
		ll_new.setOnClickListener(this);
		ll_clean=(LinearLayout) findViewById(R.id.ll_clean);
		ll_clean.setOnClickListener(this);
		tv_clean=(TextView) findViewById(R.id.tv_clean);
		tv_type = (TextView) findViewById(R.id.tv_type);
		tv_type.setText(Tools.getVerName(this));
		String dataSize = "";
		try {
			dataSize = DataCleanManager.getTotalCacheSize(this);
		} catch (Exception e) {
			Log.e(LOG_TAG, "", e);
		}
		tv_clean.setText(dataSize);
		handler = new VersionHandler(this);

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_new:
			if (NetworkUtil.isNetworkAvailable(this)) {
				// 检查版本dialog
				showVersionCheckingDialog();
			}
			API.checkVersion(this, new HttpCallback<Map<String, Object>> (MineSet.this) {
				@Override
				public void onSuccess(Map<String, Object> data) {
					String version = (String)data.get("lastVersion");
					String url = (String)data.get("downloadPath");
					Integer nowVersion = Tools.getVerCode(MineSet.this);
					Message msg = null;
					if(Double.parseDouble(version) > nowVersion){
						msg = handler.obtainMessage(VersionHandler.HAS_NEW_VERSION);
						Bundle bundle = new Bundle();
						bundle.putString("url", url);
						msg.setData(bundle);
					} else {
						msg = handler.obtainMessage(VersionHandler.NO_NEW_VERSION);
					}
					if (versionCheckingDialog != null) {
						versionCheckingDialog.dismiss();
					}
					handler.sendMessage(msg);
				}

				@Override
				public TypeToken getTypeToken() {
					return  new TypeToken<Map<String, Object>>() {
					};
				}
			} );

			break;
		case R.id.ll_clean:
			DataCleanManager.clearAllCache(this);
			String dataSize = "";
			try {
				dataSize = DataCleanManager.getTotalCacheSize(this);
			} catch (Exception e) {
				Log.e(LOG_TAG, "", e);
			}
			tv_clean.setText(dataSize);
			break;
		case R.id.img_on_off:
			if(isOpen_mineset){
				isOpen_mineset=false;
				img_on_off.setBackgroundResource(R.drawable.pos_off);
				editor.putBoolean("isOpen_mineset",false);
				editor.commit();

			}else{
				isOpen_mineset=true;
				img_on_off.setBackgroundResource(R.drawable.pos_on);		 
				editor.putBoolean("isOpen_mineset",true);
				editor.commit();
			}

			break;

		default:
			break;
		}
	}

	void showVersionCheckingDialog() {
		if (versionCheckingDialog == null) {
			versionCheckingDialog = new Dialog(this, R.style.mydialog);
			versionCheckingDialog
			.setContentView(R.layout.dialog_version_checking);
			TextView textView = (TextView) versionCheckingDialog
					.findViewById(R.id.version_checking_textView);
			textView.setText(R.string.version_check_tip);
		}
		versionCheckingDialog.show();
	}

	private void upgrading(String apkUrl) {
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setCancelable(false);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage(getResources().getString(R.string.updata_check));
		fileDownLoad(pd, apkUrl);
		pd.show();
	}

	private void fileDownLoad(ProgressDialog dialog, final String url) {
		try {
			final DownloadListener listener = new DownloadListener(this, dialog);
			final File file = new File(getExternalFilesDir(null), "last.apk");
			new Thread() {
				@Override
				public void run() {
					super.run();
					try {
						DownloadUtils.download(url,
								file, false, listener);
					} catch (Exception e) {
						Log.e(LOG_TAG, "", e);
					}
				}
			}.start();
		} catch (Exception e) {
			dialog.dismiss();
		}
	}

	private static class DownloadListener implements
	DownloadUtils.DownloadListener {
		private MineSet sa;
		private ProgressDialog pd;

		public DownloadListener(MineSet context, ProgressDialog pd) {
			super();
			this.sa = context;
			pd.setMax(100);
			pd.setProgress(0);
			this.pd = pd;
		}

		@Override
		public void downloading(int progress) {
			pd.setProgress(progress);
		}

		@Override
		public void downloaded(File dest) {
			pd.dismiss();
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(dest),
					"application/vnd.android.package-archive");
			sa.startActivity(intent);
		}

		@Override
		public void exception(Exception e) {
		}
	}

	private static class VersionHandler extends Handler {
		public static final int NO_NEW_VERSION = 0; // 无更新
		public static final int HAS_NEW_VERSION = 1; // 更新
		private Context context;

		public VersionHandler(Context context) {
			super();
			this.context = context;
		}

		public void handleMessage(Message msg) {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			switch (msg.what) {
			case NO_NEW_VERSION:
				builder.setCancelable(false);
				builder.setTitle(context.getResources().getString(
						R.string.check_version));
				builder.setMessage(context.getResources().getString(
						R.string.no_check_version));
				builder.setPositiveButton(R.string.update_i_know,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
				break;
			case HAS_NEW_VERSION:
				final String url = msg.getData().getString("url");
				builder.setCancelable(true);
				String string = context.getResources().getString(
						R.string.version_check_true);
				if (msg.getData().containsKey("updateContent")) {
					string = msg.getData().getString("updateContent");
				}
				builder.setTitle(context.getResources().getString(
						R.string.version_update_title));
				builder.setMessage(string);
				builder.setPositiveButton(R.string.update_imde,
						new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						((MineSet)context).upgrading(url);
						dialog.dismiss();
					}
				});
				builder.create().show();
				break;
			}
		}
	}

}
