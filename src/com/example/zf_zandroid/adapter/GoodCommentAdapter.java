package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.epalmpay.user_phone.R;
import com.example.zf_android.entity.GoodCommentEntity;

public class GoodCommentAdapter extends BaseAdapter {
	private Context context;
	private List<GoodCommentEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public GoodCommentAdapter(Context context, List<GoodCommentEntity> list) {
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
			convertView = inflater.inflate(R.layout.pinglun_item, null);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			holder.tv_ccc = (TextView) convertView
					.findViewById(R.id.tv_ccc);
			holder.si_rt_msxf = (RatingBar) convertView.findViewById(R.id.si_rt_msxf);
			holder.tv_time = (TextView) convertView
					.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		 holder.tv_name .setText(list.get(position).getName());
		 holder.si_rt_msxf.setRating(list.get(position).getScore()/10);
		 holder.tv_ccc .setText(list.get(position).getContent());
		 holder.tv_time .setText(list.get(position).getCreated_at());
		return convertView;
	}

	public final class ViewHolder {
		public TextView tv_name, tv_ccc,tv_time;
		public RatingBar si_rt_msxf;
		

	}
}
