package com.example.zf_android.trade;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.examlpe.zf_android.util.StringUtil;
import com.example.zf_android.R;
import com.example.zf_android.trade.common.CommonUtil;
import com.example.zf_android.trade.entity.City;
import com.example.zf_android.trade.entity.Province;

public class CitySearchActivity extends Activity implements OnClickListener{

	private LinearLayout titleback_linear_back;
	private EditText searchEditText;
	private LinearLayout linear_deletename;
	private TextView next_cancel;

	private ListView mListView;
	private LinearLayout eva_nodata;

	private List<City> mCities = new ArrayList<City>();
	private List<City> searchCities = new ArrayList<City>();

	private CitySearchAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_citysearch_list);
		initView();
	}

	private void initView() {
		titleback_linear_back = (LinearLayout) findViewById(R.id.titleback_linear_back);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		linear_deletename = (LinearLayout) findViewById(R.id.linear_deletename);
		next_cancel = (TextView) findViewById(R.id.next_cancel);

		mListView = (ListView) findViewById(R.id.mListView);
		eva_nodata = (LinearLayout) findViewById(R.id.eva_nodata);


		titleback_linear_back.setOnClickListener(this);
		linear_deletename.setOnClickListener(this);
		next_cancel.setOnClickListener(this);
		adapter = new CitySearchAdapter();
		mListView.setAdapter(adapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Constants.CITY_ID_SEARCH = searchCities.get(position).getId();
				Constants.CITY_NAME_SEARCH = searchCities.get(position).getName();
				finish();
				if (CitySelectActivity.CitySelectActivity != null) {
					CitySelectActivity.CitySelectActivity.finish();
				}
			}
		});

		searchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s.length() > 0) {
					linear_deletename.setVisibility(View.VISIBLE);
				} else {
					linear_deletename.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		searchEditText.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {

				if (!StringUtil.isNull(searchEditText.getText().toString())) {
					initCities();
				}else {
					Toast.makeText(CitySearchActivity.this,"请输入关键词",
							Toast.LENGTH_SHORT).show();
				}

				// 关闭软键盘
				hideSoftKeyboard(CitySearchActivity.this);

				return false;
			}
		});
	}
	//隐藏软键盘
	public static void hideSoftKeyboard(Activity activity){
		View view = activity.getWindow().peekDecorView(); 
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titleback_linear_back:
			finish();
			break;
		case R.id.linear_deletename:
			searchEditText.setText("");
			break;
		case R.id.next_cancel:
			finish();
			break;
		default:
			break;
		}
	}
	private void initCities() {
//		new Thread() {
//			@Override
//			public void run() {
				mCities.clear();
				searchCities.clear();
				List<Province> provinces = CommonUtil.readProvincesAndCities(CitySearchActivity.this);
				for (Province province : provinces) {
					List<City> cities = province.getCities();
					mCities.addAll(cities);
				}

				for (int i = 0; i < mCities.size(); i++) {
					if (mCities.get(i).getName().contains(searchEditText.getText().toString())) {
						searchCities.add(mCities.get(i));
					}
				}
				//去除重复元素
				if (searchCities.size()>1) {
					for(int i = 0 ; i < searchCities.size() - 1;i++){ 
						for(int j = searchCities.size() - 1 ; j > i;j--){ 
							if(searchCities.get(j).equals(searchCities.get(i))){ 
								searchCities.remove(j); 
							} 
						} 
					} 
				}
				if (searchCities.size()>0) {
					adapter.notifyDataSetChanged();
				}
		//	}
		//}.start();

	}
	public class CitySearchAdapter extends BaseAdapter {
		LayoutInflater inflater = LayoutInflater.from(CitySearchActivity.this);
		@Override
		public int getCount() {
			return searchCities.size();
		}

		@Override
		public Object getItem(int position) {
			return searchCities.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;

			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.item_list_city_search,
						parent, false);
				viewHolder.nameTextView = (TextView) convertView
						.findViewById(R.id.nameTextViews);
				viewHolder.type_pop = (RelativeLayout) convertView
						.findViewById(R.id.type_pop);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.nameTextView.setText(searchCities.get(position).getName());
			return convertView;
		}

		private class ViewHolder {
			public RelativeLayout type_pop;
			public TextView nameTextView;
			TextView tv;
		}
	}
}
