package com.example.zf_zandroid.adapter;

import java.util.List;

import com.examlpe.zf_android.util.ImageCacheUtil;
import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.TestEntitiy;






import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
			holder.content2 = (TextView) convertView.findViewById(R.id.content2);
			holder.evevt_img = (ImageView) convertView.findViewById(R.id.evevt_img);
			holder.contentText = (TextView) convertView.findViewById(R.id.contentText);
			holder.tv_x = (TextView) convertView.findViewById(R.id.tv_x);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		if (!StringUtil.isNull(list.get(position).getGood_logo())) {
			ImageCacheUtil.IMAGE_CACHE.get(list.get(position).getGood_logo(),
					holder.evevt_img);
		}
		holder.content.setText(list.get(position).getGood_name());
		holder.content2.setText(list.get(position).getGood_brand());
		holder.contentText.setText(list.get(position).getGood_channel());
		holder.tv_price .setText("￥ "+
				String.format("%.2f",Integer.valueOf(list.get(position).getGood_actualprice())/100f));
		holder.tv_x .setText("X    "+list.get(position).getGood_num() );
		//state==3?View.VISIBLE:View.GONE
		holder.btn_ishow.setVisibility(View.GONE);


		return convertView;
	}

	public final class ViewHolder {
		private TextView content,tv_price,tv_x,content2,contentText;
		private Button btn_ishow;
		private ImageView evevt_img;
	}
}
