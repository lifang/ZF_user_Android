package com.example.zf_zandroid.adapter;

import java.util.List;

import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;
import com.example.zf_android.entity.TestEntitiy;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;

import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {
	private Context context;
	private List<Goodlist> list;
	private LayoutInflater inflater;
	private int state;
	private ViewHolder holder = null;

	public CommentAdapter(Context context, List<Goodlist> list) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		inflater = LayoutInflater.from(context);
		System.out.println("--`getView`--" + position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.content_pp);

			holder.content2 = (TextView) convertView
					.findViewById(R.id.content2);
			holder.content1 = (TextView) convertView
					.findViewById(R.id.content1);
			 
		 	holder.item_et = (EditText) convertView.findViewById(R.id.item_et);
			holder.rb = (RatingBar) convertView.findViewById(R.id.si_rt_msxf);
			holder.rb.setTag(position);
			 
			convertView.setTag(holder);
		} else {
			 
			holder = (ViewHolder) convertView.getTag();
		}
		holder.item_et.setTag(position);
		if(list.get(position).getScore()==null){
			list.get(position).setScore(50+"");
		}else{
			list.get(position).setScore(list.get(position).getScore());
		}
		  
	 	list.get(position).setContent(list.get(position).getContent());
		holder.content.setText(list.get(position).getGood_name());
		holder.content2.setText(list.get(position).getGood_brand());
		holder.content1.setText(list.get(position).getGood_channel());
		holder.rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				System.out.println(arg0.getTag() + "--`onRatingChanged`--"
						+ arg1);
				int index = Integer.parseInt(arg0.getTag().toString());
				 
				list.get(index).setScore((int)arg1 * 10 + "");

			}
		});
		holder.item_et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				System.out.println("```"+position+"--->"+arg0.toString());
				list.get(position).setContent(arg0.toString());
			}
		});

		return convertView;
	}

	public final class ViewHolder {
		public TextView content, content1, content2;
		public EditText item_et;
		public RatingBar rb;
	}
}
