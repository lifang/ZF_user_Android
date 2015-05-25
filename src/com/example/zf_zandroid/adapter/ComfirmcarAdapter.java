package com.example.zf_zandroid.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.entity.MyShopCar.Good;

public class ComfirmcarAdapter extends BaseAdapter {
	private Context context;
	private List<Good> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	private TextView howMoney;
	private Activity activity;
	private int currentHowMoney;
	private CheckBox selectAll_cb;

	public ComfirmcarAdapter(Context context, List<Good> list) {
		this.context = context;
		this.list = list;
		activity = (Activity) context;
		currentHowMoney = 0;
		howMoney = (TextView) activity.findViewById(R.id.howMoney);
 
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
			convertView = inflater.inflate(R.layout.comfirm_caritem, null);
		 
			holder.title = (TextView) convertView.findViewById(R.id.title);			 
		 
			holder.showCountText = (TextView) convertView
					.findViewById(R.id.showCountText);
			holder.retail_price = (TextView) convertView
					.findViewById(R.id.retail_price);
			holder.wayName = (TextView) convertView
					.findViewById(R.id.wayName);
			holder.Model_number = (TextView) convertView
					.findViewById(R.id.Model_number);
			holder.content2 = (TextView) convertView
					.findViewById(R.id.content2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.position = position;
	 
		Good good = list.get(position);
	 
		holder.title.setText(good.getTitle());
		holder.showCountText.setText("X  " + good.getQuantity());
		 
		holder.retail_price.setText("￥ " + StringUtil.getMoneyString(good.getRetail_price()+good.getOpening_cost()));
		holder.wayName.setText(good.getName());
		holder.content2.setText(good.getModel_number());
		return convertView;
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
		public TextView Model_number,content2;
		public TextView wayName;
	}
}
