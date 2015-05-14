package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.activity.GoodDeatail;
import com.example.zf_android.entity.GoodImgUrlEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class GoodDesAdapter extends BaseAdapter {
	private Context context;
	private List<GoodImgUrlEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;
	DisplayImageOptions options = MyApplication.getDisplayOption();
	public GoodDesAdapter(Context context, List<GoodImgUrlEntity> list) {
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
			convertView = inflater.inflate(R.layout.item_goodimgurls, null);
			holder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
		lp.width = GoodDeatail.screenWidth;
		lp.height = GoodDeatail.screenWidth;
		holder.image.setLayoutParams(lp);
		holder.image.setMaxWidth(GoodDeatail.screenWidth);
		holder.image.setMaxHeight(GoodDeatail.screenWidth);
		
		ImageLoader.getInstance().displayImage(list.get(position).getUrlPath(), holder.image, options);
		return convertView;
	}

	public final class ViewHolder {
		public ImageView image;

	}
}
