package com.example.zf_android.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.zf_android.BaseActivity;
import com.example.zf_android.R;
import com.example.zf_zandroid.adapter.RateAdapter;
/**
 * 
*    
* 类名称：TradeRate   
* 类描述：   交易费率
* 创建人： ljp 
* 创建时间：2015-3-11 下午2:38:35   
* @version    
*
 */
public class TradeRate extends BaseActivity{
	private ListView lv;
	private RateAdapter myAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_tradereate);
		lv=(ListView) findViewById(R.id.lv);
	}
}
