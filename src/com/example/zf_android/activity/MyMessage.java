package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.examlpe.zf_android.util.TitleMenuUtil;
import com.examlpe.zf_android.util.Tools;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.Config;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.MessageEntity;
import com.example.zf_android.trade.API;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.common.HttpCallback;
import com.example.zf_android.trade.common.Pageable;
import com.example.zf_android.trade.widget.XListView;
import com.example.zf_android.trade.widget.XListView.IXListViewListener;
import com.example.zf_zandroid.adapter.MessageAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/***
 * 
 * 
 * � 我的消息
 * 
 * @version
 * 
 */
public class MyMessage extends BaseActivity implements IXListViewListener,
OnClickListener {
	private XListView Xlistview;
	private int page = 1;
	private RelativeLayout rl_sy, main_rl_gwc, rl_wd;
	private int rows = Config.ROWS;
	private LinearLayout eva_nodata;
	private String ids[]=new String []{};
	List<Integer> as = new ArrayList<Integer>();
	List<String> Stringas = new ArrayList<String>();
	JSONArray a;
	private MessageAdapter myAdapter;
	private LinearLayout titleback_linear_back;
	private TextView next_sure,tv_all,tv_dle;
	private int total = 0;
	private TextView countShopCar;
	private RelativeLayout rl_edit, rl_editno;
	List<MessageEntity> idList = new ArrayList<MessageEntity>();
	List<MessageEntity> myList = new ArrayList<MessageEntity>();
	List<MessageEntity> moreList = new ArrayList<MessageEntity>();
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				onLoad();

				if (myList.size() == 0) {
					Xlistview.setVisibility(View.GONE);
					eva_nodata.setVisibility(View.VISIBLE);
				}else {
					Xlistview.setVisibility(View.VISIBLE);
					eva_nodata.setVisibility(View.GONE);
				}
				myAdapter.notifyDataSetChanged();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), (String) msg.obj,
						Toast.LENGTH_SHORT).show();

				break;
			case 2: // ����������
				Toast.makeText(getApplicationContext(),
						"no 3g or wifi content", Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), " refresh too much",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_message);
		MyApplication.getInstance().addActivity(this);
		MyApplication.setIsSelect(false);
		initView();
		getData();

	}
	@Override
	protected void onResume() {
		super.onResume();
		if (Config.countShopCar != 0) {
			countShopCar.setVisibility(View.VISIBLE);
			countShopCar.setText(Config.countShopCar+"");
		}else {
			countShopCar.setVisibility(View.GONE);
		}
	}
	private void initView() {
		countShopCar = (TextView) findViewById(R.id.countShopCar);
		rl_edit = (RelativeLayout) findViewById(R.id.rl_edit);
		rl_editno = (RelativeLayout) findViewById(R.id.rl_editno);
		tv_all=(TextView) findViewById(R.id.tv_all);
		tv_dle=(TextView) findViewById(R.id.tv_dle);
		tv_all.setOnClickListener(this);
		tv_dle.setOnClickListener(this);
		rl_wd = (RelativeLayout) findViewById(R.id.main_rl_my);
		rl_wd.setOnClickListener(this);
		main_rl_gwc = (RelativeLayout) findViewById(R.id.main_rl_gwc);
		main_rl_gwc.setOnClickListener(this);
		rl_sy = (RelativeLayout) findViewById(R.id.main_rl_sy);
		rl_sy.setOnClickListener(this);

		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		titleback_linear_back.setVisibility(View.GONE);
		next_sure = (TextView) findViewById(R.id.next_sure);
		next_sure.setVisibility(View.VISIBLE);
		next_sure.setText("编辑");
		new TitleMenuUtil(MyMessage.this, "我的消息").show();
		myAdapter = new MessageAdapter(MyMessage.this, myList);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);
		Xlistview = (XListView) findViewById(R.id.x_listview);
		Xlistview.initHeaderAndFooter();
		Xlistview.setPullLoadEnable(true);
		Xlistview.setXListViewListener(this);
		Xlistview.setDivider(null);

		Xlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(MyMessage.this, MymsgDetail.class);
				i.putExtra("id", myList.get(position-1).getId()+"");
				startActivityForResult(i, 101);
			}
		});
		Xlistview.setAdapter(myAdapter);

		next_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (MyApplication.getIsSelect()) {
					next_sure.setText("编辑");
					MyApplication.setIsSelect(false);
					myAdapter.notifyDataSetChanged();

					rl_editno.setVisibility(View.VISIBLE);
					rl_edit.setVisibility(View.GONE);

				} else {
					next_sure.setText("取消");
					MyApplication.setIsSelect(true);
					rl_edit.setVisibility(View.VISIBLE);
					rl_editno.setVisibility(View.GONE);
					myAdapter.notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public void onRefresh() {
		page = 1;
		myList.clear();
		Xlistview.setPullLoadEnable(true);
		getData();
	}

	@Override
	public void onLoadMore() {
		if (myList.size() >= total) {
			Xlistview.setPullLoadEnable(false);
			Xlistview.stopLoadMore();
			CommonUtil.toastShort(this, "no more data");
		} else {
			getData();
		}

	}

	private void onLoad() {
		Xlistview.stopRefresh();
		Xlistview.stopLoadMore();
		Xlistview.setRefreshTime(Tools.getHourAndMin());
	}

	public void buttonClick() {
		page = 1;
		myList.clear();
		getData();
	}

	private void getData() {

		API.receiverGetAll(this, MyApplication.getInstance().getCustomerId(), page, rows, 
				new HttpCallback<Pageable<MessageEntity>>(this) {
			@Override
			public void onSuccess(Pageable<MessageEntity> data) {

				if (null != data.getContent()) {
					myList.addAll(data.getContent());
				}
				page++;

				total = data.getTotal();
				if(total<rows){
					Xlistview.setPullLoadEnable(false);
				}
				handler.sendEmptyMessage(0);
			}

			@Override
			public TypeToken<Pageable<MessageEntity>> getTypeToken() {
				return new TypeToken<Pageable<MessageEntity>>() {
				};
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tv_all: 
			for (int i = 0; i < myList.size(); i++) {

				if (myList.get(i).getIscheck()) {
					idList.add(myList.get(i));
				}
			}

			if (idList.size() > 0) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(
						MyMessage.this);
				final AlertDialog dialog = builder.create();
				builder.setTitle("提示");
				builder.setMessage("确定要删除吗？");
				builder.setPositiveButton("确认",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								
								MsgRead();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								dialog.dismiss();
							}

						});

				builder.create().show();
				
			}else {
				Toast.makeText(this, "请选择消息后，再执行此操作", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.tv_dle: 

			for (int i = 0; i < myList.size(); i++) {
				if (myList.get(i).getIscheck()) {
					Stringas.add(myList.get(i).getId());
				}
			}

			if (Stringas.size() > 0) {
				Msgdelete1();
			}else {
				Toast.makeText(this, "请选择消息后，再执行此操作", Toast.LENGTH_SHORT).show();
			}

			break;

		case R.id.main_rl_gwc: 
			startActivity(new Intent(MyMessage.this, ShopCar.class));
			finish();
			break;
		case R.id.main_rl_my: 
			startActivity(new Intent(MyMessage.this, MenuMine.class));
			finish();
			break;
		case R.id.main_rl_sy:  
			startActivity(new Intent(MyMessage.this, Main.class));
			finish();
			break;
		default:
			break;
		}
	}
	/*
	 * 批量标记已读
	 */
	private void MsgRead() {

		ids=new String[idList.size()];
		for (int i = 0; i < idList.size(); i++) {
			ids[i]=idList.get(i).getId();
		}

		Gson gson = new Gson();

		try {
			API.batchRead(this,MyApplication.getInstance().getCustomerId(),new JSONArray(gson.toJson(ids)),
					new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					page = 1;
					myList.clear();
					getData();

					next_sure.setText("编辑");
					MyApplication.setIsSelect(false);
					myAdapter.notifyDataSetChanged();

					rl_editno.setVisibility(View.VISIBLE);
					rl_edit.setVisibility(View.GONE);
				}
				@Override
				public TypeToken getTypeToken() {
					return null;
				}

			});
		} catch (JSONException e2) {
			e2.printStackTrace();
		}

	}
	/*
	 * 批量删除
	 */
	private void Msgdelete1() {
		Gson gson = new Gson();
		try {
			API.batchDelete(this,MyApplication.getInstance().getCustomerId(),new JSONArray(gson.toJson(Stringas)),
					new HttpCallback(this) {
				@Override
				public void onSuccess(Object data) {
					moreList.clear();
					for (int i = 0; i < myList.size(); i++) {

						if (myList.get(i).getIscheck()) {
							moreList.add(myList.get(i));
						}
					}
					myList.removeAll(moreList);

					myAdapter.notifyDataSetChanged();
					Toast.makeText(getApplicationContext(), "消息删除成功",
							Toast.LENGTH_SHORT).show();
				}
				@Override
				public TypeToken getTypeToken() {
					return null;
				}

			});
		} catch (JSONException e2) {
			e2.printStackTrace();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == 101) {
				String mflag = data.getStringExtra("flag");
				String idString = data.getStringExtra("id");
				if (mflag.equals("read")) {
					for (int i = 0; i < myList.size(); i++) {
						if (idString.equals(myList.get(i).getId())) {
							myList.get(i).setStatus(true);

						}
					}
				}else {
					for (int i = 0; i < myList.size(); i++) {
						if (idString.equals(myList.get(i).getId())) {
							myList.remove(i);
						}
					}
				}
				myAdapter.notifyDataSetChanged();
			}
		}
	}
}
