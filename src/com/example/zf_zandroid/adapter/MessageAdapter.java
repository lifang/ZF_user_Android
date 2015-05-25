package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.MessageEntity;

public class MessageAdapter extends BaseAdapter {
	private Context context;
	private List<MessageEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public MessageAdapter(Context context, List<MessageEntity> list) {
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
			convertView = inflater.inflate(R.layout.message_item, null);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.item_cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_title.setText(list.get(position).getTitle());
		holder.tv_time.setText(list.get(position).getCreate_at());
		if (MyApplication.getIsSelect()) {

			holder.item_cb.setVisibility(View.VISIBLE);
		} else {
			holder.item_cb.setVisibility(View.GONE);
		}

		if (list.get(position).getStatus() == null) {

		} else {
			if (list.get(position).getStatus()) {
				holder.tv_title.setTextColor(context.getResources().getColor(R.color.text6c6c6c6));
				holder.tv_time.setTextColor(context.getResources().getColor(R.color.text6c6c6c6));
			} else {
				holder.tv_title.setTextColor(context.getResources().getColor(R.color.text292929));
				holder.tv_time.setTextColor(context.getResources().getColor(R.color.text292929));
			}
		}

		//list.get(position).setIscheck(holder.item_cb.isChecked());
		holder.item_cb
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						list.get(position).setIscheck(isChecked);
					}
				});
		 
		if(list.get(position).getIscheck()==null){
			holder.item_cb.setChecked(false);
			list.get(position).setIscheck(false);
		}else{
			holder.item_cb.setChecked(list.get(position).getIscheck());
		}
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_title, tv_time;
		public CheckBox item_cb;

	}
}
