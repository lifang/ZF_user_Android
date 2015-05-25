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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.epalmpay.userPhone.R;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.entity.PosItem;
import com.example.zf_zandroid.adapter.PositmeAdapter;

public class PosSelecList extends BaseActivity implements OnClickListener{
	private ListView lv;
	private List<PosItem> mylist=new ArrayList<PosItem>();

	private PositmeAdapter myAdapter;
	private String title;
	ArrayList<Integer>  ids = new ArrayList<Integer>();
	private int index;
	private CheckBox cb_all;
	private LinearLayout all_Layout;
	private boolean isAllChoose = false;
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
		setContentView(R.layout.pos_selec_lv);
		initView();

		title=getIntent().getStringExtra("key");
		index=getIntent().getIntExtra("index", 0);
		tv_title.setText(title);

		switch (index) {
		case 100:
			mylist=MyApplication.pse.getBrands(); 
			break;
		case 101:

			break;
		case 102:
			mylist=MyApplication.pse.getPay_channel(); 
			break;
		case 103:
			mylist=MyApplication.pse.getPay_card(); 
			break;
		case 104:
			mylist=MyApplication.pse.getTrade_type(); 
			break;
		case 105:
			mylist=MyApplication.pse.getSale_slip(); 
			break;
		case 106:
			mylist=MyApplication.pse.gettDate(); 
			break;

		case 107:
			mylist=MyApplication.pse.getBrands(); 
			break;

		default:
			break;
		}

		int mflag = 0;
		if (mylist.size() != 0) {
			for(int i1 =0;i1<mylist.size();i1++){
				if (mylist.get(i1).getIsCheck() == null) {
					mylist.get(i1).setIsCheck(false);
				}

				if (mylist.get(i1).getIsCheck() == false) {
					isAllChoose = false;
					cb_all.setChecked(false);
				}else {
					mflag++;
				}
			}
			if (mflag == mylist.size()) {
				isAllChoose = true;
				cb_all.setChecked(true);
			}
		}
		myAdapter=new PositmeAdapter(PosSelecList.this, mylist);
		lv.setAdapter(myAdapter);
	}
	private void initView() {
		all_Layout = (LinearLayout) findViewById(R.id.all_Layout);
		tv_title=(TextView) findViewById(R.id.title);
		title1=(TextView) findViewById(R.id.delete1);
		title2=(TextView) findViewById(R.id.sure);
		title1.setOnClickListener(this);
		title2.setOnClickListener(this);
		all_Layout.setOnClickListener(this);
		lv=(ListView) findViewById(R.id.lv);

		cb_all=(CheckBox) findViewById(R.id.cb_all);
		//	cb_all.setEnabled(false);
		cb_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// 全部选择--
				isAllChoose = arg1;
				for(int i1 =0;i1<mylist.size();i1++){
					mylist.get(i1).setIsCheck(arg1);
				}
				handler.sendEmptyMessage(0);
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.all_Layout:
			if (isAllChoose == false) {
				isAllChoose = true;
				cb_all.setChecked(true);
				for(int i1 =0;i1<mylist.size();i1++){
					mylist.get(i1).setIsCheck(true);
				}
			}else {
				isAllChoose = false;
				cb_all.setChecked(false);
				for(int i1 =0;i1<mylist.size();i1++){
					mylist.get(i1).setIsCheck(false);
				}
			}
			handler.sendEmptyMessage(0);
			break;
		case R.id.delete1:
			finish();
			break;
		case R.id.sure:
			title="";
			for(int i1 =0;i1<mylist.size();i1++){
				if (mylist.get(i1).getIsCheck()==null) {
					mylist.get(i1).setIsCheck(false);
				}

				if(mylist.get(i1).getIsCheck()){
					if(title.equals("")){
						title=mylist.get(i1).getValue();
					}else{
						title=title+"."+mylist.get(i1).getValue();
					}
					ids.add(mylist.get(i1).getId());
					System.out.println("选择的ID--"+mylist.get(i1).getId());
				}
			}
			Intent intent2 = new Intent();
			intent2.putExtra("text", title);
			intent2.putIntegerArrayListExtra("ids", ids);
			PosSelecList.this.setResult(index, intent2);
			finish();
			break;
		default:
			break;
		}
	}
}
