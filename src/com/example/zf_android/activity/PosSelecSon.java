package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.PosItem;
import com.example.zf_android.entity.PrePosItem;
import com.example.zf_android.entity.TestEntitiy;
import com.example.zf_zandroid.adapter.MyExpandableListAdapter;
import com.example.zf_zandroid.adapter.PositmeAdapter;

public class PosSelecSon extends BaseActivity implements OnClickListener{
	private ExpandableListView lv;
	private List<PrePosItem> mylist=new ArrayList<PrePosItem>();;
	private MyExpandableListAdapter myAdapter;
	private String title;
	ArrayList<Integer>  ids = new ArrayList<Integer>();
	private int index;
	private CheckBox cb_all;
	private TextView tv_title,title1,title2;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("0-0-0-0-0-0-");
				myAdapter.notifyDataSetChanged();
				break;
			case 1:

				break;
			case 2: // 

				break;
			case 3:

				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pos_selec_son);
		initView();

		title=getIntent().getStringExtra("key");
		index=getIntent().getIntExtra("index", 0);
		tv_title.setText(title);
		mylist=MyApplication.pse.getCategory();

		lv.setGroupIndicator(null);
		lv.setDivider(null);
		lv.setChildDivider(getResources().getDrawable(R.drawable.dre0));
		myAdapter=new MyExpandableListAdapter(PosSelecSon.this, mylist);
		lv.setAdapter(myAdapter);
	}
	private void initView() {
		tv_title=(TextView) findViewById(R.id.title);
		title1=(TextView) findViewById(R.id.delete1);
		title2=(TextView) findViewById(R.id.sure);
		title1.setOnClickListener(this);
		title2.setOnClickListener(this);
		lv=(ExpandableListView) findViewById(R.id.lv);

		cb_all=(CheckBox) findViewById(R.id.cb_all);
		cb_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// 全部选择--
				for(int i1 =0;i1<mylist.size();i1++){
					mylist.get(i1).setIsCheck(arg1);
					if (mylist.get(i1).getSon() != null ) {
						for(int ss=0;ss<mylist.get(i1).getSon().size();ss++){
							mylist.get(i1).getSon().get(ss).setIsCheck(arg1);
						}
					}
				}
				handler.sendEmptyMessage(0);
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete1:
			finish();
			break;
		case R.id.sure:
			title="";
			for(int i1 =0;i1<mylist.size();i1++){

				if(mylist.get(i1).getIsCheck()==null){
					mylist.get(i1).setIsCheck(false);
				}
				if(mylist.get(i1).getIsCheck()){
					if(title.equals("")){
						title=mylist.get(i1).getValue();
					}else{
						title=title+"."+mylist.get(i1).getValue();
					}
					ids.add(mylist.get(i1).getId());
				}
				
				if (mylist.get(i1).getSon() != null) {
					for(int ss=0;ss<mylist.get(i1).getSon().size();ss++){
						if (mylist.get(i1).getSon().get(ss).getIsCheck()==null) {
							mylist.get(i1).getSon().get(ss).setIsCheck(false);
						}

						if(mylist.get(i1).getSon().get(ss).getIsCheck()){
							if(title.equals("")){
								title=mylist.get(i1).getSon().get(ss).getValue();
							}else{
								title=title+"."+mylist.get(i1).getSon().get(ss).getValue();
							}

							ids.add(mylist.get(i1).getSon().get(ss).getId());
							System.out.println("选择的ID--"+mylist.get(i1).getSon().get(ss).getId());
						}
					}
				}
			}
			Intent intent2 = new Intent();
			intent2.putExtra("text", title);
			intent2.putIntegerArrayListExtra("ids", ids);
			PosSelecSon.this.setResult(index, intent2);
			finish();

			break;
		default:
			break;
		}
	}
}
