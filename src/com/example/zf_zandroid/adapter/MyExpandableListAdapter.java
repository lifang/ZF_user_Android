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
		if (list.get(groupPosition).getSon() != null) 
			return list.get(groupPosition).getSon().size();
		else 
			return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return list.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		if (list.get(groupPosition).getSon() != null) 
			return list.get(groupPosition).getSon().get(childPosition);
		else 
			return null;

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		if (list.get(groupPosition).getSon() != null) 
			return childPosition;
		else 
			return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		menu_inflater = LayoutInflater.from(context);
		menuview = new menuView();
		convertView = menu_inflater
				.inflate(R.layout.expand_menu, null);

		menuview.mune_title = (TextView) convertView
				.findViewById(R.id.mune_title);	
		menuview.item_cb = (CheckBox) convertView
				.findViewById(R.id.cb_all);	

		menuview.mune_img=(ImageView) convertView.findViewById(R.id.mune_img);
		convertView.setTag(menuview);


		menuview.item_cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

				if (arg1 == true) {
					for (int i = 0; i < list.size(); i++) {
						if (i == groupPosition) {
							list.get(i).setIsCheck(true);
						}else {
							list.get(i).setIsCheck(false);
						}
						if (list.get(i).getSon() != null) {
							for(int  j=0;j<list.get(i).getSon().size();j++){
								list.get(i).getSon().get(j).setIsCheck(false);
							}
						}
					}
				}else {
					for (int i = 0; i < list.size(); i++) {
						list.get(i).setIsCheck(false);
						if (list.get(i).getSon() != null) {

							for(int  j=0;j<list.get(i).getSon().size();j++){
								list.get(i).getSon().get(j).setIsCheck(false);
							}
						}
					}
				}
				list.get(groupPosition).setIsCheck(arg1);
				notifyDataSetChanged();

			}
		});

		if(list.get(groupPosition).getIsCheck()==null){
			menuview.item_cb.setChecked(false);
			list.get(groupPosition).setIsCheck(false);
		}else{
			menuview.item_cb.setChecked(list.get(groupPosition).getIsCheck());
		}

		if (list.get(groupPosition).getSon() != null) {
			if(list.get(groupPosition).getSon().size()==0){
				menuview.mune_img.setVisibility(View.GONE);
			}else{

				if(isExpanded){
					menuview.mune_img.setBackgroundResource(R.drawable.top);
				}else{
					menuview.mune_img.setBackgroundResource(R.drawable.item_down);
				}
				menuview.mune_img.setVisibility(View.VISIBLE);
			}
		}else {
			menuview.mune_img.setVisibility(View.GONE);
		}
		menuview.mune_title.setText(list.get(groupPosition).getValue());
		System.out.println("list.get(groupPosition).getDirectoryName()"+list.get(groupPosition).getValue());
		//menuview.setOn
		return convertView;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView1, ViewGroup parent) {
		if (list.get(groupPosition).getSon() != null) {

			convertView1 = menu_inflater.inflate(R.layout.expand_menu, null); 
			TextView tv =(TextView) convertView1.findViewById(R.id.mune_title);
			ImageView IVV=(ImageView) convertView1.findViewById(R.id.mune_img);
			CheckBox cb=(CheckBox) convertView1.findViewById(R.id.cb_all);
			RelativeLayout ll= (RelativeLayout) convertView1.findViewById(R.id.menu_rl_c);
			IVV.setVisibility(View.GONE);
			ll.setBackgroundColor(context.getResources().getColor(R.color.F3F2F2));
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

					if (arg1 == true) {
						for (int i = 0; i < list.size(); i++) {

							list.get(i).setIsCheck(false);
							if (list.get(i).getSon() != null) {
								for(int j =0;j<list.get(i).getSon().size();j++){
									if (i == groupPosition) {
										if (j == childPosition) {
											list.get(groupPosition).getSon().get(j).setIsCheck(true);
										}else {
											list.get(groupPosition).getSon().get(j).setIsCheck(false);
										}
									}else {
										list.get(i).getSon().get(j).setIsCheck(false);
									}

								}
							}
						}
					}else {
						for (int i = 0; i < list.size(); i++) {

							list.get(i).setIsCheck(false);
							if (list.get(i).getSon() != null) {
								for(int j =0;j<list.get(i).getSon().size();j++){
									list.get(i).getSon().get(j).setIsCheck(false);
								}
							}
						}
					}
					list.get(groupPosition).getSon().get(childPosition).setIsCheck(arg1);
					notifyDataSetChanged();

				}
			});

			if( list.get(groupPosition).getSon().get(childPosition).getIsCheck()==null){
				cb.setChecked(false);
				list.get(groupPosition).getSon().get(childPosition).setIsCheck(false);
			}else{
				cb.setChecked( list.get(groupPosition).getSon().get(childPosition).getIsCheck());
			}
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
