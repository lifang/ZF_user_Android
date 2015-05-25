package com.example.zf_zandroid.adapter;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.MyShopCar.Good;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class ShopcarAdapter extends BaseAdapter {
	private Context context;
	private List<Good> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	private TextView howMoney;
	private Activity activity;
	private int currentHowMoney;
	private CheckBox selectAll_cb;
	private boolean isSelectAll = false;

	public ShopcarAdapter(Context context, List<Good> list) {
		this.context = context;
		this.list = list;
		activity = (Activity) context;
		currentHowMoney = 0;
		howMoney = (TextView) activity.findViewById(R.id.howMoney);

		selectAll_cb = (CheckBox) activity.findViewById(R.id.item_cb);
		selectAll_cb.setOnClickListener(onClickListenerAll);
		//selectAll_cb.setOnCheckedChangeListener(onCheckedChangeListener);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.sopping_caritem, null);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.wayName = (TextView) convertView.findViewById(R.id.wayName);
			holder.content2 = (TextView) convertView
					.findViewById(R.id.content2);
			// holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.delete_img = (ImageView)
					convertView.findViewById(R.id.delete_img);
			holder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img);

			holder.editBtn = (TextView) convertView.findViewById(R.id.editView);
			holder.editBtn.setOnClickListener(onClick);
			holder.ll_select = (LinearLayout) convertView
					.findViewById(R.id.ll_select);
			holder.editBtn.setTag(holder);
			holder.reduce = convertView.findViewById(R.id.reduce);
			holder.buyCountEdit = (EditText) convertView
					.findViewById(R.id.buyCountEdit);
			holder.showCountText = (TextView) convertView
					.findViewById(R.id.showCountText);

			holder.add = convertView.findViewById(R.id.add);

			holder.reduce.setTag(holder);
			holder.add.setTag(holder);
			holder.delete_img.setTag(holder);
			holder.delete_img.setOnClickListener(onClick);
			holder.reduce.setOnClickListener(onClick);
			holder.add.setOnClickListener(onClick);
			holder.retail_price = (TextView) convertView
					.findViewById(R.id.retail_price);
			holder.tv_changel = (TextView) convertView.findViewById(R.id.tv_changel);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.position = position;
		holder.checkBox.setTag(position);
		Good good = list.get(position);
		holder.checkBox.setChecked(good.isChecked());
		holder.tv_changel .setText(good.getName());
		holder.title.setText(good.getTitle());
		holder.showCountText.setText("X  " + good.getQuantity());
		holder.buyCountEdit.setText("" + good.getQuantity());
		holder.buyCountEdit.getText();
		holder.retail_price.setText("¥ " + StringUtil.getMoneyString(good.getRetail_price()+good.getOpening_cost()));
		//holder.wayName.setText(good.getName());
		holder.content2.setText(good.getModel_number());

		if (!StringUtil.isNull(good.getUrl_path())) {
			ImageCacheUtil.IMAGE_CACHE.get(good.getUrl_path(),holder.evevt_img);
		}

		for (int i = 0; i < list.size(); i++) {
			Boolean aBoolean = list.get(i).isChecked();
			if (aBoolean == false) {
				selectAll_cb.setChecked(false);
			}
		}
		holder.buyCountEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}
			@Override
			public void afterTextChanged(Editable s) {
				String text = s.toString();
				int len = s.toString().length(); 
				if (len == 1 && text.equals("0")) { 
					s.clear(); 
				}
			}
		});
		return convertView;
	}

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			final ViewHolder hoder = (ViewHolder) v.getTag();
			int position = hoder.position;
			Good editGood = list.get(position);
			int quantity = editGood.getQuantity();
			//int quantity = Integer.parseInt(holder.buyCountEdit.getText().toString());
			switch (v.getId()) {
			case R.id.editView:
				LinearLayout ll_select = hoder.ll_select;
				boolean isEdit = ll_select.getVisibility() == View.VISIBLE ? true
						: false;
				hoder.delete_img.setVisibility(isEdit ? View.INVISIBLE
						: View.VISIBLE);
				hoder.ll_select.setVisibility(isEdit ? View.INVISIBLE
						: View.VISIBLE);
				hoder.retail_price.setVisibility(isEdit ? View.VISIBLE
						: View.INVISIBLE);
				hoder.editBtn.setText(isEdit ? "编辑" : "完成");
				if(isEdit){
					System.out.println(position+"----"+Integer.parseInt( hoder.buyCountEdit.getText().toString()));
					changeContent(position, Integer.parseInt( hoder.buyCountEdit.getText().toString()));
				}
				break;

			case R.id.delete_img:
				AlertDialog.Builder builder = new Builder(context);
				builder.setMessage("确认删除？");
				builder.setTitle("提示");
				builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(final DialogInterface dialog, int which) {
						final int index =hoder.position;

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("id", list.get(hoder.position).getId());
						JSONObject jsonParams = new JSONObject(params);
						HttpEntity entity;
						try {
							entity = new StringEntity(jsonParams.toString(), "UTF-8");
						} catch (UnsupportedEncodingException e) {
							return;
						}
						
						MyApplication.getInstance().getClient()
						.post(context,Config.URL_CART_DELETE, null,entity,"application/json", new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int statusCode, Header[] headers,
									byte[] responseBody) {
								String responseMsg = new String(responseBody)
								.toString();
								Log.e("print", responseMsg);

								dialog.dismiss();
								list.remove(index);
								computeMoney();
								
								hoder.ll_select.setVisibility(View.INVISIBLE);
								hoder.delete_img.setVisibility(View.INVISIBLE);
								hoder.retail_price.setVisibility(View.INVISIBLE);
								hoder.editBtn.setText("编辑");
								
								notifyDataSetChanged();
							}

							@Override
							public void onFailure(int statusCode, Header[] headers,
									byte[] responseBody, Throwable error) {
								System.out.println("-onFailure---");
								Log.e("print", "-onFailure---" + error);
							}
						});

					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();

				break;
			case R.id.reduce:

				if (quantity > 0) {
					editGood.setQuantity(--quantity);
					hoder.buyCountEdit.setText(editGood.getQuantity() + "");
					hoder.showCountText.setText("X  " + editGood.getQuantity());
					computeMoney();
					System.out.println("λ��---"+position+quantity);
					changeContent(position, quantity);
				}
				break;
			case R.id.add:
				editGood.setQuantity(++quantity);
				hoder.buyCountEdit.setText(editGood.getQuantity() + "");
				hoder.showCountText.setText("X  " + editGood.getQuantity());
				computeMoney();
				changeContent(position, quantity);
				break;

			}

		}
	};
	private OnClickListener onClickListenerAll = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isSelectAll == false) {
				isSelectAll = true;
				selectAll_cb.setChecked(true);
			}else {
				isSelectAll = false;
				selectAll_cb.setChecked(false);
			}
			for (int index = 0; index < list.size(); index++) {
				list.get(index).setChecked(isSelectAll);
			}
			computeMoney();
			notifyDataSetChanged();
		}
	};
	private int flag=0;
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if(isChecked){
				flag++;
			}else{
				flag--;
			}
			if(flag==0){
				selectAll_cb.setChecked(false);
			}else if(flag==list.size()){
				selectAll_cb.setChecked(true);
			}
			int position = (Integer) buttonView.getTag();
			Good good = list.get(position);
			good.setChecked(isChecked);
			computeMoney();
			int mflag = 0;
			for (int i = 0; i < list.size(); i++) {
				Boolean aBoolean = list.get(i).isChecked();
				if (aBoolean == false) {
					selectAll_cb.setChecked(false);
				}else {
					mflag++;
				}
			}
			if (mflag == list.size()) {
				selectAll_cb.setChecked(true);
			}
			Log.e("print", "currentHowMoney:"+currentHowMoney);
			//	}

		}
	};
	public void changeContent(final int index,final int cont){

		String url =  Config.Car_edit;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", list.get(index).getId());
		params.put("quantity", cont);
		JSONObject jsonParams = new JSONObject(params);
		HttpEntity entity;
		try {
			entity = new StringEntity(jsonParams.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return;
		}
		MyApplication.getInstance().getClient()
		.post(context,url, null,entity,"application/json", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				String responseMsg = new String(responseBody)
				.toString();
				Log.e("print", responseMsg);

				list.get(index).setQuantity(cont);
				notifyDataSetChanged();

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				System.out.println("-onFailure---");
				Log.e("print", "-onFailure---" + error);
			}
		});



	}

	public final class ViewHolder {
		private int position;
		private CheckBox checkBox;
		private TextView title;
		private ImageView delete_img,evevt_img;

		private TextView editBtn,tv_changel;
		private LinearLayout ll_select;
		private TextView retail_price;
		private View delete;
		private EditText buyCountEdit;
		private TextView showCountText;
		private View reduce;
		private View add;
		public TextView content2;
		public TextView wayName;
	}

	private void computeMoney(){
		currentHowMoney = 0;
		for(Good good: list){
			if(good.isChecked()){
				currentHowMoney += (good.getRetail_price()+good.getOpening_cost())*good.getQuantity();
			}
		}
		howMoney.setText("合计 ： ￥" + StringUtil.getMoneyString(currentHowMoney) );
	}

}
