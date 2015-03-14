package com.example.zf_zandroid.adapter;

import java.util.List;
 
import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.TestEntitiy;

 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
 
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
 
import android.widget.TextView;
 

 

public class OrderDetail_PosAdapter extends BaseAdapter{
	private Context context;
	private List<Goodlist> list;
	private LayoutInflater inflater;
	private int state;
	private ViewHolder holder = null;
	public OrderDetail_PosAdapter(Context context, List<Goodlist> list ,int state) {
		this.context = context;
		this.list = list;
		this.state = state;
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
			holder = new ViewHolder();
 			convertView = inflater.inflate(R.layout.order_detail_positem, null);
 			holder.content = (TextView) convertView.findViewById(R.id.content_pp);
 		holder.btn_ishow = (Button) convertView.findViewById(R.id.btn_ishow);		 
 		holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
 		holder.tv_x = (TextView) convertView.findViewById(R.id.tv_x);
			convertView.setTag(holder);
 		}else{
 		holder = (ViewHolder)convertView.getTag();
 	}
 
  		 	holder.content.setText(list.get(position).getGood_name());
  		 	holder.tv_price .setText("￥ "+list.get(position).getGood_actualprice());
  			holder.tv_x .setText(list.get(position).getGood_actualprice());
			holder.btn_ishow.setVisibility(state==3?View.VISIBLE:View.GONE);
		 
		
		return convertView;
	}

	public final class ViewHolder {
		public TextView content,tv_price,tv_x;
		public Button btn_ishow;
	}
}
