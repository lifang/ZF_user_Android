package com.example.zf_zandroid.adapter;

import java.util.List;

import com.example.zf_android.R;
import com.example.zf_android.entity.TestEntitiy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ShopcarAdapter extends BaseAdapter {
	private Context context;
	private List<TestEntitiy> list;
	private LayoutInflater inflater;
	private ViewHolder holder = null;

	public ShopcarAdapter(Context context, List<TestEntitiy> list) {
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
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.sopping_caritem, null);
			holder.content = (TextView) convertView
					.findViewById(R.id.content_pp);
			// holder.title = (TextView) convertView.findViewById(R.id.title);
			// holder.evevt_img = (ImageView)
			// convertView.findViewById(R.id.evevt_img);
			holder.editView = (TextView) convertView
					.findViewById(R.id.editView);
			holder.editView.setOnClickListener(onClick);
			holder.ll_select = (LinearLayout) convertView
					.findViewById(R.id.ll_select);
			holder.editView.setTag(holder);
			holder.reduce = convertView.findViewById(R.id.reduce);
			holder.buyCountEdit = (EditText) convertView
					.findViewById(R.id.buyCountEdit);
			holder.add = convertView.findViewById(R.id.add);

			holder.reduce.setTag(holder);
			holder.add.setTag(holder);

			holder.reduce.setOnClickListener(onClick);
			holder.add.setOnClickListener(onClick);
			holder.cost = convertView.findViewById(R.id.cost);
			holder.delete = convertView.findViewById(R.id.delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content.setText(list.get(position).getContent());
		return convertView;
	}

	private OnClickListener onClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ViewHolder hoder = (ViewHolder) v.getTag();
			switch (v.getId()) {
			case R.id.editView:
				LinearLayout ll_select = hoder.ll_select;
				boolean isEdit = ll_select.getVisibility() == View.VISIBLE ? true
						: false;
				hoder.delete.setVisibility(isEdit ? View.INVISIBLE
						: View.VISIBLE);
				hoder.ll_select.setVisibility(isEdit ? View.INVISIBLE
						: View.VISIBLE);
				hoder.cost.setVisibility(isEdit ? View.VISIBLE
						: View.INVISIBLE);
				hoder.editView.setText(isEdit ? "±à¼­" : "Íê³É");

				break;

			case R.id.delete:
				// do delete
				break;
			case R.id.reduce:
				int now1 = Integer.parseInt(hoder.buyCountEdit.getText().toString());
				if (now1 > 0) {
					hoder.buyCountEdit.setText(--now1+"");
				}
				break;
			case R.id.add:
				int now2 = Integer.parseInt(hoder.buyCountEdit.getText().toString());
				hoder.buyCountEdit.setText(++now2+"");
				break;

			}

		}
	};

	public final class ViewHolder {
		public TextView content;
		/*** ±à¼­°´Å¥ ***/
		private TextView editView;
		private LinearLayout ll_select;
		private View cost;
		private View delete;
		private EditText buyCountEdit;
		private View reduce;
		private View add;
	}
}
