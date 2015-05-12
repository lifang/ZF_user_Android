package com.example.zf_zandroid.adapter;

import java.util.List;

import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.ChanelEntitiy;
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

public class HuilvAdapter1 extends BaseAdapter {
	private Context context;
	private List<ChanelEntitiy> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public HuilvAdapter1(Context context, List<ChanelEntitiy> list) {
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
			convertView = inflater.inflate(R.layout.gooddetail_item, null);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			holder.tv_dec = (TextView) convertView
					.findViewById(R.id.tv_dec);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(list.get(position).getName()==null){
			holder.tv_name.setText(list.get(position).getTrade_value());
		}else{
			holder.tv_name.setText(list.get(position).getName());
		}
		 
		System.out.println("list.get(position).getName()---"+list.get(position).getName());
		if(list.get(position).getService_rate()==0){
			holder.tv_price.setText(String.format("%.1f", list.get(position).getTerminal_rate()/10f)+"%");
		}else{
			holder.tv_price.setText(String.format("%.1f", list.get(position).getService_rate()/10f)+"%");
		}
		holder.tv_price.setText(String.format("%.1f", list.get(position).getService_rate()/10f)+"%");
		holder.tv_dec.setText(list.get(position).getDescription());
	  
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_name, tv_price,tv_dec;
		 

	}
}
