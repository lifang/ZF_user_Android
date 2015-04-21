package com.examlpe.zf_android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zf_android.R;

public class DialogUtil {
	private Context context;
	private String text;
	private String telephone;
	private TextView dialog_tj_tv;
	private int aa;

	public interface CallBackChange {
		void change();
	}

	public DialogUtil(Context context, String text) {
		super();
		this.context = context;
		this.text = text;
	}

	public DialogUtil(Context context) {
		super();
		this.context = context;

	}

	public Dialog getCheck(final CallBackChange call) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog, null);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView tv_yes = (TextView) v.findViewById(R.id.tv_yes);
		LinearLayout yes = (LinearLayout) v.findViewById(R.id.login_linear_yes);
		LinearLayout no = (LinearLayout) v.findViewById(R.id.login_linear_no);
		final ImageView uv = (ImageView) v.findViewById(R.id.is_show);
		if (text.endsWith("保存到本地")) {
			title.setVisibility(View.INVISIBLE);
			uv.setVisibility(View.VISIBLE);
			tv_yes.setText(text);
		} else {
			title.setText(text);
		}

		final Dialog dialog = new Dialog(context, R.style.TanChuangDialog);
		dialog.setContentView(v);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.CENTER);
		window.setLayout(ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT);// ���Ͽ��Ե��dialog��Χ������ʧ
		// window.setWindowAnimations(R.style.TanchuDialogAnim);
		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				dialog.dismiss();
				new Thread() {
					public void run() {
						
						InputStream is = context.getResources()
								.openRawResource(R.drawable.imagecode);		
						BitmapFactory.decodeStream(is);
						FileOutputStream fos = null;
						BufferedOutputStream bos = null;
						File imageFile = null;
						try {
							imageFile = new File(
									new StringBuilder()
									.append(Environment.getExternalStorageDirectory().getAbsolutePath())
									.append(File.separator).append("besttone").append(File.separator)
									.append("imageCache").toString()+ "/code.png");
							if (!imageFile.exists()) {
								fos = new FileOutputStream(imageFile);
								bos = new BufferedOutputStream(fos);
								byte[] b = new byte[1024];
								int length;
								while ((length = is.read(b)) != -1) {
									bos.write(b, 0, length);
									bos.flush();
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							try {
								if (is != null) {
									is.close();
								}
								if (bos != null) {
									bos.close();
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						;
					};
				}.start();
				call.change();
			}
		});
		no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		return dialog;
	}
}
