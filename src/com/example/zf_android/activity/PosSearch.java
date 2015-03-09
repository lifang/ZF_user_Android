package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
 

import com.example.zf_android.R;
import com.example.zf_zandroid.adapter.SearchAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

 

 

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
 
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
 
import android.widget.Button;
import android.widget.EditText;
 
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
 
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
/***
 *   搜索
 * @author Lijinpeng
 *
 * comdo
 */
public class PosSearch extends Activity implements OnEditorActionListener {
	private String lat, lng, CName;
	private EditText et;
	private ListView lv;
	private LinearLayout search_linear_yuyin, search_linear_delete;
	private static int REQUEST_CODE = 0;
	private Button back, titleright, etsbtn_clear;
 
	private SharedPreferences mySharedPreferences = null;
	private Editor editor;
	private int a = 0;
	private RelativeLayout ets_rl_r1, ets_rl_clear;
	private TextView tvtv, ets_histvshow, ml_maplocation;
	String poiStr = "",sessionId,sign;// 搜索记录
	List<String> data = new ArrayList<String>();
	List<String> key = new ArrayList<String>();
	List<String> city = new ArrayList<String>();
	private String sss = "没有数据";
	private String destinationLat, destinationLng;
	private SearchAdapter searchAdapter;
	private LinearLayout eva_nodata;
	private long merchantId;
 
	private int currentPage =1;
	private String name;
	private boolean onRefresh_number = true;
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			case 0:
 
				break;
			case 1:


				searchAdapter.notifyDataSetChanged();
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						 getData(data.get(arg2));
						CName = data.get(arg2);
						if (!poiStr.contains(CName)) {
							if (poiStr != null && !poiStr.equals("")) {
								poiStr += "," + CName;
							} else {
								poiStr += CName;
							}
							editor.putString("poiStr", poiStr);
						}
						editor.commit();
						sss = key.get(arg2);
			 

						System.out.println("模糊选择的地方 Name``" + sss
								 ) ;
				 
					}

				});
			
				break;
			case 2: // 网络有问题
				Toast.makeText(getApplicationContext(),"网络问题",
						Toast.LENGTH_SHORT).show();
				break;
			case 3:
	 
				break;
			case 4:
			 
				break;
			}
			
			 
		}
	};
	
	
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pos_search);
		mySharedPreferences = getSharedPreferences("pos_search", MODE_PRIVATE);
		editor = mySharedPreferences.edit();
		et = (EditText) findViewById(R.id.serch_edit);
		et.setOnEditorActionListener(this);
	 
		poiStr = mySharedPreferences.getString("poiStr", "");

		lv = (ListView) findViewById(R.id.lv);
		searchAdapter = new SearchAdapter(PosSearch.this, data);
		lv.setAdapter(searchAdapter);

	 
 
	 
		if (poiStr == "" || poiStr == null) {
			// 没有历史记录 下面是刷新界面跳转代码
	 
			data.add("没有搜索记录");
			

		} else {
			System.out.println("加载历史记录··4··");
 
			if (poiStr.contains(",")) {
				String[] serach = poiStr.split(",");
				for (int i = (serach.length - 1); i >= 0; i--) {

					if (a == 7) {
						break;// 历史记录显示7条
					}
					data.add(serach[i]);
					a++;
				}
				data.add("清除搜索记录");

			} else {
				data.add(poiStr);
				data.add("清除搜索记录");
			}
		 
		 

			searchAdapter.notifyDataSetChanged();
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					if (data.get(arg2).endsWith("没有搜索记录")) {

						// Toast.makeText(EditTextSearch.this, "请在输入框输入正确条件",
 
					} else if(data.get(arg2).endsWith("清除搜索记录")){
						 DeletaData();
					} else {
						// 判断历史记录是否 需要添加
						CName = data.get(arg2);
						et.setText(CName);
						getData(data.get(arg2));
 
					}
					
				}
			});
		}

		ml_maplocation = (TextView) findViewById(R.id.delete);
		ml_maplocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
			 finish();
 
			}
		});
 

	 
	}
	//add
	public void addData(String name){
		 if (!poiStr.contains(name)) {
			 if (poiStr != null && !poiStr.equals("")) {
			 poiStr += "," + name;
			 } else {
			 poiStr += name;
			 }
			 editor.putString("poiStr", poiStr);
			 editor.commit();
			 }  
	}
 

 
	// 删除记录
	public void DeletaData(){
		editor = mySharedPreferences.edit();
		editor.putString("poiStr", "");
		editor.commit();// 提交
		data.clear();
		data.add("没有搜索记录");
		searchAdapter.notifyDataSetChanged();
	}
 
	
	
	private void getData(String name) { 
		
		
	}
	
	
 
 
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		name = et.getText().toString();
		System.out.println(" content111"+name);
		if(actionId==EditorInfo.IME_ACTION_DONE){
			System.out.println(" content221"+name);
		}
	//	addData(name);
		System.out.println("---"+actionId);
		switch (actionId) {
		case 0:
			name = et.getText().toString();
			System.out.println(" content"+name);
			addData(name);
			 
			Intent intent2 = new Intent();
			intent2.putExtra("text", name);
			PosSearch.this.setResult(2, intent2);
			finish();
		  
			return true;
		 
		}

		return false;
	
	 
	}
}
