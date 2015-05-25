package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.example.zf_android.entity.JifenEntity;

public class JifenAdapter extends BaseAdapter {
	private Context context;
	private List<JifenEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public JifenAdapter(Context context, List<JifenEntity> list) {
		this.context = context;
		this.list = list;
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
			convertView = inflater.inflate(R.layout.jifen_item, null);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			 holder.tv_ddbh = (TextView) convertView.findViewById(R.id.tv_ddbh);
			 holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			 holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_pay);
			 holder.adress_name = (TextView) convertView.findViewById(R.id.adress_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		 holder.tv_count.setText(list.get(position).getQuantity()+"  分");
		 holder.adress_name.setText(list.get(position).getTypes()==1?"获得":"支出");
		 holder.tv_ddbh.setText(list.get(position).getOrder_number() );
		 holder.tv_pay.setVisibility(View.GONE);
		 holder.tv_pay.setText(list.get(position).getActual_price());
		 holder.tv_time.setText(list.get(position).getPayedAt());
		  
 
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_count, tv_ddbh,tv_time,tv_pay,adress_name;
		public CheckBox item_cb;

	}
}
