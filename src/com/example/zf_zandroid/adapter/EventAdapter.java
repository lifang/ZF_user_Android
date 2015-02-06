//package com.example.zf_zandroid.adapter;
//
//import java.util.List;
// 
//import com.example.zf_android.R;
//import com.example.zf_android.entity.TestEntitiy;
//
// 
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
// 
//
//public class EventAdapter extends BaseAdapter{
//	private Context context;
//	private List<TestEntitiy> list;
//	private LayoutInflater inflater;
//	private ViewHolder holder = null;
//	public EventAdapter(Context context, List<TestEntitiy> list) {
//		this.context = context;
//		this.list = list;
//	}
//	@Override
//	public int getCount() {
//		return list.size();
//	}
//
//	@Override
//	public Object getItem(int position) {
//		return list.get(position);
//	}
//
//	@Override
//	public long getItemId(int position) {
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		inflater = LayoutInflater.from(context);
// 		if(convertView == null){
//			holder = new ViewHolder();
// 			convertView = inflater.inflate(R.layout.order_item, null);
// 			holder.content = (TextView) convertView.findViewById(R.id.content_pp);
////			holder.title = (TextView) convertView.findViewById(R.id.title);		 
////			holder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img);
//			convertView.setTag(holder);
// 		}else{
// 		holder = (ViewHolder)convertView.getTag();
// 	}
// 
//  		holder.content.setText(list.get(position).getContent());
//		 
//		
//		return convertView;
//	}
//
//	public final class ViewHolder {
//		public TextView content;
//	}
//}
