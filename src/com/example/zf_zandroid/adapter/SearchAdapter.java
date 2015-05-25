package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
 
public class SearchAdapter extends BaseAdapter {

	private Context context;
	private List<String> list;
	private LayoutInflater inflater;
	 TextView buttonText = null;
	private ViewHolder holder = null;
	private int selectedPosition = -1;//  

	public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
	public SearchAdapter(Context context, List<String> list,int selectedPosition) {
		this.context = context;
		this.list = list;
	}
	public SearchAdapter(Context context, List<String> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

 

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		inflater = LayoutInflater.from(context);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.search_item, null);
			holder.search_item_tv = (TextView) convertView
					.findViewById(R.id.search_item_tv);
//			holder.search_rl_tiem = (RelativeLayout) convertView
//					.findViewById(R.id.search_rl_tiem);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
//		if(selectedPosition== position){
//			holder.search_item_tv.setTextColor(context.getResources().getColorStateList(R.color.color_orange_white));
//		}else{
//			holder.search_item_tv.setTextColor(context.getResources().getColorStateList(R.color.text_black_white));
//		}
		
		holder.search_item_tv.setText(list.get(position).toString());

		return convertView;
	}

	public final class ViewHolder {
		public TextView search_item_tv;
		public RelativeLayout search_rl_tiem;
	}
}
