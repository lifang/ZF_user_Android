package com.example.zf_android.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.examlpe.zf_android.util.TitleMenuUtil;
import com.example.zf_android.BaseActivity;
import com.example.zf_android.MyApplication;
import com.example.zf_android.R;
import com.example.zf_android.entity.ChanelEntitiy;
import com.example.zf_zandroid.adapter.JiaoyiHuilvAdapter;
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
	private JiaoyiHuilvAdapter myAdapter;
	private List<ChanelEntitiy> celist = new ArrayList<ChanelEntitiy>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.act_tradereate);
		lv=(ListView) findViewById(R.id.lv);
		new TitleMenuUtil(TradeRate.this, "交易费率").show();
		ChanelEntitiy ce=new ChanelEntitiy();
		ce.setName("结算时间");
		ce.setService_rate( 10000);
		 
		celist.addAll(MyApplication.getCelist() );
		myAdapter =new  JiaoyiHuilvAdapter(TradeRate.this,celist);
		lv.setAdapter(myAdapter);
		//celist=MyApplication.getCelist();
	}
}
