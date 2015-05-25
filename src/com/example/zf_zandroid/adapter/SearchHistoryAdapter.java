package com.example.zf_zandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
 
public class SearchHistoryAdapter extends BaseAdapter {
	String[] strarr;
	private Context mContext;
	
	public SearchHistoryAdapter(Context mContext, String[] strarr) {
		this.mContext = mContext;
		this.strarr = strarr;
	}

 
	public void updateListView(String[] strarr) {
		this.strarr = strarr;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.strarr.length;
	}

	public Object getItem(int position) {
		return strarr[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.search_historystr_list_item,
					null);
			viewHolder.textview = (TextView) view
					.findViewById(R.id.list_item_textview);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.textview.setText(strarr[position]);

		return view;

	}

	final static class ViewHolder {
		TextView textview; //
	}

}
