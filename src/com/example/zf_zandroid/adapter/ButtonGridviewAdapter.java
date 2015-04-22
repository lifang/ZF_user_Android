package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zf_android.R;
import com.example.zf_android.trade.entity.GriviewEntity;

public class ButtonGridviewAdapter extends BaseAdapter {
	private Context context;
	private List<GriviewEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	public int index;
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ButtonGridviewAdapter(Context context, List<GriviewEntity> list,int index) {
		this.context = context;
		this.list = list;
		this.index=index;
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
			convertView = inflater.inflate(R.layout.button_griview, null);
			holder.button_gri_tv = (TextView) convertView
					.findViewById(R.id.button_gri_tv);
		 
		 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
 
		holder.button_gri_tv.setTextColor(context.getResources().getColor(R.color.text292929));
		holder.button_gri_tv.setBackgroundResource(R.drawable.send_out_goods_shape);
		if(position==index){
			holder.button_gri_tv.setTextColor(context.getResources().getColor(R.color.bgtitle));
			holder.button_gri_tv.setBackgroundResource(R.drawable.bg_shape);
		}
		holder.button_gri_tv.setText(list.get(position).getName());
	 
	 
		return convertView;
	}

	public final class ViewHolder {
		public TextView button_gri_tv, ys,tv_price,content1,tv_td;
		public CheckBox item_cb;
		public ImageView img_type;

	}
}
