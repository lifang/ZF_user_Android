 package com.example.zf_zandroid.adapter;

import java.util.List;
 
import com.example.zf_android.R;
import com.example.zf_android.entity.MarkEntity;
import com.example.zf_android.entity.TestEntitiy;

 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

 

public class RecordAdapter extends BaseAdapter{
	private Context context;
	private List<MarkEntity> list;
	private LayoutInflater inflater;
	private ViewHolder holder1 = null;
	public RecordAdapter(Context context, List<MarkEntity> list) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
 		if(convertView == null){
			holder1 = new ViewHolder();
 			convertView = inflater.inflate(R.layout.record_item, null);
 			holder1.content1 = (TextView) convertView.findViewById(R.id.content_pp);
 		holder1.tv_name = (TextView) convertView.findViewById(R.id.tv_name);		 
 		holder1.tv_tme = (TextView) convertView.findViewById(R.id.tv_tme);
			convertView.setTag(holder1);
 		}else{
 		holder1 = (ViewHolder)convertView.getTag();
 	}
 
  	 holder1.content1.setText(list.get(position).getMarks_content());
  	 holder1.tv_name.setText(list.get(position).getMarks_person());
  	 holder1.tv_tme.setText(list.get(position).getMarks_time());
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView content1,tv_tme,tv_name;
	}
}
