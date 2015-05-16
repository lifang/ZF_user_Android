package com.example.zf_zandroid.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.R;
import com.example.zf_android.entity.Goodlist;

public class CommentAdapter extends BaseAdapter {
	private Context context;
	private List<Goodlist> list;
	private LayoutInflater inflater;
	private int state;
	private int editIngPosititon = 0;
	private AfterTextChangedListener afterTextChangedListener;
	private ViewHolder holder = null;
	
	private int selectPosition = -1;
	
	public int getSelectPosition() {
		return selectPosition;
	}
	public void setSelectPosition(int selectPosition) {
		this.selectPosition = selectPosition;
	}
	
	public CommentAdapter(Context context, List<Goodlist> list,AfterTextChangedListener afterTextChangedListener) {
		this.context = context;
		this.list = list;
		this.afterTextChangedListener = afterTextChangedListener;
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
		//if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.comment_item, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.content_pp);
			holder.contentText = (TextView) convertView
					.findViewById(R.id.contentText);
			holder.content2 = (TextView) convertView
					.findViewById(R.id.content2);
			holder.content1 = (TextView) convertView
					.findViewById(R.id.content1);

			holder.maxCountTextView = (TextView) convertView.findViewById(R.id.maxCountTextView);
			holder.item_et = (EditText) convertView.findViewById(R.id.item_et);
			holder.rb = (RatingBar) convertView.findViewById(R.id.si_rt_msxf);
			holder.rb.setTag(position);

//			convertView.setTag(holder);
//		} else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		holder.item_et.setTag(position);
		if(list.get(position).getScore()==null){
			list.get(position).setScore(50+"");
		}else{
			list.get(position).setScore(list.get(position).getScore());
		}

		//list.get(position).setContent(list.get(position).getContent());
		holder.content.setText(list.get(position).getGood_name());
		holder.content2.setText(list.get(position).getGood_brand());
		holder.contentText.setText(list.get(position).getGood_channel());

		list.get(position).setScore((int)5 * 10 + "");
		holder.rb.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
				System.out.println(arg0.getTag() + "--`onRatingChanged`--"
						+ arg1);
				int index = Integer.parseInt(arg0.getTag().toString());

				list.get(index).setScore((int)arg1 * 10 + "");

			}
		});
		if (editIngPosititon == position) {
			holder.item_et.setFocusable(true);
			holder.item_et.setFocusableInTouchMode(true);
			holder.item_et.requestFocus();
		}
		
		holder.item_et.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
		
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				editIngPosititon = position;
				list.get(position).setContent(arg0.toString());
				afterTextChangedListener.onAfterTextChanged(position);
			}
		});
	
		if (!StringUtil.isNull(list.get(position).getContent())) {
			holder.item_et.setText(list.get(position).getContent());
			holder.item_et.getText();
			holder.item_et.setSelection(list.get(position).getContent().length());
		}
		if (!StringUtil.isNull(list.get(position).getContent())) {
			if (list.get(position).getContent().length() > 0) {
				holder.maxCountTextView.setText("还可填写"+(200-list.get(position).getContent().length())+"个汉字");
			}else if (list.get(position).getContent().length() == 0) {
				holder.maxCountTextView.setText("最多填写200个汉字");
			}else if (list.get(position).getContent().length() > 200) {
				holder.maxCountTextView.setText("已超出允许最多字数");
			}
		}

		return convertView;
	}

	
	public interface AfterTextChangedListener {
		public void onAfterTextChanged(int position);
	}
	public final class ViewHolder {
		public TextView content, content1, content2,contentText,maxCountTextView;
		public EditText item_et;
		public RatingBar rb;
	}
	
	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}
}
