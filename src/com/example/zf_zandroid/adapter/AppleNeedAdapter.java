package com.example.zf_zandroid.adapter;

import java.util.List;

import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.AdressEntity;
import com.example.zf_android.entity.ApplyneedEntity;
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

public class AppleNeedAdapter extends BaseAdapter {
	private Context context;
	private List<ApplyneedEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public AppleNeedAdapter(Context context, List<ApplyneedEntity> list) {
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
			convertView = inflater.inflate(R.layout.apply_need_item, null);
			holder.adress_name = (TextView) convertView
					.findViewById(R.id.adress_name);
			 
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.adress_name.setText(position+1+"."+list.get(position).getName()+"("+list.get(position).getIntroduction()+")");
 
	 
 
		return convertView;
	}

	public final class ViewHolder {
		public TextView adress_name;

	}
}
