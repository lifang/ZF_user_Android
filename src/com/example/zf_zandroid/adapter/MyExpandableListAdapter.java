package com.example.zf_zandroid.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zf_android.R;
import com.example.zf_android.entity.PrePosItem;

public class MyExpandableListAdapter extends BaseExpandableListAdapter{

	List<PrePosItem> list;
	private Context context;
	private LayoutInflater child_inflater;
	private LayoutInflater menu_inflater;
	private menuView menuview = null;
	private chlidView childview = null;

	public MyExpandableListAdapter(Context context, List<PrePosItem> list
			) {
		//	this.onClick = onClick;
		this.context = context;
		this.list = list;

	}

	@Override
	public int getGroupCount() {
		return list.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return list.get(groupPosition).getSon().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getSon().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		menu_inflater = LayoutInflater.from(context);
		if (convertView == null) {
			menuview = new menuView();
			convertView = menu_inflater
					.inflate(R.layout.expand_menu, null);

			menuview.mune_title = (TextView) convertView
					.findViewById(R.id.mune_title);	
			menuview.item_cb = (CheckBox) convertView
					.findViewById(R.id.cb_all);	

			menuview.mune_img=(ImageView) convertView.findViewById(R.id.mune_img);
			convertView.setTag(menuview);
		} else {
			menuview = (menuView) convertView.getTag();
		}

		menuview.item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				list.get(groupPosition).setIsCheck(arg1);
				if(arg1){

					for(int i =0;i<list.get(groupPosition).getSon().size();i++){
						list.get(groupPosition).getSon().get(i).setIsCheck(arg1);
					}
					notifyDataSetChanged();}
			}
		});

		if(list.get(groupPosition).getIsCheck()==null){
			menuview.item_cb.setChecked(false);
			list.get(groupPosition).setIsCheck(false);
		}else{
			menuview.item_cb.setChecked(list.get(groupPosition).getIsCheck());
		}

		if(list.get(groupPosition).getSon().size()==0||list.get(groupPosition).getSon().size()==1 ){
			menuview.mune_img.setVisibility(View.GONE);
		}else{

			if(isExpanded){
				menuview.mune_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.top));
			}else{
				menuview.mune_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.item_down));
			}
			menuview.mune_img.setVisibility(View.VISIBLE);
		}
		menuview.mune_title.setText(list.get(groupPosition).getValue());
		System.out.println("list.get(groupPosition).getDirectoryName()"+list.get(groupPosition).getValue());
		//menuview.setOn
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView1, ViewGroup parent) {

		convertView1 = menu_inflater.inflate(R.layout.expand_menu, null); 
		TextView tv =(TextView) convertView1.findViewById(R.id.mune_title);
		ImageView IVV=(ImageView) convertView1.findViewById(R.id.mune_img);
		CheckBox cb=(CheckBox) convertView1.findViewById(R.id.cb_all);
		RelativeLayout ll= (RelativeLayout) convertView1.findViewById(R.id.menu_rl_c);
		IVV.setVisibility(View.GONE);
		ll.setBackgroundColor(context.getResources().getColor(R.color.bg0etitle));
		//	tv.setPadding(50,  0, 0,  0);
		tv.setText(list.get(groupPosition).getSon().get(childPosition).getValue());


		if(list.get(groupPosition).getIsCheck()==null){
			menuview.item_cb.setChecked(false);
			list.get(groupPosition).setIsCheck(false);
		}else{
			menuview.item_cb.setChecked(list.get(groupPosition).getIsCheck());
		}

		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				list.get(groupPosition).getSon().get(childPosition).setIsCheck(arg1);
			}
		});

		if( list.get(groupPosition).getSon().get(childPosition).getIsCheck()==null){
			cb.setChecked(false);
			list.get(groupPosition).getSon().get(childPosition).setIsCheck(false);
		}else{
			cb.setChecked( list.get(groupPosition).getSon().get(childPosition).getIsCheck());
		}

		return convertView1;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	final static class menuView {
		public TextView mune_title;
		public ImageView mune_img;
		public CheckBox item_cb;

	}
	final static class chlidView {
		public TextView expand_child_tv;
	}
}
