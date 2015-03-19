package com.example.zf_zandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zf_android.R;
import com.example.zf_android.entity.PosEntity;

import java.util.List;

public class GridviewAdapter extends BaseAdapter {
	private Context context;
	private List<PosEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public GridviewAdapter(Context context, List<PosEntity> list) {
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
			convertView = inflater.inflate(R.layout.griview_item, null);
			holder.title = (TextView) convertView
					.findViewById(R.id.title);
		 
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.ys = (TextView) convertView
			.findViewById(R.id.ys); 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	 
		holder.title.setText(list.get(position).getTitle());
		holder.tv_price.setText("￥"+list.get(position).getRetail_price()/100+"");
	 
	 
		holder.ys.setText("已售"+list.get(position).getVolume_number());
		
	 
		
	 
		return convertView;
	}

	public final class ViewHolder {
		public TextView title, ys,tv_price,content1,tv_td;
		public CheckBox item_cb;
		public ImageView img_type;

	}
}
