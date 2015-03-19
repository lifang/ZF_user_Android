package com.example.zf_zandroid.adapter;

import java.util.List;

import org.apache.http.Header;

import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.activity.ShopCar;
import com.example.zf_android.entity.MyShopCar;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_android.entity.MyShopCar.Good;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopcarAdapter extends BaseAdapter {
	private Context context;
	private List<Good> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	private TextView howMoney;
	private Activity activity;
	private int currentHowMoney;
	private CheckBox selectAll_cb;

	public ShopcarAdapter(Context context, List<Good> list) {
		this.context = context;
		this.list = list;
		activity = (Activity) context;
		currentHowMoney = 0;
		howMoney = (TextView) activity.findViewById(R.id.howMoney);
		
		selectAll_cb = (CheckBox) activity.findViewById(R.id.item_cb);
		selectAll_cb.setOnCheckedChangeListener(onCheckedChangeListener);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.sopping_caritem, null);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.wayName = (TextView) convertView.findViewById(R.id.wayName);
			holder.Model_number = (TextView) convertView
					.findViewById(R.id.Model_number);
			// holder.title = (TextView) convertView.findViewById(R.id.title);
			// holder.evevt_img = (ImageView)
			// convertView.findViewById(R.id.evevt_img);
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

			holder.reduce.setOnClickListener(onClick);
			holder.add.setOnClickListener(onClick);
			holder.retail_price = (TextView) convertView
					.findViewById(R.id.retail_price);
			holder.delete = convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.position = position;
		holder.checkBox.setTag(position);
		Good good = list.get(position);
		holder.checkBox.setChecked(good.isChecked());

		holder.title.setText(good.getTitle());
		holder.showCountText.setText("X  " + good.getQuantity());
		holder.buyCountEdit.setText("" + good.getQuantity());
		holder.buyCountEdit.getText();
		holder.retail_price.setText("$ " + good.getRetail_price());
		holder.wayName.setText(good.getName());
		holder.Model_number.setText(good.getModel_number());
		return convertView;
	}

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ViewHolder hoder = (ViewHolder) v.getTag();
			int position = hoder.position;
			Good editGood = list.get(position);
		  	int quantity = editGood.getQuantity();
		 	//int quantity = Integer.parseInt(holder.buyCountEdit.getText().toString());
			switch (v.getId()) {
			case R.id.editView:
				LinearLayout ll_select = hoder.ll_select;
				boolean isEdit = ll_select.getVisibility() == View.VISIBLE ? true
						: false;
				hoder.delete.setVisibility(isEdit ? View.INVISIBLE
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

			case R.id.delete:
				// do delete
				break;
			case R.id.reduce:

				if (quantity > 0) {
					editGood.setQuantity(--quantity);
					hoder.buyCountEdit.setText(editGood.getQuantity() + "");
					hoder.showCountText.setText("X  " + editGood.getQuantity());
					if (hoder.checkBox.isChecked()) {
						currentHowMoney -= editGood.getRetail_price();
						howMoney.setText("合计 ： " + currentHowMoney);
					}
					System.out.println("λ��---"+position+quantity);
				    changeContent(position, quantity);
				}
				break;
			case R.id.add:
				editGood.setQuantity(++quantity);
				hoder.buyCountEdit.setText(editGood.getQuantity() + "");
				hoder.showCountText.setText("X  " + editGood.getQuantity());
				if (hoder.checkBox.isChecked()) {
					currentHowMoney += editGood.getRetail_price();
					howMoney.setText("合计 ： " + currentHowMoney);
				}
				 changeContent(position, quantity);
				break;

			}

		}
	};
	private int flag=0;
	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			if (selectAll_cb == buttonView) {
				for (int index = 0; index < list.size(); index++) {
					list.get(index).setChecked(isChecked);
				}
 
				notifyDataSetChanged();
			} else {
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
				currentHowMoney += (isChecked ? good.getRetail_price()
						* good.getQuantity() : -good.getRetail_price()
						* good.getQuantity());
				howMoney.setText("合计 ：" + currentHowMoney);
				Log.e("print", "currentHowMoney:"+currentHowMoney);
			}

		}
	};
	public void changeContent(final int index,final int cont){
		 
			// TODO Auto-generated method stub
			String url =  Config.Car_edit;
			RequestParams params = new RequestParams();
			params.put("id", list.get(index).getId());
			params.put("quantity", cont);
			params.setUseJsonStreamer(true);

			MyApplication.getInstance().getClient()
					.post(url, params, new AsyncHttpResponseHandler() {

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
							// TODO Auto-generated method stub
							System.out.println("-onFailure---");
							Log.e("print", "-onFailure---" + error);
						}
					});
	 
		 
		 
	}
	public final class ViewHolder {
		private int position;
		private CheckBox checkBox;
		private TextView title;

	 
		private TextView editBtn;
		private LinearLayout ll_select;
		private TextView retail_price;
		private View delete;
		private EditText buyCountEdit;
		private TextView showCountText;
		private View reduce;
		private View add;
		public TextView Model_number;
		public TextView wayName;
	}
}
