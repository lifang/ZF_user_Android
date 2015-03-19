package com.example.zf_zandroid.adapter;

import java.util.List;

import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.TestEntitiy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdressAdapter extends BaseAdapter {
	private Context context;
	private List<AdressEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public AdressAdapter(Context context, List<AdressEntity> list) {
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
			convertView = inflater.inflate(R.layout.adress_item, null);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.adress_name);
			 holder.tv_tel = (TextView) convertView.findViewById(R.id.tv_tel);
			holder.item_cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			 holder.adresss = (TextView) convertView.findViewById(R.id.adresss);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 holder.adresss.setText("收件地址 ： "+list.get(position).getAddress());
		holder.tv_title.setText("收件人 ： "+list.get(position).getReceiver());
		 holder.tv_tel.setText( list.get(position).getMoblephone());
		if(MyApplication.getIsSelect()){
			 
			holder.item_cb.setVisibility(View.VISIBLE);
		}else{
			holder.item_cb.setVisibility(View.GONE);
		}
		list.get(position).setIscheck(holder.item_cb.isChecked());
		holder.item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				list.get(position).setIscheck(isChecked);
			}
		});
 
 
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_title, tv_time,tv_tel,adresss;
		public CheckBox item_cb;

	}
}
