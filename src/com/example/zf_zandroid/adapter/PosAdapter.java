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

public class PosAdapter extends BaseAdapter {
	private Context context;
	private List<PosEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public PosAdapter(Context context, List<PosEntity> list) {
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
			convertView = inflater.inflate(R.layout.pos_item, null);
			holder.title = (TextView) convertView
					.findViewById(R.id.title);
			holder.img_type = (ImageView) convertView.findViewById(R.id.img_type);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			holder.ys = (TextView) convertView
			.findViewById(R.id.ys);
			holder.content1 = (TextView) convertView
					.findViewById(R.id.content1);
			holder.tv_td = (TextView) convertView
					.findViewById(R.id.tv_td);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//volume_number":123,"id":2,"good_brand":"Ʒ��1","total_score":1,
		//"retail_price":72464,"pay_channe":"ͨ��2",
		//"Title":"̩ɽPos�콢��2","Model_number":"�ͺ�10"}
		holder.title.setText(list.get(position).getTitle());
		holder.tv_price.setText("￥"+list.get(position).getRetail_price()/100+"");
		holder.content1.setText(list.get(position).getModel_number());
		holder.tv_td.setText(list.get(position).getPay_channe());
		holder.ys.setText("已售"+list.get(position).getVolume_number());
		
		if(list.get(position).getHas_lease()==null||list.get(position).getHas_lease()){
			holder.img_type.setVisibility(View.VISIBLE);
		}else{
			holder.img_type.setVisibility(View.INVISIBLE);
		}
	 
		
	 
		return convertView;
	}

	public final class ViewHolder {
		public TextView title, ys,tv_price,content1,tv_td;
		public CheckBox item_cb;
		public ImageView img_type;

	}
}
